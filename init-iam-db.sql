-- Script de inicialización para IAM Service Database
CREATE DATABASE IF NOT EXISTS car2go_iam_service;

-- Crear usuario específico para IAM (opcional, para mejor seguridad)
-- CREATE USER IF NOT EXISTS 'iam_user'@'%' IDENTIFIED BY 'iam_password';
-- GRANT ALL PRIVILEGES ON car2go_iam_service.* TO 'iam_user'@'%';
-- FLUSH PRIVILEGES;

SHOW DATABASES;