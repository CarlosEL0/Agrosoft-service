package com.agrosoft.api.features.ai_analysis.client;

import com.agrosoft.api.features.ai_analysis.dto.groq.GroqRequestDTO;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

// Le decimos que lea la URL de nuestro application.properties
@FeignClient(name = "groqClient", url = "${groq.api.url}")
public interface GroqClient {

    @PostMapping(value = "/chat/completions", consumes = "application/json")
    GroqResponseDTO generarRespuesta(
            @RequestHeader("Authorization") String apiKey,
            @RequestBody GroqRequestDTO request
    );
}