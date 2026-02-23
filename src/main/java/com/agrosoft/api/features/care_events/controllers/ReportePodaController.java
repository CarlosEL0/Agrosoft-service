package com.agrosoft.api.features.care_events.controllers;

import com.agrosoft.api.features.care_events.dto.ReportePodaRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReportePoda;
import com.agrosoft.api.features.care_events.service.ReportePodaService;
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
    public ResponseEntity<ReportePoda> crearReporte(@RequestBody ReportePodaRequestDTO request) {
        return new ResponseEntity<>(podaService.crearReporte(request), HttpStatus.CREATED);
    }

    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<ReportePoda> obtenerPorEvento(@PathVariable UUID idEvento) {
        return ResponseEntity.ok(podaService.obtenerPorEvento(idEvento));
    }

    @PutMapping("/{idPoda}")
    public ResponseEntity<ReportePoda> actualizarReporte(
            @PathVariable UUID idPoda,
            @RequestBody ReportePodaRequestDTO request) {
        return ResponseEntity.ok(podaService.actualizarReporte(idPoda, request));
    }
}