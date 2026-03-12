package com.agrosoft.api.features.crops.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CultivoRequestDTO {

    @NotNull( message = "El ID del usuario es requerido")
    private UUID idUsuario;

    @NotBlank( message = "El nombre del cultivo es requerido")
    private String nombreCultivo;

    private String tipoCultivo;

    @NotBlank( message = "La fecha de siembra es requerida")
    @PastOrPresent( message = "La fecha de siembra no puede ser en el futuro")
    private LocalDate fechaSiembra;

    private String notasGenerales;

    @NotNull( message = "La region del cultivo es requerida")
    private String region;

    @NotBlank(message = "El tamaño del terreno es obligatorio")
    private Integer tamanoTerreno;

    @NotNull(message = "La cantidad de semillas es obligatoria")
    @Min(value = 1, message = "La cantidad de semillas debe ser mayor o igual a 1")
    private Integer cantidadSemillas;

    @NegativeOrZero(message = "El PH minimo no puede ser negativo")
    private BigDecimal phSueloMin;

    @NegativeOrZero(message = "El PH maximo no puede ser negativo")
    private BigDecimal phSueloMax;
}