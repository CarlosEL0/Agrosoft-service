package com.agrosoft.api.features.crops.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "cultivo",
        indexes = {
                @Index(name = "idx_cultivo_usuario", columnList = "id_usuario"),
                @Index(name = "idx_cultivo_fecha_siembra", columnList = "fecha_siembra"),
                @Index(name = "idx_cultivo_tipo", columnList = "tipo_cultivo")
        }
)
public class CultivoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_cultivo", updatable = false, nullable = false)
    private UUID idCultivo;

    @Column(name = "id_usuario", nullable = false)
    private UUID idUsuario;

    @Column(name = "nombre_cultivo", nullable = false)
    private String nombreCultivo;

    @Column(name = "tipo_cultivo", nullable = false)
    private String tipoCultivo;

    @Column(name = "fecha_siembra", nullable = false)
    private LocalDate fechaSiembra;

    @Column(name = "notas_generales", columnDefinition = "TEXT")
    private String notasGenerales;

    @Column(name = "altura_esperada", precision = 10, scale = 2)
    private BigDecimal alturaEsperada;

    @Column(name = "dias_germinacion")
    private Integer diasGerminacion;

    @Column(name = "dias_vegetativo")
    private Integer diasVegetativo;

    @Column(name = "dias_floracion")
    private Integer diasFloracion;

    @Column(name = "dias_cosecha")
    private Integer diasCosecha;

    @Column(name = "tamano_terreno")
    private Integer tamanoTerreno;

    @Column(name ="cantidad_semillas")
    private Integer cantidadSemillas;

    @Column(name = "ph_suelo_min", precision = 3, scale = 1)
    private BigDecimal phSueloMin;

    @Column(name = "ph_suelo_max", precision = 3, scale = 1)
    private BigDecimal phSueloMax;
}