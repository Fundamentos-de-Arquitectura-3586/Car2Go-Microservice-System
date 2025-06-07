package com.car2go.car2go_payment_service.payment.interfaces.rest.transform;

import com.car2go.car2go_payment_service.payment.domain.model.commands.CreatePlanCommand;
import com.car2go.car2go_payment_service.payment.interfaces.rest.resources.CreatePlanResource;

public class CreatePlanCommandFromResourceAssembler {
    public static CreatePlanCommand toCommandFromResource (CreatePlanResource resource) {

        return new CreatePlanCommand (
                resource.name(),
                resource.price());
    }
}
