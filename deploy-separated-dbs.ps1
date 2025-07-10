#!/usr/bin/env pwsh

# Script para desplegar Car2Go con bases de datos separadas
# Cada microservicio tendrá su propia instancia de MySQL

Write-Host " Iniciando despliegue de Car2Go con Bases de Datos Separadas..." -ForegroundColor Green

# Detener y limpiar contenedores existentes
Write-Host " Limpiando contenedores existentes..." -ForegroundColor Yellow
docker-compose -f docker-compose-separated-dbs.yml down
docker system prune -f

# Construir las imágenes
Write-Host " Construyendo imágenes de microservicios..." -ForegroundColor Blue
docker-compose -f docker-compose-separated-dbs.yml build --no-cache

# Verificar que las imágenes se construyeron correctamente
Write-Host " Verificando imágenes construidas..." -ForegroundColor Cyan
docker images | findstr "car2go\|eureka\|gateway"

# Levantar los servicios en el orden correcto
Write-Host " Levantando bases de datos MySQL..." -ForegroundColor Magenta
docker-compose -f docker-compose-separated-dbs.yml up -d mysql-iam mysql-vehicle mysql-payment mysql-profile mysql-interaction

# Esperar a que las bases de datos estén listas
Write-Host " Esperando a que las bases de datos estén listas..." -ForegroundColor Yellow
Start-Sleep -Seconds 60

# Verificar que las bases de datos estén corriendo
Write-Host " Verificando estado de las bases de datos..." -ForegroundColor Cyan
docker ps | findstr mysql

# Levantar Eureka
Write-Host " Levantando Eureka Discovery Service..." -ForegroundColor Blue
docker-compose -f docker-compose-separated-dbs.yml up -d eureka-server

# Esperar a que Eureka esté listo
Write-Host " Esperando a que Eureka esté listo..." -ForegroundColor Yellow
Start-Sleep -Seconds 30

# Levantar los microservicios
Write-Host " Levantando microservicios..." -ForegroundColor Green
docker-compose -f docker-compose-separated-dbs.yml up -d iam-service management-service payment-service profile-service userinteraction-service

# Esperar a que los microservicios estén listos
Write-Host " Esperando a que los microservicios estén listos..." -ForegroundColor Yellow
Start-Sleep -Seconds 45

# Levantar el Gateway
Write-Host " Levantando API Gateway..." -ForegroundColor Magenta
docker-compose -f docker-compose-separated-dbs.yml up -d gateway

# Esperar a que el Gateway esté listo
Write-Host " Esperando a que el Gateway esté listo..." -ForegroundColor Yellow
Start-Sleep -Seconds 20

# Verificar el estado de todos los servicios
Write-Host "Estado de todos los servicios:" -ForegroundColor Green
docker-compose -f docker-compose-separated-dbs.yml ps

# Verificar logs de errores
Write-Host "Verificando logs de errores..." -ForegroundColor Yellow
$services = @("mysql-iam", "mysql-vehicle", "mysql-payment", "mysql-profile", "mysql-interaction", "eureka-server", "iam-service", "management-service", "payment-service", "profile-service", "userinteraction-service", "gateway")

foreach ($service in $services) {
    Write-Host "--- Logs de $service ---" -ForegroundColor Cyan
    docker logs $service --tail 5 2>&1 | Select-String -Pattern "ERROR|Exception|Failed|denied" | Select-Object -First 3
}

# Pruebas básicas de conectividad
Write-Host "Realizando pruebas básicas de conectividad..." -ForegroundColor Green

Write-Host " Probando Eureka (http://localhost:8761)..." -ForegroundColor Blue
try {
    $eureka = Invoke-RestMethod -Uri "http://localhost:8761/eureka/apps" -TimeoutSec 10 -ErrorAction Stop
    Write-Host " Eureka responde correctamente" -ForegroundColor Green
} catch {
    Write-Host " Error conectando a Eureka: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host " Probando Gateway (http://localhost:8080)..." -ForegroundColor Blue
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/actuator/health" -TimeoutSec 10 -ErrorAction Stop
    Write-Host " Gateway responde correctamente (Status: $($response.StatusCode))" -ForegroundColor Green
} catch {
    Write-Host " Error conectando al Gateway: $($_.Exception.Message)" -ForegroundColor Red
}

# Verificar que cada servicio tenga su propia base de datos
Write-Host " Verificando bases de datos separadas..." -ForegroundColor Magenta

$databases = @(
    @{Name="IAM"; Container="mysql-iam"; Port="3306"; Database="car2go_iam_service"},
    @{Name="Vehicle"; Container="mysql-vehicle"; Port="3307"; Database="car2go_vehicle_service"},
    @{Name="Payment"; Container="mysql-payment"; Port="3308"; Database="car2go_payment_service"},
    @{Name="Profile"; Container="mysql-profile"; Port="3309"; Database="car2go_profile_service"},
    @{Name="Interaction"; Container="mysql-interaction"; Port="3310"; Database="car2go_userinteraction_service"}
)

foreach ($db in $databases) {
    Write-Host " Verificando BD $($db.Name) en puerto $($db.Port)..." -ForegroundColor Cyan
    try {
        $result = docker exec $($db.Container) mysql -uroot -p$($db.Name.ToLower())_password -e "SHOW DATABASES;" 2>$null
        if ($result -match $($db.Database)) {
            Write-Host " Base de datos $($db.Database) existe en $($db.Container)" -ForegroundColor Green
        } else {
            Write-Host " Base de datos $($db.Database) no encontrada en $($db.Container)" -ForegroundColor Yellow
        }
    } catch {
        Write-Host " Error verificando $($db.Container): $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host " Despliegue completado!" -ForegroundColor Green
Write-Host ""
Write-Host " Servicios disponibles:" -ForegroundColor Cyan
Write-Host " Eureka Server: http://localhost:8761" -ForegroundColor White
Write-Host " API Gateway: http://localhost:8080" -ForegroundColor White
Write-Host " IAM Service: http://localhost:8081" -ForegroundColor White
Write-Host " Vehicle Service: http://localhost:8082" -ForegroundColor White
Write-Host " Payment Service: http://localhost:8083" -ForegroundColor White
Write-Host " Profile Service: http://localhost:8084" -ForegroundColor White
Write-Host " User Interaction Service: http://localhost:8085" -ForegroundColor White
Write-Host ""
Write-Host " Bases de datos MySQL:" -ForegroundColor Cyan
Write-Host " IAM Database: localhost:3306" -ForegroundColor White
Write-Host " Vehicle Database: localhost:3307" -ForegroundColor White
Write-Host " Payment Database: localhost:3308" -ForegroundColor White
Write-Host " Profile Database: localhost:3309" -ForegroundColor White
Write-Host " Interaction Database: localhost:3310" -ForegroundColor White
Write-Host ""
Write-Host " Para ver logs: docker-compose -f docker-compose-separated-dbs.yml logs [servicio]" -ForegroundColor Gray
Write-Host " Para detener: docker-compose -f docker-compose-separated-dbs.yml down" -ForegroundColor Gray
