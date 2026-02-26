package com.agrosoft.api.features.care_events.controllers;

import com.agrosoft.api.features.care_events.dto.ReporteRiegoRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReporteRiego;
import com.agrosoft.api.features.care_events.services.ReporteRiegoService;
import com.agrosoft.api.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/riegos")
@RequiredArgsConstructor
public class ReporteRiegoController {

    private final ReporteRiegoService riegoService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReporteRiego>> crearReporte(@RequestBody ReporteRiegoRequestDTO request) {
        return new ResponseEntity<>(ApiResponse.success("Reporte de riego creado exitosamente", riegoService.crearReporte(request)), HttpStatus.CREATED);
    }

    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<ApiResponse<ReporteRiego>> obtenerPorEvento(@PathVariable UUID idEvento) {
        return ResponseEntity.ok(ApiResponse.success("Reporte recuperado", riegoService.obtenerPorEvento(idEvento)));
    }

    @PutMapping("/{idRiego}")
    public ResponseEntity<ApiResponse<ReporteRiego>> actualizarReporte(
            @PathVariable UUID idRiego,
            @RequestBody ReporteRiegoRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Reporte actualizado", riegoService.actualizarReporte(idRiego, request)));
    }
}