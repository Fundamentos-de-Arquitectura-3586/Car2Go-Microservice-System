package com.car2go.car2go_iam_service;

import com.car2go.car2go_iam_service.iam.application.internal.commandservices.UserCommandServiceImpl;
import com.car2go.car2go_iam_service.iam.application.internal.queryservices.UserQueryServiceImpl;
import com.car2go.car2go_iam_service.iam.domain.model.aggregates.User;
import com.car2go.car2go_iam_service.iam.domain.model.commands.SignUpCommand;
import com.car2go.car2go_iam_service.iam.domain.model.entities.Role;
import com.car2go.car2go_iam_service.iam.domain.model.queries.GetUserByUsernameQuery;
import com.car2go.car2go_iam_service.iam.domain.model.valueobjects.Roles;
import com.car2go.car2go_iam_service.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.car2go.car2go_iam_service.iam.infrastructure.persistence.jpa.repositories.UserRepository;
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
class IamServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserCommandServiceImpl userCommandService;

    @Autowired
    private UserQueryServiceImpl userQueryService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        
        // Create default roles
        Role sellerRole = new Role(Roles.ROLE_SELLER);
        Role buyerRole = new Role(Roles.ROLE_BUYER);
        roleRepository.save(sellerRole);
        roleRepository.save(buyerRole);
    }

    @Test
    void testCreateUser_ShouldSucceed() {
        // Arrange
        List<Role> roles = List.of(new Role(Roles.ROLE_SELLER));
        SignUpCommand command = new SignUpCommand("testuser", "password123", roles);

        // Act
        Optional<User> result = userCommandService.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
        assertNotNull(result.get().getPassword());
    }

    @Test
    void testFindUserByUsername_ShouldReturnUser() {
        // Arrange
        Role role = new Role(Roles.ROLE_BUYER);
        roleRepository.save(role);
        User user = new User("finduser", "encodedpassword", List.of(role));
        userRepository.save(user);

        // Act
        Optional<User> result = userQueryService.handle(new GetUserByUsernameQuery("finduser"));

        // Assert
        assertTrue(result.isPresent());
        assertEquals("finduser", result.get().getUsername());
    }

    @Test
    void testUserRepository_SaveAndRetrieve() {
        // Arrange
        Role role = new Role(Roles.ROLE_ADMIN);
        roleRepository.save(role);
        User user = new User("repouser", "hashedpassword", List.of(role));

        // Act
        User savedUser = userRepository.save(user);
        Optional<User> retrievedUser = userRepository.findByUsername("repouser");

        // Assert
        assertNotNull(savedUser.getId());
        assertTrue(retrievedUser.isPresent());
        assertEquals("repouser", retrievedUser.get().getUsername());
        assertEquals(1, retrievedUser.get().getRoles().size());
    }
}
