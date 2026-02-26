package com.agrosoft.api.features.crops.controllers;

import com.agrosoft.api.features.crops.dto.EtapaCrecimientoRequestDTO;
import com.agrosoft.api.features.crops.entities.EtapaCrecimientoEntity;
import com.agrosoft.api.features.crops.service.EtapaCrecimientoService;
import com.agrosoft.api.shared.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<EtapaCrecimientoEntity>> crearEtapa(@RequestBody EtapaCrecimientoRequestDTO request) {
        return new ResponseEntity<>(ApiResponse.success("Etapa creada exitosamente", etapaService.crearEtapa(request)), HttpStatus.CREATED);
    }

    @GetMapping("/ciclo/{idCiclo}")
    public ResponseEntity<ApiResponse<List<EtapaCrecimientoEntity>>> obtenerPorCiclo(@PathVariable UUID idCiclo) {
        return ResponseEntity.ok(ApiResponse.success("Etapas recuperadas", etapaService.obtenerPorCiclo(idCiclo)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EtapaCrecimientoEntity>> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Etapa recuperada", etapaService.obtenerPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EtapaCrecimientoEntity>> actualizarEtapa(
            @PathVariable UUID id,
            @RequestBody EtapaCrecimientoRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Etapa actualizada", etapaService.actualizarEtapa(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarEtapa(@PathVariable UUID id) {
        etapaService.eliminarEtapa(id);
        return ResponseEntity.ok(ApiResponse.success("Etapa eliminada correctamente"));
    }
}