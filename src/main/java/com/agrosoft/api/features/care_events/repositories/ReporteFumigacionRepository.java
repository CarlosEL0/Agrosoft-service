package com.agrosoft.api.features.care_events.repositories;

import com.agrosoft.api.features.care_events.entities.ReporteFumigacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReporteFumigacionRepository extends JpaRepository<ReporteFumigacion, UUID> {

    Optional<ReporteFumigacion> findByIdEvento(UUID idEvento);
}