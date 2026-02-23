package com.agrosoft.api.features.monitoring.controllers;

import com.agrosoft.api.features.monitoring.dto.RegistroCrecimientoRequestDTO;
import com.agrosoft.api.features.monitoring.dto.RegistroCrecimientoResponseDTO;
import com.agrosoft.api.features.monitoring.services.RegistroCrecimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/crecimiento")
@RequiredArgsConstructor
public class RegistroCrecimientoController {

    private final RegistroCrecimientoService service;

    @PostMapping
    public ResponseEntity<RegistroCrecimientoResponseDTO> registrar(@RequestBody RegistroCrecimientoRequestDTO request) {
        return new ResponseEntity<>(service.registrarCrecimiento(request), HttpStatus.CREATED);
    }

    @GetMapping("/cultivo/{idCultivo}")
    public ResponseEntity<List<RegistroCrecimientoResponseDTO>> obtenerPorCultivo(@PathVariable UUID idCultivo) {
        return ResponseEntity.ok(service.obtenerPorCultivo(idCultivo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroCrecimientoResponseDTO> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroCrecimientoResponseDTO> actualizar(
            @PathVariable UUID id,
            @RequestBody RegistroCrecimientoRequestDTO request) {
        return ResponseEntity.ok(service.actualizarRegistro(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        service.eliminarRegistro(id);
        return ResponseEntity.noContent().build();
    }
}