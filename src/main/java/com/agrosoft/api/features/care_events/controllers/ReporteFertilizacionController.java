package com.agrosoft.api.features.care_events.controllers;

import com.agrosoft.api.features.care_events.dto.ReporteFertilizacionRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReporteFertilizacion;
import com.agrosoft.api.features.care_events.services.ReporteFertilizacionService;
import com.agrosoft.api.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fertilizaciones")
@RequiredArgsConstructor
public class ReporteFertilizacionController {

    private final ReporteFertilizacionService fertilizacionService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReporteFertilizacion>> crearReporte(@Valid @RequestBody ReporteFertilizacionRequestDTO request) {
        return new ResponseEntity<>(ApiResponse.success("Reporte de fertilización creado", fertilizacionService.crearReporte(request)), HttpStatus.CREATED);
    }

    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<ApiResponse<ReporteFertilizacion>> obtenerPorEvento(@PathVariable UUID idEvento) {
        return ResponseEntity.ok(ApiResponse.success("Reporte recuperado", fertilizacionService.obtenerPorEvento(idEvento)));
    }

    @PutMapping("/{idFertilizacion}")
    public ResponseEntity<ApiResponse<ReporteFertilizacion>> actualizarReporte(
            @PathVariable UUID idFertilizacion,
            @Valid @RequestBody ReporteFertilizacionRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Reporte actualizado", fertilizacionService.actualizarReporte(idFertilizacion, request)));
    }
}