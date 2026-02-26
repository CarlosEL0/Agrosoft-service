package com.agrosoft.api.features.monitoring.controllers;

import com.agrosoft.api.features.monitoring.dto.RegistroCrecimientoRequestDTO;
import com.agrosoft.api.features.monitoring.dto.RegistroCrecimientoResponseDTO;
import com.agrosoft.api.features.monitoring.services.RegistroCrecimientoService;
import com.agrosoft.api.shared.response.ApiResponse; 
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
    public ResponseEntity<ApiResponse<RegistroCrecimientoResponseDTO>> registrar(@RequestBody RegistroCrecimientoRequestDTO request) {
        RegistroCrecimientoResponseDTO response = service.registrarCrecimiento(request);
        return new ResponseEntity<>(ApiResponse.success("Registro de crecimiento creado exitosamente", response), HttpStatus.CREATED);
    }

    @GetMapping("/cultivo/{idCultivo}")
    public ResponseEntity<ApiResponse<List<RegistroCrecimientoResponseDTO>>> obtenerPorCultivo(@PathVariable UUID idCultivo) {
        List<RegistroCrecimientoResponseDTO> response = service.obtenerPorCultivo(idCultivo);
        return ResponseEntity.ok(ApiResponse.success("Historial de crecimiento recuperado", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RegistroCrecimientoResponseDTO>> obtenerPorId(@PathVariable UUID id) {
        RegistroCrecimientoResponseDTO response = service.obtenerPorId(id);
        return ResponseEntity.ok(ApiResponse.success("Registro recuperado", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RegistroCrecimientoResponseDTO>> actualizar(
            @PathVariable UUID id,
            @RequestBody RegistroCrecimientoRequestDTO request) {
        RegistroCrecimientoResponseDTO response = service.actualizarRegistro(id, request);
        return ResponseEntity.ok(ApiResponse.success("Registro actualizado exitosamente", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable UUID id) {
        service.eliminarRegistro(id);
        return ResponseEntity.ok(ApiResponse.success("Registro eliminado correctamente"));
    }
}