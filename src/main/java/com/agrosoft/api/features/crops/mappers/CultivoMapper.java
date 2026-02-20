package com.agrosoft.api.features.crops.mappers;

import com.agrosoft.api.features.crops.dto.CultivoRequestDTO;
import com.agrosoft.api.features.crops.entities.CultivoEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CultivoMapper {

    CultivoEntity toEntity(CultivoRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CultivoRequestDTO dto, @MappingTarget CultivoEntity entity);
}