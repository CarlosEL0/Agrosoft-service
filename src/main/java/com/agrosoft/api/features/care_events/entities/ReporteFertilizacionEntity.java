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
        name = "reporte_fertilizacion",
        indexes = {
                @Index(name = "idx_fertilizacion_evento", columnList = "id_evento")
        }
)
public class ReporteFertilizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_fertilizacion", updatable = false, nullable = false)
    private UUID idFertilizacion;

    @Column(name = "id_evento", nullable = false)
    private UUID idEvento;

    @Column(name = "tipo_fertilizante", nullable = false)
    private String tipoFertilizante;

    @Column(name = "nombre_fertilizante")
    private String nombreFertilizante;

    @Column(name = "cantidad_aplicada")
    private Double cantidadAplicada;

    @Column(name = "unidad_medida")
    private String unidadMedida;

    @Column(name = "metodo_aplicacion")
    private String metodoAplicacion;

    @Column(name = "costo")
    private Double costo;
}