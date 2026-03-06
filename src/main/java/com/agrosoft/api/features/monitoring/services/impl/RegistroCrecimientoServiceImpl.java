package com.agrosoft.api.features.monitoring.services.impl;

import com.agrosoft.api.features.crops.repositories.CultivoRepository;
import com.agrosoft.api.features.monitoring.dto.RegistroCrecimientoRequestDTO;
import com.agrosoft.api.features.monitoring.dto.RegistroCrecimientoResponseDTO;
import com.agrosoft.api.features.monitoring.entities.RegistroCrecimiento;
import com.agrosoft.api.features.monitoring.mappers.RegistroCrecimientoMapper;
import com.agrosoft.api.features.monitoring.repositories.RegistroCrecimientoRepository;
import com.agrosoft.api.features.monitoring.services.RegistroCrecimientoService;
import com.agrosoft.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistroCrecimientoServiceImpl implements RegistroCrecimientoService {

    private final RegistroCrecimientoRepository repository;
    private final CultivoRepository cultivoRepository;
    private final RegistroCrecimientoMapper mapper;

    @Override
    @Transactional
    public RegistroCrecimientoResponseDTO registrarCrecimiento(RegistroCrecimientoRequestDTO request) {
        if (!cultivoRepository.existsById(request.getIdCultivo())) {
            throw new ResourceNotFoundException("El cultivo con ID " + request.getIdCultivo() + " no existe.");
        }

        RegistroCrecimiento entidad = mapper.toEntity(request);
        RegistroCrecimiento guardado = repository.save(entidad);
        return mapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroCrecimientoResponseDTO> obtenerPorCultivo(UUID idCultivo) {
        List<RegistroCrecimiento> registros = repository.findByIdCultivoOrderByFechaRegistroDesc(idCultivo);
        return mapper.toResponseDTOList(registros);
    }

    @Override
    @Transactional(readOnly = true)
    public RegistroCrecimientoResponseDTO obtenerPorId(UUID id) {
        return mapper.toResponseDTO(getEntidad(id));
    }

    @Override
    @Transactional
    public RegistroCrecimientoResponseDTO actualizarRegistro(UUID id, RegistroCrecimientoRequestDTO request) {
        RegistroCrecimiento existente = getEntidad(id);

        // Si intentan cambiar el cultivo a uno que no existe, validamos
        if (request.getIdCultivo() != null && !request.getIdCultivo().equals(existente.getIdCultivo())) {
            if (!cultivoRepository.existsById(request.getIdCultivo())) {
                throw new ResourceNotFoundException("El nuevo cultivo asignado no existe.");
            }
        }

        mapper.updateEntityFromDto(request, existente);
        RegistroCrecimiento actualizado = repository.save(existente);
        return mapper.toResponseDTO(actualizado);
    }

    @Override
    @Transactional
    public void eliminarRegistro(UUID id) {
        RegistroCrecimiento existente = getEntidad(id);
        repository.delete(existente);
    }

    // Método privado para reutilizar la búsqueda y lanzar excepción estandarizada (SRP)
    private RegistroCrecimiento getEntidad(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de crecimiento no encontrado con ID: " + id));
    }
}