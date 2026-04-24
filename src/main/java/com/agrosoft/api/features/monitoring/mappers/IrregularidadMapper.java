package com.agrosoft.api.features.monitoring.mappers;

import com.agrosoft.api.features.monitoring.dto.IrregularidadRequestDTO;
import com.agrosoft.api.features.monitoring.dto.IrregularidadResponseDTO;
import com.agrosoft.api.features.monitoring.entities.Irregularidad;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IrregularidadMapper {

    Irregularidad toEntity(IrregularidadRequestDTO dto);

    IrregularidadResponseDTO toResponseDTO(Irregularidad entity);
    List<IrregularidadResponseDTO> toResponseDTOList(List<Irregularidad> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(IrregularidadRequestDTO dto, @MappingTarget Irregularidad entity);
}