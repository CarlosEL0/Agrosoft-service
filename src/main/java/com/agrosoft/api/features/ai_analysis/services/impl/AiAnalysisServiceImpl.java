package com.agrosoft.api.features.ai_analysis.services.impl;

import com.agrosoft.api.features.ai_analysis.client.GroqClient;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqMessageDTO;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqRequestDTO;
import com.agrosoft.api.features.ai_analysis.dto.groq.GroqResponseDTO;
import com.agrosoft.api.features.ai_analysis.services.AiAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiAnalysisServiceImpl implements AiAnalysisService {

    private final GroqClient groqClient;

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.model}")
    private String model;

    @Override
    public String probarPrompt(String preguntaDelAgricultor) {
        GroqMessageDTO systemMessage = new GroqMessageDTO("system",
                "Eres un ingeniero agrónomo experto. Responde de forma clara, breve y profesional a las dudas sobre cultivos.");

        GroqMessageDTO userMessage = new GroqMessageDTO("user", preguntaDelAgricultor);

        GroqRequestDTO request = GroqRequestDTO.builder()
                .model(model)
                .messages(List.of(systemMessage, userMessage))
                .temperature(0.3)
                .build();

        GroqResponseDTO response = groqClient.generarRespuesta("Bearer " + apiKey, request);

        return response.getChoices().get(0).getMessage().getContent();
    }
}