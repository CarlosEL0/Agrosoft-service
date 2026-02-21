package com.agrosoft.api.features.ai_analysis.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "recomendacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recomendacion {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id_recomendacion", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "id_analisis", nullable = false)
    private UUID idAnalisis;

    @Column(name = "id_cultivo", nullable = false)
    private UUID idCultivo;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @Column(name = "prioridad")
    private String prioridad; // baja, media, alta

    @Column(nullable = false)
    private Boolean aplicada = false;
}