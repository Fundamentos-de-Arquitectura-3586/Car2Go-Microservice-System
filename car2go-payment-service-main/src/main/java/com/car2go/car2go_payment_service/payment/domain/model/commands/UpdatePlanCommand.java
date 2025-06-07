package com.car2go.car2go_payment_service.payment.domain.model.commands;

public record UpdatePlanCommand(Long planId, String name, Double price) {

    public UpdatePlanCommand {
        if (planId == null) {
            throw new IllegalArgumentException("PlanId cannot be null");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Price cannot be null");
        }
    }
}
