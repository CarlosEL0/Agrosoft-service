package com.agrosoft.api.features.user.dto;

import lombok.Data;

@Data
public class UsuarioRequestDTO {
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String password;
}