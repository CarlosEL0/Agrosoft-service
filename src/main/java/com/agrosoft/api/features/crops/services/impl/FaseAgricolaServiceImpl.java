package com.agrosoft.api.features.crops.services.impl;

import com.agrosoft.api.features.ai_analysis.client.GroqClient;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqMessageDTO;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqRequestDTO;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqResponseDTO;
import com.agrosoft.api.features.ai_analysis.prompts.AiPromptProvider;
import com.agrosoft.api.features.crops.dto.FaseAgricolaRequestDTO;
import com.agrosoft.api.features.crops.entities.Cultivo;
import com.agrosoft.api.features.crops.entities.EtapaCrecimiento;
import com.agrosoft.api.features.crops.entities.FaseAgricola;
import com.agrosoft.api.features.crops.enums.EtapaPredeterminada;
import com.agrosoft.api.features.crops.mappers.FaseAgricolaMapper;
import com.agrosoft.api.features.crops.repositories.CultivoRepository;
import com.agrosoft.api.features.crops.repositories.EtapaCrecimientoRepository;
import com.agrosoft.api.features.crops.repositories.FaseAgricolaRepository;
import com.agrosoft.api.features.crops.services.FaseAgricolaService;
import com.agrosoft.api.shared.exceptions.ResourceNotFoundException;
import com.agrosoft.api.shared.utils.AiJson;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaseAgricolaServiceImpl implements FaseAgricolaService {

    private final FaseAgricolaRepository faseAgricolaRepository;
    private final FaseAgricolaMapper faseAgricolaMapper;
    private final EtapaCrecimientoRepository etapaRepository;
    private final CultivoRepository cultivoRepository;

    private final GroqClient groqClient;
    private final AiPromptProvider promptProvider;
    private final ObjectMapper objectMapper;

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.model}")
    private String model;

    @Override
    @Transactional
    public FaseAgricola crearFase(FaseAgricolaRequestDTO request) {
        FaseAgricola nuevaFase = faseAgricolaMapper.toEntity(request);
        FaseAgricola faseGuardada = faseAgricolaRepository.save(nuevaFase);

        Cultivo cultivo = cultivoRepository.findById(faseGuardada.getIdCultivo())
                .orElseThrow(() -> new ResourceNotFoundException("Cultivo no encontrado"));

        LocalDate fechaActual = faseGuardada.getFechaInicio();

        Map<String, Integer> diasPredichos = predecirDiasConIA(cultivo);

        for (EtapaPredeterminada etapaDefecto : EtapaPredeterminada.values()) {

            String claveEtapa = etapaDefecto.name().toLowerCase();
            int diasDuracion = diasPredichos.getOrDefault(claveEtapa, 15);

            LocalDate fechaFinEstimada = fechaActual.plusDays(diasDuracion);

            EtapaCrecimiento nuevaEtapa = EtapaCrecimiento.builder()
                    .idCiclo(faseGuardada.getIdCiclo())
                    .nombreEtapa(etapaDefecto.getNombre())
                    .ordenEtapa(etapaDefecto.getOrden())
                    .fechaInicio(fechaActual)
                    .fechaFin(fechaFinEstimada)
                    .build();

            etapaRepository.save(nuevaEtapa);

            fechaActual = fechaFinEstimada;
        }

        return faseGuardada;
    }

    private Map<String, Integer> predecirDiasConIA(Cultivo cultivo) {
        String systemPrompt = promptProvider.getStagePredictionSystemPrompt();
        String userPrompt = promptProvider.buildStagePredictionUserPrompt(cultivo);

        GroqRequestDTO groqRequest = GroqRequestDTO.builder()
                .model(model)
                .messages(List.of(
                        new GroqMessageDTO("system", systemPrompt),
                        new GroqMessageDTO("user", userPrompt)
                ))
                .temperature(0.1)
                .responseFormat(GroqRequestDTO.ResponseFormat.builder().type("json_object").build())
                .build();

        try {
            GroqResponseDTO groqResponse = groqClient.generarRespuesta("Bearer " + apiKey, groqRequest);
            String jsonRespuesta = groqResponse.getChoices().get(0).getMessage().getContent();
            String jsonLimpio = AiJson.cleanJsonResponse(jsonRespuesta);
            JsonNode rootNode = objectMapper.readTree(jsonLimpio);

            Map<String, Integer> predicciones = new HashMap<>();
            predicciones.put("germinacion", rootNode.has("germinacion") ? rootNode.get("germinacion").asInt() : 10);
            predicciones.put("plantula", rootNode.has("plantula") ? rootNode.get("plantula").asInt() : 15);
            predicciones.put("crecimiento", rootNode.has("crecimiento") ? rootNode.get("crecimiento").asInt() : 20);
            predicciones.put("floracion", rootNode.has("floracion") ? rootNode.get("floracion").asInt() : 20);
            predicciones.put("cosecha", rootNode.has("cosecha") ? rootNode.get("cosecha").asInt() : 15);

            log.info("Predicción de IA exitosa para cultivo {}: {}", cultivo.getNombreCultivo(), predicciones);
            return predicciones;

        } catch (Exception e) {
            log.warn("Fallo al predecir días con IA para el cultivo {}. Usando días de respaldo. Error: {}", cultivo.getNombreCultivo(), e.getMessage());
            //Si Groq falla, devolvemos un mapa seguro para no detener la creación.
            Map<String, Integer> prediccionesDefault = new HashMap<>();
            prediccionesDefault.put("germinacion", 10);
            prediccionesDefault.put("plantula", 10);
            prediccionesDefault.put("crecimiento", 20);
            prediccionesDefault.put("floracion", 20);
            prediccionesDefault.put("cosecha", 15);
            return prediccionesDefault;
        }
    }

    @Override
    public List<FaseAgricola> obtenerPorCultivo(UUID idCultivo) {
        return faseAgricolaRepository.findByIdCultivoOrderByNumeroCicloAsc(idCultivo);
    }

    @Override
    public FaseAgricola obtenerPorId(UUID id) {
        return faseAgricolaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fase no encontrada con ID: " + id));
    }

    @Override
    public FaseAgricola actualizarFase(UUID id, FaseAgricolaRequestDTO request) {
        FaseAgricola faseExistente = obtenerPorId(id);
        faseAgricolaMapper.updateEntityFromDto(request, faseExistente);
        return faseAgricolaRepository.save(faseExistente);
    }

    @Override
    public void eliminarFase(UUID id) {
        obtenerPorId(id);
        faseAgricolaRepository.deleteById(id);
    }

    @Override
    public Map<String, Integer> predecirEtapas(String nombreCultivo, String tipoCultivo, String region) {
        Cultivo cultivoMock = Cultivo.builder()
                .nombreCultivo(nombreCultivo)
                .tipoCultivo(tipoCultivo)
                .region(region)
                .build();
        return predecirDiasConIA(cultivoMock);
    }
}