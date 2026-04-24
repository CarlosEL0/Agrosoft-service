package com.agrosoft.api.features.monitoring.services;

import com.agrosoft.api.features.monitoring.dto.IrregularidadRequestDTO;
import com.agrosoft.api.features.monitoring.dto.IrregularidadResponseDTO;
import com.agrosoft.api.features.monitoring.entities.Irregularidad;

import java.util.List;
import java.util.UUID;

public interface IrregularidadService {
    IrregularidadResponseDTO reportarIrregularidad(IrregularidadRequestDTO request);
    List<IrregularidadResponseDTO> obtenerPorCultivo(UUID idCultivo);
    List<IrregularidadResponseDTO> obtenerActivasPorCultivo(UUID idCultivo);
    IrregularidadResponseDTO obtenerPorId(UUID id);
    IrregularidadResponseDTO actualizarIrregularidad(UUID id, IrregularidadRequestDTO request);
    void eliminarIrregularidad(UUID id);
}