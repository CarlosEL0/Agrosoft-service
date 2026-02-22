package com.agrosoft.api.features.care_events.service;

import com.agrosoft.api.features.care_events.dto.ReporteFertilizacionRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReporteFertilizacionEntity;

import java.util.UUID;

public interface ReporteFertilizacionService {
    ReporteFertilizacionEntity crearReporte(ReporteFertilizacionRequestDTO request);
    ReporteFertilizacionEntity obtenerPorEvento(UUID idEvento);
    ReporteFertilizacionEntity actualizarReporte(UUID idFertilizacion, ReporteFertilizacionRequestDTO request);
}