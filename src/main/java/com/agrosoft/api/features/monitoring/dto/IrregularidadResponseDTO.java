package com.agrosoft.api.features.monitoring.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class IrregularidadResponseDTO {
    private UUID id;
    private UUID idCultivo;
    private UUID idRegistro;
    private String tipoIrregularidad;
    private String nombrePlaga;
    private String nivelDano;
    private String comentarioAgricultor;
    private String severidad;
    private String estado;
    private String descripcion;
    private LocalDateTime fechaDeteccion;
}