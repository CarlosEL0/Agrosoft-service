package com.agrosoft.api.features.harvest.services;

import com.agrosoft.api.features.harvest.dto.ReporteCosechaRequestDTO;
import com.agrosoft.api.features.harvest.dto.ReporteCosechaResponseDTO;

public interface ReporteCosechaService {
    ReporteCosechaResponseDTO generarReporteCosecha(ReporteCosechaRequestDTO request);
}