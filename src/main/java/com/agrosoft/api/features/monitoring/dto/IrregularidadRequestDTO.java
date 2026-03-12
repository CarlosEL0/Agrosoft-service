package com.agrosoft.api.features.monitoring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class IrregularidadRequestDTO {

    @NotNull(message = "El ID del cultivo es obligatorio")
    private UUID idCultivo;

    private UUID idRegistro;

    @NotBlank(message = "El tipo de irregularidad es obligatorio")
    private String tipoIrregularidad;

    private String nombrePlaga;
    private String nivelDano;
    private String comentarioAgricultor;

    @NotBlank(message = "La severidad es obligatoria")
    private String severidad;

    private String estado;
    private String descripcion;
}