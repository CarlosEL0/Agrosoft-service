package com.agrosoft.api.features.care_events.services.impl;

import com.agrosoft.api.features.care_events.dto.ReportePodaRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReportePoda;
import com.agrosoft.api.features.care_events.mappers.ReportePodaMapper;
import com.agrosoft.api.features.care_events.repositories.ReportePodaRepository;
import com.agrosoft.api.features.care_events.services.ReportePodaService;
import com.agrosoft.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportePodaServiceImpl implements ReportePodaService {

    private final ReportePodaRepository podaRepository;
    private final ReportePodaMapper podaMapper;

    @Override
    public ReportePoda crearReporte(ReportePodaRequestDTO request) {
        ReportePoda nuevoReporte = podaMapper.toEntity(request);
        return podaRepository.save(nuevoReporte);
    }

    @Override
    public ReportePoda obtenerPorEvento(UUID idEvento) {
        return podaRepository.findByIdEvento(idEvento)
                .orElseThrow(() -> new ResourceNotFoundException("No hay reporte de poda para el evento: " + idEvento));
    }

    @Override
    public ReportePoda actualizarReporte(UUID idPoda, ReportePodaRequestDTO request) {
        ReportePoda reporte = podaRepository.findById(idPoda)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado"));
        podaMapper.updateEntityFromDto(request, reporte);
        return podaRepository.save(reporte);
    }
}