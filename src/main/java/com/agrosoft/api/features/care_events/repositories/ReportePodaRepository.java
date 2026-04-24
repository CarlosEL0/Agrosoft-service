package com.agrosoft.api.features.care_events.repositories;

import com.agrosoft.api.features.care_events.entities.ReportePoda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReportePodaRepository extends JpaRepository<ReportePoda, UUID> {

    Optional<ReportePoda> findByIdEvento(UUID idEvento);
}