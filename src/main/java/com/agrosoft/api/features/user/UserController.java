package com.agrosoft.api.features.user;

import com.agrosoft.api.features.user.dto.UsuarioRequestDTO;
import com.agrosoft.api.features.user.dto.UsuarioResponseDTO;
import com.agrosoft.api.features.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> registrar(@RequestBody UsuarioRequestDTO request) {
        UsuarioResponseDTO response = userService.registrarUsuario(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}