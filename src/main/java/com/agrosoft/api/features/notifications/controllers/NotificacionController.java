package com.agrosoft.api.features.notifications.controllers;

import com.agrosoft.api.features.notifications.dto.NotificacionResponseDTO;
import com.agrosoft.api.features.notifications.service.NotificacionService;
import com.agrosoft.api.shared.response.ApiResponse;
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

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<ApiResponse<List<NotificacionResponseDTO>>> obtenerMisNotificaciones(@PathVariable UUID idUsuario) {
        return ResponseEntity.ok(ApiResponse.success("Notificaciones recuperadas", notificacionService.obtenerNotificacionesUsuario(idUsuario)));
    }

    @PatchMapping("/{idNotificacion}/leida")
    public ResponseEntity<ApiResponse<Void>> marcarLeida(@PathVariable UUID idNotificacion) {
        notificacionService.marcarComoLeida(idNotificacion);
        return ResponseEntity.ok(ApiResponse.success("Notificación marcada como leída"));
    }

    @PostMapping("/check-etapas")
    public ResponseEntity<ApiResponse<String>> ejecutarRevisionManual() {
        notificacionService.generarNotificacionesFinEtapa();
        return ResponseEntity.ok(ApiResponse.success("Revisión de etapas finalizada exitosamente", "Revisión completada"));
    }
}