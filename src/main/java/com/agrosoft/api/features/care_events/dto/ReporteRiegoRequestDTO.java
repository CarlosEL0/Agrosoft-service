package com.agrosoft.api.features.care_events.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ReporteRiegoRequestDTO {
    private UUID idEvento;
    private Double cantidadAgua;
    private String metodoRiego;
    private Integer duracionMinutos;
}