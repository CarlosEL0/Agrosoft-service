package com.agrosoft.api.features.ai_analysis.services;

import com.agrosoft.api.features.ai_analysis.dto.AnalisisIaRequestDTO;
import com.agrosoft.api.features.ai_analysis.dto.AnalisisIaResponseDTO;

public interface AiAnalysisService {
    AnalisisIaResponseDTO generarAnalisis(AnalisisIaRequestDTO request);
}