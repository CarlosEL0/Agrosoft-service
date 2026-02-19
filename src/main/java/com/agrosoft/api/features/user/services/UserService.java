package com.agrosoft.api.features.user.services;

import com.agrosoft.api.features.user.dto.UsuarioRequestDTO;
import com.agrosoft.api.features.user.dto.UsuarioResponseDTO;

public interface UserService {
    UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO request);
}