package com.car2go.car2go_payment_service.payment.application.internal.services.queryservices;

import com.car2go.car2go_payment_service.payment.domain.model.aggregates.Transaction;
import com.car2go.car2go_payment_service.payment.domain.model.queries.GetAllTransactionsQuery;
import com.car2go.car2go_payment_service.payment.domain.model.queries.GetTransactionByIdQuery;
import com.car2go.car2go_payment_service.payment.domain.services.TransactionQueryService;
import com.car2go.car2go_payment_service.payment.infrastructure.persistence.jpa.TransactionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionQueryServiceImpl implements TransactionQueryService {

    private final TransactionRepository transactionRepository;

    public TransactionQueryServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> handle(GetAllTransactionsQuery query) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // SIMPLIFIED: Use username instead of UserDetailsImpl for microservice isolation
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean hasBuyerRole = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_BUYER"));
        boolean hasSellerRole = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_SELLER"));

        if (hasBuyerRole) {
            // TODO: For now returning all transactions, implement user-specific filtering later
            List<Transaction> buyerTransactions = transactionRepository.findAll();
            return buyerTransactions.isEmpty() ? List.of() : buyerTransactions;
        } else if (hasSellerRole) {
            // TODO: For now returning all transactions, implement user-specific filtering later  
            List<Transaction> sellerTransactions = transactionRepository.findAll();
            return sellerTransactions.isEmpty() ? List.of() : sellerTransactions;
        }
        
        throw new IllegalArgumentException("User does not have required role");
    }

    @Override
    public Optional<Transaction> handle(GetTransactionByIdQuery query) {
        return transactionRepository.findById(query.id());
    }
}
