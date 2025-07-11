package com.pe.platform.interaction;

import com.pe.platform.interaction.domain.model.aggregates.Review;
import com.pe.platform.interaction.domain.model.aggregates.Favorite;
import com.pe.platform.interaction.domain.services.ReviewCommandService;
import com.pe.platform.interaction.domain.services.FavoriteCommandService;
import com.pe.platform.interaction.infrastructure.persistence.jpa.ReviewRepository;
import com.pe.platform.interaction.infrastructure.persistence.jpa.FavoriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class InteractionServiceTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ReviewCommandService reviewCommandService;

    @Autowired
    private FavoriteCommandService favoriteCommandService;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
        favoriteRepository.deleteAll();
    }

    @Test
    void testCreateReview_ShouldSucceed() {
        // Arrange & Act
        Review review = reviewCommandService.createReview(1L, 1L, 5, "Excellent vehicle!");

        // Assert
        assertNotNull(review.getId());
        assertEquals(5, review.getRating());
        assertEquals("Excellent vehicle!", review.getComment());
    }

    @Test
    void testCreateFavorite_ShouldSucceed() {
        // Arrange & Act
        Optional<Favorite> result = favoriteCommandService.addFavorite(1L, 1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getVehicleId());
    }

    @Test
    void testSaveReview_ShouldPersist() {
        // Arrange
        Review review = new Review(1L, 1L, 4, "Good car");

        // Act
        Review savedReview = reviewRepository.save(review);

        // Assert
        assertNotNull(savedReview.getId());
        assertEquals(4, savedReview.getRating());
        assertEquals("Good car", savedReview.getComment());
    }
}
