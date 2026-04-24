package com.agrosoft.api.features.user.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class UsuarioResponseDTO {
    private UUID id;
    private String nombre;
    private String apellido;
    private String correoElectronico;
}