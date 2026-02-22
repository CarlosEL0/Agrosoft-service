package com.agrosoft.api.features.care_events.services.impl;

import com.agrosoft.api.features.care_events.dto.EventoCuidadoRequestDTO;
import com.agrosoft.api.features.care_events.entities.EventoCuidadoEntity;
import com.agrosoft.api.features.care_events.mappers.EventoCuidadoMapper;
import com.agrosoft.api.features.care_events.repositories.EventoCuidadoRepository;
import com.agrosoft.api.features.care_events.service.EventoCuidadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventoCuidadoServiceImpl implements EventoCuidadoService {

    private final EventoCuidadoRepository eventoRepository;
    private final EventoCuidadoMapper eventoMapper;

    @Override
    public EventoCuidadoEntity crearEvento(EventoCuidadoRequestDTO request) {
        EventoCuidadoEntity nuevoEvento = eventoMapper.toEntity(request);
        return eventoRepository.save(nuevoEvento);
    }

    @Override
    public List<EventoCuidadoEntity> obtenerHistorialPorCultivo(UUID idCultivo) {
        return eventoRepository.findByIdCultivoOrderByFechaEventoDesc(idCultivo);
    }

    @Override
    public List<EventoCuidadoEntity> obtenerHistorialPorTipo(UUID idCultivo, String tipoEvento) {
        return eventoRepository.findByIdCultivoAndTipoEventoOrderByFechaEventoDesc(idCultivo, tipoEvento);
    }

    @Override
    public EventoCuidadoEntity obtenerPorId(UUID id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + id));
    }

    @Override
    public EventoCuidadoEntity actualizarEvento(UUID id, EventoCuidadoRequestDTO request) {
        EventoCuidadoEntity eventoExistente = obtenerPorId(id);
        eventoMapper.updateEntityFromDto(request, eventoExistente);
        return eventoRepository.save(eventoExistente);
    }

    @Override
    public void eliminarEvento(UUID id) {
        obtenerPorId(id);
        eventoRepository.deleteById(id);
    }
}