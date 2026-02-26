package com.agrosoft.api.features.care_events.services.impl;

import com.agrosoft.api.features.care_events.dto.ReporteFumigacionRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReporteFumigacion;
import com.agrosoft.api.features.care_events.mappers.ReporteFumigacionMapper;
import com.agrosoft.api.features.care_events.repositories.ReporteFumigacionRepository;
import com.agrosoft.api.features.care_events.service.ReporteFumigacionService;
import com.agrosoft.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReporteFumigacionServiceImpl implements ReporteFumigacionService {

    private final ReporteFumigacionRepository fumigacionRepository;
    private final ReporteFumigacionMapper fumigacionMapper;

    @Override
    public ReporteFumigacion crearReporte(ReporteFumigacionRequestDTO request) {
        ReporteFumigacion nuevoReporte = fumigacionMapper.toEntity(request);
        return fumigacionRepository.save(nuevoReporte);
    }

    @Override
    public ReporteFumigacion obtenerPorEvento(UUID idEvento) {
        return fumigacionRepository.findByIdEvento(idEvento)
                .orElseThrow(() -> new ResourceNotFoundException("No hay reporte de fumigación para el evento: " + idEvento));
    }

    @Override
    public ReporteFumigacion actualizarReporte(UUID idFumigacion, ReporteFumigacionRequestDTO request) {
        ReporteFumigacion reporte = fumigacionRepository.findById(idFumigacion)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado"));
        fumigacionMapper.updateEntityFromDto(request, reporte);
        return fumigacionRepository.save(reporte);
    }
}