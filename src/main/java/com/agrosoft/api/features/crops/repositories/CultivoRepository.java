package com.agrosoft.api.features.crops.repositories;

import com.agrosoft.api.features.crops.entities.CultivoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface CultivoRepository extends JpaRepository<CultivoEntity, UUID> {

    List<CultivoEntity> findByIdUsuario(UUID idUsuario);
}