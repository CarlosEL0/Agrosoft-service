package com.agrosoft.api.features.ai_analysis.services.impl;

import com.agrosoft.api.features.ai_analysis.client.GroqClient;
import com.agrosoft.api.features.ai_analysis.dto.AnalisisIaResponseDTO;
import com.agrosoft.api.features.ai_analysis.dto.InterpretacionCrecimientoRequestDTO;
import com.agrosoft.api.features.ai_analysis.dto.RecomendacionDTO;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqMessageDTO;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqRequestDTO;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqResponseDTO;
import com.agrosoft.api.features.ai_analysis.entities.AnalisisIa;
import com.agrosoft.api.features.ai_analysis.entities.Recomendacion;
import com.agrosoft.api.features.ai_analysis.mappers.AiAnalysisMapper;
import com.agrosoft.api.features.ai_analysis.prompts.AiPromptProvider;
import com.agrosoft.api.features.ai_analysis.repositories.AnalisisIaRepository;
import com.agrosoft.api.features.ai_analysis.repositories.RecomendacionRepository;
import com.agrosoft.api.features.ai_analysis.services.AiGrowthInterpretationService;
import com.agrosoft.api.features.crops.entities.CultivoEntity;
import com.agrosoft.api.features.crops.repositories.CultivoRepository;
import com.agrosoft.api.features.monitoring.entities.RegistroCrecimiento;
import com.agrosoft.api.features.monitoring.repositories.RegistroCrecimientoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiGrowthInterpretationServiceImpl implements AiGrowthInterpretationService {

    private final CultivoRepository cultivoRepository;
    private final RegistroCrecimientoRepository registroCrecimientoRepository;
    private final AnalisisIaRepository analisisIaRepository;
    private final RecomendacionRepository recomendacionRepository;

    private final GroqClient groqClient;
    private final AiPromptProvider promptProvider;
    private final ObjectMapper objectMapper;
    private final AiAnalysisMapper aiAnalysisMapper;

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.model}")
    private String model;

    @Override
    @Transactional
    public AnalisisIaResponseDTO interpretarCrecimiento(InterpretacionCrecimientoRequestDTO request) {
        CultivoEntity cultivo = cultivoRepository.findById(request.getIdCultivo())
                .orElseThrow(() -> new RuntimeException("Cultivo no encontrado"));

        // 2. Obtener el historial de crecimiento (ya viene ordenado de más reciente a más antiguo por nuestro Repository)
        List<RegistroCrecimiento> registros = registroCrecimientoRepository.findByIdCultivoOrderByFechaRegistroDesc(request.getIdCultivo());

        // 3. Aplicar límite si el agricultor solo quiere evaluar los últimos N registros
        if (request.getLimiteRegistros() != null && request.getLimiteRegistros() > 0) {
            registros = registros.stream().limit(request.getLimiteRegistros()).collect(Collectors.toList());
        }

        String historialTexto = formatearHistorial(registros);

        String systemPrompt = promptProvider.getGrowthInterpretationSystemPrompt();
        String userPrompt = promptProvider.buildGrowthInterpretationUserPrompt(cultivo, historialTexto, request.getPreguntaAdicional());

        // 5. Llamar a Groq (JSON Mode)
        GroqRequestDTO groqRequest = GroqRequestDTO.builder()
                .model(model)
                .messages(List.of(
                        new GroqMessageDTO("system", systemPrompt),
                        new GroqMessageDTO("user", userPrompt)
                ))
                .temperature(0.2) // Temperatura baja para respuestas analíticas y precisas
                .responseFormat(GroqRequestDTO.ResponseFormat.builder().type("json_object").build())
                .build();

        GroqResponseDTO groqResponse = groqClient.generarRespuesta("Bearer " + apiKey, groqRequest);
        String jsonRespuestaGroq = groqResponse.getChoices().get(0).getMessage().getContent();

        // 6. Procesar, Guardar y Mapear el Response
        return procesarYGuardarRespuesta(jsonRespuestaGroq, cultivo.getIdCultivo());
    }

    private String formatearHistorial(List<RegistroCrecimiento> registros) {
        if (registros.isEmpty()) return "No se encontraron registros de crecimiento.";

        StringBuilder sb = new StringBuilder();
        for (RegistroCrecimiento r : registros) {
            sb.append(String.format("- [%s] Altura: %scm | Grosor Tallo: %scm | Diámetro: %scm | Salud: %s\n",
                    r.getFechaRegistro(),
                    r.getAlturaPlanta() != null ? r.getAlturaPlanta() : "N/A",
                    r.getGrosorTallo() != null ? r.getGrosorTallo() : "N/A",
                    r.getDiametro() != null ? r.getDiametro() : "N/A",
                    r.getEstadoSalud() != null ? r.getEstadoSalud() : "N/A"));
        }
        return sb.toString();
    }

    private AnalisisIaResponseDTO procesarYGuardarRespuesta(String json, java.util.UUID idCultivo) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);

            // Guardar Análisis en la BD
            AnalisisIa analisisGuardado = analisisIaRepository.save(AnalisisIa.builder()
                    .idCultivo(idCultivo)
                    .tipoAnalisis("interpretacion")
                    .resultadoAnalisis(rootNode.get("resultadoAnalisis").asText())
                    .build());

            // Guardar Recomendaciones en la BD
            List<Recomendacion> recomendacionesGuardadas = new ArrayList<>();
            if (rootNode.has("recomendaciones") && rootNode.get("recomendaciones").isArray()) {
                for (JsonNode recNode : rootNode.get("recomendaciones")) {
                    Recomendacion rec = recomendacionRepository.save(Recomendacion.builder()
                            .idAnalisis(analisisGuardado.getId())
                            .idCultivo(idCultivo)
                            .titulo(recNode.get("titulo").asText())
                            .descripcion(recNode.get("descripcion").asText())
                            .prioridad(recNode.has("prioridad") ? recNode.get("prioridad").asText() : "media")
                            .aplicada(false)
                            .build());
                    recomendacionesGuardadas.add(rec);
                }
            }

            // Convertir a DTO usando nuestro Mapper reutilizable
            List<RecomendacionDTO> recomendacionesDTO = aiAnalysisMapper.toRecomendacionDTOList(recomendacionesGuardadas);
            return aiAnalysisMapper.toResponseDTO(analisisGuardado, recomendacionesDTO);

        } catch (Exception e) {
            throw new RuntimeException("Error procesando IA de crecimiento: " + e.getMessage(), e);
        }
    }
}