package com.agrosoft.api.features.care_events.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class EventoCuidadoRequestDTO {

    @NotNull(message = "El ID del cultivo es obligatorio")
    private UUID idCultivo;

    private UUID idEtapa;

    @NotBlank(message = "El tipo de evento es obligatorio")
    private String tipoEvento; // riego, fertilizacion, fumigacion, poda

    @NotNull(message = "La fecha del evento es obligatoria")
    @PastOrPresent(message = "La fecha del evento no puede ser en el futuro")
    private LocalDate fechaEvento;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    private String observaciones;
}