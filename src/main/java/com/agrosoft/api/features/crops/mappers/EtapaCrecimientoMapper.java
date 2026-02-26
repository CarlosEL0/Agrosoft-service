package com.agrosoft.api.features.crops.mappers;

import com.agrosoft.api.features.crops.dto.EtapaCrecimientoRequestDTO;
import com.agrosoft.api.features.crops.entities.EtapaCrecimiento;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EtapaCrecimientoMapper {

    EtapaCrecimiento toEntity(EtapaCrecimientoRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EtapaCrecimientoRequestDTO dto, @MappingTarget EtapaCrecimiento entity);
}