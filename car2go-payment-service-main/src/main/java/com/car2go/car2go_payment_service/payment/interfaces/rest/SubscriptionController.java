package com.car2go.car2go_payment_service.payment.interfaces.rest;

import com.car2go.car2go_payment_service.payment.domain.model.aggregates.Subscription;
import com.car2go.car2go_payment_service.payment.domain.model.valueobjects.SubscriptionStatus;
import com.car2go.car2go_payment_service.payment.domain.model.commands.CreateSubscriptionCommand;
import com.car2go.car2go_payment_service.payment.domain.model.commands.UpdateSubscriptionCommand;
import com.car2go.car2go_payment_service.payment.domain.services.SubscriptionCommandService;
import com.car2go.car2go_payment_service.payment.domain.services.SubscriptionQueryService;
import com.car2go.car2go_payment_service.payment.interfaces.rest.resources.CreateSubscriptionResource;
import com.car2go.car2go_payment_service.payment.interfaces.rest.resources.SubscriptionResource;
import com.car2go.car2go_payment_service.payment.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/api/v1/subscription", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubscriptionController {
    private final SubscriptionQueryService subscriptionQueryService;
    private final SubscriptionCommandService subscriptionCommandService;

    public SubscriptionController(SubscriptionQueryService subscriptionQueryService,
                                  SubscriptionCommandService subscriptionCommandService) {
        this.subscriptionQueryService = subscriptionQueryService;
        this.subscriptionCommandService = subscriptionCommandService;
    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PostMapping
    public ResponseEntity<SubscriptionResource> createSubscription(@RequestBody CreateSubscriptionResource resource) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // SIMPLIFIED: Use hardcoded profileId for now, implement JWT token parsing later
        Long profileId = 1L; // TODO: Extract from JWT token in microservice architecture

        var command = new CreateSubscriptionCommand(resource.price(), resource.description(), profileId);

        Optional<Subscription> subscription = subscriptionCommandService.handle(command);
        return subscription.map(resp ->
                        new ResponseEntity<>(SubscriptionResourceFromEntityAssembler.toResourceFromEntity(resp), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @GetMapping("/me")
    public ResponseEntity<SubscriptionResource> getMySubscription() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // SIMPLIFIED: Use hardcoded profileId for now, implement JWT token parsing later
        Long profileId = 1L; // TODO: Extract from JWT token in microservice architecture

        Optional<Subscription> subscription = subscriptionQueryService.getByProfileId(profileId);
        return subscription.map(value -> ResponseEntity.ok(SubscriptionResourceFromEntityAssembler.toResourceFromEntity(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PutMapping("/me/cancel")
    public ResponseEntity<SubscriptionResource> cancelMySubscription() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // SIMPLIFIED: Use hardcoded profileId for now, implement JWT token parsing later
        Long profileId = 1L; // TODO: Extract from JWT token in microservice architecture

        Optional<Subscription> subscription = subscriptionCommandService.handle(new UpdateSubscriptionCommand(profileId, SubscriptionStatus.CANCELLED));
        return subscription.map(sub -> ResponseEntity.ok(SubscriptionResourceFromEntityAssembler.toResourceFromEntity(sub)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<SubscriptionResource>> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionQueryService.getAllSubscriptions();
        List<SubscriptionResource> subscriptionResources = subscriptions.stream()
                .map(SubscriptionResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(subscriptionResources);
    }
}
