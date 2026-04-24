package com.agrosoft.api.features.harvest.services.impl;

import com.agrosoft.api.features.ai_analysis.client.GroqClient;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqMessageDTO;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqRequestDTO;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqResponseDTO;
import com.agrosoft.api.features.ai_analysis.entities.AnalisisIa;
import com.agrosoft.api.features.ai_analysis.prompts.AiPromptProvider;
import com.agrosoft.api.features.ai_analysis.repositories.AnalisisIaRepository;
import com.agrosoft.api.features.care_events.repositories.EventoCuidadoRepository;
import com.agrosoft.api.features.crops.entities.Cultivo;
import com.agrosoft.api.features.crops.repositories.CultivoRepository;
import com.agrosoft.api.features.harvest.dto.ReporteCosechaRequestDTO;
import com.agrosoft.api.features.harvest.dto.ReporteCosechaResponseDTO;
import com.agrosoft.api.features.harvest.entities.ReporteCosecha;
import com.agrosoft.api.features.harvest.mappers.ReporteCosechaMapper;
import com.agrosoft.api.features.harvest.repositories.ReporteCosechaRepository;
import com.agrosoft.api.features.harvest.services.ReporteCosechaService;
import com.agrosoft.api.features.monitoring.repositories.IrregularidadRepository;
import com.agrosoft.api.shared.exceptions.BusinessRuleException;
import com.agrosoft.api.shared.exceptions.IntegrationException;
import com.agrosoft.api.shared.exceptions.ResourceNotFoundException;
import com.agrosoft.api.shared.utils.AiJson;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReporteCosechaServiceImpl implements ReporteCosechaService {

    private final CultivoRepository cultivoRepository;
    private final AnalisisIaRepository analisisIaRepository;
    private final ReporteCosechaRepository reporteCosechaRepository;
    private final ReporteCosechaMapper mapper;
    private final IrregularidadRepository irregularidadRepository;
    private final EventoCuidadoRepository eventoCuidadoRepository;
    private final GroqClient groqClient;
    private final AiPromptProvider promptProvider;
    private final ObjectMapper objectMapper;

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.model}")
    private String model;

    private static final double HARVEST_TEMPERATURE = 0.5;

    @Override
    @Transactional
    public ReporteCosechaResponseDTO generarReporteCosecha(ReporteCosechaRequestDTO request) {
        log.info("Iniciando generación de reporte de cosecha para ciclo: {}", request.getIdCiclo());

        validarReporteDuplicado(request.getIdCiclo());
        Cultivo cultivo = obtenerCultivo(request.getIdCultivo());
        
        String historialPlagas = construirHistorialPlagas(cultivo);
        String historialCuidados = construirHistorialCuidados(cultivo);
        
        logHistorialEnviado(historialPlagas, historialCuidados);

        String systemPrompt = promptProvider.getHarvestSystemPrompt();
        String userPrompt = promptProvider.buildHarvestUserPrompt(
            cultivo, request, historialPlagas, historialCuidados
        );

        GroqResponseDTO groqResponse = llamarGroq(systemPrompt, userPrompt);
        String jsonRespuestaGroq = groqResponse.getChoices().get(0).getMessage().getContent();

        log.debug("Respuesta cruda de Groq recibida: {}", jsonRespuestaGroq);

        return procesarYGuardarRespuesta(jsonRespuestaGroq, cultivo, request);
    }

    private void validarReporteDuplicado(java.util.UUID idCiclo) {
        if (reporteCosechaRepository.existsByIdCiclo(idCiclo)) {
            throw new BusinessRuleException("Ya existe un reporte de cosecha para este ciclo agrícola.");
        }
    }

    private Cultivo obtenerCultivo(java.util.UUID idCultivo) {
        return cultivoRepository.findById(idCultivo)
                .orElseThrow(() -> new ResourceNotFoundException("Cultivo no encontrado"));
    }

    private String construirHistorialPlagas(Cultivo cultivo) {
        return irregularidadRepository.findByIdCultivoOrderByFechaDeteccionDesc(cultivo.getIdCultivo())
                .stream()
                .map(p -> String.format("- %s (Severidad: %s, Daño: %s)", 
                    p.getNombrePlaga(), p.getSeveridad(), p.getNivelDano()))
                .collect(Collectors.joining("\n"));
    }

    private String construirHistorialCuidados(Cultivo cultivo) {
        return eventoCuidadoRepository.findByIdCultivoOrderByFechaEventoDesc(cultivo.getIdCultivo())
                .stream()
                .map(e -> String.format("- %s: %s", 
                    e.getTipoEvento().toUpperCase(), e.getDescripcion()))
                .collect(Collectors.joining("\n"));
    }

    private void logHistorialEnviado(String historialPlagas, String historialCuidados) {
        log.debug("========== HISTORIAL ENVIADO A LA IA ==========");
        log.debug("Plagas:\n{}", historialPlagas.isEmpty() ? "(vacío)" : historialPlagas);
        log.debug("Cuidados:\n{}", historialCuidados.isEmpty() ? "(vacío)" : historialCuidados);
        log.debug("===============================================");
    }

    private GroqResponseDTO llamarGroq(String systemPrompt, String userPrompt) {
        GroqRequestDTO groqRequest = GroqRequestDTO.builder()
                .model(model)
                .messages(List.of(
                        new GroqMessageDTO("system", systemPrompt),
                        new GroqMessageDTO("user", userPrompt)
                ))
                .temperature(HARVEST_TEMPERATURE)
                .responseFormat(GroqRequestDTO.ResponseFormat.builder().type("json_object").build())
                .build();

        return groqClient.generarRespuesta("Bearer " + apiKey, groqRequest);
    }

    private ReporteCosechaResponseDTO procesarYGuardarRespuesta(
            String jsonRespuestaGroq, 
            Cultivo cultivo, 
            ReporteCosechaRequestDTO request) {
        
        try {
            String jsonLimpio = AiJson.cleanJsonResponse(jsonRespuestaGroq);
            JsonNode rootNode = objectMapper.readTree(jsonLimpio);

            validarCamposObligatorios(rootNode);

            AnalisisIa analisisGuardado = guardarAnalisis(rootNode, cultivo);
            ReporteCosecha nuevoReporte = construirReporte(rootNode, request, analisisGuardado);
            ReporteCosecha reporteGuardado = reporteCosechaRepository.save(nuevoReporte);

            log.info("Reporte de cosecha generado exitosamente con ID: {}", reporteGuardado.getId());

            return mapper.toResponseDTO(reporteGuardado);

        } catch (Exception e) {
            log.error("Error procesando el reporte de cosecha de IA", e);
            throw new IntegrationException("Error procesando el reporte de cosecha de IA: " + e.getMessage(), e);
        }
    }

    private void validarCamposObligatorios(JsonNode rootNode) {
        if (!rootNode.has("resumenCiclo")) {
            throw new IntegrationException("El JSON de la IA no contiene el campo 'resumenCiclo'");
        }
        if (!rootNode.has("factoresExito")) {
            throw new IntegrationException("El JSON de la IA no contiene el campo 'factoresExito'");
        }
        if (!rootNode.has("areasMejora")) {
            throw new IntegrationException("El JSON de la IA no contiene el campo 'areasMejora'");
        }
    }

    private AnalisisIa guardarAnalisis(JsonNode rootNode, Cultivo cultivo) {
        return analisisIaRepository.save(AnalisisIa.builder()
                .idCultivo(cultivo.getIdCultivo())
                .tipoAnalisis("reporte_cosecha")
                .resultadoAnalisis(rootNode.get("resumenCiclo").asText())
                .build());
    }

    private ReporteCosecha construirReporte(
            JsonNode rootNode, 
            ReporteCosechaRequestDTO request, 
            AnalisisIa analisisGuardado) {
        
        ReporteCosecha nuevoReporte = mapper.toEntity(request);
        nuevoReporte.setIdAnalisis(analisisGuardado.getId());

        // Inyectar métricas calculadas por la IA
        nuevoReporte.setRendimientoEsperado(new BigDecimal(rootNode.get("rendimientoEsperado").asText()));
        nuevoReporte.setDesviacionRendimiento(new BigDecimal(rootNode.get("desviacionRendimiento").asText()));
        nuevoReporte.setEficienciaRiego(new BigDecimal(rootNode.get("eficienciaRiego").asText()));
        nuevoReporte.setCostoTotal(new BigDecimal(rootNode.get("costoTotal").asText()));
        nuevoReporte.setCostoPorKg(new BigDecimal(rootNode.get("costoPorKg").asText()));
        nuevoReporte.setResumenCiclo(rootNode.get("resumenCiclo").asText());

        // Extraer y validar factoresExito y areasMejora
        String factoresExito = extraerCampoTexto(rootNode, "factoresExito");
        String areasMejora = extraerCampoTexto(rootNode, "areasMejora");

        nuevoReporte.setFactoresExito(factoresExito);
        nuevoReporte.setAreasMejora(areasMejora);
        nuevoReporte.setFechaGeneracion(java.time.LocalDateTime.now());

        return nuevoReporte;
    }

    private String extraerCampoTexto(JsonNode rootNode, String nombreCampo) {
        if (!rootNode.has(nombreCampo)) {
            log.warn("El campo '{}' NO EXISTE en la respuesta de la IA", nombreCampo);
            return obtenerMensajeVacioFallback(nombreCampo);
        }

        JsonNode campo = rootNode.get(nombreCampo);

        if (campo.isArray()) {
            StringBuilder sb = new StringBuilder();
            for (JsonNode item : campo) {
                if (!sb.isEmpty()) {
                    sb.append("\n");
                }
                sb.append("- ").append(item.asText());
            }
            String resultado = sb.toString();

            if (resultado.trim().isEmpty()) {
                log.warn("El campo '{}' es un array vacío", nombreCampo);
                return obtenerMensajeVacioFallback(nombreCampo);
            }

            return resultado;
        }

        String valor = campo.asText();
        if (valor == null || valor.trim().isEmpty()) {
            log.warn("El campo '{}' está vacío en la respuesta de la IA", nombreCampo);
            return obtenerMensajeVacioFallback(nombreCampo);
        }

        return valor;
    }

    private String obtenerMensajeVacioFallback(String nombreCampo) {
        return String.format("No se pudo determinar %s específico con la información disponible.",
                nombreCampo.replace("factores", "factores de ").replace("areas", "áreas de "));
    }
}