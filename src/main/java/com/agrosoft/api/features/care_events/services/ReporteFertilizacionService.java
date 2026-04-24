package com.agrosoft.api.features.care_events.services;

import com.agrosoft.api.features.care_events.dto.ReporteFertilizacionRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReporteFertilizacion;

import java.util.UUID;

public interface ReporteFertilizacionService {
    ReporteFertilizacion crearReporte(ReporteFertilizacionRequestDTO request);
    ReporteFertilizacion obtenerPorEvento(UUID idEvento);
    ReporteFertilizacion actualizarReporte(UUID idFertilizacion, ReporteFertilizacionRequestDTO request);
}