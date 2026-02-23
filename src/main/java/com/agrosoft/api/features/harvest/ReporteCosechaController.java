package com.agrosoft.api.features.harvest;

import com.agrosoft.api.features.harvest.dto.ReporteCosechaRequestDTO;
import com.agrosoft.api.features.harvest.dto.ReporteCosechaResponseDTO;
import com.agrosoft.api.features.harvest.services.ReporteCosechaService;
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
    public ResponseEntity<ReporteCosechaResponseDTO> generarReporteIA(@RequestBody ReporteCosechaRequestDTO request) {
        ReporteCosechaResponseDTO response = reporteCosechaService.generarReporteCosecha(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}