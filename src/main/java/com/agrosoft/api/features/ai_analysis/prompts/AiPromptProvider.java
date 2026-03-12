package com.agrosoft.api.features.ai_analysis.prompts;

import com.agrosoft.api.features.ai_analysis.dto.AnalisisIaRequestDTO;
import com.agrosoft.api.features.crops.entities.Cultivo;
import com.agrosoft.api.features.monitoring.entities.Irregularidad;
import org.springframework.stereotype.Component;

@Component
public class AiPromptProvider {

    // ==========================================
    // CONSTANTES DE SYSTEM PROMPTS (El "Rol" de la IA)
    // ==========================================

    private static final String SYSTEM_DIAGNOSIS_TEMPLATE = """
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

    private static final String SYSTEM_HARVEST_TEMPLATE = """
            Eres el Auditor Agrícola y Analista de Rendimiento del sistema AgroSoft.
            Tu objetivo es analizar los datos finales de una cosecha, calcular métricas de eficiencia y dar conclusiones detalladas.
            
            REGLA ESTRICTA: Tu respuesta DEBE ser ÚNICA Y EXCLUSIVAMENTE un objeto JSON válido.
            No incluyas saludos, ni explicaciones fuera del JSON.
            
            IMPORTANTE: Los campos 'factoresExito' y 'areasMejora' NUNCA deben estar vacíos. Siempre debes proporcionar análisis detallados basándote en los datos del historial.
            
            El JSON debe tener exactamente esta estructura y usar números (no strings) para los valores monetarios/métricos. Reemplaza los valores 0.0 con tus cálculos reales:
            {
              "rendimientoEsperado": 0.0,
              "desviacionRendimiento": 0.0,
              "eficienciaRiego": 0.0,
              "costoTotal": 0.0,
              "costoPorKg": 0.0,
              "resumenCiclo": "Resumen detallado de cómo le fue a este ciclo agrícola...",
              "factoresExito": "Lista detallada y específica de factores que contribuyeron al éxito o fracaso de esta cosecha, basándote en las plagas detectadas, los tratamientos aplicados, y las condiciones del cultivo...",
              "areasMejora": "Recomendaciones concretas y accionables para optimizar el próximo ciclo agrícola, incluyendo mejoras en el manejo de plagas, eficiencia de riego, control de costos, etc..."
            }
            """;

    private static final String SYSTEM_CARE_SUMMARY_TEMPLATE = """
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

    private static final String SYSTEM_GROWTH_INTERPRETATION_TEMPLATE = """
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

    // ==========================================
    // CONSTANTES DE USER PROMPTS (Los datos)
    // ==========================================

    private static final String USER_DIAGNOSIS_TEMPLATE = """
            Datos Básicos del Cultivo:
            - Nombre: %s
            - Tipo: %s
            - Fecha de Siembra: %s
            - Region de cultivo: %s
            %s
            
            Tipo de Análisis solicitado: %s
            Pregunta/Contexto adicional del agricultor: %s
            
            Genera el análisis en JSON considerando todos los datos anteriores.
            """;

    private static final String PLAGA_CONTEXT_TEMPLATE = """
            
            ¡ATENCIÓN! Se ha reportado la siguiente anomalía en el cultivo:
            - Tipo de irregularidad: %s
            - Posible plaga/enfermedad: %s
            - Nivel de daño observado: %s
            - Severidad: %s
            - Comentarios del agricultor: %s
            """;

    private static final String USER_HARVEST_BASIC_TEMPLATE = """
            Datos Básicos del Cultivo:
            - Nombre: %s
            - Tipo: %s
            - Fecha de Siembra: %s
            
            Datos Reales de la Cosecha (Reportados por el agricultor):
            - Fecha de Cosecha: %s
            - Cantidad Cosechada Real: %s kg
            - Calidad del Cultivo: %s
            
            Por favor, genera el JSON del reporte de cosecha calculando las métricas solicitadas basándote en estándares agronómicos para este tipo de cultivo.
            """;

    private static final String USER_HARVEST_FULL_TEMPLATE = """
            Datos Básicos del Cultivo:
            - Nombre: %s
            - Tipo: %s
            - Fecha de Siembra: %s
            - Region de cultivo: %s
            
            Datos Reales de la Cosecha:
            - Fecha de Cosecha: %s
            - Cantidad Cosechada Real: %s kg
            - Calidad del Cultivo: %s
            
            HISTORIAL COMPLETO DEL CICLO AGRÍCOLA:
            
            === PLAGAS Y ENFERMEDADES DETECTADAS ===
            %s
            
            === EVENTOS DE CUIDADO APLICADOS ===
            %s
            
            INSTRUCCIONES CRÍTICAS:
            1. Genera el JSON del reporte de cosecha con TODOS los campos completos y sin omitir ninguno.
            
            2. Para el campo 'factoresExito': Analiza el historial y enumera MÍNIMO 3 factores específicos que contribuyeron al resultado. Ejemplos:
               - "Control efectivo del pulgón mediante jabón potásico, evitando pérdidas mayores"
               - "Calidad excelente lograda gracias a fertilizaciones oportunas cada 15 días"
               - "Riego constante que mantuvo la humedad óptima del suelo"
            
            3. Para el campo 'areasMejora': Identifica MÍNIMO 3 mejoras concretas y accionables. Ejemplos:
               - "Implementar monitoreo preventivo de plagas para detectarlas antes de que alcancen severidad moderada"
               - "Reducir costos mediante el uso de fertilizantes orgánicos caseros"
               - "Mejorar el rendimiento aumentando la densidad de siembra en un 15%%"
            
