package com.agrosoft.api.features.monitoring;

import com.agrosoft.api.features.monitoring.dto.IrregularidadRequestDTO;
import com.agrosoft.api.features.monitoring.dto.IrregularidadResponseDTO;
import com.agrosoft.api.features.monitoring.services.IrregularidadService;
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
    public ResponseEntity<IrregularidadResponseDTO> reportar(@RequestBody IrregularidadRequestDTO request) {
        return new ResponseEntity<>(service.reportarIrregularidad(request), HttpStatus.CREATED);
    }

    @GetMapping("/cultivo/{idCultivo}")
    public ResponseEntity<List<IrregularidadResponseDTO>> obtenerPorCultivo(
            @PathVariable UUID idCultivo,
            @RequestParam(required = false) String estado) {

        if ("activa".equalsIgnoreCase(estado)) {
            return ResponseEntity.ok(service.obtenerActivasPorCultivo(idCultivo));
        }
        return ResponseEntity.ok(service.obtenerPorCultivo(idCultivo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IrregularidadResponseDTO> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IrregularidadResponseDTO> actualizar(
            @PathVariable UUID id,
            @RequestBody IrregularidadRequestDTO request) {
        return ResponseEntity.ok(service.actualizarIrregularidad(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        service.eliminarIrregularidad(id);
        return ResponseEntity.noContent().build();
    }
}