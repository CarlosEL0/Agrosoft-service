package com.agrosoft.api.features.crops.services.impl;

import com.agrosoft.api.features.crops.dto.FaseAgricolaRequestDTO;
import com.agrosoft.api.features.crops.entities.EtapaCrecimiento;
import com.agrosoft.api.features.crops.entities.FaseAgricola;
import com.agrosoft.api.features.crops.enums.EtapaPredeterminada;
import com.agrosoft.api.features.crops.mappers.FaseAgricolaMapper;
import com.agrosoft.api.features.crops.repositories.EtapaCrecimientoRepository;
import com.agrosoft.api.features.crops.repositories.FaseAgricolaRepository;
import com.agrosoft.api.features.crops.services.FaseAgricolaService;
import com.agrosoft.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FaseAgricolaServiceImpl implements FaseAgricolaService {

    private final FaseAgricolaRepository faseAgricolaRepository;
    private final FaseAgricolaMapper faseAgricolaMapper;
    private final EtapaCrecimientoRepository etapaRepository;

    @Override
    @Transactional
    public FaseAgricola crearFase(FaseAgricolaRequestDTO request) {
        // 1. Guardamos el ciclo (fase) principal
        FaseAgricola nuevaFase = faseAgricolaMapper.toEntity(request);
        FaseAgricola faseGuardada = faseAgricolaRepository.save(nuevaFase);

        // 2. Generación automática de las 5 etapas base
        for (EtapaPredeterminada etapaDefecto : EtapaPredeterminada.values()) {
            EtapaCrecimiento nuevaEtapa = EtapaCrecimiento.builder()
                    .idCiclo(faseGuardada.getIdCiclo())
                    .nombreEtapa(etapaDefecto.getNombre())
                    .ordenEtapa(etapaDefecto.getOrden())
                    .build();
            etapaRepository.save(nuevaEtapa);
        }

        return faseGuardada;
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