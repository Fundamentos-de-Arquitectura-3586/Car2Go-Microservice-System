package com.car2go.car2go_payment_service.payment.interfaces.rest;

import com.car2go.car2go_payment_service.payment.domain.model.queries.GetAllPlanQuery;
import com.car2go.car2go_payment_service.payment.domain.model.queries.GetPlanByIdQuery;
import com.car2go.car2go_payment_service.payment.domain.services.PlanCommandService;
import com.car2go.car2go_payment_service.payment.domain.services.PlanQueryService;
import com.car2go.car2go_payment_service.payment.interfaces.rest.resources.CreatePlanResource;
import com.car2go.car2go_payment_service.payment.interfaces.rest.resources.PlanResource;
import com.car2go.car2go_payment_service.payment.interfaces.rest.transform.CreatePlanCommandFromResourceAssembler;
import com.car2go.car2go_payment_service.payment.interfaces.rest.transform.PlanResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value= "/api/v1/plans", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name="Plans", description = "Plans Management Endpoints")
public class PlanController {

    private final PlanQueryService planQueryService;
    private final PlanCommandService planCommandService;

    public PlanController(PlanQueryService planQueryService, PlanCommandService planCommandService) {
        this.planQueryService = planQueryService;
        this.planCommandService = planCommandService;
    }

    @PostMapping
    public ResponseEntity<PlanResource> createPlan(@RequestBody CreatePlanResource resource){
        var createPlanCommand = CreatePlanCommandFromResourceAssembler.toCommandFromResource(resource);
        var plan = planCommandService.handle(createPlanCommand);
        if(plan.isEmpty()) return ResponseEntity.badRequest().build();
        var planResource = PlanResourceFromEntityAssembler.toResourceFromEntity(plan.get());
        return new ResponseEntity<>(planResource, HttpStatus.CREATED);
    }

    @GetMapping("/{planId}")
    public ResponseEntity<PlanResource> getPlanById(@PathVariable Long planId){
        var getPlanByIdQuery = new GetPlanByIdQuery(planId);
        var plan = planQueryService.handle(getPlanByIdQuery);
        if(plan.isEmpty()) return ResponseEntity.notFound().build();
        var planResource = PlanResourceFromEntityAssembler.toResourceFromEntity(plan.get());
        return ResponseEntity.ok(planResource);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlanResource>> getAllPlans() {
        var plans = planQueryService.handle(new GetAllPlanQuery());
        if(plans.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var planResources = plans.stream().map(PlanResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(planResources);
    }

}
