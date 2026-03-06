package com.agrosoft.api.features.images.controllers;

import com.agrosoft.api.features.images.dto.ImagenResponseDTO;
import com.agrosoft.api.features.images.services.ImagenService;
import com.agrosoft.api.shared.exceptions.BusinessRuleException;
import com.agrosoft.api.shared.exceptions.IntegrationException;
import com.agrosoft.api.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/imagenes")
@RequiredArgsConstructor
public class ImagenController {

    private final ImagenService imagenService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ImagenResponseDTO>> subirImagen(
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
            return ResponseEntity.ok(ApiResponse.success("Imagen subida exitosamente", nuevaImagen));
        } catch (IOException e) {
            throw new IntegrationException("Error al comunicarse con Cloudinary: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new BusinessRuleException("Tipo de imagen no válido: " + tipo);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ImagenResponseDTO>>> obtenerTodasLasImagenes() {
        return ResponseEntity.ok(ApiResponse.success("Imágenes recuperadas exitosamente", imagenService.obtenerTodasLasImagenes()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ImagenResponseDTO>> obtenerImagenPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Imagen recuperada", imagenService.obtenerImagenPorId(id)));
    }
}