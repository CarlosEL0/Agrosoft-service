package com.agrosoft.api.features.ai_analysis.services;

import com.agrosoft.api.features.ai_analysis.client.GroqClient;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqMessageDTO;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqRequestDTO;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiAnalysisService {

    private final GroqClient groqClient;

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.model}")
    private String model;

    public String probarPrompt(String preguntaDelAgricultor) {
        // 1. Preparamos el contexto (System) y la pregunta (User)
        GroqMessageDTO systemMessage = new GroqMessageDTO("system",
                "Eres un ingeniero agrónomo experto. Responde de forma clara, breve y profesional a las dudas sobre cultivos.");

        GroqMessageDTO userMessage = new GroqMessageDTO("user", preguntaDelAgricultor);

        // 2. Armamos la petición
        GroqRequestDTO request = GroqRequestDTO.builder()
                .model(model)
                .messages(List.of(systemMessage, userMessage))
                .temperature(0.3) // Bajo para que sea preciso y no invente cosas
                .build();

        // 3. Llamamos a la API de Groq (Añadiendo "Bearer " a la llave)
        GroqResponseDTO response = groqClient.generarRespuesta("Bearer " + apiKey, request);

        // 4. Extraemos el texto de la respuesta
        return response.getChoices().get(0).getMessage().getContent();
    }
}