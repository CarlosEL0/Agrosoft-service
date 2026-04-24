package com.agrosoft.api.features.ai_analysis.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ResumenCuidadosRequestDTO {
    private UUID idCultivo;

    private Integer diasRetroceso;

    private String preguntaAdicional;
}