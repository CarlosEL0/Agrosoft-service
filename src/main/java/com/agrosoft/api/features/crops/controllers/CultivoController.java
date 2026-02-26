package com.agrosoft.api.features.crops.controllers;

import com.agrosoft.api.features.crops.dto.CultivoRequestDTO;
import com.agrosoft.api.features.crops.entities.Cultivo;
import com.agrosoft.api.features.crops.services.CultivoService;
import com.agrosoft.api.shared.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<Cultivo>> crearCultivo(@RequestBody CultivoRequestDTO request) {
        Cultivo nuevoCultivo = cultivoService.crearCultivo(request);
        return new ResponseEntity<>(ApiResponse.success("Cultivo creado exitosamente", nuevoCultivo), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Cultivo>>> obtenerTodos() {
        return ResponseEntity.ok(ApiResponse.success("Lista de cultivos recuperada", cultivoService.obtenerTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Cultivo>> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Cultivo recuperado", cultivoService.obtenerPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Cultivo>> actualizarCultivo(
            @PathVariable UUID id,
            @RequestBody CultivoRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Cultivo actualizado exitosamente", cultivoService.actualizarCultivo(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarCultivo(@PathVariable UUID id) {
        cultivoService.eliminarCultivo(id);
        return ResponseEntity.ok(ApiResponse.success("Cultivo eliminado correctamente"));
    }
}