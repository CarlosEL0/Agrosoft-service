package com.agrosoft.api.features.auth.controllers;

import com.agrosoft.api.features.auth.dto.AuthRequestDTO;
import com.agrosoft.api.features.auth.dto.AuthResponseDTO;
import com.agrosoft.api.features.auth.services.AuthService;
import com.agrosoft.api.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Login exitoso", authService.login(request)));
    }
}