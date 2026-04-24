package com.agrosoft.api.features.crops.repositories;

import com.agrosoft.api.features.crops.entities.EtapaCrecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EtapaCrecimientoRepository extends JpaRepository<EtapaCrecimiento, UUID> {
    List<EtapaCrecimiento> findByIdCicloOrderByOrdenEtapaAsc(UUID idCiclo);
    List<EtapaCrecimiento> findByFechaFin(LocalDate fechaFin);
}