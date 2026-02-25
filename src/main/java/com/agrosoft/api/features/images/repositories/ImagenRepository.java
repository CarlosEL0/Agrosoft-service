package com.agrosoft.api.features.images.repositories;

import com.agrosoft.api.features.images.entities.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, UUID> {

    // Métodos para buscar evidencias de cada tipo
    List<Imagen> findByIdRegistroCrecimiento(UUID id);
    List<Imagen> findByIdRiego(UUID id);
    List<Imagen> findByIdFertilizacion(UUID id);
    List<Imagen> findByIdFumigacion(UUID id);
    List<Imagen> findByIdPoda(UUID id);
    List<Imagen> findByIdIrregularidad(UUID id);
}