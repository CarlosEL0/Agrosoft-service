package com.agrosoft.api.features.harvest.repositories;

import com.agrosoft.api.features.harvest.entities.ReporteCosecha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReporteCosechaRepository extends JpaRepository<ReporteCosecha, UUID> {

    // Útil para que el agricultor vea todos sus reportes históricos de un cultivo
    List<ReporteCosecha> findByIdCultivoOrderByFechaGeneracionDesc(UUID idCultivo);

    // Útil para buscar si un ciclo específico ya tiene su reporte de cosecha cerrado
    boolean existsByIdCiclo(UUID idCiclo);
}