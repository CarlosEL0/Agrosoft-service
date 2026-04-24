package com.agrosoft.api.features.ai_analysis.services;

import com.agrosoft.api.features.ai_analysis.dto.AnalisisIaResponseDTO;
import com.agrosoft.api.features.ai_analysis.dto.ResumenCuidadosRequestDTO;

public interface AiCareSummaryService {
    AnalisisIaResponseDTO generarResumenCuidados(ResumenCuidadosRequestDTO request);
}