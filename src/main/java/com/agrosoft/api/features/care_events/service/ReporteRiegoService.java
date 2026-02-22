package com.agrosoft.api.features.care_events.service;

import com.agrosoft.api.features.care_events.dto.ReporteRiegoRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReporteRiego;

import java.util.UUID;

public interface ReporteRiegoService {
    ReporteRiego crearReporte(ReporteRiegoRequestDTO request);
    ReporteRiego obtenerPorEvento(UUID idEvento);
    ReporteRiego actualizarReporte(UUID idRiego, ReporteRiegoRequestDTO request);
}