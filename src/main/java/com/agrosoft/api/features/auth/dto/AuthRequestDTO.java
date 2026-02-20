package com.agrosoft.api.features.auth.dto;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String correoElectronico;
    private String password;
}