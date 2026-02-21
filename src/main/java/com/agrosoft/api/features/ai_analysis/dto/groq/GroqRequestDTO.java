package com.agrosoft.api.features.ai_analysis.dto.groq;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class GroqRequestDTO {
    private String model;
    private List<GroqMessageDTO> messages;
    private double temperature;
}