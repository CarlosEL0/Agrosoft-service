package com.agrosoft.api.features.ai_analysis;

import com.agrosoft.api.features.ai_analysis.dto.AnalisisIaRequestDTO;
import com.agrosoft.api.features.ai_analysis.dto.AnalisisIaResponseDTO;
import com.agrosoft.api.features.ai_analysis.services.AiAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiAnalysisController {

    private final AiAnalysisService aiAnalysisService;

    @PostMapping("/analyze")
    public ResponseEntity<AnalisisIaResponseDTO> generarAnalisisCultivo(@RequestBody AnalisisIaRequestDTO request) {
        AnalisisIaResponseDTO response = aiAnalysisService.generarAnalisis(request);
        return ResponseEntity.ok(response);
    }
}