package com.agrosoft.api.features.notifications.services;

import com.agrosoft.api.features.notifications.dto.NotificacionResponseDTO;
import java.util.List;
import java.util.UUID;

public interface NotificacionService {
    void generarNotificacionesFinEtapa();
    List<NotificacionResponseDTO> obtenerNotificacionesUsuario(UUID idUsuario);
    void marcarComoLeida(UUID idNotificacion);
}