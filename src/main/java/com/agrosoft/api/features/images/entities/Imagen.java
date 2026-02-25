package com.agrosoft.api.features.images.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "imagen", indexes = {
        @Index(name = "idx_img_crecimiento", columnList = "id_registro_crecimiento"),
        @Index(name = "idx_img_irregularidad", columnList = "id_irregularidad"),
        @Index(name = "idx_img_riego", columnList = "id_riego"),
        @Index(name = "idx_img_fumigacion", columnList = "id_fumigacion")
})
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_imagen", updatable = false, nullable = false)
    private UUID idImagen;

    @Column(name = "url_archivo", nullable = false)
    private String urlArchivo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_subida", nullable = false)
    private LocalDateTime fechaSubida;

    // --- Referencias Opcionales (Solo una estará llena) ---

    @Column(name = "id_registro_crecimiento")
    private UUID idRegistroCrecimiento;

    @Column(name = "id_riego")
    private UUID idRiego;

    @Column(name = "id_fertilizacion")
    private UUID idFertilizacion;

    @Column(name = "id_fumigacion")
    private UUID idFumigacion;

    @Column(name = "id_poda")
    private UUID idPoda;

    @Column(name = "id_irregularidad")
    private UUID idIrregularidad;

    // Método helper para asignar fecha antes de guardar
    @PrePersist
    protected void onCreate() {
        if (this.fechaSubida == null) {
            this.fechaSubida = LocalDateTime.now();
        }
    }
}