# Este script construye y despliega todos los microservicios

Write-Host "Car2Go Microservices Deployment Script" -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Green

# Función para imprimir mensajes de estado
function Write-Status {
    param($Message)
    Write-Host "[INFO] $Message" -ForegroundColor Green
}

function Write-Warning {
    param($Message)
    Write-Host "[WARNING] $Message" -ForegroundColor Yellow
}

function Write-Error {
    param($Message)
    Write-Host "[ERROR] $Message" -ForegroundColor Red
}

# Verificar que Docker está corriendo
try {
    docker info | Out-Null
    Write-Status "Docker está corriendo [OK]"
} catch {
    Write-Error "Docker no está corriendo. Por favor, inicia Docker Desktop."
    exit 1
}

# Limpiar contenedores y volúmenes existentes (opcional)
$cleanup = Read-Host "¿Deseas limpiar contenedores y volúmenes existentes? (y/N)"
if ($cleanup -eq "y" -or $cleanup -eq "Y") {
    Write-Warning "Limpiando contenedores y volúmenes existentes..."
    docker-compose down -v --remove-orphans
    docker system prune -f
}

# Construir las aplicaciones Java
Write-Status "Construyendo aplicaciones Java con Maven..."

$services = @("eureka", "gateway", "car2go-iam-service-main", "car2go-management-service-main", "car2go-payment-service-main", "car2go-profile-service-main", "car2go-userinteraction-main")

foreach ($service in $services) {
    if (Test-Path $service) {
        Write-Status "Construyendo $service..."
        Set-Location $service
        
        # Verificar que existe mvnw.cmd
        if (Test-Path "mvnw.cmd") {
            # Construir con Maven
            Write-Host "Ejecutando: .\mvnw.cmd clean package -DskipTests" -ForegroundColor Gray
            .\mvnw.cmd clean package -DskipTests
            
            if ($LASTEXITCODE -eq 0) {
                Write-Status "$service construido exitosamente [OK]"
                
                # Verificar que se creó el JAR
                if (Test-Path "target\*.jar") {
                    Write-Status "JAR encontrado en target/ [OK]"
                } else {
                    Write-Warning "No se encontró JAR en target/ para $service"
                }
            } else {
                Write-Error "Error construyendo $service [ERROR]"
                Write-Host "Código de salida: $LASTEXITCODE" -ForegroundColor Red
                Set-Location ..
                exit 1
            }
        } else {
            Write-Warning "No se encontró mvnw.cmd en $service, saltando..."
        }
        Set-Location ..
    } else {
        Write-Warning "Directorio $service no encontrado, saltando..."
    }
}

# Construir e iniciar los contenedores
Write-Status "Iniciando contenedores con Docker Compose..."
docker-compose up --build -d

# Verificar el estado de los servicios
Write-Status "Verificando estado de los servicios..."
Start-Sleep 10

Write-Host ""
Write-Host "Estado de los Contenedores:" -ForegroundColor Cyan
Write-Host "==============================" -ForegroundColor Cyan
docker-compose ps

Write-Host ""
Write-Host "URLs de los Servicios:" -ForegroundColor Cyan
Write-Host "=========================" -ForegroundColor Cyan
Write-Host "Eureka Server: http://localhost:8761"
Write-Host "API Gateway: http://localhost:8080"
Write-Host "IAM Service: http://localhost:8081"
Write-Host "Vehicle Service: http://localhost:8082"
Write-Host "Payment Service: http://localhost:8083"
Write-Host "Profile Service: http://localhost:8084"
Write-Host "User Interaction Service: http://localhost:8085"
Write-Host "MySQL Database: localhost:3306"

Write-Host ""
Write-Host "Comandos útiles:" -ForegroundColor Cyan
Write-Host "=================" -ForegroundColor Cyan
Write-Host "Ver logs: docker-compose logs -f [servicio]"
Write-Host "Parar todo: docker-compose down"
Write-Host "Reiniciar servicio: docker-compose restart [servicio]"
Write-Host "Ver estado: docker-compose ps"

Write-Status "Deployment completado!"

# Abrir Eureka en el navegador
Start-Process "http://localhost:8761"
