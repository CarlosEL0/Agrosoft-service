package com.agrosoft.api.features.notifications.repositories;

import com.agrosoft.api.features.notifications.entities.DescripcionNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DescripcionNotificacionRepository extends JpaRepository<DescripcionNotificacion, UUID> {
    Optional<DescripcionNotificacion> findByTipoNotificacion(String tipoNotificacion);
}