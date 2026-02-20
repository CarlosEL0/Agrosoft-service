package com.agrosoft.api.features.crops.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class FaseAgricolaRequestDTO {
    private UUID idCultivo;
    private Integer numeroCiclo;
    private String nombreCiclo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
}