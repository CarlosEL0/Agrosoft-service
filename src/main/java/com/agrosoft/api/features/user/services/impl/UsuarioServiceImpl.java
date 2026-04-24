package com.agrosoft.api.features.user.services.impl;

import com.agrosoft.api.features.user.dto.UsuarioRequestDTO;
import com.agrosoft.api.features.user.dto.UsuarioResponseDTO;
import com.agrosoft.api.features.user.entities.Usuario;
import com.agrosoft.api.features.user.mappers.UsuarioMapper;
import com.agrosoft.api.features.user.repositories.UsuarioRepository;
import com.agrosoft.api.features.user.services.UsuarioService;
import com.agrosoft.api.shared.exceptions.BusinessRuleException;
import com.agrosoft.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.agrosoft.api.features.user.dto.UsuarioUpdateDTO;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO request) {
        if (usuarioRepository.existsByCorreoElectronico(request.getCorreoElectronico())) {
            // Se cambiará por una Excepción Global desde 'shared/exceptions'
            throw new BusinessRuleException("El correo ya está registrado");
        }

        // 1. Mapeamos de DTO a Entidad
        Usuario nuevoUsuario = usuarioMapper.toEntity(request);

        //encriptamos antes de guardarlo
        nuevoUsuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));

        // 3. Guardamos en BD
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        // 4. Mapeamos de Entidad a DTO de respuesta
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
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        return usuarioMapper.toResponseDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO actualizarUsuario(UUID id, UsuarioUpdateDTO updateDTO) {
        // 1. Buscamos al usuario en la BD
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

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
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
