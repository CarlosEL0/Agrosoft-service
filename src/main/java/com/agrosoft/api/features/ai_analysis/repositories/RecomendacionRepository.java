package com.agrosoft.api.features.ai_analysis.repositories;

import com.agrosoft.api.features.ai_analysis.entities.Recomendacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecomendacionRepository extends JpaRepository<Recomendacion, UUID> {

    // Para ver las recomendaciones pendientes de un cultivo
    List<Recomendacion> findByIdCultivoAndAplicadaFalse(UUID idCultivo);
}