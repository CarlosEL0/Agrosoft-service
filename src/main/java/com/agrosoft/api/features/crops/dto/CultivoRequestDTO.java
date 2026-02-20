package com.agrosoft.api.features.crops.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CultivoRequestDTO {
    private UUID idUsuario;
    private String nombreCultivo;
    private String tipoCultivo;
    private LocalDate fechaSiembra;
    private String notasGenerales;

    private BigDecimal alturaEsperada;
    private Integer diasGerminacion;
    private Integer diasVegetativo;
    private Integer diasFloracion;
    private Integer diasCosecha;
    private Integer tamanoTerreno;
    private Integer cantidadSemillas;
    private BigDecimal phSueloMin;
    private BigDecimal phSueloMax;
}