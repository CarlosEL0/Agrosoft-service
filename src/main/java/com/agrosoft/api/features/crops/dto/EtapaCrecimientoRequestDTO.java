package com.agrosoft.api.features.crops.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class EtapaCrecimientoRequestDTO {

    @NotNull(message = "El ID del ciclo es obligatorio")
    private UUID idCiclo;

    @NotBlank(message = "El nombre de la etapa es obligatorio")
    private String nombreEtapa;

    @NotNull(message = "El orden de la etapa es obligatorio")
    @Positive(message = "El orden de la etapa debe ser mayor a cero")
    private Integer ordenEtapa;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    private LocalDate fechaFin;
}