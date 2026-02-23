package com.agrosoft.api.features.care_events.service;

import com.agrosoft.api.features.care_events.dto.EventoCuidadoRequestDTO;
import com.agrosoft.api.features.care_events.entities.EventoCuidado;

import java.util.List;
import java.util.UUID;

public interface EventoCuidadoService {
    EventoCuidado crearEvento(EventoCuidadoRequestDTO request);
    List<EventoCuidado> obtenerHistorialPorCultivo(UUID idCultivo);
    List<EventoCuidado> obtenerHistorialPorTipo(UUID idCultivo, String tipoEvento);
    EventoCuidado obtenerPorId(UUID id);
    EventoCuidado actualizarEvento(UUID id, EventoCuidadoRequestDTO request);
    void eliminarEvento(UUID id);
}