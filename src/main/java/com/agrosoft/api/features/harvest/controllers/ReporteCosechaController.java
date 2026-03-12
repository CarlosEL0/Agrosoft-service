package com.agrosoft.api.features.harvest.controllers;

import com.agrosoft.api.features.harvest.dto.ReporteCosechaRequestDTO;
import com.agrosoft.api.features.harvest.dto.ReporteCosechaResponseDTO;
import com.agrosoft.api.features.harvest.services.ReporteCosechaService;
import com.agrosoft.api.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cosechas")
@RequiredArgsConstructor
public class ReporteCosechaController {

    private final ReporteCosechaService reporteCosechaService;

    @PostMapping("/reporte-ia")
    public ResponseEntity<ApiResponse<ReporteCosechaResponseDTO>> generarReporteIA(@Valid @RequestBody ReporteCosechaRequestDTO request) {
        ReporteCosechaResponseDTO response = reporteCosechaService.generarReporteCosecha(request);
        return new ResponseEntity<>(ApiResponse.success("Reporte de cosecha generado exitosamente", response), HttpStatus.CREATED);
    }
}