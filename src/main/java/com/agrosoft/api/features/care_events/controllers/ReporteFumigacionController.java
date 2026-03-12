package com.agrosoft.api.features.care_events.controllers;

import com.agrosoft.api.features.care_events.dto.ReporteFumigacionRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReporteFumigacion;
import com.agrosoft.api.features.care_events.services.ReporteFumigacionService;
import com.agrosoft.api.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fumigaciones")
@RequiredArgsConstructor
public class ReporteFumigacionController {

    private final ReporteFumigacionService fumigacionService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReporteFumigacion>> crearReporte(@Valid @RequestBody ReporteFumigacionRequestDTO request) {
        return new ResponseEntity<>(ApiResponse.success("Reporte de fumigación creado", fumigacionService.crearReporte(request)), HttpStatus.CREATED);
    }

    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<ApiResponse<ReporteFumigacion>> obtenerPorEvento(@PathVariable UUID idEvento) {
        return ResponseEntity.ok(ApiResponse.success("Reporte recuperado", fumigacionService.obtenerPorEvento(idEvento)));
    }

    @PutMapping("/{idFumigacion}")
    public ResponseEntity<ApiResponse<ReporteFumigacion>> actualizarReporte(
            @PathVariable UUID idFumigacion,
            @Valid @RequestBody ReporteFumigacionRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Reporte actualizado", fumigacionService.actualizarReporte(idFumigacion, request)));
    }
}