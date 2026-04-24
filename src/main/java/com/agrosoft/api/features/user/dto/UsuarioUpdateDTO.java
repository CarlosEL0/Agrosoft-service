package com.agrosoft.api.features.user.dto;

import jakarta.validation.MessageInterpolator;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import javax.crypto.Mac;

@Data
public class UsuarioUpdateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
}