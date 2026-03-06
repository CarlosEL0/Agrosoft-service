package com.agrosoft.api.security.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

@Getter
public class AgroSoftSecurityUser extends User {

    // Aquí agregamos todos los campos extra que queramos tener en memoria RAM
    private final UUID idUsuario;

    public AgroSoftSecurityUser(UUID idUsuario, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.idUsuario = idUsuario;
    }
}