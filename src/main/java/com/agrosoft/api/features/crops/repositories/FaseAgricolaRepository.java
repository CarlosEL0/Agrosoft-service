package com.agrosoft.api.features.crops.repositories;

import com.agrosoft.api.features.crops.entities.FaseAgricola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FaseAgricolaRepository extends JpaRepository<FaseAgricola, UUID> {

    List<FaseAgricola> findByIdCultivoOrderByNumeroCicloAsc(UUID idCultivo);
}