package com.agrosoft.api.features.care_events.mappers;

import com.agrosoft.api.features.care_events.dto.ReportePodaRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReportePoda;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportePodaMapper {

    ReportePoda toEntity(ReportePodaRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ReportePodaRequestDTO dto, @MappingTarget ReportePoda entity);
}