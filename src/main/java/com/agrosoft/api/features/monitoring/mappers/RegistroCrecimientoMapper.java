package com.agrosoft.api.features.monitoring.mappers;

import com.agrosoft.api.features.monitoring.dto.RegistroCrecimientoRequestDTO;
import com.agrosoft.api.features.monitoring.dto.RegistroCrecimientoResponseDTO;
import com.agrosoft.api.features.monitoring.entities.RegistroCrecimiento;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegistroCrecimientoMapper {

    @Mapping(target = "id", ignore = true)
    RegistroCrecimiento toEntity(RegistroCrecimientoRequestDTO dto);

    RegistroCrecimientoResponseDTO toResponseDTO(RegistroCrecimiento entity);

    List<RegistroCrecimientoResponseDTO> toResponseDTOList(List<RegistroCrecimiento> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(RegistroCrecimientoRequestDTO dto, @MappingTarget RegistroCrecimiento entity);
}