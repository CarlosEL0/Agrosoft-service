package com.agrosoft.api.features.user.services.impl;

import com.agrosoft.api.features.user.dto.UsuarioRequestDTO;
import com.agrosoft.api.features.user.dto.UsuarioResponseDTO;
import com.agrosoft.api.features.user.entities.Usuario;
import com.agrosoft.api.features.user.mappers.UsuarioMapper;
import com.agrosoft.api.features.user.repositories.UsuarioRepository;
import com.agrosoft.api.features.user.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agrosoft.api.features.user.dto.UsuarioUpdateDTO;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

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

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> obtenerTodos() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO obtenerPorId(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return usuarioMapper.toResponseDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO actualizarUsuario(UUID id, UsuarioUpdateDTO updateDTO) {
        // 1. Buscamos al usuario en la BD
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // 2. MapStruct actualiza el objeto 'usuario' con los datos nuevos
        usuarioMapper.updateEntityFromDTO(updateDTO, usuario);

        // 3. Guardamos y retornamos (Hibernate optimiza este UPDATE por debajo)
        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(usuarioActualizado);
    }

    @Override
    @Transactional
    public void eliminarUsuario(UUID id) {
        // Verificamos que exista antes de intentar borrarlo
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
