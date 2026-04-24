package com.agrosoft.api.features.harvest.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reporte_cosecha")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteCosecha {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id_cosecha", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "id_ciclo", nullable = false)
    private UUID idCiclo;

    @Column(name = "id_cultivo", nullable = false)
    private UUID idCultivo;

    @Column(name = "id_analisis", nullable = false)
    private UUID idAnalisis;

    @Column(name = "fecha_cosecha", nullable = false)
    private LocalDate fechaCosecha;

    @Column(name = "cantidad_cosechada", precision = 10, scale = 2, nullable = false)
    private BigDecimal cantidadCosechada;

    @Column(name = "calidad_cultivo", nullable = false)
    private String calidadCultivo;

    // ==========================================
    // MÉTRICAS CALCULADAS POR IA
    // ==========================================
    @Column(name = "rendimiento_esperado", precision = 10, scale = 2)
    private BigDecimal rendimientoEsperado;

    @Column(name = "desviacion_rendimiento", precision = 5, scale = 2)
    private BigDecimal desviacionRendimiento;

    @Column(name = "eficiencia_riego", precision = 5, scale = 2)
    private BigDecimal eficienciaRiego;

    @Column(name = "costo_total", precision = 10, scale = 2)
    private BigDecimal costoTotal;

    @Column(name = "costo_por_kg", precision = 10, scale = 2)
    private BigDecimal costoPorKg;

    // ==========================================
    // CONCLUSIONES DE LA IA
    // ==========================================
    @Column(name = "resumen_ciclo", columnDefinition = "TEXT")
    private String resumenCiclo;

    @Column(name = "factores_exito", columnDefinition = "TEXT")
    private String factoresExito;

    @Column(name = "areas_mejora", columnDefinition = "TEXT")
    private String areasMejora;

    @CreationTimestamp
    @Column(name = "fecha_generacion", updatable = false)
    private LocalDateTime fechaGeneracion;
}