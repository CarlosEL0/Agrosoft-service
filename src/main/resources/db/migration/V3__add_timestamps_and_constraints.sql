-- =========================================================================
-- DESCRIPCIÓN: Añade columnas de auditoría y restricciones CHECK
-- =========================================================================

-- 1. Añadir created_at y updated_at a las tablas principales
ALTER TABLE usuario
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE cultivo
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE face_agricola
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE evento_cuidado
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Evita que un cultivo tenga un terreno negativo o cero
ALTER TABLE cultivo
    ADD CONSTRAINT chk_cultivo_tamano_terreno CHECK (tamano_terreno > 0);

-- Validar que el porcentaje de poda siempre esté entre 0.1 y 100
ALTER TABLE reporte_poda
    ADD CONSTRAINT chk_poda_porcentaje CHECK (porcentaje_podado > 0 AND porcentaje_podado <= 100);

-- Validar estados permitidos en irregularidades (Catálogo estricto)
ALTER TABLE irregularidad
    ADD CONSTRAINT chk_irregularidad_estado CHECK (estado IN ('activa', 'tratada', 'resuelta'));