package com.car2go.car2go_payment_service.payment.interfaces.rest.transform;

import com.car2go.car2go_payment_service.payment.domain.model.commands.CreatePlanCommand;
import com.car2go.car2go_payment_service.payment.domain.model.commands.UpdatePlanCommand;
import com.car2go.car2go_payment_service.payment.interfaces.rest.resources.CreatePlanResource;
import com.car2go.car2go_payment_service.payment.interfaces.rest.resources.UpdatePlanResource;

public class UpdatePlanCommandFromResourceAssembler {
    public static UpdatePlanCommand toCommandFromResource (UpdatePlanResource resource) {

        return new UpdatePlanCommand (
                resource.planId(),
                resource.name(),
                resource.price());
    }
}
