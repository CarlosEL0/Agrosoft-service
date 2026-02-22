package com.agrosoft.api.features.ai_analysis.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "analisis_ia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalisisIa {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id_analisis", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "id_cultivo", nullable = false)
    private UUID idCultivo;

    @Column(name = "id_irregularidad")
    private UUID idIrregularidad;

    @Column(name = "tipo_analisis", nullable = false)
    private String tipoAnalisis;

    @Column(name = "resultado_analisis", columnDefinition = "TEXT", nullable = false)
    private String resultadoAnalisis;

    @CreationTimestamp
    @Column(name = "fecha_analisis", updatable = false)
    private LocalDateTime fechaAnalisis;
}