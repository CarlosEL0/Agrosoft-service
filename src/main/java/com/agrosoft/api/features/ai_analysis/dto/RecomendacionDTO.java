package com.agrosoft.api.features.ai_analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecomendacionDTO {
    private UUID idRecomendacion;
    private String titulo;
    private String descripcion;
    private String prioridad; // "alta", "media", "baja"
    private Boolean aplicada;
}