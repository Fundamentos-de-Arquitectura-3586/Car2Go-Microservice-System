package com.car2go.car2go_payment_service.payment.interfaces.rest.transform;

import com.car2go.car2go_payment_service.payment.domain.model.aggregates.Transaction;
import com.car2go.car2go_payment_service.payment.interfaces.rest.resources.TransactionResource;

public class TransactionResourceFromEntityAssembler {
    public static TransactionResource toResourceFromEntity(Transaction entity) {
        return new TransactionResource(
                entity.getId(),
                entity.getBuyerId(),    
                entity.getSellerId(),   
                entity.getVehicleId(),  
                entity.getAmount(),
                entity.getPaymentStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
       );
    }
}
