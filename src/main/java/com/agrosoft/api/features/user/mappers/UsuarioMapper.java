package com.agrosoft.api.features.user.mappers;

import com.agrosoft.api.features.user.dto.UsuarioRequestDTO;
import com.agrosoft.api.features.user.dto.UsuarioResponseDTO;
import com.agrosoft.api.features.user.dto.UsuarioUpdateDTO;
import com.agrosoft.api.features.user.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioMapper {

    // Convierte lo que llega de la web a una entidad para la base de datos
    @Mapping(target = "id", ignore = true)
    Usuario toEntity(UsuarioRequestDTO dto);

    // Convierte la entidad de la base de datos a una respuesta segura para la web
    UsuarioResponseDTO toResponseDTO(Usuario entity);

    //Actualiza la entidad existente con los datos del DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "correoElectronico", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntityFromDTO(UsuarioUpdateDTO dto, @MappingTarget Usuario entity);

}