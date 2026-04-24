package com.agrosoft.api.features.harvest.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ReporteCosechaResponseDTO {
    private UUID id;
    private UUID idCiclo;
    private UUID idCultivo;
    private UUID idAnalisis;

    // Datos del agricultor
    private LocalDate fechaCosecha;
    private BigDecimal cantidadCosechada;
    private String calidadCultivo;

    // Métricas calculadas por la IA
    private BigDecimal rendimientoEsperado;
    private BigDecimal desviacionRendimiento;
    private BigDecimal eficienciaRiego;
    private BigDecimal costoTotal;
    private BigDecimal costoPorKg;

    // Conclusiones de la IA
    private String resumenCiclo;
    private String factoresExito;
    private String areasMejora;

    private LocalDateTime fechaGeneracion;
}