package com.agrosoft.api.features.monitoring.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "registro_crecimiento",
        indexes = {
                @Index(name = "idx_crecimiento_cultivo", columnList = "id_cultivo"),
                @Index(name = "idx_crecimiento_fecha", columnList = "fecha_registro")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroCrecimiento {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id_registro", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "id_cultivo", nullable = false)
    private UUID idCultivo;

    @Column(name = "id_etapa")
    private UUID idEtapa;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "altura_planta", precision = 6, scale = 2)
    private BigDecimal alturaPlanta;

    @Column(name = "grosor_tallo", precision = 6, scale = 2)
    private BigDecimal grosorTallo;

    @Column(name = "diametro", precision = 6, scale = 2)
    private BigDecimal diametro;

    @Column(name = "estado_salud")
    private String estadoSalud;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
}