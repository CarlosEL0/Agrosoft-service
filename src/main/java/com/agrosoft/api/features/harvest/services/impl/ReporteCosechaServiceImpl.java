package com.agrosoft.api.features.harvest.services.impl;

import com.agrosoft.api.features.ai_analysis.client.GroqClient;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqMessageDTO;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqRequestDTO;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqResponseDTO;
import com.agrosoft.api.features.ai_analysis.entities.AnalisisIa;
import com.agrosoft.api.features.ai_analysis.prompts.AiPromptProvider;
import com.agrosoft.api.features.ai_analysis.repositories.AnalisisIaRepository;
import com.agrosoft.api.features.crops.entities.CultivoEntity;
import com.agrosoft.api.features.crops.repositories.CultivoRepository;
import com.agrosoft.api.features.harvest.dto.ReporteCosechaRequestDTO;
import com.agrosoft.api.features.harvest.dto.ReporteCosechaResponseDTO;
import com.agrosoft.api.features.harvest.entities.ReporteCosecha;
import com.agrosoft.api.features.harvest.mappers.ReporteCosechaMapper;
import com.agrosoft.api.features.harvest.repositories.ReporteCosechaRepository;
import com.agrosoft.api.features.harvest.services.ReporteCosechaService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteCosechaServiceImpl implements ReporteCosechaService {

    private final CultivoRepository cultivoRepository;
    private final AnalisisIaRepository analisisIaRepository;

    private final ReporteCosechaRepository reporteCosechaRepository;
    private final ReporteCosechaMapper mapper;

    private final GroqClient groqClient;
    private final AiPromptProvider promptProvider;
    private final ObjectMapper objectMapper;

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.model}")
    private String model;

    @Override
    @Transactional
    public ReporteCosechaResponseDTO generarReporteCosecha(ReporteCosechaRequestDTO request) {

        // 1. Validar que no exista un reporte duplicado para este ciclo
        if (reporteCosechaRepository.existsByIdCiclo(request.getIdCiclo())) {
            throw new RuntimeException("Ya existe un reporte de cosecha para este ciclo agrícola.");
        }

        // 2. Obtener el contexto del cultivo
        CultivoEntity cultivo = cultivoRepository.findById(request.getIdCultivo())
                .orElseThrow(() -> new RuntimeException("Cultivo no encontrado"));



        // 3. Armar los Prompts para Groq
        String systemPrompt = promptProvider.getHarvestSystemPrompt();
        String userPrompt = promptProvider.buildHarvestUserPrompt(cultivo, request);

        // 4. Llamar a Groq
        GroqResponseDTO groqResponse = llamarGroq(systemPrompt, userPrompt);
        String jsonRespuestaGroq = groqResponse.getChoices().get(0).getMessage().getContent();

        // 5. Procesar JSON y Guardar en BD
        try {
            JsonNode rootNode = objectMapper.readTree(jsonRespuestaGroq);

            // Guardamos el "choro" general en AnalisisIa por consistencia histórica
            AnalisisIa analisisGuardado = analisisIaRepository.save(AnalisisIa.builder()
                    .idCultivo(cultivo.getIdCultivo())
                    .tipoAnalisis("reporte_cosecha")
                    .resultadoAnalisis(rootNode.get("resumenCiclo").asText())
                    .build());

            // Mapeamos lo que mandó el agricultor
            ReporteCosecha nuevoReporte = mapper.toEntity(request);
            nuevoReporte.setIdAnalisis(analisisGuardado.getId());

            // Inyectamos las métricas que calculó la IA
            nuevoReporte.setRendimientoEsperado(new BigDecimal(rootNode.get("rendimientoEsperado").asText()));
            nuevoReporte.setDesviacionRendimiento(new BigDecimal(rootNode.get("desviacionRendimiento").asText()));
            nuevoReporte.setEficienciaRiego(new BigDecimal(rootNode.get("eficienciaRiego").asText()));
            nuevoReporte.setCostoTotal(new BigDecimal(rootNode.get("costoTotal").asText()));
            nuevoReporte.setCostoPorKg(new BigDecimal(rootNode.get("costoPorKg").asText()));
            nuevoReporte.setResumenCiclo(rootNode.get("resumenCiclo").asText());
            nuevoReporte.setFactoresExito(rootNode.get("factoresExito").asText());
            nuevoReporte.setAreasMejora(rootNode.get("areasMejora").asText());
            nuevoReporte.setFechaGeneracion(java.time.LocalDateTime.now());

            // Guardamos el mega-reporte
            ReporteCosecha reporteGuardado = reporteCosechaRepository.save(nuevoReporte);

            return mapper.toResponseDTO(reporteGuardado);

        } catch (Exception e) {
            throw new RuntimeException("Error procesando el reporte de cosecha de IA: " + e.getMessage(), e);
        }
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
}