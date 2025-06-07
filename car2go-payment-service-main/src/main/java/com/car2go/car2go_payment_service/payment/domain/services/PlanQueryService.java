package com.car2go.car2go_payment_service.payment.domain.services;

import com.car2go.car2go_payment_service.payment.domain.model.entities.Plan;
import com.car2go.car2go_payment_service.payment.domain.model.queries.GetAllPlanQuery;
import com.car2go.car2go_payment_service.payment.domain.model.queries.GetPlanByIdQuery;

import java.util.List;
import java.util.Optional;

public interface PlanQueryService {
    Optional<Plan> handle(GetPlanByIdQuery query);
    List<Plan> handle(GetAllPlanQuery query);
}
