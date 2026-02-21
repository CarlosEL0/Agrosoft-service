package com.agrosoft.api.features.ai_analysis.dto.groq;

import lombok.Data;
import java.util.List;

@Data
public class GroqResponseDTO {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private GroqMessageDTO message;
    }
}