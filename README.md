# Microservices - Car 2 Go
Microservices Migration

## 🏗️ Architecture

- **Eureka Server** (Port 8761) - Service Discovery
- **Gateway** (Port 8080) - API Gateway for routing
- **iam-service** (Port 8081) - Simple greeting microservice  
- **management-service** (Port 8082) - Service 
- **payment-service** (Port 8083) - Service 
- **user-interaction-service** (Port 8084) - Service 
- **vehicle-service** (Port 8085) - Service 

## 📋 Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- Maven (included in each service as `mvnw`)

## 🚀 Quick Start

### 1. Build All Services (ESSENTIAL!)
```powershell
# Build Eureka Server
cd eureka-server
.\mvnw clean package -DskipTests
cd ..

# Build Gateway
cd gateway  
.\mvnw clean package -DskipTests
cd ..

```

### 2. Start All Services
```powershell
# Start all containers
docker-compose up -d

# Check if all are running
docker-compose ps
```

### 3. Wait for Services to Start
Wait 30-60 seconds for all services to fully start and register with Eureka.

## 🧪 Testing the Services

### Check Service Registration
Visit Eureka Dashboard: http://localhost:8761

You should see all services registered:
- GATEWAY-SERVICE



### Test API Endpoints

**Through Gateway (Recommended):**
```powershell
# Test Service-A
Invoke-WebRequest -Uri "http://localhost:8080/service-a/service-a" -UseBasicParsing

# Test Service-B (calls Service-A internally)
Invoke-WebRequest -Uri "http://localhost:8080/service-b/service-b" -UseBasicParsing
```

**Direct Access:**
```powershell
# Direct Service-A
Invoke-WebRequest -Uri "http://localhost:8081/service-a" -UseBasicParsing

# Direct Service-B
Invoke-WebRequest -Uri "http://localhost:8082/service-b" -UseBasicParsing
```

### View API Documentation
- **Service-A Swagger**: http://localhost:8081/swagger-ui.html
- **Service-B Swagger**: http://localhost:8082/swagger-ui.html

## ⚠️ Important Precautions

### Before Starting
1. **ALWAYS run `mvn clean package` before building Docker images**
2. Make sure ports 8080, 8081, 8082, 8761 are available
3. Ensure Docker is running

### Troubleshooting

**Services not registering with Eureka:**
- Wait longer (up to 2 minutes)
- Check if Eureka server started first
- Restart containers: `docker-compose restart`

**404 errors through gateway:**
- Make sure you're using the correct URL pattern: `/service-name/service-name`
- Check Eureka dashboard to confirm service registration
- Try direct service access first

**Build failures:**
- Ensure Java 17+ is installed
- Clean and rebuild: `.\mvnw clean package -DskipTests`
- Check for port conflicts

### Service URLs Pattern
Due to Spring Cloud Gateway auto-discovery routing:
- Gateway URL: `http://localhost:8080/service-name/service-name`
- Direct URL: `http://localhost:PORT/service-name`

This happens because Gateway strips the first `/service-name` from the path.

## 🔄 Common Commands

```powershell
# Stop all services
docker-compose down

# Rebuild and restart everything
docker-compose down
# Build each service (see step 1 above)
docker-compose up -d

# View logs
docker-compose logs gateway
docker-compose logs service-a
docker-compose logs service-b

# Check running containers
docker-compose ps
```

## 📚 Learning Notes

- **Eureka**: Handles service discovery and registration
- **Gateway**: Routes requests to appropriate services based on URL patterns
- **Service-A**: Simple REST service
- **Service-B**: Demonstrates service-to-service communication
- **Docker Compose**: Orchestrates all services with proper networking

## 🎯 Key Learning Points

1. Services automatically register with Eureka on startup
2. Gateway discovers services dynamically through Eureka
3. Services can communicate with each other using service names
4. Each service has its own Swagger documentation
5. Load balancing happens automatically through Eureka

Made by Sebastian Ramirez with the help of AI agents that managed to find the smallest mistakes...