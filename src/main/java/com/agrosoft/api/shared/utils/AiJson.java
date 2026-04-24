package com.agrosoft.api.shared.utils;

public class AiJson {

    private AiJson() {
    }

    /**
     * Limpia la respuesta de la IA eliminando las etiquetas de Markdown
     * para asegurar que el ObjectMapper pueda parsearlo correctamente.
     */
    public static String cleanJsonResponse(String rawResponse) {
        if (rawResponse == null || rawResponse.isBlank()) {
            return "{}";
        }

        String cleaned = rawResponse.trim();

        // Quitar la etiqueta de apertura ```json o ```
        if (cleaned.startsWith("```json")) {
            cleaned = cleaned.substring(7);
        } else if (cleaned.startsWith("```")) {
            cleaned = cleaned.substring(3);
        }

        if (cleaned.endsWith("```")) {
            cleaned = cleaned.substring(0, cleaned.length() - 3);
        }

        return cleaned.trim();
    }
}