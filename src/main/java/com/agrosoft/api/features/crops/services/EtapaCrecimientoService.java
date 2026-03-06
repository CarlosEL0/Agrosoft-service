package com.agrosoft.api.features.crops.services;

import com.agrosoft.api.features.crops.dto.EtapaCrecimientoRequestDTO;
import com.agrosoft.api.features.crops.entities.EtapaCrecimiento;

import java.util.List;
import java.util.UUID;

public interface EtapaCrecimientoService {
    EtapaCrecimiento crearEtapa(EtapaCrecimientoRequestDTO request);
    List<EtapaCrecimiento> obtenerPorCiclo(UUID idCiclo);
    EtapaCrecimiento obtenerPorId(UUID id);
    EtapaCrecimiento actualizarEtapa(UUID id, EtapaCrecimientoRequestDTO request);
    void eliminarEtapa(UUID id);
}