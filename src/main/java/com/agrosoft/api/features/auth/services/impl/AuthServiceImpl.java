package com.agrosoft.api.features.auth.services.impl;

import com.agrosoft.api.features.auth.dto.AuthRequestDTO;
import com.agrosoft.api.features.auth.dto.AuthResponseDTO;
import com.agrosoft.api.features.auth.services.AuthService;
import com.agrosoft.api.security.service.PasetoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasetoUtil pasetoUtil;

    @Override
    public AuthResponseDTO login(AuthRequestDTO request) {
        // 1. Esto lanzará una excepción automáticamente si la contraseña es incorrecta
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCorreoElectronico(),
                        request.getPassword()
                )
        );

        // 2. Si llega aquí, las credenciales son válidas. Generamos el token PASETO.
        String token = pasetoUtil.generateToken(request.getCorreoElectronico());

        // 3. Devolvemos el token
        return new AuthResponseDTO(token);
    }
}