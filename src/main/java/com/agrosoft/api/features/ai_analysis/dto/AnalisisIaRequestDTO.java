package com.agrosoft.api.features.ai_analysis.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class AnalisisIaRequestDTO {

    //¿De qué cultivo estamos hablando?
    private UUID idCultivo;

    //Si el análisis es sobre una plaga/enfermedad específica que ya registraron
    private UUID idIrregularidad;

    // ¿Qué queremos que haga la IA? (ej: "recomendacion", "resumen_ciclo", "diagnostico")
    private String tipoAnalisis;

    //Si el agricultor quiere hacerle una pregunta específica a la IA
    private String preguntaAdicional;
}