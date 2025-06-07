package com.car2go.car2go_payment_service.payment.interfaces.rest.transform;

import com.car2go.car2go_payment_service.payment.domain.model.aggregates.Subscription;
import com.car2go.car2go_payment_service.payment.domain.model.entities.Plan;
import com.car2go.car2go_payment_service.payment.interfaces.rest.resources.PlanResource;
import com.car2go.car2go_payment_service.payment.interfaces.rest.resources.SubscriptionResource;

public class PlanResourceFromEntityAssembler {
    public static PlanResource toResourceFromEntity(Plan entity) {
        return new PlanResource(entity.getName(), entity.getPrice());
    }
}
