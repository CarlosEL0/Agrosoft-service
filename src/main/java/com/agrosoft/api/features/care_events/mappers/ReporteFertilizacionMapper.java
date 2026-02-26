package com.agrosoft.api.features.care_events.mappers;

import com.agrosoft.api.features.care_events.dto.ReporteFertilizacionRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReporteFertilizacion;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReporteFertilizacionMapper {

    ReporteFertilizacion toEntity(ReporteFertilizacionRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ReporteFertilizacionRequestDTO dto, @MappingTarget ReporteFertilizacion entity);
}