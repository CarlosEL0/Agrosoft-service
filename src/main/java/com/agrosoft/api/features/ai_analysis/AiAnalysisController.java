package com.agrosoft.api.features.ai_analysis;

import com.agrosoft.api.features.ai_analysis.dto.AnalisisIaRequestDTO;
import com.agrosoft.api.features.ai_analysis.dto.AnalisisIaResponseDTO;
import com.agrosoft.api.features.ai_analysis.dto.InterpretacionCrecimientoRequestDTO;
import com.agrosoft.api.features.ai_analysis.dto.ResumenCuidadosRequestDTO;
import com.agrosoft.api.features.ai_analysis.services.AiAnalysisService;
import com.agrosoft.api.features.ai_analysis.services.AiCareSummaryService;
import com.agrosoft.api.features.ai_analysis.services.AiGrowthInterpretationService;
import com.agrosoft.api.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiAnalysisController {

    private final AiAnalysisService aiAnalysisService;
    private final AiCareSummaryService aiCareSummaryService;
    private final AiGrowthInterpretationService aiGrowthInterpretationService;

    @PostMapping("/analyze")
    public ResponseEntity<ApiResponse<AnalisisIaResponseDTO>> generarAnalisisCultivo(@RequestBody AnalisisIaRequestDTO request) {
        AnalisisIaResponseDTO response = aiAnalysisService.generarAnalisis(request);
        return ResponseEntity.ok(ApiResponse.success("Análisis generado exitosamente", response));
    }

    @PostMapping("/resumen-cuidados")
    public ResponseEntity<ApiResponse<AnalisisIaResponseDTO>> generarResumenCuidados(@RequestBody ResumenCuidadosRequestDTO request) {
        AnalisisIaResponseDTO response = aiCareSummaryService.generarResumenCuidados(request);
        return new ResponseEntity<>(ApiResponse.success("Resumen de cuidados generado exitosamente", response), HttpStatus.CREATED);
    }

    @PostMapping("/interpretacion-crecimiento")
    public ResponseEntity<ApiResponse<AnalisisIaResponseDTO>> interpretarCrecimiento(@RequestBody InterpretacionCrecimientoRequestDTO request) {
        AnalisisIaResponseDTO response = aiGrowthInterpretationService.interpretarCrecimiento(request);
        return new ResponseEntity<>(ApiResponse.success("Interpretación de crecimiento generada exitosamente", response), HttpStatus.CREATED);
    }
}