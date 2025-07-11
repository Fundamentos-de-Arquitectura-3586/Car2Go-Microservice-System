package com.pe.platform.profiles;

import com.pe.platform.profiles.domain.model.aggregates.Profile;
import com.pe.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.pe.platform.profiles.domain.model.queries.GetAllProfilesQuery;
import com.pe.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import com.pe.platform.profiles.domain.services.ProfileCommandService;
import com.pe.platform.profiles.domain.services.ProfileQueryService;
import com.pe.platform.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
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
class ProfileServiceTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileCommandService profileCommandService;

    @Autowired
    private ProfileQueryService profileQueryService;

    @BeforeEach
    void setUp() {
        profileRepository.deleteAll();
    }

    @Test
    void testCreateProfile_ShouldSucceed() {
        // Arrange
        CreateProfileCommand command = new CreateProfileCommand(
                "John",
                "Doe",
                "john@example.com",
                "image.jpg",
                "12345678",
                "123 Main St",
                "+1234567890"
        );

        // Act
        Profile profile = new Profile(command, 1L);
        Profile savedProfile = profileRepository.save(profile);

        // Assert
        assertNotNull(savedProfile.getId());
        assertEquals("John", savedProfile.getFirstName());
        assertEquals("john@example.com", savedProfile.getEmail());
    }

    @Test
    void testFindProfileById_ShouldReturnProfile() {
        // Arrange
        CreateProfileCommand command = new CreateProfileCommand(
                "Jane",
                "Smith",
                "jane@example.com",
                "jane.jpg",
                "87654321",
                "456 Oak Ave",
                "+0987654321"
        );
        Profile profile = new Profile(command, 2L);
        Profile savedProfile = profileRepository.save(profile);

        // Act
        Optional<Profile> result = profileQueryService.handle(new GetProfileByIdQuery((long) savedProfile.getId()));

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Jane", result.get().getFirstName());
        assertEquals("jane@example.com", result.get().getEmail());
    }

    @Test
    void testGetAllProfiles_ShouldReturnList() {
        // Arrange
        CreateProfileCommand command = new CreateProfileCommand(
                "Bob",
                "Johnson",
                "bob@example.com",
                "bob.jpg",
                "11111111",
                "789 Pine St",
                "+1111111111"
        );
        Profile profile = new Profile(command, 3L);
        profileRepository.save(profile);

        // Act
        List<Profile> result = profileQueryService.handle(new GetAllProfilesQuery());

        // Assert
        assertEquals(1, result.size());
        assertEquals("Bob", result.get(0).getFirstName());
    }
}
