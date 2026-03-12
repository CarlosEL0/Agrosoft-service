package com.agrosoft.api.features.monitoring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class RegistroCrecimientoRequestDTO {

    @NotBlank(message = "El idCultivo es obligatorio")
    private UUID idCultivo;

    private UUID idEtapa;

    @NotNull(message = "La fecha de registro es obligatoria")
    @PastOrPresent(message = "La fecha de registro no puede ser en el futuro")
    private LocalDate fechaRegistro;

    @Positive(message = "La altura de la planta debe ser positiva")
    private BigDecimal alturaPlanta;

    @Positive(message = "El grosor del tallo debe ser positivo")
    private BigDecimal grosorTallo;

    @Positive(message = "El diametro de la planta debe ser positivo")
    private BigDecimal diametro;

    private String estadoSalud;
    private String observaciones;
}