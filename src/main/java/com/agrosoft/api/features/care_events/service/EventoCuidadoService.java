package com.agrosoft.api.features.care_events.service;

import com.agrosoft.api.features.care_events.dto.EventoCuidadoRequestDTO;
import com.agrosoft.api.features.care_events.entities.EventoCuidadoEntity;

import java.util.List;
import java.util.UUID;

public interface EventoCuidadoService {
    EventoCuidadoEntity crearEvento(EventoCuidadoRequestDTO request);
    List<EventoCuidadoEntity> obtenerHistorialPorCultivo(UUID idCultivo);
    List<EventoCuidadoEntity> obtenerHistorialPorTipo(UUID idCultivo, String tipoEvento);
    EventoCuidadoEntity obtenerPorId(UUID id);
    EventoCuidadoEntity actualizarEvento(UUID id, EventoCuidadoRequestDTO request);
    void eliminarEvento(UUID id);
}