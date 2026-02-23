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
        name = "reporte_poda",
        indexes = {
                @Index(name = "idx_poda_evento", columnList = "id_evento")
        }
)
public class ReportePoda {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_poda", updatable = false, nullable = false)
    private UUID idPoda;

    @Column(name = "id_evento", nullable = false)
    private UUID idEvento;

    @Column(name = "tipo_poda")
    private String tipoPoda;

    @Column(name = "partes_podadas")
    private String partesPodadas;

    @Column(name = "porcentaje_podado")
    private Double porcentajePodado;

    @Column(name = "herramientas_utilizadas")
    private String herramientasUtilizadas;

    @Column(name = "estado_planta_despues")
    private String estadoPlantaDespues;
}