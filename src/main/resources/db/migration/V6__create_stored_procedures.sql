-- =========================================================================
--  Procedimientos almacenados para transacciones y flujos complejos
-- =========================================================================

-- ---------------------------------------------------------
-- TRANSACCIÓN EXPLÍCITA Y MANEJO DE ERRORES
-- Registrar un reporte de cosecha de forma segura. Si algo falla,
-- se cancela todo el proceso para no dejar datos huérfanos.
-- ---------------------------------------------------------
CREATE OR REPLACE PROCEDURE sp_registrar_cosecha_segura(
    IN p_id_cultivo BIGINT,
    IN p_cantidad_cosechada NUMERIC,
    IN p_rendimiento_esperado NUMERIC
)
LANGUAGE plpgsql AS $$
DECLARE
    v_estado_actual VARCHAR;
    v_desviacion NUMERIC;
BEGIN
    -- 1. Validar si el cultivo existe y su estado actual
SELECT estado INTO v_estado_actual
FROM cultivo
WHERE id_cultivo = p_id_cultivo;

IF v_estado_actual IS NULL THEN
        RAISE EXCEPTION 'El cultivo con ID % no existe', p_id_cultivo;
END IF;

    IF v_estado_actual = 'cosechado' THEN
        ROLLBACK;
        RAISE EXCEPTION 'Violación de negocio: El cultivo ya fue cosechado previamente.';
    END IF;

    -- 2. Lógica matemática de la desviación
    v_desviacion := p_cantidad_cosechada - p_rendimiento_esperado;

    -- 3. Inserción del reporte y actualización del estado del cultivo
INSERT INTO reporte_cosecha (id_cultivo, cantidad_cosechada, rendimiento_esperado, desviacion_rendimiento)
VALUES (p_id_cultivo, p_cantidad_cosechada, p_rendimiento_esperado, v_desviacion);

UPDATE cultivo
SET estado = 'cosechado', updated_at = CURRENT_TIMESTAMP
WHERE id_cultivo = p_id_cultivo;

-- 4. Confirma transacción de forma explícita
COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        -- Si ocurre cualquier error de base de datos, abortamos todo
        ROLLBACK;
        RAISE EXCEPTION 'Error crítico al registrar la cosecha: %', SQLERRM;
END;
$$;

-- ---------------------------------------------------------
-- LÓGICA DE NEGOCIO COMPLEJA
-- Evaluar el estado de salud del cultivo basándose
-- en la cantidad de recomendaciones de IA que el agricultor aplicó.
-- ---------------------------------------------------------
CREATE OR REPLACE PROCEDURE sp_evaluar_salud_cultivo(
    IN p_id_cultivo BIGINT
)
LANGUAGE plpgsql AS $$
DECLARE
    v_total_rec INT;
    v_aplicadas INT;
    v_porcentaje NUMERIC;
    v_diagnostico VARCHAR(50);
BEGIN
    -- estadísticas del cultivo
SELECT COUNT(id_recomendacion),
       SUM(CASE WHEN aplicada = true THEN 1 ELSE 0 END)
INTO v_total_rec, v_aplicadas
FROM recomendacion
WHERE id_cultivo = p_id_cultivo;

-- Lógica compleja de negocio usando IF y CASE
IF v_total_rec > 0 THEN
        v_porcentaje := (v_aplicadas::NUMERIC / v_total_rec) * 100;

CASE
            WHEN v_porcentaje >= 80 THEN v_diagnostico := 'Saludable - Excelente Adopción de IA';
WHEN v_porcentaje >= 50 THEN v_diagnostico := 'Regular - Requiere mayor atención';
ELSE v_diagnostico := 'Crítico - Riesgo de pérdida de cosecha';
END CASE;

        -- Lanza un NOTICE simulando la actualización de un log o tabla
        RAISE NOTICE 'Cultivo %: % (%.2f%% de recomendaciones aplicadas)', p_id_cultivo, v_diagnostico, v_porcentaje;
ELSE
        RAISE NOTICE 'El cultivo % no tiene recomendaciones generadas por la IA aún.', p_id_cultivo;
END IF;
END;
$$;

-- ---------------------------------------------------------
-- CURSORES Y LÓGICA ITERATIVA
-- Recorrer todos los cultivos activos e identificar
-- aquellos que lleven más de 7 días sin recibir riego.
-- ---------------------------------------------------------
CREATE OR REPLACE PROCEDURE sp_auditoria_masiva_riegos()
LANGUAGE plpgsql AS $$
DECLARE
    v_registro RECORD;
    v_dias_sin_riego INT;
BEGIN
FOR v_registro IN (SELECT id_cultivo, nombre_cultivo FROM cultivo WHERE estado = 'activo')
    LOOP
        -- Consultar los días desde el último riego de este cultivo
SELECT EXTRACT(DAY FROM (CURRENT_DATE - MAX(fecha_evento)))
INTO v_dias_sin_riego
FROM evento_cuidado
WHERE id_cultivo = v_registro.id_cultivo AND tipo_evento = 'riego';

IF v_dias_sin_riego IS NULL THEN
            RAISE NOTICE 'ALERTA: El cultivo "%" (ID: %) NUNCA ha sido regado.',
                         v_registro.nombre_cultivo, v_registro.id_cultivo;
        ELSIF v_dias_sin_riego > 7 THEN
            RAISE NOTICE 'ALERTA: El cultivo "%" (ID: %) lleva % días sin agua.',
                         v_registro.nombre_cultivo, v_registro.id_cultivo, v_dias_sin_riego;
END IF;
END LOOP;
END;
$$;