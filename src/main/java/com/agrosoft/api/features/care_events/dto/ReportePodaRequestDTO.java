package com.agrosoft.api.features.care_events.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.util.UUID;

@Data
public class ReportePodaRequestDTO {

    @NotNull(message = "El ID del evento es obligatorio")
    private UUID idEvento;

    @NotBlank(message = "El tipo de poda es obligatorio")
    private String tipoPoda;

    @NotBlank(message = "Las partes podadas son obligatorias")
    private String partesPodadas;

    @NotNull(message = "El porcentaje podado es obligatorio")
    @Positive(message = "El porcentaje debe ser mayor a cero")
    @Max(value = 100, message = "El porcentaje podado no puede ser mayor a 100")
    private Double porcentajePodado;

    @NotBlank(message = "Las herramientas utilizadas son obligatorias")
    private String herramientasUtilizadas;

    @NotBlank(message = "El estado de la planta después de la poda es obligatorio")
    private String estadoPlantaDespues;
}