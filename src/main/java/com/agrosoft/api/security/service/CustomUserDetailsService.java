package com.agrosoft.api.security.service;

import com.agrosoft.api.features.user.entities.Usuario;
import com.agrosoft.api.features.user.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        // Convertimos tu Entidad 'Usuario' al objeto 'UserDetails' que entiende Spring Security
        return User.builder()
                .username(usuario.getCorreoElectronico())
                .password(usuario.getPassword())
                .roles("USER")
                .build();
    }
}