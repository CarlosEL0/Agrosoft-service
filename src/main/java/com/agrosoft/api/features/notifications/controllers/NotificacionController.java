package com.agrosoft.api.features.notifications.controllers;

import com.agrosoft.api.features.notifications.dto.NotificacionResponseDTO;
import com.agrosoft.api.features.notifications.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    // Obtener mis notificaciones
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<NotificacionResponseDTO>> obtenerMisNotificaciones(@PathVariable UUID idUsuario) {
        return ResponseEntity.ok(notificacionService.obtenerNotificacionesUsuario(idUsuario));
    }

    // Marcar una como leída
    @PatchMapping("/{idNotificacion}/leida")
    public ResponseEntity<Void> marcarLeida(@PathVariable UUID idNotificacion) {
        notificacionService.marcarComoLeida(idNotificacion);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/check-etapas")
    public ResponseEntity<String> ejecutarRevisionManual() {
        notificacionService.generarNotificacionesFinEtapa();
        return ResponseEntity.ok("Revisión de etapas finalizada.");
    }
}