package com.agrosoft.api.features.crops.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EtapaPredeterminada {
    GERMINACION("Germinación", 1),
    PLANTULA("Plántula", 2),
    CRECIMIENTO("Crecimiento", 3),
    FLORACION("Floración", 4),
    COSECHA("Cosecha", 5);

    private final String nombre;
    private final int orden;
}