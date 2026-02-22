package com.agrosoft.api.features.care_events.repositories;

import com.agrosoft.api.features.care_events.entities.ReporteRiego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReporteRiegoRepository extends JpaRepository<ReporteRiego, UUID> {
    // Buscar el detalle de riego asociado a un evento específico
    Optional<ReporteRiego> findByIdEvento(UUID idEvento);
}