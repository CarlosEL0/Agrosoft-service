package com.agrosoft.api.features.ai_analysis;

import com.agrosoft.api.features.ai_analysis.services.AiAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiAnalysisController {

    private final AiAnalysisService aiAnalysisService;

    @PostMapping("/test")
    public ResponseEntity<Map<String, String>> probarIa(@RequestBody Map<String, String> request) {
        String pregunta = request.get("pregunta");
        String respuestaIa = aiAnalysisService.probarPrompt(pregunta);

        return ResponseEntity.ok(Map.of("respuesta", respuestaIa));
    }
}