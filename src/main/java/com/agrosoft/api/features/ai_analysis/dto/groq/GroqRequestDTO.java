package com.agrosoft.api.features.ai_analysis.dto.groq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class GroqRequestDTO {
    private String model;
    private List<GroqMessageDTO> messages;
    private double temperature;

    @JsonProperty("response_format")
    private ResponseFormat responseFormat;

    @Data
    @Builder
    public static class ResponseFormat {
        private String type;
    }
}