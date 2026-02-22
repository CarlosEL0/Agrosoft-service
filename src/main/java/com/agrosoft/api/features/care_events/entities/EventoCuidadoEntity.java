package com.agrosoft.api.features.care_events.entities;

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
        name = "evento_cuidado",
        indexes = {
                @Index(name = "idx_evento_cultivo", columnList = "id_cultivo"),
                @Index(name = "idx_evento_fecha", columnList = "fecha_evento")
        }
)
public class EventoCuidadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_evento", updatable = false, nullable = false)
    private UUID idEvento;

    @Column(name = "id_cultivo", nullable = false)
    private UUID idCultivo;

    @Column(name = "id_etapa") // Puede ser nulo
    private UUID idEtapa;

    @Column(name = "tipo_evento", nullable = false)
    private String tipoEvento;

    @Column(name = "fecha_evento", nullable = false)
    private LocalDate fechaEvento;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
}