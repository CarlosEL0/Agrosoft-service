package com.agrosoft.api.features.crops.services.impl;

import com.agrosoft.api.features.crops.dto.FaseAgricolaRequestDTO;
import com.agrosoft.api.features.crops.entities.FaseAgricola;
import com.agrosoft.api.features.crops.mappers.FaseAgricolaMapper;
import com.agrosoft.api.features.crops.repositories.FaseAgricolaRepository;
import com.agrosoft.api.features.crops.services.FaseAgricolaService;
import com.agrosoft.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FaseAgricolaServiceImpl implements FaseAgricolaService {

    private final FaseAgricolaRepository faseAgricolaRepository;
    private final FaseAgricolaMapper faseAgricolaMapper;

    @Override
    public FaseAgricola crearFase(FaseAgricolaRequestDTO request) {
        FaseAgricola nuevaFase = faseAgricolaMapper.toEntity(request);
        return faseAgricolaRepository.save(nuevaFase);
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