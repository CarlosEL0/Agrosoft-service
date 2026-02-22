package com.agrosoft.api.features.care_events.controllers;

import com.agrosoft.api.features.care_events.dto.ReporteFertilizacionRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReporteFertilizacionEntity;
import com.agrosoft.api.features.care_events.service.ReporteFertilizacionService;
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
    public ResponseEntity<ReporteFertilizacionEntity> crearReporte(@RequestBody ReporteFertilizacionRequestDTO request) {
        return new ResponseEntity<>(fertilizacionService.crearReporte(request), HttpStatus.CREATED);
    }

    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<ReporteFertilizacionEntity> obtenerPorEvento(@PathVariable UUID idEvento) {
        return ResponseEntity.ok(fertilizacionService.obtenerPorEvento(idEvento));
    }

    @PutMapping("/{idFertilizacion}")
    public ResponseEntity<ReporteFertilizacionEntity> actualizarReporte(
            @PathVariable UUID idFertilizacion,
            @RequestBody ReporteFertilizacionRequestDTO request) {
        return ResponseEntity.ok(fertilizacionService.actualizarReporte(idFertilizacion, request));
    }
}