package com.agrosoft.api.features.care_events.services.impl;

import com.agrosoft.api.features.care_events.dto.ReporteRiegoRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReporteRiego;
import com.agrosoft.api.features.care_events.mappers.ReporteRiegoMapper;
import com.agrosoft.api.features.care_events.repositories.ReporteRiegoRepository;
import com.agrosoft.api.features.care_events.service.ReporteRiegoService;
import com.agrosoft.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReporteRiegoServiceImpl implements ReporteRiegoService {

    private final ReporteRiegoRepository riegoRepository;
    private final ReporteRiegoMapper riegoMapper;

    @Override
    public ReporteRiego crearReporte(ReporteRiegoRequestDTO request) {
        // Aquí podríamos verificar que el evento exista llamando al otro repositorio,
        // pero por desacoplamiento confiamos en que el ID es correcto o dejaremos que
        // la BD lance error si activaste la FK.
        ReporteRiego nuevoReporte = riegoMapper.toEntity(request);
        return riegoRepository.save(nuevoReporte);
    }

    @Override
    public ReporteRiego obtenerPorEvento(UUID idEvento) {
        return riegoRepository.findByIdEvento(idEvento)
                .orElseThrow(() -> new ResourceNotFoundException("No hay reporte de riego para el evento: " + idEvento));
    }

    @Override
    public ReporteRiego actualizarReporte(UUID idRiego, ReporteRiegoRequestDTO request) {
        ReporteRiego reporte = riegoRepository.findById(idRiego)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado"));
        riegoMapper.updateEntityFromDto(request, reporte);
        return riegoRepository.save(reporte);
    }
}