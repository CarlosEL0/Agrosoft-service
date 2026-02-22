package com.agrosoft.api.features.ai_analysis.dto.groq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroqMessageDTO {
    private String role;
    private String content;
}