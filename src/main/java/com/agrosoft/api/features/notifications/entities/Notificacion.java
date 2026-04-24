package com.agrosoft.api.features.notifications.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "notificacion",
        indexes = {
                @Index(name = "idx_notif_usuario", columnList = "id_usuario"),
                @Index(name = "idx_notif_recurso", columnList = "id_recurso")
        }
)
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_notificacion", updatable = false, nullable = false)
    private UUID idNotificacion;

    @Column(name = "id_usuario", nullable = false)
    private UUID idUsuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_desc_notificacion", nullable = false)
    private DescripcionNotificacion descripcion;

    @Column(name = "id_recurso")
    private UUID idRecurso;

    @Column(name = "tipo_recurso")
    private String tipoRecurso;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio;

    @Column(nullable = false)
    private boolean leido;
}