package com.agrosoft.api.features.crops.services;

import com.agrosoft.api.features.crops.dto.FaseAgricolaRequestDTO;
import com.agrosoft.api.features.crops.entities.FaseAgricola;

import java.util.List;
import java.util.UUID;

public interface FaseAgricolaService {
    FaseAgricola crearFase(FaseAgricolaRequestDTO request);
    List<FaseAgricola> obtenerPorCultivo(UUID idCultivo);
    FaseAgricola obtenerPorId(UUID id);
    FaseAgricola actualizarFase(UUID id, FaseAgricolaRequestDTO request);
    void eliminarFase(UUID id);
}