-- Script para crear todas las bases de datos necesarias para Car2Go
-- Se ejecuta automáticamente cuando se inicia el contenedor MySQL

CREATE DATABASE IF NOT EXISTS car2go_iam_service;
CREATE DATABASE IF NOT EXISTS car2go_vehicle_service;
CREATE DATABASE IF NOT EXISTS car2go_payment_service;
CREATE DATABASE IF NOT EXISTS car2go_profile_service;
CREATE DATABASE IF NOT EXISTS car2go_userinteraction_service;

-- Mostrar las bases de datos creadas
SHOW DATABASES;
