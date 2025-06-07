package com.car2go.car2go_payment_service.payment.interfaces.rest.transform;

import com.car2go.car2go_payment_service.payment.domain.model.commands.CreateTransactionCommand;
import com.car2go.car2go_payment_service.payment.interfaces.rest.resources.CreateTransactionResource;

public class CreateTransactionCommandFromResourceAssembler {
    public static CreateTransactionCommand toCommandFromResource(CreateTransactionResource resource) {
        return new CreateTransactionCommand(
                resource.buyerId(),
                resource.sellerId(),
                resource.vehicleId(),
                resource.amount()
        );
    }
}
