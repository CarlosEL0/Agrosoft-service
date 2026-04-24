package com.agrosoft.api.features.care_events.mappers;

import com.agrosoft.api.features.care_events.dto.ReporteFumigacionRequestDTO;
import com.agrosoft.api.features.care_events.entities.ReporteFumigacion;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReporteFumigacionMapper {

    ReporteFumigacion toEntity(ReporteFumigacionRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ReporteFumigacionRequestDTO dto, @MappingTarget ReporteFumigacion entity);
}