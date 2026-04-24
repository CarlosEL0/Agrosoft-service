package com.agrosoft.api.features.care_events.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.util.UUID;

@Data
public class ReporteFertilizacionRequestDTO {

    @NotNull(message = "El ID del evento es obligatorio")
    private UUID idEvento;

    @NotBlank(message = "El tipo de fertilizante es obligatorio")
    private String tipoFertilizante;

    @NotBlank(message = "El nombre del fertilizante es obligatorio")
    private String nombreFertilizante;

    @NotNull(message = "La cantidad aplicada es obligatoria")
    @Positive(message = "La cantidad aplicada debe ser mayor a cero")
    private Double cantidadAplicada;

    @NotBlank(message = "La unidad de medida es obligatoria")
    private String unidadMedida;

    @NotBlank(message = "El método de aplicación es obligatorio")
    private String metodoAplicacion;

    @PositiveOrZero(message = "El costo no puede ser negativo")
    private Double costo;
}