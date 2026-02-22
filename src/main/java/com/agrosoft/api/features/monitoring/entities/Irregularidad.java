package com.agrosoft.api.features.monitoring.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "irregularidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Irregularidad {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id_irregularidad", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "id_cultivo", nullable = false)
    private UUID idCultivo;

    @Column(name = "id_registro")
    private UUID idRegistro;

    @Column(name = "tipo_irregularidad", nullable = false)
    private String tipoIrregularidad;

    @Column(name = "nombre_plaga")
    private String nombrePlaga;

    @Column(name = "nivel_dano")
    private String nivelDano;

    @Column(name = "comentario_agricultor", columnDefinition = "TEXT")
    private String comentarioAgricultor;

    @Column(name = "severidad")
    private String severidad;

    @CreationTimestamp
    @Column(name = "fecha_deteccion", updatable = false)
    private LocalDateTime fechaDeteccion;

    @Column(name = "estado", nullable = false)
    @Builder.Default
    private String estado = "activa";

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
}