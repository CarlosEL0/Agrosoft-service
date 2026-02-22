package com.agrosoft.api.features.harvest.mappers;

import com.agrosoft.api.features.harvest.dto.ReporteCosechaRequestDTO;
import com.agrosoft.api.features.harvest.dto.ReporteCosechaResponseDTO;
import com.agrosoft.api.features.harvest.entities.ReporteCosecha;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReporteCosechaMapper {

    // Convierte lo que entra del frontend a una Entidad (ignorando los campos que llenará la IA)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idAnalisis", ignore = true)
    @Mapping(target = "rendimientoEsperado", ignore = true)
    @Mapping(target = "desviacionRendimiento", ignore = true)
    @Mapping(target = "eficienciaRiego", ignore = true)
    @Mapping(target = "costoTotal", ignore = true)
    @Mapping(target = "costoPorKg", ignore = true)
    @Mapping(target = "resumenCiclo", ignore = true)
    @Mapping(target = "factoresExito", ignore = true)
    @Mapping(target = "areasMejora", ignore = true)
    @Mapping(target = "fechaGeneracion", ignore = true)
    ReporteCosecha toEntity(ReporteCosechaRequestDTO dto);

    // Convierte la Entidad final (ya procesada por la IA) al DTO de salida
    ReporteCosechaResponseDTO toResponseDTO(ReporteCosecha entity);

    List<ReporteCosechaResponseDTO> toResponseDTOList(List<ReporteCosecha> entities);
}