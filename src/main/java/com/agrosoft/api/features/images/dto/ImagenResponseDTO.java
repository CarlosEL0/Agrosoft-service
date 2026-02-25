package com.agrosoft.api.features.images.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ImagenResponseDTO {
    private UUID idImagen;
    private String urlArchivo;
    private String descripcion;
    private LocalDateTime fechaSubida;
}