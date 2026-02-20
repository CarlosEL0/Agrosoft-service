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
        name = "face_agricola",
        indexes = {
                @Index(name = "idx_face_cultivo", columnList = "id_cultivo"),
                @Index(name = "idx_face_numero_ciclo", columnList = "numero_ciclo")
        }
)
public class FaseAgricolaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_ciclo", updatable = false, nullable = false)
    private UUID idCiclo;

    @Column(name = "id_cultivo", nullable = false)
    private UUID idCultivo;

    @Column(name = "numero_ciclo", nullable = false)
    private Integer numeroCiclo;

    @Column(name = "nombre_ciclo", nullable = false)
    private String nombreCiclo;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "estado", nullable = false)
    @Builder.Default
    private String estado = "en_proceso";
}