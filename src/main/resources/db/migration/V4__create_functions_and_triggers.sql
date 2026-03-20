-- ---------------------------------------------------------
-- 1. ESCALAR DE AUDITORÍA
-- Actualizar automáticamente el campo updated_at
-- ---------------------------------------------------------
CREATE OR REPLACE FUNCTION fn_trg_update_timestamp()
RETURNS TRIGGER AS $$
BEGIN

    NEW.updated_at := CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- ---------------------------------------------------------
-- 2. ASIGNACIÓN DE TRIGGERS DE AUDITORÍA
-- ---------------------------------------------------------
CREATE TRIGGER trg_usuario_upd_timestamp
    BEFORE UPDATE ON usuario
    FOR EACH ROW
    EXECUTE FUNCTION fn_trg_update_timestamp();

CREATE TRIGGER trg_cultivo_upd_timestamp
    BEFORE UPDATE ON cultivo
    FOR EACH ROW
    EXECUTE FUNCTION fn_trg_update_timestamp();

CREATE TRIGGER trg_face_agricola_upd_timestamp
    BEFORE UPDATE ON face_agricola
    FOR EACH ROW
    EXECUTE FUNCTION fn_trg_update_timestamp();

CREATE TRIGGER trg_evento_cuidado_upd_timestamp
    BEFORE UPDATE ON evento_cuidado
    FOR EACH ROW
    EXECUTE FUNCTION fn_trg_update_timestamp();

-- ---------------------------------------------------------
-- 3. LÓGICA DE NEGOCIO
-- Prevenir que una etapa termine antes de iniciar
-- ---------------------------------------------------------
CREATE OR REPLACE FUNCTION fn_trg_validar_fechas_etapa()
RETURNS TRIGGER AS $$
BEGIN
    -- Validamos si la fecha de fin viene en el INSERT/UPDATE y si rompe la lógica
    IF NEW.fecha_fin IS NOT NULL AND NEW.fecha_fin < NEW.fecha_inicio THEN
        RAISE EXCEPTION 'Regla de negocio fallida: La fecha de fin (%) es anterior a la fecha de inicio (%) para la etapa %',
                        NEW.fecha_fin, NEW.fecha_inicio, NEW.nombre_etapa
            USING HINT = 'Asegúrese de que la fecha de fin sea igual o posterior a la fecha de inicio en el calendario.';
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- ---------------------------------------------------------
-- 4. ASIGNACIÓN DE TRIGGER DE LÓGICA DE NEGOCIO
-- ---------------------------------------------------------
CREATE TRIGGER trg_etapa_crecimiento_chk_fechas
    BEFORE INSERT OR UPDATE ON etapa_crecimiento
                         FOR EACH ROW
                         EXECUTE FUNCTION fn_trg_validar_fechas_etapa();