package com.agrosoft.api.features.monitoring.services.impl;

import com.agrosoft.api.features.monitoring.dto.IrregularidadRequestDTO;
import com.agrosoft.api.features.monitoring.entities.Irregularidad;
import com.agrosoft.api.features.monitoring.mappers.IrregularidadMapper;
import com.agrosoft.api.features.monitoring.repositories.IrregularidadRepository;
import com.agrosoft.api.features.monitoring.services.IrregularidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IrregularidadServiceImpl implements IrregularidadService {

    private final IrregularidadRepository repository;
    private final IrregularidadMapper mapper;

    @Override
    public Irregularidad reportarIrregularidad(IrregularidadRequestDTO request) {
        Irregularidad nuevaIrregularidad = mapper.toEntity(request);
        // Si no envían estado, forzamos que sea 'activa'
        if (nuevaIrregularidad.getEstado() == null) {
            nuevaIrregularidad.setEstado("activa");
        }
        return repository.save(nuevaIrregularidad);
    }

    @Override
    public List<Irregularidad> obtenerPorCultivo(UUID idCultivo) {
        return repository.findByIdCultivoOrderByFechaDeteccionDesc(idCultivo);
    }

    @Override
    public List<Irregularidad> obtenerActivasPorCultivo(UUID idCultivo) {
        return repository.findByIdCultivoAndEstado(idCultivo, "activa");
    }

    @Override
    public Irregularidad obtenerPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Irregularidad no encontrada"));
    }

    @Override
    public Irregularidad actualizarIrregularidad(UUID id, IrregularidadRequestDTO request) {
        Irregularidad existente = obtenerPorId(id);
        mapper.updateEntityFromDto(request, existente);
        return repository.save(existente);
    }

    @Override
    public void eliminarIrregularidad(UUID id) {
        obtenerPorId(id);
        repository.deleteById(id);
    }
}