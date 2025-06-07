package com.car2go.car2go_payment_service.payment.interfaces.rest.transform;

import com.car2go.car2go_payment_service.payment.domain.model.commands.UpdateTransactionCommand;
import com.car2go.car2go_payment_service.payment.interfaces.rest.resources.UpdateTransactionResource;

public class UpdateTransactionCommandFromResourceAssembler {
    public static UpdateTransactionCommand toCommandFromResource(UpdateTransactionResource resource) {
        return new UpdateTransactionCommand(resource.transactionId(), resource.status());
    }
}
