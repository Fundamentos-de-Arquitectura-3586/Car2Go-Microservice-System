package com.pe.platform.vehicle.interfaces.rest;

import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import com.pe.platform.vehicle.domain.model.commands.CreateVehicleCommand;
import com.pe.platform.vehicle.domain.model.commands.UpdateVehicleCommand;
import com.pe.platform.vehicle.domain.model.queries.*;
import com.pe.platform.vehicle.domain.model.valueobjects.vehicleStatus;
import com.pe.platform.vehicle.domain.services.VehicleCommandService;
import com.pe.platform.vehicle.domain.services.VehicleQueryService;
import com.pe.platform.vehicle.interfaces.rest.resources.CreateVehicleResource;
import com.pe.platform.vehicle.interfaces.rest.resources.UpdateVehicleResource;
import com.pe.platform.vehicle.interfaces.rest.resources.VehicleResource;
import com.pe.platform.vehicle.interfaces.rest.transform.CreateVehicleCommandFromResourceAssembler;
import com.pe.platform.vehicle.interfaces.rest.transform.UpdateVehicleCommandFromResourceAssembler;
import com.pe.platform.vehicle.interfaces.rest.transform.VehicleResourceFromEntityAssembler;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {

    private final VehicleCommandService vehicleCommandService;
    private final VehicleQueryService vehicleQueryService;

    public VehicleController(VehicleCommandService vehicleCommandService, VehicleQueryService vehicleQueryService) {
        this.vehicleCommandService = vehicleCommandService;
        this.vehicleQueryService = vehicleQueryService;
    }

    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @PostMapping
    public ResponseEntity<VehicleResource> createVehicle(@RequestBody CreateVehicleResource resource) {
        Optional<Vehicle> vehicle = vehicleCommandService.handle(CreateVehicleCommandFromResourceAssembler.toCommandFromResource(resource));
        return vehicle.map(resp -> new ResponseEntity<>(VehicleResourceFromEntityAssembler.toResourceFromEntity(resp), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResource> getVehicleById(@PathVariable int id) {
        Optional<Vehicle> vehicle = vehicleQueryService.handle(new GetVehicleByIdQuery(id));
        return vehicle.map(resp -> ResponseEntity.ok(VehicleResourceFromEntityAssembler.toResourceFromEntity(resp)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<VehicleResource>> getAllByLocation(@PathVariable String location) {
        var getAllVehicleByLocation = new GetAllVehicleByLocationQuery(location);
        var vehicles = vehicleQueryService.handle(getAllVehicleByLocation);
        if (vehicles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var vehicleResources = vehicles.stream()
                .map(VehicleResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(vehicleResources);
    }

    @GetMapping("/all")
    public ResponseEntity<List<VehicleResource>> getAllVehicles() {
        var vehicles = vehicleQueryService.handle(new GetAllVehicleQuery());
        if (vehicles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var vehicleResources = vehicles.stream().map(VehicleResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(vehicleResources);
    }

    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @PutMapping("/{vehicleId}")
    public ResponseEntity<VehicleResource> updateVehicle(@PathVariable int vehicleId, @RequestBody UpdateVehicleResource resource) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        Long userId = (Long) authentication.getPrincipal(); // Suponiendo que tu filtro pone el userId como principal

        UpdateVehicleCommand command = UpdateVehicleCommandFromResourceAssembler.toCommandFromResource(resource);
        Optional<Vehicle> updatedVehicleOptional = vehicleCommandService.handle(command, vehicleId);

        return updatedVehicleOptional
                .filter(updatedVehicle -> updatedVehicle.getProfileId() == userId)
                .map(updatedVehicle -> ResponseEntity.ok(VehicleResourceFromEntityAssembler.toResourceFromEntity(updatedVehicle)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/all/vehicles/profile/{profileId}")
    public ResponseEntity<List<VehicleResource>> getAllVehiclesByProfileId(@PathVariable long profileId) {
        var vehicles = vehicleQueryService.handle(new GetAllVehicleByProfileId(profileId));
        if (vehicles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var vehicleResources = vehicles.stream().map(VehicleResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(vehicleResources);
    }

    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable int vehicleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        Long userId = (Long) authentication.getPrincipal(); // extraído del JWT

        try {
            vehicleCommandService.deleteVehicle(vehicleId, userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
    }



}
