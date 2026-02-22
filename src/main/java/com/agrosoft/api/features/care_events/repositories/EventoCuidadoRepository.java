package com.agrosoft.api.features.care_events.repositories;

import com.agrosoft.api.features.care_events.entities.EventoCuidadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventoCuidadoRepository extends JpaRepository<EventoCuidadoEntity, UUID> {

    // Historial completo ordenado por fecha (lo más nuevo primero)
    List<EventoCuidadoEntity> findByIdCultivoOrderByFechaEventoDesc(UUID idCultivo);
    // Filtrar por tipo (ej: "Solo quiero ver los riegos")
    List<EventoCuidadoEntity> findByIdCultivoAndTipoEventoOrderByFechaEventoDesc(UUID idCultivo, String tipoEvento);
}