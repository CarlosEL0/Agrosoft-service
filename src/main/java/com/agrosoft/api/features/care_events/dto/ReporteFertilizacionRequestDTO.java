package com.agrosoft.api.features.care_events.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ReporteFertilizacionRequestDTO {
    private UUID idEvento;
    private String tipoFertilizante;
    private String nombreFertilizante;
    private Double cantidadAplicada;
    private String unidadMedida;
    private String metodoAplicacion;
    private Double costo;
}