-- =========================================================================
--      Vistas de análisis, agregación y seguimiento de la hipótesis
-- =========================================================================

-- ---------------------------------------------------------
-- VISTA 1: JOIN
-- Obtener un resumen legible del cultivo, su dueño y su fase actual
-- ---------------------------------------------------------
CREATE OR REPLACE VIEW vw_cultivos_detallados AS
SELECT
    c.id_cultivo,
    c.nombre_cultivo,
    c.tipo_cultivo,
    u.nombre || ' ' || u.apellido AS nombre_agricultor,
    f.nombre_ciclo,
    f.estado AS estado_fase
FROM cultivo c
         INNER JOIN usuario u
                    ON c.id_usuario = u.id_usuario
         LEFT JOIN face_agricola f
                   ON c.id_cultivo = f.id_cultivo;

-- ---------------------------------------------------------
-- VISTA 2: Funciones de agregación
-- Contar y clasificar los eventos de cuidado por cultivo
-- ---------------------------------------------------------
CREATE OR REPLACE VIEW vw_estadisticas_eventos AS
SELECT
    e.id_cultivo,
    COUNT(e.id_evento) AS total_eventos,
    SUM(CASE WHEN e.tipo_evento = 'riego' THEN 1 ELSE 0 END) AS total_riegos,
    SUM(CASE WHEN e.tipo_evento = 'fertilizacion' THEN 1 ELSE 0 END) AS total_fertilizaciones,
    MAX(e.fecha_evento) AS ultimo_evento_registrado
FROM evento_cuidado e
GROUP BY e.id_cultivo;

-- ---------------------------------------------------------
-- VISTA 3: Específica para la Hipótesis
-- Hipótesis: "Si los usuarios aplican las recomendaciones... en al menos un 70%"
-- Cruzar el % de recomendaciones aplicadas con la desviación de la cosecha
-- ---------------------------------------------------------
CREATE OR REPLACE VIEW vw_analisis_hipotesis_ia AS
WITH stats_recomendaciones AS (
    SELECT
        id_cultivo,
        COUNT(id_recomendacion) AS total_recomendaciones,
        SUM(CASE WHEN aplicada = true THEN 1 ELSE 0 END) AS recomendaciones_aplicadas
    FROM recomendacion
    GROUP BY id_cultivo
)
SELECT
    sr.id_cultivo,
    c.nombre_cultivo,
    sr.total_recomendaciones,
    sr.recomendaciones_aplicadas,
    ROUND((sr.recomendaciones_aplicadas::NUMERIC / NULLIF(sr.total_recomendaciones, 0)) * 100, 2) AS porcentaje_aplicacion,
    rc.rendimiento_esperado,
    rc.cantidad_cosechada,
    rc.desviacion_rendimiento,
    CASE
        WHEN ROUND((sr.recomendaciones_aplicadas::NUMERIC / NULLIF(sr.total_recomendaciones, 0)) * 100, 2) >= 70 THEN 'Cumple Condición (>70%)'
        ELSE 'No Cumple Condición'
        END AS estado_hipotesis
FROM stats_recomendaciones sr
         INNER JOIN cultivo c
                    ON sr.id_cultivo = c.id_cultivo
         LEFT JOIN reporte_cosecha rc
                   ON sr.id_cultivo = rc.id_cultivo;