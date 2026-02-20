package com.agrosoft.api.features.crops.mappers;

import com.agrosoft.api.features.crops.dto.EtapaCrecimientoRequestDTO;
import com.agrosoft.api.features.crops.entities.EtapaCrecimientoEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EtapaCrecimientoMapper {

    EtapaCrecimientoEntity toEntity(EtapaCrecimientoRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EtapaCrecimientoRequestDTO dto, @MappingTarget EtapaCrecimientoEntity entity);
}