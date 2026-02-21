package com.agrosoft.api.features.ai_analysis.repositories;

import com.agrosoft.api.features.ai_analysis.entities.AnalisisIa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnalisisIaRepository extends JpaRepository<AnalisisIa, UUID> {

    // Método para buscar todos los análisis de un cultivo específico
    List<AnalisisIa> findByIdCultivoOrderByFechaAnalisisDesc(UUID idCultivo);
}