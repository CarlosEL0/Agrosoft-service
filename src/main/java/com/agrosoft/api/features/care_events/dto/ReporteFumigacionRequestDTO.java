package com.agrosoft.api.features.care_events.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ReporteFumigacionRequestDTO {
    private UUID idEvento;
    private String nombreProducto;
    private String tipoProducto;
    private String ingredienteActivo;
    private Double dosis;
    private String unidadMedida;
    private Double totalMezclaLitros;
    private String metodoAplicacion;
    private String plagaObjetivo;
    private Integer periodoSeguridadDias;
    private Double costo;
}