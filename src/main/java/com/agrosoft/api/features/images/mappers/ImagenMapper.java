package com.agrosoft.api.features.images.mappers;

import com.agrosoft.api.features.images.dto.ImagenResponseDTO;
import com.agrosoft.api.features.images.entities.Imagen;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImagenMapper {

    // De Entidad a DTO (Para mostrar al usuario)
    ImagenResponseDTO toDTO(Imagen entity);

    // De DTO a Entidad (Por si acaso, aunque aquí usaremos más el servicio manual)
    Imagen toEntity(ImagenResponseDTO dto);

    // Para actualizar datos sin borrar lo que ya existe
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ImagenResponseDTO dto, @MappingTarget Imagen entity);
}