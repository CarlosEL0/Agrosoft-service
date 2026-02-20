package com.agrosoft.api.features.crops.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "etapa_crecimiento",
        indexes = {
                @Index(name = "idx_etapa_ciclo", columnList = "id_ciclo"),
                @Index(name = "idx_etapa_orden", columnList = "orden_etapa")
        }
)
public class EtapaCrecimientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_etapa", updatable = false, nullable = false)
    private UUID idEtapa;

    @Column(name = "id_ciclo", nullable = false)
    private UUID idCiclo;

    @Column(name = "nombre_etapa", nullable = false)
    private String nombreEtapa;

    @Column(name = "orden_etapa", nullable = false)
    private Integer ordenEtapa;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
}