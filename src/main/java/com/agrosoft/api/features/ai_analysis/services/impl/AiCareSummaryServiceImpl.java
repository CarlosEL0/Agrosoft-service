package com.agrosoft.api.features.ai_analysis.services.impl;

import com.agrosoft.api.features.ai_analysis.client.GroqClient;
import com.agrosoft.api.features.ai_analysis.dto.AnalisisIaResponseDTO;
import com.agrosoft.api.features.ai_analysis.dto.RecomendacionDTO;
import com.agrosoft.api.features.ai_analysis.dto.ResumenCuidadosRequestDTO;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqMessageDTO;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqRequestDTO;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqResponseDTO;
import com.agrosoft.api.features.ai_analysis.entities.AnalisisIa;
import com.agrosoft.api.features.ai_analysis.entities.Recomendacion;
import com.agrosoft.api.features.ai_analysis.mappers.AiAnalysisMapper;
import com.agrosoft.api.features.ai_analysis.prompts.AiPromptProvider;
import com.agrosoft.api.features.ai_analysis.repositories.AnalisisIaRepository;
import com.agrosoft.api.features.ai_analysis.repositories.RecomendacionRepository;
import com.agrosoft.api.features.ai_analysis.services.AiCareSummaryService;
import com.agrosoft.api.features.care_events.entities.EventoCuidado;
import com.agrosoft.api.features.care_events.repositories.EventoCuidadoRepository;
import com.agrosoft.api.features.crops.entities.Cultivo;
import com.agrosoft.api.features.crops.repositories.CultivoRepository;
import com.agrosoft.api.shared.exceptions.IntegrationException;
import com.agrosoft.api.shared.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiCareSummaryServiceImpl implements AiCareSummaryService {

    private final CultivoRepository cultivoRepository;
    private final EventoCuidadoRepository eventoCuidadoRepository;
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
    public AnalisisIaResponseDTO generarResumenCuidados(ResumenCuidadosRequestDTO request) {
        Cultivo cultivo = cultivoRepository.findById(request.getIdCultivo())
                .orElseThrow(() -> new ResourceNotFoundException("Cultivo no encontrado"));

        List<EventoCuidado> eventos = eventoCuidadoRepository.findAll();

        List<EventoCuidado> eventosDelCultivo = eventos.stream()
                .filter(e -> e.getIdCultivo().equals(request.getIdCultivo()))
                .filter(e -> {
                    if (request.getDiasRetroceso() == null) return true;
                    LocalDate fechaLimite = LocalDate.now().minusDays(request.getDiasRetroceso());
                    return !e.getFechaEvento().isBefore(fechaLimite);
                })
                .collect(Collectors.toList());

        String historialTexto = formatearHistorial(eventosDelCultivo);

        String systemPrompt = promptProvider.getCareSummarySystemPrompt();
        String userPrompt = promptProvider.buildCareSummaryUserPrompt(cultivo, historialTexto, request.getPreguntaAdicional());

        GroqRequestDTO groqRequest = GroqRequestDTO.builder()
                .model(model)
                .messages(List.of(
                        new GroqMessageDTO("system", systemPrompt),
                        new GroqMessageDTO("user", userPrompt)
                ))
                .temperature(0.2)
                .responseFormat(GroqRequestDTO.ResponseFormat.builder().type("json_object").build())
                .build();

        GroqResponseDTO groqResponse = groqClient.generarRespuesta("Bearer " + apiKey, groqRequest);
        String jsonRespuestaGroq = groqResponse.getChoices().get(0).getMessage().getContent();

        // 5. Procesar, Guardar y Mapear el Response
        return procesarYGuardarRespuesta(jsonRespuestaGroq, cultivo.getIdCultivo());
    }

    private String formatearHistorial(List<EventoCuidado> eventos) {
        if (eventos.isEmpty()) return "No se registraron cuidados.";

        StringBuilder sb = new StringBuilder();
        for (EventoCuidado e : eventos) {
            sb.append(String.format("- [%s] %s: %s (Obs: %s)\n",
                    e.getFechaEvento(),
                    e.getTipoEvento().toUpperCase(),
                    e.getDescripcion() != null ? e.getDescripcion() : "Sin descripción",
                    e.getObservaciones() != null ? e.getObservaciones() : "Ninguna"));
        }
        return sb.toString();
    }

    private AnalisisIaResponseDTO procesarYGuardarRespuesta(String json, java.util.UUID idCultivo) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);

            // A. Guardar Análisis
            AnalisisIa analisisGuardado = analisisIaRepository.save(AnalisisIa.builder()
                    .idCultivo(idCultivo)
                    .tipoAnalisis("resumen")
                    .resultadoAnalisis(rootNode.get("resultadoAnalisis").asText())
                    .build());

            // B. Guardar Recomendaciones
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

            // C. Mapear al ResponseDTO reutilizado
            List<RecomendacionDTO> recomendacionesDTO = aiAnalysisMapper.toRecomendacionDTOList(recomendacionesGuardadas);
            return aiAnalysisMapper.toResponseDTO(analisisGuardado, recomendacionesDTO);

        } catch (Exception e) {
            throw new IntegrationException("Error procesando IA: " + e.getMessage(), e);
        }
    }
}