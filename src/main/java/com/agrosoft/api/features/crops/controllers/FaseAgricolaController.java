package com.agrosoft.api.features.crops.controllers;

import com.agrosoft.api.features.crops.dto.FaseAgricolaRequestDTO;
import com.agrosoft.api.features.crops.entities.FaseAgricola;
import com.agrosoft.api.features.crops.services.FaseAgricolaService;
import com.agrosoft.api.shared.response.ApiResponse;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse<FaseAgricola>> crearFase(@Valid @RequestBody FaseAgricolaRequestDTO request) {
        return new ResponseEntity<>(ApiResponse.success("Fase agrícola creada exitosamente", faseAgricolaService.crearFase(request)), HttpStatus.CREATED);
    }

    @GetMapping("/cultivo/{idCultivo}")
    public ResponseEntity<ApiResponse<List<FaseAgricola>>> obtenerPorCultivo(@PathVariable UUID idCultivo) {
        return ResponseEntity.ok(ApiResponse.success("Fases recuperadas", faseAgricolaService.obtenerPorCultivo(idCultivo)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FaseAgricola>> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Fase recuperada", faseAgricolaService.obtenerPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FaseAgricola>> actualizarFase(
            @PathVariable UUID id,
            @Valid @RequestBody FaseAgricolaRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Fase actualizada", faseAgricolaService.actualizarFase(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarFase(@PathVariable UUID id) {
        faseAgricolaService.eliminarFase(id);
        return ResponseEntity.ok(ApiResponse.success("Fase eliminada correctamente"));
    }
}