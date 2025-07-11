package com.car2go.car2go_payment_service;

import com.car2go.car2go_payment_service.payment.domain.model.commands.CreatePlanCommand;
import com.car2go.car2go_payment_service.payment.domain.model.commands.CreateTransactionCommand;
import com.car2go.car2go_payment_service.payment.domain.model.entities.Plan;
import com.car2go.car2go_payment_service.payment.domain.model.aggregates.Transaction;
import com.car2go.car2go_payment_service.payment.domain.model.queries.GetAllPlanQuery;
import com.car2go.car2go_payment_service.payment.domain.services.PlanCommandService;
import com.car2go.car2go_payment_service.payment.domain.services.PlanQueryService;
import com.car2go.car2go_payment_service.payment.infrastructure.persistence.jpa.PlanRepository;
import com.car2go.car2go_payment_service.payment.infrastructure.persistence.jpa.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PaymentServiceTest {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PlanCommandService planCommandService;

    @Autowired
    private PlanQueryService planQueryService;

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();
        planRepository.deleteAll();
    }

    @Test
    void testCreatePlan_ShouldSucceed() {
        // Arrange
        CreatePlanCommand command = new CreatePlanCommand("Premium Plan", 29.99);

        // Act
        Optional<Plan> result = planCommandService.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Premium Plan", result.get().getName());
        assertEquals(29.99, result.get().getPrice());
    }

    @Test
    void testGetAllPlans_ShouldReturnList() {
        // Arrange
        Plan plan = new Plan("Basic Plan", 9.99);
        planRepository.save(plan);

        // Act
        List<Plan> result = planQueryService.handle(new GetAllPlanQuery());

        // Assert
        assertEquals(1, result.size());
        assertEquals("Basic Plan", result.get(0).getName());
    }

    @Test
    void testCreateTransaction_ShouldSucceed() {
        // Arrange
        CreateTransactionCommand command = new CreateTransactionCommand(1L, 2L, 3L, 100.0);

        // Act
        Transaction transaction = new Transaction(command.buyerId(), command.sellerId(), command.vehicleId(), command.amount());
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Assert
        assertNotNull(savedTransaction.getId());
        assertEquals(1L, savedTransaction.getBuyerId());
        assertEquals(2L, savedTransaction.getSellerId());
        assertEquals(100.0, savedTransaction.getAmount());
    }
}
