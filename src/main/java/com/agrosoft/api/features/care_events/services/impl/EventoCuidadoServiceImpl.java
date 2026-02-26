package com.agrosoft.api.features.care_events.services.impl;

import com.agrosoft.api.features.care_events.dto.EventoCuidadoRequestDTO;
import com.agrosoft.api.features.care_events.entities.EventoCuidado;
import com.agrosoft.api.features.care_events.mappers.EventoCuidadoMapper;
import com.agrosoft.api.features.care_events.repositories.EventoCuidadoRepository;
import com.agrosoft.api.features.care_events.service.EventoCuidadoService;
import com.agrosoft.api.shared.exceptions.ResourceNotFoundException;
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
    public EventoCuidado crearEvento(EventoCuidadoRequestDTO request) {
        EventoCuidado nuevoEvento = eventoMapper.toEntity(request);
        return eventoRepository.save(nuevoEvento);
    }

    @Override
    public List<EventoCuidado> obtenerHistorialPorCultivo(UUID idCultivo) {
        return eventoRepository.findByIdCultivoOrderByFechaEventoDesc(idCultivo);
    }

    @Override
    public List<EventoCuidado> obtenerHistorialPorTipo(UUID idCultivo, String tipoEvento) {
        return eventoRepository.findByIdCultivoAndTipoEventoOrderByFechaEventoDesc(idCultivo, tipoEvento);
    }

    @Override
    public EventoCuidado obtenerPorId(UUID id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + id));
    }

    @Override
    public EventoCuidado actualizarEvento(UUID id, EventoCuidadoRequestDTO request) {
        EventoCuidado eventoExistente = obtenerPorId(id);
        eventoMapper.updateEntityFromDto(request, eventoExistente);
        return eventoRepository.save(eventoExistente);
    }

    @Override
    public void eliminarEvento(UUID id) {
        obtenerPorId(id);
        eventoRepository.deleteById(id);
    }
}