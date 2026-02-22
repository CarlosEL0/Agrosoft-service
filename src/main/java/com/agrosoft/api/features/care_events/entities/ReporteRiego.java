package com.agrosoft.api.features.care_events.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "reporte_riego",
        indexes = {
                @Index(name = "idx_riego_evento", columnList = "id_evento")
        }
)
public class ReporteRiego {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_riego", updatable = false, nullable = false)
    private UUID idRiego;

    @Column(name = "id_evento", nullable = false)
    private UUID idEvento;

    @Column(name = "cantidad_agua")
    private Double cantidadAgua;

    @Column(name = "metodo_riego")
    private String metodoRiego;

    @Column(name = "duracion_minutos")
    private Integer duracionMinutos;
}