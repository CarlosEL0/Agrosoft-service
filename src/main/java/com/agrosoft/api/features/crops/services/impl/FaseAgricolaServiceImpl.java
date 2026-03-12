package com.agrosoft.api.features.crops.services.impl;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FaseAgricolaServiceImpl implements FaseAgricolaService {

    private final FaseAgricolaRepository faseAgricolaRepository;
    private final FaseAgricolaMapper faseAgricolaMapper;
    private final EtapaCrecimientoRepository etapaRepository;
    private final CultivoRepository cultivoRepository;


    @Override
    @Transactional
    public FaseAgricola crearFase(FaseAgricolaRequestDTO request) {
        FaseAgricola nuevaFase = faseAgricolaMapper.toEntity(request);
        FaseAgricola faseGuardada = faseAgricolaRepository.save(nuevaFase);

        // 1. Obtenemos el cultivo para leer sus parámetros de días
        Cultivo cultivo = cultivoRepository.findById(faseGuardada.getIdCultivo())
                .orElseThrow(() -> new ResourceNotFoundException("Cultivo no encontrado"));

        LocalDate fechaActual = faseGuardada.getFechaInicio();

        // 3. Iteramos sobre el Enum creando las etapas con fechas predictivas
        for (EtapaPredeterminada etapaDefecto : EtapaPredeterminada.values()) {

            int diasDuracion = calcularDiasEtapa(etapaDefecto, cultivo);
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

    private int calcularDiasEtapa(EtapaPredeterminada etapa, Cultivo cultivo) {
        int diasGerm = 10;
        int diasVeg = 30;
        int diasFlor = 20;
        int diasCos = 15;

        return switch (etapa) {
            case GERMINACION -> diasGerm;
            case PLANTULA -> diasVeg / 3;
            case CRECIMIENTO -> diasVeg - (diasVeg / 3);
            case FLORACION -> diasFlor;
            case COSECHA -> diasCos;
        };
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
}