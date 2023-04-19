package ru.kpfu.itis.khabibullin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.khabibullin.models.Dish;
import ru.kpfu.itis.khabibullin.models.Review;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Review, Long> {
    Review findReviewById(Long id);
    Review findReviewsByRating(Integer rating);
    Review findReviewByUsername(String username);
    List<Review> findByDish(Dish dish);
}
