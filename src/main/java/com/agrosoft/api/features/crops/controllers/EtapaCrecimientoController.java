package com.agrosoft.api.features.crops.controllers;

import com.agrosoft.api.features.crops.dto.EtapaCrecimientoRequestDTO;
import com.agrosoft.api.features.crops.entities.EtapaCrecimientoEntity;
import com.agrosoft.api.features.crops.service.EtapaCrecimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/etapas")
@RequiredArgsConstructor
public class EtapaCrecimientoController {

    private final EtapaCrecimientoService etapaService;

    @PostMapping
    public ResponseEntity<EtapaCrecimientoEntity> crearEtapa(@RequestBody EtapaCrecimientoRequestDTO request) {
        return new ResponseEntity<>(etapaService.crearEtapa(request), HttpStatus.CREATED);
    }

    @GetMapping("/ciclo/{idCiclo}")
    public ResponseEntity<List<EtapaCrecimientoEntity>> obtenerPorCiclo(@PathVariable UUID idCiclo) {
        return ResponseEntity.ok(etapaService.obtenerPorCiclo(idCiclo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtapaCrecimientoEntity> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(etapaService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EtapaCrecimientoEntity> actualizarEtapa(
            @PathVariable UUID id,
            @RequestBody EtapaCrecimientoRequestDTO request) {
        return ResponseEntity.ok(etapaService.actualizarEtapa(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEtapa(@PathVariable UUID id) {
        etapaService.eliminarEtapa(id);
        return ResponseEntity.noContent().build();
    }
}