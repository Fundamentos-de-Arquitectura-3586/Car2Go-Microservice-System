package com.pe.platform.vehicle;

import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import com.pe.platform.vehicle.domain.model.commands.CreateVehicleCommand;
import com.pe.platform.vehicle.domain.model.queries.GetAllVehicleQuery;
import com.pe.platform.vehicle.domain.model.queries.GetVehicleByIdQuery;
import com.pe.platform.vehicle.domain.services.VehicleCommandService;
import com.pe.platform.vehicle.domain.services.VehicleQueryService;
import com.pe.platform.vehicle.infrastructure.persistence.jpa.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class VehicleServiceTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleCommandService vehicleCommandService;

    @Autowired
    private VehicleQueryService vehicleQueryService;

    @BeforeEach
    void setUp() {
        // Configurar contexto de seguridad para tests
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken("1", "password")
        );
        vehicleRepository.deleteAll();
    }

    @Test
    void testCreateVehicle_ShouldSucceed() {
        // Arrange
        CreateVehicleCommand command = new CreateVehicleCommand(
                "Test Vehicle",
                "+1234567890",
                "test@example.com",
                "Toyota",
                "Camry",
                "Blue",
                "2023",
                25000.0,
                "Automatic",
                "V6",
                15000.0,
                "4",
                "ABC-123",
                "Lima",
                "Test description",
                List.of("image1.jpg"),
                "Gasoline",
                120
        );

        // Act
        Optional<Vehicle> result = vehicleCommandService.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test Vehicle", result.get().getName());
        assertEquals("Toyota", result.get().getBrand());
    }

    @Test
    void testFindVehicleById_ShouldReturnVehicle() {
        // Arrange
        CreateVehicleCommand command = new CreateVehicleCommand(
                "Find Vehicle",
                "+1234567890",
                "find@example.com",
                "Honda",
                "Civic",
                "Red",
                "2022",
                20000.0,
                "Manual",
                "I4",
                25000.0,
                "4",
                "XYZ-789",
                "Lima",
                "Find description",
                List.of("image2.jpg"),
                "Gasoline",
                110
        );
        Vehicle vehicle = new Vehicle(command);
        vehicle.setProfileId(1L);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        // Act
        Optional<Vehicle> result = vehicleQueryService.handle(new GetVehicleByIdQuery(savedVehicle.getId()));

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Find Vehicle", result.get().getName());
        assertEquals("Honda", result.get().getBrand());
    }

    @Test
    void testGetAllVehicles_ShouldReturnList() {
        // Arrange
        CreateVehicleCommand command1 = new CreateVehicleCommand(
                "Vehicle 1",
                "+1111111111",
                "v1@example.com",
                "Toyota",
                "Corolla",
                "White",
                "2021",
                18000.0,
                "Automatic",
                "I4",
                30000.0,
                "4",
                "AAA-111",
                "Lima",
                "Description 1",
                List.of("img1.jpg"),
                "Gasoline",
                100
        );

        Vehicle vehicle1 = new Vehicle(command1);
        vehicle1.setProfileId(1L);
        vehicleRepository.save(vehicle1);

        // Act
        List<Vehicle> result = vehicleQueryService.handle(new GetAllVehicleQuery());

        // Assert
        assertEquals(1, result.size());
        assertEquals("Vehicle 1", result.get(0).getName());
    }
}
