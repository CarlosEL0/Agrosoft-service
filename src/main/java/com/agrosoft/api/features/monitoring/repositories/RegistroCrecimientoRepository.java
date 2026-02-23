package com.agrosoft.api.features.monitoring.repositories;

import com.agrosoft.api.features.monitoring.entities.RegistroCrecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegistroCrecimientoRepository extends JpaRepository<RegistroCrecimiento, UUID> {

    // Para ver el historial de crecimiento de un cultivo ordenado del más nuevo al más viejo
    List<RegistroCrecimiento> findByIdCultivoOrderByFechaRegistroDesc(UUID idCultivo);
}