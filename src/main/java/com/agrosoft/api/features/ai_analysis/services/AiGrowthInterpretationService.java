package com.agrosoft.api.features.ai_analysis.services;

import com.agrosoft.api.features.ai_analysis.dto.AnalisisIaResponseDTO;
import com.agrosoft.api.features.ai_analysis.dto.InterpretacionCrecimientoRequestDTO;

public interface AiGrowthInterpretationService {
    AnalisisIaResponseDTO interpretarCrecimiento(InterpretacionCrecimientoRequestDTO request);
}