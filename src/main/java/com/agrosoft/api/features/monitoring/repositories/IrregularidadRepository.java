package com.agrosoft.api.features.monitoring.repositories;

import com.agrosoft.api.features.monitoring.entities.Irregularidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IrregularidadRepository extends JpaRepository<Irregularidad, UUID> {

    // Traer el historial de plagas de un cultivo, de la más reciente a la más antigua
    List<Irregularidad> findByIdCultivoOrderByFechaDeteccionDesc(UUID idCultivo);

    // Filtrar plagas por estado (ej. "activa")
    List<Irregularidad> findByIdCultivoAndEstado(UUID idCultivo, String estado);
}