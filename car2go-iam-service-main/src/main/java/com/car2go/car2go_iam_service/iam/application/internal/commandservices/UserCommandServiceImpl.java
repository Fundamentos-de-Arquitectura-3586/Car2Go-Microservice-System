package com.car2go.car2go_iam_service.iam.application.internal.commandservices;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import com.car2go.car2go_iam_service.iam.application.internal.outboundservices.hashing.HashingService;
import com.car2go.car2go_iam_service.iam.application.internal.outboundservices.tokens.TokenService;
import com.car2go.car2go_iam_service.iam.domain.model.aggregates.User;
import com.car2go.car2go_iam_service.iam.domain.model.commands.SignInCommand;
import com.car2go.car2go_iam_service.iam.domain.model.commands.SignUpCommand;
import com.car2go.car2go_iam_service.iam.domain.model.valueobjects.Roles;
import com.car2go.car2go_iam_service.iam.domain.services.UserCommandService;
import com.car2go.car2go_iam_service.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.car2go.car2go_iam_service.iam.infrastructure.persistence.jpa.repositories.UserRepository;

import java.util.Optional;

/**
 * User command service implementation
 * <p>
 *     This class implements the {@link UserCommandService} interface and provides the implementation for the
 *     {@link SignInCommand} and {@link SignUpCommand} commands.
 * </p>
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
    }

    /**
     * Handle the sign-up command
     * <p>
     *     This method handles the {@link SignUpCommand} command and returns the user.
     * </p>
     * @param command the sign-up command containing the username and password
     * @return the created user
     */
    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username()))
            return Optional.empty(); // Devuelve un Optional vacío si el nombre de usuario ya existe

        var roles = command.roles();
        if (roles.isEmpty()) {
            var role = roleRepository.findByName(Roles.ROLE_SELLER);
            if (role.isPresent()) roles.add(role.get());
        } else {
            roles = roles.stream().map(role -> {
            var existingRole = roleRepository.findByName(Roles.valueOf(role.getStringName()));
                return existingRole.orElseGet(() -> roleRepository.save(role)); // Crea el rol si no existe
            }).toList();
        }

        var user = new User(command.username(), hashingService.encode(command.password()), roles);
        userRepository.save(user);
        return userRepository.findByUsername(command.username());
    }

    /**
     * Handle the sign-in command
     * <p>
     *     This method handles the {@link SignInCommand} command and returns the user and the token.
     * </p>
     * @param command the sign-in command containing the username and password
     * @return and optional containing the user matching the username and the generated token
     * @throws RuntimeException if the user is not found or the password is invalid
     */
    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
    var userOptional = userRepository.findByUsername(command.username());
    
    if (userOptional.isEmpty()) {
        throw new RuntimeException("User not found");
    }
    
    var user = userOptional.get(); // Ahora es seguro usar .get()

    if (!hashingService.matches(command.password(), user.getPassword()))
        throw new RuntimeException("Invalid password");

    var token = tokenService.generateToken(user.getUsername(), user.getId());
    return Optional.of(ImmutablePair.of(user, token));
}
}