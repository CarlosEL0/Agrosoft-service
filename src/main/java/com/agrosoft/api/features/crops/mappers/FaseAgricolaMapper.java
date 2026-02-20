package com.agrosoft.api.features.crops.mappers;

import com.agrosoft.api.features.crops.dto.FaseAgricolaRequestDTO;
import com.agrosoft.api.features.crops.entities.FaseAgricolaEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FaseAgricolaMapper {

    FaseAgricolaEntity toEntity(FaseAgricolaRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(FaseAgricolaRequestDTO dto, @MappingTarget FaseAgricolaEntity entity);
}