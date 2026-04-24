package com.agrosoft.api.features.auth.services;

import com.agrosoft.api.features.auth.dto.AuthRequestDTO;
import com.agrosoft.api.features.auth.dto.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(AuthRequestDTO request);
}