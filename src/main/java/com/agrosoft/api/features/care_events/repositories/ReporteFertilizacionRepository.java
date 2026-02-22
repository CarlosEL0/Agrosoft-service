package com.agrosoft.api.features.care_events.repositories;

import com.agrosoft.api.features.care_events.entities.ReporteFertilizacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReporteFertilizacionRepository extends JpaRepository<ReporteFertilizacionEntity, UUID> {

    // Buscar el detalle por el ID del evento padre
    Optional<ReporteFertilizacionEntity> findByIdEvento(UUID idEvento);
}