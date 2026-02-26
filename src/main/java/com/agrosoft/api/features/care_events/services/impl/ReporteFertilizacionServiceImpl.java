package com.agrosoft.api.features.care_events.services.impl;

import com.agrosoft.api.features.care_events.dto.ReporteFertilizacionRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReporteFertilizacion;
import com.agrosoft.api.features.care_events.mappers.ReporteFertilizacionMapper;
import com.agrosoft.api.features.care_events.repositories.ReporteFertilizacionRepository;
import com.agrosoft.api.features.care_events.services.ReporteFertilizacionService;
import com.agrosoft.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReporteFertilizacionServiceImpl implements ReporteFertilizacionService {

    private final ReporteFertilizacionRepository fertilizacionRepository;
    private final ReporteFertilizacionMapper fertilizacionMapper;

    @Override
    public ReporteFertilizacion crearReporte(ReporteFertilizacionRequestDTO request) {
        ReporteFertilizacion nuevoReporte = fertilizacionMapper.toEntity(request);
        return fertilizacionRepository.save(nuevoReporte);
    }

    @Override
    public ReporteFertilizacion obtenerPorEvento(UUID idEvento) {
        return fertilizacionRepository.findByIdEvento(idEvento)
                .orElseThrow(() -> new ResourceNotFoundException("No hay reporte de fertilización para el evento: " + idEvento));
    }

    @Override
    public ReporteFertilizacion actualizarReporte(UUID idFertilizacion, ReporteFertilizacionRequestDTO request) {
        ReporteFertilizacion reporte = fertilizacionRepository.findById(idFertilizacion)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado"));
        fertilizacionMapper.updateEntityFromDto(request, reporte);
        return fertilizacionRepository.save(reporte);
    }
}