package com.agrosoft.api.features.monitoring.controllers;

import com.agrosoft.api.features.monitoring.dto.IrregularidadRequestDTO;
import com.agrosoft.api.features.monitoring.dto.IrregularidadResponseDTO;
import com.agrosoft.api.features.monitoring.services.IrregularidadService;
import com.agrosoft.api.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/irregularidades")
@RequiredArgsConstructor
public class IrregularidadController {

    private final IrregularidadService service;

    @PostMapping
    public ResponseEntity<ApiResponse<IrregularidadResponseDTO>> reportar(@RequestBody IrregularidadRequestDTO request) {
        return new ResponseEntity<>(ApiResponse.success("Irregularidad reportada", service.reportarIrregularidad(request)), HttpStatus.CREATED);
    }

    @GetMapping("/cultivo/{idCultivo}")
    public ResponseEntity<ApiResponse<List<IrregularidadResponseDTO>>> obtenerPorCultivo(
            @PathVariable UUID idCultivo,
            @RequestParam(required = false) String estado) {

        if ("activa".equalsIgnoreCase(estado)) {
            return ResponseEntity.ok(ApiResponse.success("Irregularidades activas recuperadas", service.obtenerActivasPorCultivo(idCultivo)));
        }
        return ResponseEntity.ok(ApiResponse.success("Historial de irregularidades recuperado", service.obtenerPorCultivo(idCultivo)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<IrregularidadResponseDTO>> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Irregularidad recuperada", service.obtenerPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<IrregularidadResponseDTO>> actualizar(
            @PathVariable UUID id,
            @RequestBody IrregularidadRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Irregularidad actualizada", service.actualizarIrregularidad(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable UUID id) {
        service.eliminarIrregularidad(id);
        return ResponseEntity.ok(ApiResponse.success("Irregularidad eliminada correctamente"));
    }
}