package com.agrosoft.api.features.care_events.controllers;

import com.agrosoft.api.features.care_events.dto.EventoCuidadoRequestDTO;
import com.agrosoft.api.features.care_events.entities.EventoCuidado;
import com.agrosoft.api.features.care_events.service.EventoCuidadoService;
import com.agrosoft.api.shared.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<EventoCuidado>> crearEvento(@RequestBody EventoCuidadoRequestDTO request) {
        return new ResponseEntity<>(ApiResponse.success("Evento registrado", eventoService.crearEvento(request)), HttpStatus.CREATED);
    }

    @GetMapping("/cultivo/{idCultivo}")
    public ResponseEntity<ApiResponse<List<EventoCuidado>>> obtenerHistorial(@PathVariable UUID idCultivo) {
        return ResponseEntity.ok(ApiResponse.success("Historial recuperado", eventoService.obtenerHistorialPorCultivo(idCultivo)));
    }

    @GetMapping("/cultivo/{idCultivo}/tipo")
    public ResponseEntity<ApiResponse<List<EventoCuidado>>> obtenerPorTipo(
            @PathVariable UUID idCultivo,
            @RequestParam String tipo) {
        return ResponseEntity.ok(ApiResponse.success("Eventos recuperados", eventoService.obtenerHistorialPorTipo(idCultivo, tipo)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EventoCuidado>> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Evento recuperado", eventoService.obtenerPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EventoCuidado>> actualizarEvento(
            @PathVariable UUID id,
            @RequestBody EventoCuidadoRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Evento actualizado", eventoService.actualizarEvento(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarEvento(@PathVariable UUID id) {
        eventoService.eliminarEvento(id);
        return ResponseEntity.ok(ApiResponse.success("Evento eliminado correctamente"));
    }
}