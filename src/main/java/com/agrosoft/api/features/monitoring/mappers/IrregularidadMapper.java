package com.agrosoft.api.features.monitoring.mappers;

import com.agrosoft.api.features.monitoring.dto.IrregularidadRequestDTO;
import com.agrosoft.api.features.monitoring.entities.Irregularidad;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IrregularidadMapper {

    Irregularidad toEntity(IrregularidadRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(IrregularidadRequestDTO dto, @MappingTarget Irregularidad entity);
}