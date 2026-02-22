package com.agrosoft.api.features.care_events.controllers;

import com.agrosoft.api.features.care_events.dto.ReporteRiegoRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReporteRiego;
import com.agrosoft.api.features.care_events.service.ReporteRiegoService;
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
    public ResponseEntity<ReporteRiego> crearReporte(@RequestBody ReporteRiegoRequestDTO request) {
        return new ResponseEntity<>(riegoService.crearReporte(request), HttpStatus.CREATED);
    }

    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<ReporteRiego> obtenerPorEvento(@PathVariable UUID idEvento) {
        return ResponseEntity.ok(riegoService.obtenerPorEvento(idEvento));
    }

    @PutMapping("/{idRiego}")
    public ResponseEntity<ReporteRiego> actualizarReporte(
            @PathVariable UUID idRiego,
            @RequestBody ReporteRiegoRequestDTO request) {
        return ResponseEntity.ok(riegoService.actualizarReporte(idRiego, request));
    }
}