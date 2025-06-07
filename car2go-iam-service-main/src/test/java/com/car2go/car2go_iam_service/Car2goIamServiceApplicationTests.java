package com.car2go.car2go_iam_service;

import com.car2go.car2go_iam_service.iam.domain.model.aggregates.User;
import com.car2go.car2go_iam_service.iam.domain.model.entities.Role;
import com.car2go.car2go_iam_service.iam.domain.model.queries.GetAllUsersQuery;
import com.car2go.car2go_iam_service.iam.domain.model.queries.GetUserByIdQuery;
import com.car2go.car2go_iam_service.iam.domain.services.UserQueryService;
import com.car2go.car2go_iam_service.iam.interfaces.rest.UsersController;
import com.car2go.car2go_iam_service.iam.interfaces.rest.resources.UserResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsersControllerTest {

    @Mock
    private UserQueryService userQueryService;

    @InjectMocks
    private UsersController usersController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_shouldReturnAllUsers() {
        // Arrange
        User user1 = createMockUser(1L, "user1");
        User user2 = createMockUser(2L, "user2");
        List<User> mockUsers = List.of(user1, user2);
        
        when(userQueryService.handle(any(GetAllUsersQuery.class))).thenReturn(mockUsers);
        
        // Act
        ResponseEntity<List<UserResource>> response = usersController.getAllUsers();
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
        
        ArgumentCaptor<GetAllUsersQuery> queryCaptor = ArgumentCaptor.forClass(GetAllUsersQuery.class);
        verify(userQueryService, times(1)).handle(queryCaptor.capture());
    }

    @Test
    void getUserById_whenUserExists_shouldReturnUser() {
        // Arrange
        Long userId = 1L;
        User mockUser = createMockUser(userId, "testuser");
        
        when(userQueryService.handle(any(GetUserByIdQuery.class))).thenReturn(Optional.of(mockUser));
        
        // Act
        ResponseEntity<UserResource> response = usersController.getUserById(userId);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().id()).isEqualTo(userId);
        assertThat(response.getBody().username()).isEqualTo("testuser");
        
        ArgumentCaptor<GetUserByIdQuery> queryCaptor = ArgumentCaptor.forClass(GetUserByIdQuery.class);
        verify(userQueryService, times(1)).handle(queryCaptor.capture());
        assertThat(queryCaptor.getValue().userId()).isEqualTo(userId);
    }

    @Test
    void getUserById_whenUserDoesNotExist_shouldReturnNotFound() {
        // Arrange
        Long userId = 999L;
        
        when(userQueryService.handle(any(GetUserByIdQuery.class))).thenReturn(Optional.empty());
        
        // Act
        ResponseEntity<UserResource> response = usersController.getUserById(userId);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
        
        ArgumentCaptor<GetUserByIdQuery> queryCaptor = ArgumentCaptor.forClass(GetUserByIdQuery.class);
        verify(userQueryService, times(1)).handle(queryCaptor.capture());
        assertThat(queryCaptor.getValue().userId()).isEqualTo(userId);
    }
    
    private User createMockUser(Long id, String username) {
        User user = mock(User.class);
        Set<Role> roles = new HashSet<>();
        Role role = mock(Role.class);
        when(role.getStringName()).thenReturn("ROLE_USER");
        roles.add(role);
        
        when(user.getId()).thenReturn(id);
        when(user.getUsername()).thenReturn(username);
        when(user.getRoles()).thenReturn(roles);
        
        return user;
    }
}