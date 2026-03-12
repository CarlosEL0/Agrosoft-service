-- 1. Agregamos la nueva columna con un valor temporal para que los registros que ya tienes no fallen
ALTER TABLE cultivo ADD COLUMN region VARCHAR(255) DEFAULT 'Chiapas, Mexico';

-- 2. Quitamos el valor por defecto para obligar a que de ahora en adelante se envíe la región desde el frontend
ALTER TABLE cultivo ALTER COLUMN region DROP DEFAULT;

-- 3. Borramos la columna vieja de altura que ya no vamos a utilizar
ALTER TABLE cultivo DROP COLUMN altura_esperada;