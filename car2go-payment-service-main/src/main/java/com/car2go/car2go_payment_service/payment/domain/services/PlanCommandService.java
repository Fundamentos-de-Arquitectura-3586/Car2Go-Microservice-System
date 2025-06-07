package com.car2go.car2go_payment_service.payment.domain.services;

import com.car2go.car2go_payment_service.payment.domain.model.commands.CreatePlanCommand;
import com.car2go.car2go_payment_service.payment.domain.model.commands.UpdatePlanCommand;
import com.car2go.car2go_payment_service.payment.domain.model.entities.Plan;

import java.util.Optional;

public interface PlanCommandService {
    Optional<Plan> handle(CreatePlanCommand command);
    Optional<Plan> handle(UpdatePlanCommand command);
}
