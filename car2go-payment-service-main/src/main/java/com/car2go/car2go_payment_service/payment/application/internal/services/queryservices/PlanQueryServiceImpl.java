package com.car2go.car2go_payment_service.payment.application.internal.services.queryservices;

import com.car2go.car2go_payment_service.payment.domain.model.entities.Plan;
import com.car2go.car2go_payment_service.payment.domain.model.queries.GetAllPlanQuery;
import com.car2go.car2go_payment_service.payment.domain.model.queries.GetPlanByIdQuery;
import com.car2go.car2go_payment_service.payment.domain.services.PlanQueryService;
import com.car2go.car2go_payment_service.payment.infrastructure.persistence.jpa.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanQueryServiceImpl implements PlanQueryService {
    private final PlanRepository planRepository;

    public PlanQueryServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }


    @Override
    public Optional<Plan> handle(GetPlanByIdQuery query) {
        return planRepository.findById(query.id());
    }

    @Override
    public List<Plan> handle(GetAllPlanQuery query) {
        return planRepository.findAll();
    }

}
