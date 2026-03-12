package com.agrosoft.api.features.crops.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CultivoRequestDTO {
    private UUID idUsuario;
    private String nombreCultivo;
    private String tipoCultivo;
    private LocalDate fechaSiembra;
    private String notasGenerales;

    @NotNull( message = "La region del cultivo es requerida")
    private String region;

    private Integer tamanoTerreno;
    private Integer cantidadSemillas;
    private BigDecimal phSueloMin;
    private BigDecimal phSueloMax;
}