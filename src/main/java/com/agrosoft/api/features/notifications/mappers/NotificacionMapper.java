package com.agrosoft.api.features.notifications.mappers;

import com.agrosoft.api.features.notifications.dto.NotificacionResponseDTO;
import com.agrosoft.api.features.notifications.entities.Notificacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificacionMapper {

    @Mapping(source = "descripcion.titulo", target = "titulo")
    @Mapping(source = "descripcion.mensajeBase", target = "mensaje")
    @Mapping(source = "descripcion.tipoNotificacion", target = "tipoNotificacion")
    NotificacionResponseDTO toDTO(Notificacion entity);
}