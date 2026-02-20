package com.agrosoft.api.features.crops.repositories;

import com.agrosoft.api.features.crops.entities.EtapaCrecimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EtapaCrecimientoRepository extends JpaRepository<EtapaCrecimientoEntity, UUID> {

    List<EtapaCrecimientoEntity> findByIdCicloOrderByOrdenEtapaAsc(UUID idCiclo);
}