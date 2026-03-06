package com.agrosoft.api.features.images.service;

import com.agrosoft.api.features.images.dto.ImagenResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ImagenService {

    ImagenResponseDTO subirEvidencia(
            MultipartFile archivo,
            String descripcion,
            UUID idReferencia,
            String tipoReferencia
    ) throws IOException;
    List<ImagenResponseDTO> obtenerTodasLasImagenes();
    ImagenResponseDTO obtenerImagenPorId(UUID idImagen);
}