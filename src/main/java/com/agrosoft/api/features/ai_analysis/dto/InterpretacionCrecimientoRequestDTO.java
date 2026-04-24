package com.agrosoft.api.features.ai_analysis.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class InterpretacionCrecimientoRequestDTO {

    private UUID idCultivo;

    private Integer limiteRegistros;

    private String preguntaAdicional;
}