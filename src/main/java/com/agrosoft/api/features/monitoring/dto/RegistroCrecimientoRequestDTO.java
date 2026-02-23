package com.agrosoft.api.features.monitoring.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class RegistroCrecimientoRequestDTO {
    private UUID idCultivo;
    private UUID idEtapa;
    private LocalDate fechaRegistro;
    private BigDecimal alturaPlanta;
    private BigDecimal grosorTallo;
    private BigDecimal diametro;
    private String estadoSalud;
    private String observaciones;
}