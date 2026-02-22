package com.agrosoft.api.features.care_events.mappers;

import com.agrosoft.api.features.care_events.dto.ReporteRiegoRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReporteRiego;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReporteRiegoMapper {

    ReporteRiego toEntity(ReporteRiegoRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ReporteRiegoRequestDTO dto, @MappingTarget ReporteRiego entity);
}