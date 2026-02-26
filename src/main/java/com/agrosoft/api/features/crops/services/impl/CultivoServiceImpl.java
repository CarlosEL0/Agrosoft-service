package com.agrosoft.api.features.crops.services.impl;

import com.agrosoft.api.features.crops.dto.CultivoRequestDTO;
import com.agrosoft.api.features.crops.entities.CultivoEntity;
import com.agrosoft.api.features.crops.mappers.CultivoMapper;
import com.agrosoft.api.features.crops.repositories.CultivoRepository;
import com.agrosoft.api.features.crops.service.CultivoService;
import com.agrosoft.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CultivoServiceImpl implements CultivoService {

    private final CultivoRepository cultivoRepository;
    private final CultivoMapper cultivoMapper;

    @Override
    public CultivoEntity crearCultivo(CultivoRequestDTO request) {
        CultivoEntity nuevoCultivo = cultivoMapper.toEntity(request);
        return cultivoRepository.save(nuevoCultivo);
    }

    @Override
    public List<CultivoEntity> obtenerTodos() {
        return cultivoRepository.findAll();
    }

    @Override
    public CultivoEntity obtenerPorId(UUID id) {
        return cultivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cultivo no encontrado con ID: " + id));
    }

    @Override
    public CultivoEntity actualizarCultivo(UUID id, CultivoRequestDTO request) {
        CultivoEntity cultivoExistente = obtenerPorId(id);
        cultivoMapper.updateEntityFromDto(request, cultivoExistente);
        return cultivoRepository.save(cultivoExistente);
    }

    @Override
    public void eliminarCultivo(UUID id) {
        obtenerPorId(id);
        cultivoRepository.deleteById(id);
    }
}