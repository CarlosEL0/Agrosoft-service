package com.agrosoft.api.features.crops.controllers;

import com.agrosoft.api.features.crops.dto.FaseAgricolaRequestDTO;
import com.agrosoft.api.features.crops.entities.FaseAgricolaEntity;
import com.agrosoft.api.features.crops.service.FaseAgricolaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fases")
@RequiredArgsConstructor
public class FaseAgricolaController {

    private final FaseAgricolaService faseAgricolaService;

    @PostMapping
    public ResponseEntity<FaseAgricolaEntity> crearFase(@RequestBody FaseAgricolaRequestDTO request) {
        return new ResponseEntity<>(faseAgricolaService.crearFase(request), HttpStatus.CREATED);
    }

    @GetMapping("/cultivo/{idCultivo}")
    public ResponseEntity<List<FaseAgricolaEntity>> obtenerPorCultivo(@PathVariable UUID idCultivo) {
        return ResponseEntity.ok(faseAgricolaService.obtenerPorCultivo(idCultivo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaseAgricolaEntity> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(faseAgricolaService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FaseAgricolaEntity> actualizarFase(
            @PathVariable UUID id,
            @RequestBody FaseAgricolaRequestDTO request) {
        return ResponseEntity.ok(faseAgricolaService.actualizarFase(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFase(@PathVariable UUID id) {
        faseAgricolaService.eliminarFase(id);
        return ResponseEntity.noContent().build();
    }
}