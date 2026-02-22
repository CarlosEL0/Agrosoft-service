package com.agrosoft.api.features.monitoring.services;

import com.agrosoft.api.features.monitoring.dto.IrregularidadRequestDTO;
import com.agrosoft.api.features.monitoring.entities.Irregularidad;

import java.util.List;
import java.util.UUID;

public interface IrregularidadService {
    Irregularidad reportarIrregularidad(IrregularidadRequestDTO request);
    List<Irregularidad> obtenerPorCultivo(UUID idCultivo);
    List<Irregularidad> obtenerActivasPorCultivo(UUID idCultivo);
    Irregularidad obtenerPorId(UUID id);
    Irregularidad actualizarIrregularidad(UUID id, IrregularidadRequestDTO request);
    void eliminarIrregularidad(UUID id);
}