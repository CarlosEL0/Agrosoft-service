package com.agrosoft.api.features.care_events.controllers;

import com.agrosoft.api.features.care_events.dto.ReporteFumigacionRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReporteFumigacion;
import com.agrosoft.api.features.care_events.service.ReporteFumigacionService;
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
    public ResponseEntity<ReporteFumigacion> crearReporte(@RequestBody ReporteFumigacionRequestDTO request) {
        return new ResponseEntity<>(fumigacionService.crearReporte(request), HttpStatus.CREATED);
    }

    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<ReporteFumigacion> obtenerPorEvento(@PathVariable UUID idEvento) {
        return ResponseEntity.ok(fumigacionService.obtenerPorEvento(idEvento));
    }

    @PutMapping("/{idFumigacion}")
    public ResponseEntity<ReporteFumigacion> actualizarReporte(
            @PathVariable UUID idFumigacion,
            @RequestBody ReporteFumigacionRequestDTO request) {
        return ResponseEntity.ok(fumigacionService.actualizarReporte(idFumigacion, request));
    }
}