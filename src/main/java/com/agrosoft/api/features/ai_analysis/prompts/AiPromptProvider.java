package com.agrosoft.api.features.ai_analysis.prompts;

import com.agrosoft.api.features.ai_analysis.dto.AnalisisIaRequestDTO;
import com.agrosoft.api.features.crops.entities.CultivoEntity;
import org.springframework.stereotype.Component;

@Component
public class AiPromptProvider {

    public String getSystemPrompt() {
        return """
            Eres el Ingeniero Agrónomo principal del sistema AgroSoft.
            Tu objetivo es analizar los datos del cultivo y dar un diagnóstico certero.
            
            REGLA ESTRICTA: Tu respuesta DEBE ser ÚNICA Y EXCLUSIVAMENTE un objeto JSON válido.
            No incluyas saludos, ni explicaciones fuera del JSON, ni bloques de código markdown.
            
            El JSON debe tener exactamente esta estructura:
            {
              "resultadoAnalisis": "Aquí va tu resumen y diagnóstico general detallado.",
              "recomendaciones": [
                {
                  "titulo": "Título corto de la acción",
                  "descripcion": "Cómo llevar a cabo esta acción paso a paso",
                  "prioridad": "alta"
                }
              ]
            }
            """;
    }

    public String buildUserPrompt(CultivoEntity cultivo, AnalisisIaRequestDTO request) {
        return String.format("""
            Datos del Cultivo:
            - Nombre: %s
            - Tipo: %s
            - Fecha de Siembra: %s
            
            Tipo de Análisis solicitado: %s
            Pregunta/Contexto adicional del agricultor: %s
            
            Por favor, genera el JSON con el análisis.
            """,
                cultivo.getNombreCultivo(),
                cultivo.getTipoCultivo(),
                cultivo.getFechaSiembra(),
                request.getTipoAnalisis(),
                request.getPreguntaAdicional() != null ? request.getPreguntaAdicional() : "Ninguna"
        );
    }
}