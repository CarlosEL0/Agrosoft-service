package com.agrosoft.api.features.images.controllers;

import com.agrosoft.api.features.images.dto.ImagenResponseDTO;
import com.agrosoft.api.features.images.service.ImagenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/imagenes")
@RequiredArgsConstructor
public class ImagenController {

    private final ImagenService imagenService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImagenResponseDTO> subirImagen(
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam(value = "descripcion", required = false) String descripcion,
            @RequestParam("idReferencia") UUID idReferencia,
            @RequestParam("tipo") String tipo // Ej: "RIEGO", "PODA", "CRECIMIENTO", "IRREGULARIDAD"
    ) {
        try {
            ImagenResponseDTO nuevaImagen = imagenService.subirEvidencia(
                    archivo,
                    descripcion,
                    idReferencia,
                    tipo
            );
            return ResponseEntity.ok(nuevaImagen);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        } catch (IllegalArgumentException e) {
            // Retorna 400 si el "tipo" no es válido
            return ResponseEntity.badRequest().build();
        }
    }
}