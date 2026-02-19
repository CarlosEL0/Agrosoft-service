package com.agrosoft.api.features.crops.mappers;

import com.agrosoft.api.features.crops.dto.CultivoRequestDTO;
import com.agrosoft.api.features.crops.entities.CultivoEntity;
import org.springframework.stereotype.Component;

@Component
public class CultivoMapper {

    public CultivoEntity toEntity(CultivoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return CultivoEntity.builder()
                .idUsuario(dto.getIdUsuario())
                .nombreCultivo(dto.getNombreCultivo())
                .tipoCultivo(dto.getTipoCultivo())
                .fechaSiembra(dto.getFechaSiembra())
                .notasGenerales(dto.getNotasGenerales())
                .alturaEsperada(dto.getAlturaEsperada())
                .diasGerminacion(dto.getDiasGerminacion())
                .diasVegetativo(dto.getDiasVegetativo())
                .diasFloracion(dto.getDiasFloracion())
                .diasCosecha(dto.getDiasCosecha())
                .phSueloMin(dto.getPhSueloMin())
                .phSueloMax(dto.getPhSueloMax())
                .build();
    }

    public void updateEntityFromDto(CultivoRequestDTO dto, CultivoEntity entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setNombreCultivo(dto.getNombreCultivo());
        entity.setTipoCultivo(dto.getTipoCultivo());
        entity.setFechaSiembra(dto.getFechaSiembra());
        entity.setNotasGenerales(dto.getNotasGenerales());
        entity.setAlturaEsperada(dto.getAlturaEsperada());
        entity.setDiasGerminacion(dto.getDiasGerminacion());
        entity.setDiasVegetativo(dto.getDiasVegetativo());
        entity.setDiasFloracion(dto.getDiasFloracion());
        entity.setDiasCosecha(dto.getDiasCosecha());
        entity.setPhSueloMin(dto.getPhSueloMin());
        entity.setPhSueloMax(dto.getPhSueloMax());
    }
}