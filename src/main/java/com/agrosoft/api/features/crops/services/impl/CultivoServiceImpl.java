package com.agrosoft.api.features.crops.services.impl;

import com.agrosoft.api.features.crops.dto.CultivoRequestDTO;
import com.agrosoft.api.features.crops.entities.CultivoEntity;
import com.agrosoft.api.features.crops.repositories.CultivoRepository;
import com.agrosoft.api.features.crops.service.CultivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CultivoServiceImpl implements CultivoService {

    private final CultivoRepository cultivoRepository;

    @Override
    public CultivoEntity crearCultivo(CultivoRequestDTO request) {
        // Aquí idealmente usaríamos un Mapper, por ahora lo hacemos manual
        CultivoEntity nuevoCultivo = CultivoEntity.builder()
                .idUsuario(request.getIdUsuario())
                .nombreCultivo(request.getNombreCultivo())
                .tipoCultivo(request.getTipoCultivo())
                .fechaSiembra(request.getFechaSiembra())
                .notasGenerales(request.getNotasGenerales())
                .alturaEsperada(request.getAlturaEsperada())
                .diasGerminacion(request.getDiasGerminacion())
                .diasVegetativo(request.getDiasVegetativo())
                .diasFloracion(request.getDiasFloracion())
                .diasCosecha(request.getDiasCosecha())
                .phSueloMin(request.getPhSueloMin())
                .phSueloMax(request.getPhSueloMax())
                .build();

        // Método POST (Guardar nuevo)
        return cultivoRepository.save(nuevoCultivo);
    }

    @Override
    public List<CultivoEntity> obtenerTodos() {
        // Método GET (Todos)
        return cultivoRepository.findAll();
    }

    @Override
    public CultivoEntity obtenerPorId(UUID id) {
        // Método GET (Por ID)
        return cultivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cultivo no encontrado con ID: " + id));
    }

    @Override
    public CultivoEntity actualizarCultivo(UUID id, CultivoRequestDTO request) {
        // Primero buscamos si existe
        CultivoEntity cultivoExistente = obtenerPorId(id);

        // Actualizamos los campos
        cultivoExistente.setNombreCultivo(request.getNombreCultivo());
        cultivoExistente.setTipoCultivo(request.getTipoCultivo());
        cultivoExistente.setFechaSiembra(request.getFechaSiembra());
        cultivoExistente.setNotasGenerales(request.getNotasGenerales());
        cultivoExistente.setAlturaEsperada(request.getAlturaEsperada());
        cultivoExistente.setDiasGerminacion(request.getDiasGerminacion());
        cultivoExistente.setDiasVegetativo(request.getDiasVegetativo());
        cultivoExistente.setDiasFloracion(request.getDiasFloracion());
        cultivoExistente.setDiasCosecha(request.getDiasCosecha());
        cultivoExistente.setPhSueloMin(request.getPhSueloMin());
        cultivoExistente.setPhSueloMax(request.getPhSueloMax());

        // Método PUT (Como ya tiene ID, Hibernate hace un UPDATE)
        return cultivoRepository.save(cultivoExistente);
    }

    @Override
    public void eliminarCultivo(UUID id) {
        // Verificamos que exista antes de borrar
        obtenerPorId(id);
        // Método DELETE
        cultivoRepository.deleteById(id);
    }
}