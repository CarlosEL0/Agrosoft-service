package com.agrosoft.api.features.user.services;

import com.agrosoft.api.features.user.dto.UsuarioRequestDTO;
import com.agrosoft.api.features.user.dto.UsuarioResponseDTO;
import com.agrosoft.api.features.user.dto.UsuarioUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface UsuarioService {

    UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO request);

    List<UsuarioResponseDTO> obtenerTodos();

    UsuarioResponseDTO obtenerPorId(UUID id);

    UsuarioResponseDTO actualizarUsuario(UUID id, UsuarioUpdateDTO updateDTO);

    void eliminarUsuario(UUID id);
}