            4. Ambos campos DEBEN tener contenido detallado basado en el historial real. NO uses frases genéricas.
            
            5. Si el historial está vacío, deduce factores basándote en la calidad y cantidad cosechada reportadas.
            
            6. Usa el formato de lista con viñetas o numeración para facilitar la lectura.
            """;

    private static final String USER_CARE_SUMMARY_TEMPLATE = """
            Datos Básicos del Cultivo:
            - Nombre: %s
            - Tipo: %s
            - Fecha de Siembra: %s
            
            Historial de Cuidados Recientes:
            %s
            
            Comentarios del agricultor: %s
            
            Por favor, genera el diagnóstico y las recomendaciones en formato JSON.
            """;

    private static final String USER_GROWTH_INTERPRETATION_TEMPLATE = """
            Parámetros Teóricos del Cultivo:
            - Nombre: %s
            - Tipo: %s
            - Fecha de Siembra: %s
            - Region de cultivo: %s
            
            Registros Reales de Crecimiento (Del más reciente al más antiguo):
            %s
            
            Comentarios del agricultor: %s
            
            Por favor, genera la interpretación de crecimiento y las recomendaciones en formato JSON.
            """;

    // ==========================================
    // MÉTODOS
    // ==========================================

    public String getSystemPrompt() {
        return SYSTEM_DIAGNOSIS_TEMPLATE;
    }

    public String buildUserPrompt(Cultivo cultivo, Irregularidad plaga, AnalisisIaRequestDTO request) {
        String plagaContext = "";
        if (plaga != null) {
            plagaContext = String.format(PLAGA_CONTEXT_TEMPLATE,
                    plaga.getTipoIrregularidad(),
                    plaga.getNombrePlaga() != null ? plaga.getNombrePlaga() : "No identificada",
                    plaga.getNivelDano(),
                    plaga.getSeveridad(),
                    plaga.getComentarioAgricultor() != null ? plaga.getComentarioAgricultor() : "Ninguno");
        }

        return String.format(USER_DIAGNOSIS_TEMPLATE,
                cultivo.getNombreCultivo(),
                cultivo.getTipoCultivo(),
                cultivo.getFechaSiembra(),
                cultivo.getRegion(),
                plagaContext,
                request.getTipoAnalisis(),
                request.getPreguntaAdicional() != null ? request.getPreguntaAdicional() : "Ninguna");
    }

    public String getHarvestSystemPrompt() {
        return SYSTEM_HARVEST_TEMPLATE;
    }

    public String buildHarvestUserPrompt(Cultivo cultivo, com.agrosoft.api.features.harvest.dto.ReporteCosechaRequestDTO request) {
        return String.format(USER_HARVEST_BASIC_TEMPLATE,
                cultivo.getNombreCultivo(),
                cultivo.getTipoCultivo(),
                cultivo.getFechaSiembra(),
                request.getFechaCosecha(),
                request.getCantidadCosechada(),
                request.getCalidadCultivo());
    }

    public String buildHarvestUserPrompt(Cultivo cultivo, com.agrosoft.api.features.harvest.dto.ReporteCosechaRequestDTO request, String historialPlagas, String historialCuidados) {
        return String.format(USER_HARVEST_FULL_TEMPLATE,
                cultivo.getNombreCultivo(),
                cultivo.getTipoCultivo(),
                cultivo.getFechaSiembra(),
                cultivo.getRegion() != null ? cultivo.getRegion() : "No especificada",
                request.getFechaCosecha(),
                request.getCantidadCosechada(),
                request.getCalidadCultivo(),
                historialPlagas.isEmpty() ? "No hubo plagas reportadas." : historialPlagas,
                historialCuidados.isEmpty() ? "No se registraron cuidados especiales." : historialCuidados
        );
    }

    public String getCareSummarySystemPrompt() {
        return SYSTEM_CARE_SUMMARY_TEMPLATE;
    }

    public String buildCareSummaryUserPrompt(Cultivo cultivo, String historialCuidados, String preguntaAdicional) {
        return String.format(USER_CARE_SUMMARY_TEMPLATE,
                cultivo.getNombreCultivo(),
                cultivo.getTipoCultivo(),
                cultivo.getFechaSiembra(),
                historialCuidados != null && !historialCuidados.isBlank() ? historialCuidados : "No se han registrado eventos de cuidado recientes.",
                preguntaAdicional != null ? preguntaAdicional : "Ninguno"
        );
    }

    public String getGrowthInterpretationSystemPrompt() {
        return SYSTEM_GROWTH_INTERPRETATION_TEMPLATE;
    }

    public String buildGrowthInterpretationUserPrompt(Cultivo cultivo, String historialCrecimiento, String preguntaAdicional) {
        return String.format(USER_GROWTH_INTERPRETATION_TEMPLATE,
                cultivo.getNombreCultivo(),
                cultivo.getTipoCultivo(),
                cultivo.getFechaSiembra(),
                cultivo.getRegion() != null ? cultivo.getRegion() : "No especificada",
                historialCrecimiento != null && !historialCrecimiento.isBlank() ? historialCrecimiento : "No hay registros de crecimiento disponibles.",
                preguntaAdicional != null ? preguntaAdicional : "Ninguno"
        );
    }
}