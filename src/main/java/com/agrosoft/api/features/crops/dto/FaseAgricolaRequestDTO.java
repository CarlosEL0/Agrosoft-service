package com.agrosoft.api.features.crops.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class FaseAgricolaRequestDTO {

    @NotNull(message = "El ID del cultivo es obligatorio")
    private UUID idCultivo;

    @NotNull(message = "El número de ciclo es obligatorio")
    @Positive(message = "El número de ciclo debe ser mayor a cero")
    private Integer numeroCiclo;

    @NotBlank(message = "El nombre del ciclo es obligatorio")
    private String nombreCiclo;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @PastOrPresent(message = "La fecha de inicio debe ser una fecha pasada o presente")
    private LocalDate fechaInicio;

    @Future(message = "La fecha de inicio debe ser una fecha futura")
    private LocalDate fechaFin;
    private String estado;
}