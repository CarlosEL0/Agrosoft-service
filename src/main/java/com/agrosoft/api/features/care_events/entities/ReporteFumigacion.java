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
        name = "reporte_fumigacion",
        indexes = {
                @Index(name = "idx_fumigacion_evento", columnList = "id_evento"),
                @Index(name = "idx_fumigacion_tipo", columnList = "tipo_producto")
        }
)
public class ReporteFumigacion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_fumigacion", updatable = false, nullable = false)
    private UUID idFumigacion;

    @Column(name = "id_evento", nullable = false)
    private UUID idEvento;

    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;

    @Column(name = "tipo_producto")
    private String tipoProducto;

    @Column(name = "ingrediente_activo")
    private String ingredienteActivo;

    @Column(name = "dosis")
    private Double dosis;

    @Column(name = "unidad_medida")
    private String unidadMedida;

    @Column(name = "total_mezcla_litros")
    private Double totalMezclaLitros;

    @Column(name = "metodo_aplicacion")
    private String metodoAplicacion;

    @Column(name = "plaga_objetivo")
    private String plagaObjetivo;

    @Column(name = "periodo_seguridad_dias")
    private Integer periodoSeguridadDias;

    @Column(name = "costo")
    private Double costo;
}