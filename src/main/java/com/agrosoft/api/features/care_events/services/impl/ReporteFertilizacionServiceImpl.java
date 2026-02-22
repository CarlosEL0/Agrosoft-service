package com.agrosoft.api.features.care_events.services.impl;

import com.agrosoft.api.features.care_events.dto.ReporteFertilizacionRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReporteFertilizacionEntity;
import com.agrosoft.api.features.care_events.mappers.ReporteFertilizacionMapper;
import com.agrosoft.api.features.care_events.repositories.ReporteFertilizacionRepository;
import com.agrosoft.api.features.care_events.service.ReporteFertilizacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReporteFertilizacionServiceImpl implements ReporteFertilizacionService {

    private final ReporteFertilizacionRepository fertilizacionRepository;
    private final ReporteFertilizacionMapper fertilizacionMapper;

    @Override
    public ReporteFertilizacionEntity crearReporte(ReporteFertilizacionRequestDTO request) {
        ReporteFertilizacionEntity nuevoReporte = fertilizacionMapper.toEntity(request);
        return fertilizacionRepository.save(nuevoReporte);
    }

    @Override
    public ReporteFertilizacionEntity obtenerPorEvento(UUID idEvento) {
        return fertilizacionRepository.findByIdEvento(idEvento)
                .orElseThrow(() -> new RuntimeException("No hay reporte de fertilización para el evento: " + idEvento));
    }

    @Override
    public ReporteFertilizacionEntity actualizarReporte(UUID idFertilizacion, ReporteFertilizacionRequestDTO request) {
        ReporteFertilizacionEntity reporte = fertilizacionRepository.findById(idFertilizacion)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
        fertilizacionMapper.updateEntityFromDto(request, reporte);
        return fertilizacionRepository.save(reporte);
    }
}