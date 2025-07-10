# Docker Guide - Car2Go Microservices

## 🚀 Quick Start

### Opción 1: Script Automático (Recomendado)

**Windows PowerShell:**
```powershell
.\deploy.ps1
```

**Linux/Mac Bash:**
```bash
chmod +x deploy.sh
./deploy.sh
```

### Opción 2: Manual

```bash
# 1. Construir todos los microservicios
mvn clean package -DskipTests  # En cada directorio de microservicio

# 2. Iniciar todos los contenedores
docker-compose up --build -d

# 3. Verificar estado
docker-compose ps
```

## 📊 Servicios y Puertos

| Servicio | Puerto | URL |
|----------|--------|-----|
| MySQL Database | 3306 | localhost:3306 |
| Eureka Server | 8761 | http://localhost:8761 |
| API Gateway | 8080 | http://localhost:8080 |
| IAM Service | 8081 | http://localhost:8081 |
| Vehicle Service | 8082 | http://localhost:8082 |
| Payment Service | 8083 | http://localhost:8083 |
| Profile Service | 8084 | http://localhost:8084 |
| User Interaction | 8085 | http://localhost:8085 |

## 🗄️ Bases de Datos MySQL

Las siguientes bases de datos se crean automáticamente:

```sql
CREATE DATABASE car2go_iam_service;
CREATE DATABASE car2go_vehicle_service;
CREATE DATABASE car2go_payment_service;
CREATE DATABASE car2go_profile_service;
CREATE DATABASE car2go_userinteraction_service;
```

**Credenciales por defecto:**
- Host: `localhost` (o `mysql` desde dentro de los contenedores)
- Puerto: `3306`
- Usuario: `root`
- Contraseña: `password`

## 🔧 Comandos Útiles

### Ver logs de un servicio específico
```bash
docker-compose logs -f iam-service
docker-compose logs -f mysql
```

### Reiniciar un servicio
```bash
docker-compose restart iam-service
```

### Parar todos los servicios
```bash
docker-compose down
```

### Limpiar todo (contenedores, volúmenes, redes)
```bash
docker-compose down -v --remove-orphans
docker system prune -f
```

### Verificar estado de los servicios
```bash
docker-compose ps
```

### Conectarse a MySQL
```bash
docker exec -it mysql-car2go mysql -u root -p
# Password: password
```

## 🔍 Health Checks

El sistema incluye health checks para:
- **MySQL**: Verifica que la base de datos esté disponible
- **Eureka**: Verifica que el discovery service esté funcionando

Los servicios esperarán a que sus dependencias estén saludables antes de iniciarse.

## 🛠️ Troubleshooting

### Problema: Error de conexión a base de datos
```bash
# Verificar que MySQL esté funcionando
docker-compose logs mysql

# Verificar que las bases de datos se crearon
docker exec -it mysql-car2go mysql -u root -p -e "SHOW DATABASES;"
```

### Problema: Servicio no se registra en Eureka
```bash
# Verificar logs del servicio
docker-compose logs -f [nombre-servicio]

# Verificar que Eureka esté funcionando
curl http://localhost:8761/eureka/apps

eureka-server:8761
```

### Problema: Puertos ocupados
```bash
# Ver qué procesos usan los puertos
netstat -ano | findstr :8080  # Windows
lsof -i :8080                 # Linux/Mac

# Cambiar puertos en docker-compose.yml si es necesario
```

## 🔄 Desarrollo Local

Para desarrollo local, puedes ejecutar algunos servicios localmente y otros en Docker:

1. **Solo MySQL en Docker:**
```bash
docker run -d --name mysql-local \
  -e MYSQL_ROOT_PASSWORD=password \
  -e MYSQL_DATABASE=car2go_iam_service \
  -p 3306:3306 \
  mysql:8.0
```

2. **Ejecutar microservicios desde IDE:**
   - Configurar variables de entorno para apuntar a `localhost:3306`
   - Ejecutar Eureka primero, luego los demás servicios

## 📝 Variables de Entorno

Cada microservicio acepta las siguientes variables de entorno:

- `DB_HOST`: Host de la base de datos (default: localhost)
- `DB_PORT`: Puerto de la base de datos (default: 3306)
- `DB_NAME`: Nombre de la base de datos
- `DB_USERNAME`: Usuario de la base de datos (default: root)
- `DB_PASSWORD`: Contraseña de la base de datos (default: password)
- `EUREKA_SERVER`: URL del servidor Eureka
