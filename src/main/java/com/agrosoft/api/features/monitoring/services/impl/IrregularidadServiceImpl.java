package com.agrosoft.api.features.monitoring.services.impl;

import com.agrosoft.api.features.monitoring.dto.IrregularidadRequestDTO;
import com.agrosoft.api.features.monitoring.dto.IrregularidadResponseDTO;
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
    public IrregularidadResponseDTO reportarIrregularidad(IrregularidadRequestDTO request) {
        Irregularidad nuevaIrregularidad = mapper.toEntity(request);
        // Si no envían estado, forzamos que sea 'activa'
        if (nuevaIrregularidad.getEstado() == null) {
            nuevaIrregularidad.setEstado("activa");
        }
        Irregularidad guardada = repository.save(nuevaIrregularidad);
        return mapper.toResponseDTO(guardada);
    }

    @Override
    public List<IrregularidadResponseDTO> obtenerPorCultivo(UUID idCultivo) {
        List<Irregularidad> lista = repository.findByIdCultivoOrderByFechaDeteccionDesc(idCultivo);
        return mapper.toResponseDTOList(lista);
    }

    @Override
    public List<IrregularidadResponseDTO> obtenerActivasPorCultivo(UUID idCultivo) {
        List<Irregularidad> lista = repository.findByIdCultivoAndEstado(idCultivo, "activa");
        return mapper.toResponseDTOList(lista);
    }

    @Override
    public IrregularidadResponseDTO obtenerPorId(UUID id) {
        Irregularidad entidad = getIrregularidadEntity(id);
        return mapper.toResponseDTO(entidad);
    }

    @Override
    public IrregularidadResponseDTO actualizarIrregularidad(UUID id, IrregularidadRequestDTO request) {
        Irregularidad existente = getIrregularidadEntity(id);
        mapper.updateEntityFromDto(request, existente);
        Irregularidad actualizada = repository.save(existente);
        return mapper.toResponseDTO(actualizada);
    }

    @Override
    public void eliminarIrregularidad(UUID id) {
        getIrregularidadEntity(id); // Validamos que exista antes de borrar
        repository.deleteById(id);
    }

    // ==========================================
    // MÉTODOS PRIVADOS (Uso interno)
    // ==========================================
    private Irregularidad getIrregularidadEntity(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Irregularidad no encontrada con ID: " + id));
    }
}