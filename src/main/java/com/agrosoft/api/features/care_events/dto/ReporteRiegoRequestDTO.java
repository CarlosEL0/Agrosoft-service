package com.agrosoft.api.features.care_events.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.util.UUID;

@Data
public class ReporteRiegoRequestDTO {

    @NotBlank(message = "El idEvento es obligatorio")
    private UUID idEvento;

    @NotBlank(message = "La cantidad de agua es obligatoria")
    @Positive(message = "La cantidad de agua debe ser positiva")
    private Double cantidadAgua;

    @NotBlank(message = "El metodo de riego es obligatorio")
    private String metodoRiego;

    @NotBlank(message = "La duracion de riego es obligatoria")
    @Positive(message = "La duracion de riego debe ser positiva")
    private Integer duracionMinutos;
}