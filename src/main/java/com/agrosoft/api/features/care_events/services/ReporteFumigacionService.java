package com.agrosoft.api.features.care_events.services;

import com.agrosoft.api.features.care_events.dto.ReporteFumigacionRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReporteFumigacion;

import java.util.UUID;

public interface ReporteFumigacionService {
    ReporteFumigacion crearReporte(ReporteFumigacionRequestDTO request);
    ReporteFumigacion obtenerPorEvento(UUID idEvento);
    ReporteFumigacion actualizarReporte(UUID idFumigacion, ReporteFumigacionRequestDTO request);
}