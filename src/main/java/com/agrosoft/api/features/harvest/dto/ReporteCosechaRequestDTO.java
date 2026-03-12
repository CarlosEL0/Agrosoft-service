package com.agrosoft.api.features.harvest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ReporteCosechaRequestDTO {

    @NotBlank(message = "El idCiclo es obligatorio")
    private UUID idCiclo;

    @NotBlank(message = "El idCultivo es obligatorio")
    private UUID idCultivo;

    @NotBlank(message = "La fecha de cosecha es obligatoria")
    @PastOrPresent(message = "La fecha de cosecha no puede ser en el futuro")
    private LocalDate fechaCosecha;

    @NotBlank(message = "La cantidad de cosechada es obligatoria")
    @Positive(message = "La cantidad de cosechada debe ser positiva")
    private BigDecimal cantidadCosechada;

    @NotBlank(message = "La calidad del cultivo es obligatoria")
    private String calidadCultivo;
}