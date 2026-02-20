package com.agrosoft.api.features.crops.service;

import com.agrosoft.api.features.crops.dto.FaseAgricolaRequestDTO;
import com.agrosoft.api.features.crops.entities.FaseAgricolaEntity;

import java.util.List;
import java.util.UUID;

public interface FaseAgricolaService {
    FaseAgricolaEntity crearFase(FaseAgricolaRequestDTO request);
    List<FaseAgricolaEntity> obtenerPorCultivo(UUID idCultivo);
    FaseAgricolaEntity obtenerPorId(UUID id);
    FaseAgricolaEntity actualizarFase(UUID id, FaseAgricolaRequestDTO request);
    void eliminarFase(UUID id);
}