package com.agrosoft.api.features.care_events.controllers;

import com.agrosoft.api.features.care_events.dto.ReportePodaRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReportePoda;
import com.agrosoft.api.features.care_events.services.ReportePodaService;
import com.agrosoft.api.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/podas")
@RequiredArgsConstructor
public class ReportePodaController {

    private final ReportePodaService podaService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReportePoda>> crearReporte(@RequestBody ReportePodaRequestDTO request) {
        return new ResponseEntity<>(ApiResponse.success("Reporte de poda creado", podaService.crearReporte(request)), HttpStatus.CREATED);
    }

    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<ApiResponse<ReportePoda>> obtenerPorEvento(@PathVariable UUID idEvento) {
        return ResponseEntity.ok(ApiResponse.success("Reporte recuperado", podaService.obtenerPorEvento(idEvento)));
    }

    @PutMapping("/{idPoda}")
    public ResponseEntity<ApiResponse<ReportePoda>> actualizarReporte(
            @PathVariable UUID idPoda,
            @RequestBody ReportePodaRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Reporte actualizado", podaService.actualizarReporte(idPoda, request)));
    }
}