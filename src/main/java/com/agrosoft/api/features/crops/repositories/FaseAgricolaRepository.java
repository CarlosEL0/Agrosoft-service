package com.agrosoft.api.features.crops.repositories;

import com.agrosoft.api.features.crops.entities.FaseAgricolaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FaseAgricolaRepository extends JpaRepository<FaseAgricolaEntity, UUID> {

    List<FaseAgricolaEntity> findByIdCultivoOrderByNumeroCicloAsc(UUID idCultivo);
}