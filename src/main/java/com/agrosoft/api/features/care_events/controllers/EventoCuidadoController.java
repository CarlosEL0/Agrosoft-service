package com.agrosoft.api.features.care_events.controllers;

import  com.agrosoft.api.features.care_events.dto.EventoCuidadoRequestDTO;
import com.agrosoft.api.features.care_events.entities.EventoCuidado;
import com.agrosoft.api.features.care_events.service.EventoCuidadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/eventos")
@RequiredArgsConstructor
public class EventoCuidadoController {

    private final EventoCuidadoService eventoService;

    @PostMapping
    public ResponseEntity<EventoCuidado> crearEvento(@RequestBody EventoCuidadoRequestDTO request) {
        return new ResponseEntity<>(eventoService.crearEvento(request), HttpStatus.CREATED);
    }

    @GetMapping("/cultivo/{idCultivo}")
    public ResponseEntity<List<EventoCuidado>> obtenerHistorial(@PathVariable UUID idCultivo) {
        return ResponseEntity.ok(eventoService.obtenerHistorialPorCultivo(idCultivo));
    }

    // Endpoint extra para filtrar: /api/v1/eventos/cultivo/{id}/tipo?tipo=riego
    @GetMapping("/cultivo/{idCultivo}/tipo")
    public ResponseEntity<List<EventoCuidado>> obtenerPorTipo(
            @PathVariable UUID idCultivo,
            @RequestParam String tipo) {
        return ResponseEntity.ok(eventoService.obtenerHistorialPorTipo(idCultivo, tipo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoCuidado> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(eventoService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoCuidado> actualizarEvento(
            @PathVariable UUID id,
            @RequestBody EventoCuidadoRequestDTO request) {
        return ResponseEntity.ok(eventoService.actualizarEvento(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable UUID id) {
        eventoService.eliminarEvento(id);
        return ResponseEntity.noContent().build();
    }
}