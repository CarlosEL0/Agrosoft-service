package com.agrosoft.api.features.notifications.repositories;

import com.agrosoft.api.features.notifications.entities.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, UUID> {
    List<Notificacion> findByIdUsuarioOrderByFechaEnvioDesc(UUID idUsuario);
    // Evitar duplicados: ¿Ya existe una notificación para este recurso (etapa) hoy?
    boolean existsByIdRecursoAndTipoRecurso(UUID idRecurso, String tipoRecurso);
}