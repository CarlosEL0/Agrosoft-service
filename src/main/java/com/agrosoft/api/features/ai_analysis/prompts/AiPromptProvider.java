package com.agrosoft.api.features.ai_analysis.prompts;

import com.agrosoft.api.features.ai_analysis.dto.AnalisisIaRequestDTO;
import com.agrosoft.api.features.crops.entities.Cultivo;
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

    public String buildUserPrompt(Cultivo cultivo, Irregularidad plaga, AnalisisIaRequestDTO request) {
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

    public String buildHarvestUserPrompt(Cultivo cultivo, com.agrosoft.api.features.harvest.dto.ReporteCosechaRequestDTO request) {
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

    public String getCareSummarySystemPrompt() {
        return """
            Eres el Asesor Agrícola Experto del sistema AgroSoft.
            Tu objetivo es analizar el historial de cuidados (riegos, fertilizaciones, etc.) de un cultivo y determinar si se están haciendo correctamente o si hay deficiencias.
            
            REGLA ESTRICTA: Tu respuesta DEBE ser ÚNICA Y EXCLUSIVAMENTE un objeto JSON válido.
            No incluyas saludos, ni explicaciones fuera del JSON.
            
            El JSON debe tener exactamente esta estructura:
            {
              "resultadoAnalisis": "Diagnóstico detallado sobre si los riegos y fertilizaciones reportados son adecuados para la etapa actual del cultivo, mencionando aciertos y alertas...",
              "recomendaciones": [
                {
                  "titulo": "Título de la recomendación (ej. Aumentar frecuencia de riego)",
                  "descripcion": "Descripción detallada de cómo mejorar el cuidado en los próximos días...",
                  "prioridad": "alta"
                }
              ]
            }
            """;
    }

    public String buildCareSummaryUserPrompt(Cultivo cultivo, String historialCuidados, String preguntaAdicional) {
        return String.format("""
            Datos Básicos del Cultivo:
            - Nombre: %s
            - Tipo: %s
            - Fecha de Siembra: %s
            
            Historial de Cuidados Recientes:
            %s
            
            Comentarios del agricultor: %s
            
            Por favor, genera el diagnóstico y las recomendaciones en formato JSON.
            """,
                cultivo.getNombreCultivo(),
                cultivo.getTipoCultivo(),
                cultivo.getFechaSiembra(),
                historialCuidados != null && !historialCuidados.isBlank() ? historialCuidados : "No se han registrado eventos de cuidado recientes.",
                preguntaAdicional != null ? preguntaAdicional : "Ninguno"
        );
    }

    public String getGrowthInterpretationSystemPrompt() {
        return """
            Eres el Especialista en Fisiología Vegetal y Biometría del sistema AgroSoft.
            Tu objetivo es analizar los registros de crecimiento de una planta (altura, grosor de tallo, diámetro) y compararlos con los parámetros esperados de ese cultivo para detectar enanismo, estiramiento excesivo (etiolación) o crecimiento óptimo.
            
            REGLA ESTRICTA: Tu respuesta DEBE ser ÚNICA Y EXCLUSIVAMENTE un objeto JSON válido.
            No incluyas saludos, ni explicaciones fuera del JSON.
            
            El JSON debe tener exactamente esta estructura:
            {
              "resultadoAnalisis": "Interpretación detallada de la curva de crecimiento. Menciona si la altura y el tallo son proporcionales y si va acorde a lo esperado...",
              "recomendaciones": [
                {
                  "titulo": "Acción recomendada (ej. Aumentar horas de luz)",
                  "descripcion": "Descripción detallada del porqué y cómo hacerlo...",
                  "prioridad": "media"
                }
              ]
            }
            """;
    }

    public String buildGrowthInterpretationUserPrompt(Cultivo cultivo, String historialCrecimiento, String preguntaAdicional) {
        return String.format("""
            Parámetros Teóricos del Cultivo:
            - Nombre: %s
            - Tipo: %s
            - Fecha de Siembra: %s
            - Altura Máxima Esperada al Cosechar: %s cm
            
            Registros Reales de Crecimiento (Del más reciente al más antiguo):
            %s
            
            Comentarios del agricultor: %s
            
            Por favor, genera la interpretación de crecimiento y las recomendaciones en formato JSON.
            """,
                cultivo.getNombreCultivo(),
                cultivo.getTipoCultivo(),
                cultivo.getFechaSiembra(),
                cultivo.getAlturaEsperada() != null ? cultivo.getAlturaEsperada() : "No especificada",
                historialCrecimiento != null && !historialCrecimiento.isBlank() ? historialCrecimiento : "No hay registros de crecimiento disponibles.",
                preguntaAdicional != null ? preguntaAdicional : "Ninguno"
        );
    }

    public String buildHarvestUserPrompt(Cultivo cultivo, com.agrosoft.api.features.harvest.dto.ReporteCosechaRequestDTO request, String historialPlagas, String historialCuidados) {
        return String.format("""
            Datos Básicos del Cultivo:
            - Nombre: %s
            - Tipo: %s
            - Fecha de Siembra: %s
            
            Datos Reales de la Cosecha:
            - Fecha de Cosecha: %s
            - Cantidad Cosechada Real: %s kg
            - Calidad del Cultivo: %s
            
            HISTORIAL DEL CICLO:
            Plagas y Enfermedades presentadas:
            %s
            
            Cuidados Aplicados (Riegos, Fumigaciones, etc.):
            %s
            
            Instrucciones: Genera el JSON del reporte de cosecha. Analiza profundamente el historial proporcionado para deducir y llenar los campos 'factoresExito' y 'areasMejora'. Justifica por qué el rendimiento y el costo fueron afectados basándote en las plagas y cuidados reales.
            """,
                cultivo.getNombreCultivo(),
                cultivo.getTipoCultivo(),
                cultivo.getFechaSiembra(),
                request.getFechaCosecha(),
                request.getCantidadCosechada(),
                request.getCalidadCultivo(),
                historialPlagas.isEmpty() ? "No hubo plagas reportadas." : historialPlagas,
                historialCuidados.isEmpty() ? "No se registraron cuidados especiales." : historialCuidados
        );
    }
}