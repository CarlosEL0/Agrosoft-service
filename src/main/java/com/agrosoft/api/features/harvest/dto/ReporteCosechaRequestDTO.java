package com.agrosoft.api.features.harvest.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ReporteCosechaRequestDTO {

    private UUID idCiclo;
    private UUID idCultivo;

    private LocalDate fechaCosecha;
    private BigDecimal cantidadCosechada;
    private String calidadCultivo;
}