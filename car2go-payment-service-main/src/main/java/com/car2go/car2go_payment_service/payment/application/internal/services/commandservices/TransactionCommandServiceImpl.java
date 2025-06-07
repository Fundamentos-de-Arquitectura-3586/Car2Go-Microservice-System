package com.car2go.car2go_payment_service.payment.application.internal.services.commandservices;

import com.car2go.car2go_payment_service.payment.domain.model.aggregates.Transaction;
import com.car2go.car2go_payment_service.payment.domain.model.commands.CreateTransactionCommand;
import com.car2go.car2go_payment_service.payment.domain.model.commands.UpdateTransactionCommand;
import com.car2go.car2go_payment_service.payment.domain.services.TransactionCommandService;
import com.car2go.car2go_payment_service.payment.infrastructure.persistence.jpa.TransactionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TransactionCommandServiceImpl implements TransactionCommandService {

    private final TransactionRepository transactionRepository;
    // REMOVED: External service dependencies for microservice isolation
    // private final ProfileQueryService profileQueryService;
    // private final VehicleQueryService vehicleQueryService;

    public TransactionCommandServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
        // REMOVED: External service dependencies
        // this.profileQueryService = profileQueryService;
        // this.vehicleQueryService = vehicleQueryService;
    }

    @Override
    public Optional<Transaction> handle(CreateTransactionCommand command) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // SIMPLIFIED: Use username instead of UserDetailsImpl for microservice isolation
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean hasRequiredRole = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_BUYER"));

        if (hasRequiredRole){
            // SIMPLIFIED: Use IDs directly instead of fetching external entities
            // In a microservices architecture, we trust the command parameters 
            // and validate through API calls if needed
            Long buyerId = command.buyerId();
            Long sellerId = command.sellerId(); 
            Long vehicleId = command.vehicleId();
            Double amount = command.amount();
            
            var transaction = new Transaction(buyerId, sellerId, vehicleId, amount);
            transactionRepository.save(transaction);
            return Optional.of(transaction);
        } else{
            throw new SecurityException("Only buyers can create a transaction");
        }

    }

    @Override
    public Optional<Transaction> handle(UpdateTransactionCommand command) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(command.transactionId());
        if (transactionOpt.isEmpty()) {
            throw new IllegalArgumentException("Transaction does not exist for the given id");
        }

        Transaction transaction = transactionOpt.get();
        transaction.setStatus(command.status());
        transactionRepository.save(transaction);
        return Optional.of(transaction);
    }
}
