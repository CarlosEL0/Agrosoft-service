package com.agrosoft.api.features.care_events.mappers;

import com.agrosoft.api.features.care_events.dto.EventoCuidadoRequestDTO;
import com.agrosoft.api.features.care_events.entities.EventoCuidado;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventoCuidadoMapper {

    EventoCuidado toEntity(EventoCuidadoRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EventoCuidadoRequestDTO dto, @MappingTarget EventoCuidado entity);
}