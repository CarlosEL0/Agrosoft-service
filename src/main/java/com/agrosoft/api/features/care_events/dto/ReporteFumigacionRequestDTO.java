package com.agrosoft.api.features.care_events.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.util.UUID;

@Data
public class ReporteFumigacionRequestDTO {

    @NotNull(message = "El ID del evento es obligatorio")
    private UUID idEvento;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombreProducto;

    @NotBlank(message = "El tipo de producto es obligatorio")
    private String tipoProducto;

    @NotBlank(message = "El ingrediente activo es obligatorio")
    private String ingredienteActivo;

    @NotNull(message = "La dosis es obligatoria")
    @Positive(message = "La dosis debe ser mayor a cero")
    private Double dosis;

    @NotBlank(message = "La unidad de medida es obligatoria")
    private String unidadMedida;

    @NotNull(message = "El total de mezcla en litros es obligatorio")
    @Positive(message = "El total de mezcla debe ser mayor a cero")
    private Double totalMezclaLitros;

    @NotBlank(message = "El método de aplicación es obligatorio")
    private String metodoAplicacion;

    @NotBlank(message = "La plaga objetivo es obligatoria")
    private String plagaObjetivo;

    @PositiveOrZero(message = "El periodo de seguridad en días no puede ser negativo")
    private Integer periodoSeguridadDias;

    @PositiveOrZero(message = "El costo no puede ser negativo")
    private Double costo;
}