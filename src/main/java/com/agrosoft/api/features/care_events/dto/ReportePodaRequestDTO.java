package com.agrosoft.api.features.care_events.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ReportePodaRequestDTO {
    private UUID idEvento;
    private String tipoPoda;
    private String partesPodadas;
    private Double porcentajePodado;
    private String herramientasUtilizadas;
    private String estadoPlantaDespues;
}