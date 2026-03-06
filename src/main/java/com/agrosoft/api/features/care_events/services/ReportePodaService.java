package com.agrosoft.api.features.care_events.services;

import com.agrosoft.api.features.care_events.dto.ReportePodaRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReportePoda;

import java.util.UUID;

public interface ReportePodaService {
    ReportePoda crearReporte(ReportePodaRequestDTO request);
    ReportePoda obtenerPorEvento(UUID idEvento);
    ReportePoda actualizarReporte(UUID idPoda, ReportePodaRequestDTO request);
}