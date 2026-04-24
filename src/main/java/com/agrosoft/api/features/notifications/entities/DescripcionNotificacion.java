package com.agrosoft.api.features.notifications.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "descripcion_notificacion")
public class DescripcionNotificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_desc_notificacion", updatable = false, nullable = false)
    private UUID idDescNotificacion;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String mensajeBase;

    // "FIN_ETAPA", "ALERTA_RIEGO", etc.
    @Column(name = "tipo_notificacion", nullable = false, unique = true)
    private String tipoNotificacion;
}