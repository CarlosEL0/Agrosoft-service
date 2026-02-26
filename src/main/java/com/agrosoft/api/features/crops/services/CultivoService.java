package com.agrosoft.api.features.crops.services;

import com.agrosoft.api.features.crops.dto.CultivoRequestDTO;
import com.agrosoft.api.features.crops.entities.Cultivo;

import java.util.List;
import java.util.UUID;

public interface CultivoService {
    Cultivo crearCultivo(CultivoRequestDTO request);
    List<Cultivo> obtenerTodos();
    Cultivo obtenerPorId(UUID id);
    Cultivo actualizarCultivo(UUID id, CultivoRequestDTO request);
    void eliminarCultivo(UUID id);
}