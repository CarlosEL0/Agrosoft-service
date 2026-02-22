package com.agrosoft.api.features.ai_analysis.services.impl;

import com.agrosoft.api.features.ai_analysis.client.GroqClient;
import com.agrosoft.api.features.ai_analysis.dto.AnalisisIaRequestDTO;
import com.agrosoft.api.features.ai_analysis.dto.AnalisisIaResponseDTO;
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
import com.agrosoft.api.features.ai_analysis.services.AiAnalysisService;
import com.agrosoft.api.features.crops.entities.CultivoEntity;
import com.agrosoft.api.features.crops.repositories.CultivoRepository;
import com.agrosoft.api.features.monitoring.entities.Irregularidad;
import com.agrosoft.api.features.monitoring.repositories.IrregularidadRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AiAnalysisServiceImpl implements AiAnalysisService {

    private final GroqClient groqClient;
    private final CultivoRepository cultivoRepository;
    private final IrregularidadRepository irregularidadRepository; // Ya existe porque hicimos el merge
    private final AnalisisIaRepository analisisIaRepository;
    private final RecomendacionRepository recomendacionRepository;
    private final AiPromptProvider promptProvider;
    private final ObjectMapper objectMapper;
    private final AiAnalysisMapper aiAnalysisMapper;

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.model}")
    private String model;

    @Override
    @Transactional
    public AnalisisIaResponseDTO generarAnalisis(AnalisisIaRequestDTO request) {

        // 1. OBTENER EL CULTIVO
        CultivoEntity cultivo = cultivoRepository.findById(request.getIdCultivo())
                .orElseThrow(() -> new RuntimeException("Cultivo no encontrado"));

        // 2. OBTENER LA PLAGA
        Irregularidad plaga = null;
        if (request.getIdIrregularidad() != null) {
            plaga = irregularidadRepository.findById(request.getIdIrregularidad())
                    .orElseThrow(() -> new RuntimeException("Irregularidad no encontrada"));
        }

        // 3. CONSTRUIR PROMPT E INYECTAR DATOS
        String systemPrompt = promptProvider.getSystemPrompt();
        String userPrompt = promptProvider.buildUserPrompt(cultivo, plaga, request);

        // 4. LLAMAR A GROQ
        GroqResponseDTO groqResponse = llamarGroq(systemPrompt, userPrompt);

        // 5. PROCESAR, GUARDAR Y MAPEAR RESPUESTA
        return procesarYGuardarRespuesta(groqResponse, cultivo, request);
    }

    private GroqResponseDTO llamarGroq(String systemPrompt, String userPrompt) {
        GroqRequestDTO groqRequest = GroqRequestDTO.builder()
                .model(model)
                .messages(List.of(
                        new GroqMessageDTO("system", systemPrompt),
                        new GroqMessageDTO("user", userPrompt)
                ))
                .temperature(0.2)
                .responseFormat(GroqRequestDTO.ResponseFormat.builder().type("json_object").build())
                .build();

        return groqClient.generarRespuesta("Bearer " + apiKey, groqRequest);
    }

    private AnalisisIaResponseDTO procesarYGuardarRespuesta(GroqResponseDTO groqResponse, CultivoEntity cultivo, AnalisisIaRequestDTO request) {
        String jsonRespuestaGroq = groqResponse.getChoices().get(0).getMessage().getContent();

        try {
            JsonNode rootNode = objectMapper.readTree(jsonRespuestaGroq);
            if (!rootNode.has("resultadoAnalisis")) {
                throw new RuntimeException("El JSON de la IA es inválido.");
            }

            // A. Guardar Análisis
            AnalisisIa analisisGuardado = analisisIaRepository.save(AnalisisIa.builder()
                    .idCultivo(cultivo.getIdCultivo())
                    .idIrregularidad(request.getIdIrregularidad())
                    .tipoAnalisis(request.getTipoAnalisis())
                    .resultadoAnalisis(rootNode.get("resultadoAnalisis").asText())
                    .build());

            // B. Guardar Recomendaciones
            List<Recomendacion> recomendacionesGuardadas = new ArrayList<>();
            if (rootNode.has("recomendaciones") && rootNode.get("recomendaciones").isArray()) {
                for (JsonNode recNode : rootNode.get("recomendaciones")) {
                    Recomendacion rec = recomendacionRepository.save(Recomendacion.builder()
                            .idAnalisis(analisisGuardado.getId())
                            .idCultivo(cultivo.getIdCultivo())
                            .titulo(recNode.get("titulo").asText())
                            .descripcion(recNode.get("descripcion").asText())
                            .prioridad(recNode.has("prioridad") ? recNode.get("prioridad").asText() : "media")
                            .aplicada(false)
                            .build());
                    recomendacionesGuardadas.add(rec);
                }
            }

            List<RecomendacionDTO> recomendacionesDTO = aiAnalysisMapper.toRecomendacionDTOList(recomendacionesGuardadas);
            return aiAnalysisMapper.toResponseDTO(analisisGuardado, recomendacionesDTO);

        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la IA: " + e.getMessage(), e);
        }
    }
}