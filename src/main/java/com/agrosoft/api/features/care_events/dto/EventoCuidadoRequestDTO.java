package com.agrosoft.api.features.care_events.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class EventoCuidadoRequestDTO {
    private UUID idCultivo;
    private UUID idEtapa;
    private String tipoEvento; // riego, fertilizacion, fumigacion, poda
    private LocalDate fechaEvento;
    private String descripcion;
    private String observaciones;
}