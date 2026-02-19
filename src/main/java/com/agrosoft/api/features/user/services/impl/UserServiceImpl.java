package com.agrosoft.api.features.user.services.impl;

import com.agrosoft.api.features.user.dto.UsuarioRequestDTO;
import com.agrosoft.api.features.user.dto.UsuarioResponseDTO;
import com.agrosoft.api.features.user.entities.Usuario;
import com.agrosoft.api.features.user.mappers.UsuarioMapper;
import com.agrosoft.api.features.user.repositories.UsuarioRepository;
import com.agrosoft.api.features.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    @Transactional
    public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO request) {
        if (usuarioRepository.existsByCorreoElectronico(request.getCorreoElectronico())) {
            // Se cambiará por una Excepción Global desde 'shared/exceptions'
            throw new RuntimeException("El correo ya está registrado");
        }

        // 1. Mapeamos de DTO a Entidad
        Usuario nuevoUsuario = usuarioMapper.toEntity(request);

        // 2. Guardamos en BD
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        // 3. Mapeamos de Entidad a DTO de respuesta
        return usuarioMapper.toResponseDTO(usuarioGuardado);
    }
}