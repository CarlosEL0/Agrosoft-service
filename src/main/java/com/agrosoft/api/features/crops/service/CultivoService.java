package com.agrosoft.api.features.crops.service;

import com.agrosoft.api.features.crops.dto.CultivoRequestDTO;
import com.agrosoft.api.features.crops.entities.CultivoEntity;

import java.util.List;
import java.util.UUID;

public interface CultivoService {
    CultivoEntity crearCultivo(CultivoRequestDTO request);
    List<CultivoEntity> obtenerTodos();
    CultivoEntity obtenerPorId(UUID id);
    CultivoEntity actualizarCultivo(UUID id, CultivoRequestDTO request);
    void eliminarCultivo(UUID id);
}