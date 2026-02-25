package com.agrosoft.api.features.notifications.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class NotificacionResponseDTO {
    private UUID idNotificacion;
    private String titulo;
    private String mensaje;
    private String tipoNotificacion;
    private LocalDateTime fechaEnvio;
    private boolean leido;
    private UUID idRecurso;
    private String tipoRecurso;
}