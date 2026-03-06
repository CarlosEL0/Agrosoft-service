package com.agrosoft.api.features.care_events.repositories;

import com.agrosoft.api.features.care_events.entities.ReporteFertilizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReporteFertilizacionRepository extends JpaRepository<ReporteFertilizacion, UUID> {

    // Buscar el detalle por el ID del evento padre
    Optional<ReporteFertilizacion> findByIdEvento(UUID idEvento);
}