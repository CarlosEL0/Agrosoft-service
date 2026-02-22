package com.agrosoft.api.features.ai_analysis.prompts;

import com.agrosoft.api.features.ai_analysis.dto.AnalisisIaRequestDTO;
import com.agrosoft.api.features.crops.entities.CultivoEntity;
import com.agrosoft.api.features.monitoring.entities.Irregularidad;
import org.springframework.stereotype.Component;

@Component
public class AiPromptProvider {

    public String getSystemPrompt() {
        return """
            Eres el Ingeniero Agrónomo principal del sistema AgroSoft.
            Tu objetivo es analizar los datos del cultivo, identificar problemas como plagas o enfermedades, y dar un diagnóstico certero.
            
            REGLA ESTRICTA: Tu respuesta DEBE ser ÚNICA Y EXCLUSIVAMENTE un objeto JSON válido.
            No incluyas saludos, ni explicaciones fuera del JSON.
            
            El JSON debe tener exactamente esta estructura:
            {
              "resultadoAnalisis": "Aquí va tu resumen y diagnóstico general detallado.",
              "recomendaciones": [
                {
                  "titulo": "Título corto de la acción (ej. Aplicar insecticida)",
                  "descripcion": "Cómo llevar a cabo esta acción paso a paso de forma profesional",
                  "prioridad": "alta"
                }
              ]
            }
            """;
    }

    public String buildUserPrompt(CultivoEntity cultivo, Irregularidad plaga, AnalisisIaRequestDTO request) {
        String baseContext = String.format("""
            Datos Básicos del Cultivo:
            - Nombre: %s
            - Tipo: %s
            - Fecha de Siembra: %s
            """, cultivo.getNombreCultivo(), cultivo.getTipoCultivo(), cultivo.getFechaSiembra());

        String plagaContext = "";
        if (plaga != null) {
            plagaContext = String.format("""
                
                ¡ATENCIÓN! Se ha reportado la siguiente anomalía en el cultivo:
                - Tipo de irregularidad: %s
                - Posible plaga/enfermedad: %s
                - Nivel de daño observado: %s
                - Severidad: %s
                - Comentarios del agricultor: %s
                """,
                    plaga.getTipoIrregularidad(),
                    plaga.getNombrePlaga() != null ? plaga.getNombrePlaga() : "No identificada",
                    plaga.getNivelDano(),
                    plaga.getSeveridad(),
                    plaga.getComentarioAgricultor() != null ? plaga.getComentarioAgricultor() : "Ninguno");
        }

        String requestContext = String.format("""
            
            Tipo de Análisis solicitado: %s
            Pregunta/Contexto adicional del agricultor: %s
            
            Genera el análisis en JSON considerando todos los datos anteriores.
            """,
                request.getTipoAnalisis(),
                request.getPreguntaAdicional() != null ? request.getPreguntaAdicional() : "Ninguna");

        return baseContext + plagaContext + requestContext;
    }

    //reporte de cosecha
    public String getHarvestSystemPrompt() {
        return """
            Eres el Auditor Agrícola y Analista de Rendimiento del sistema AgroSoft.
            Tu objetivo es analizar los datos finales de una cosecha, calcular métricas de eficiencia y dar conclusiones detalladas.
            
            REGLA ESTRICTA: Tu respuesta DEBE ser ÚNICA Y EXCLUSIVAMENTE un objeto JSON válido.
            No incluyas saludos, ni explicaciones fuera del JSON.
            
            El JSON debe tener exactamente esta estructura y usar números (no strings) para los valores monetarios/métricos:
            {
              "rendimientoEsperado": 550.50,
              "desviacionRendimiento": -5.2,
              "eficienciaRiego": 2.5,
              "costoTotal": 1500.00,
              "costoPorKg": 3.00,
              "resumenCiclo": "Resumen detallado de cómo le fue a este ciclo agrícola...",
              "factoresExito": "Lista de cosas que salieron bien o mal que impactaron la cosecha...",
              "areasMejora": "Recomendaciones específicas para que el próximo ciclo sea más rentable..."
            }
            """;
    }

    public String buildHarvestUserPrompt(CultivoEntity cultivo, com.agrosoft.api.features.harvest.dto.ReporteCosechaRequestDTO request) {
        return String.format("""
            Datos Básicos del Cultivo:
            - Nombre: %s
            - Tipo: %s
            - Fecha de Siembra: %s
            
            Datos Reales de la Cosecha (Reportados por el agricultor):
            - Fecha de Cosecha: %s
            - Cantidad Cosechada Real: %s kg
            - Calidad del Cultivo: %s
            
            Por favor, genera el JSON del reporte de cosecha calculando las métricas solicitadas basándote en estándares agronómicos para este tipo de cultivo.
            """,
                cultivo.getNombreCultivo(),
                cultivo.getTipoCultivo(),
                cultivo.getFechaSiembra(),
                request.getFechaCosecha(),
                request.getCantidadCosechada(),
                request.getCalidadCultivo());
    }
}