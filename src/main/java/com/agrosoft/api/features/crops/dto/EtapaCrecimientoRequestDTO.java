package com.agrosoft.api.features.crops.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class EtapaCrecimientoRequestDTO {
    private UUID idCiclo;
    private String nombreEtapa;
    private Integer ordenEtapa;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}