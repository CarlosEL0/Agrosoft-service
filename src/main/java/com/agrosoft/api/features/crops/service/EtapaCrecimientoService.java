package com.agrosoft.api.features.crops.service;

import com.agrosoft.api.features.crops.dto.EtapaCrecimientoRequestDTO;
import com.agrosoft.api.features.crops.entities.EtapaCrecimientoEntity;

import java.util.List;
import java.util.UUID;

public interface EtapaCrecimientoService {
    EtapaCrecimientoEntity crearEtapa(EtapaCrecimientoRequestDTO request);
    List<EtapaCrecimientoEntity> obtenerPorCiclo(UUID idCiclo);
    EtapaCrecimientoEntity obtenerPorId(UUID id);
    EtapaCrecimientoEntity actualizarEtapa(UUID id, EtapaCrecimientoRequestDTO request);
    void eliminarEtapa(UUID id);
}