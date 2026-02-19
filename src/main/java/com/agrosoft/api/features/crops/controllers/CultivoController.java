package com.agrosoft.api.features.crops.controllers;

import com.agrosoft.api.features.crops.dto.CultivoRequestDTO;
import com.agrosoft.api.features.crops.entities.CultivoEntity;
import com.agrosoft.api.features.crops.service.CultivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cultivos")
@RequiredArgsConstructor
public class CultivoController {

    private final CultivoService cultivoService;

    @PostMapping
    public ResponseEntity<CultivoEntity> crearCultivo(@RequestBody CultivoRequestDTO request) {
        CultivoEntity nuevoCultivo = cultivoService.crearCultivo(request);
        return new ResponseEntity<>(nuevoCultivo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CultivoEntity>> obtenerTodos() {
        return ResponseEntity.ok(cultivoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CultivoEntity> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(cultivoService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CultivoEntity> actualizarCultivo(
            @PathVariable UUID id,
            @RequestBody CultivoRequestDTO request) {
        return ResponseEntity.ok(cultivoService.actualizarCultivo(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCultivo(@PathVariable UUID id) {
        cultivoService.eliminarCultivo(id);
        return ResponseEntity.noContent().build();
    }
}