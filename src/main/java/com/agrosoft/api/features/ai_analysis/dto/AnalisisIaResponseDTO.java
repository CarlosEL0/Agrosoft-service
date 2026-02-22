package com.agrosoft.api.features.ai_analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalisisIaResponseDTO {
    private UUID idAnalisis;
    private UUID idCultivo;
    private String tipoAnalisis;
    private String resultadoAnalisis; // El texto general generado por la IA

    // La lista de pasos a seguir extraídos mágicamente por nuestro backend
    private List<RecomendacionDTO> recomendaciones;
}