package com.agrosoft.api.features.monitoring.services;

import com.agrosoft.api.features.monitoring.dto.RegistroCrecimientoRequestDTO;
import com.agrosoft.api.features.monitoring.dto.RegistroCrecimientoResponseDTO;

import java.util.List;
import java.util.UUID;

public interface RegistroCrecimientoService {
    RegistroCrecimientoResponseDTO registrarCrecimiento(RegistroCrecimientoRequestDTO request);
    List<RegistroCrecimientoResponseDTO> obtenerPorCultivo(UUID idCultivo);
    RegistroCrecimientoResponseDTO obtenerPorId(UUID id);
    RegistroCrecimientoResponseDTO actualizarRegistro(UUID id, RegistroCrecimientoRequestDTO request);
    void eliminarRegistro(UUID id);
}