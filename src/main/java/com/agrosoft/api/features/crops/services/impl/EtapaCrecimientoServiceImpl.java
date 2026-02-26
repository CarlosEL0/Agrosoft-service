package com.agrosoft.api.features.crops.services.impl;

import com.agrosoft.api.features.crops.dto.EtapaCrecimientoRequestDTO;
import com.agrosoft.api.features.crops.entities.EtapaCrecimientoEntity;
import com.agrosoft.api.features.crops.mappers.EtapaCrecimientoMapper;
import com.agrosoft.api.features.crops.repositories.EtapaCrecimientoRepository;
import com.agrosoft.api.features.crops.service.EtapaCrecimientoService;
import com.agrosoft.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EtapaCrecimientoServiceImpl implements EtapaCrecimientoService {

    private final EtapaCrecimientoRepository etapaRepository;
    private final EtapaCrecimientoMapper etapaMapper;

    @Override
    public EtapaCrecimientoEntity crearEtapa(EtapaCrecimientoRequestDTO request) {
        EtapaCrecimientoEntity nuevaEtapa = etapaMapper.toEntity(request);
        return etapaRepository.save(nuevaEtapa);
    }

    @Override
    public List<EtapaCrecimientoEntity> obtenerPorCiclo(UUID idCiclo) {
        return etapaRepository.findByIdCicloOrderByOrdenEtapaAsc(idCiclo);
    }

    @Override
    public EtapaCrecimientoEntity obtenerPorId(UUID id) {
        return etapaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Etapa no encontrada con ID: " + id));
    }

    @Override
    public EtapaCrecimientoEntity actualizarEtapa(UUID id, EtapaCrecimientoRequestDTO request) {
        EtapaCrecimientoEntity etapaExistente = obtenerPorId(id);
        etapaMapper.updateEntityFromDto(request, etapaExistente);
        return etapaRepository.save(etapaExistente);
    }

    @Override
    public void eliminarEtapa(UUID id) {
        obtenerPorId(id);
        etapaRepository.deleteById(id);
    }
}