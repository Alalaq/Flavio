package ru.kpfu.itis.khabibullin.services;

import ru.kpfu.itis.khabibullin.dto.DishDto;
import ru.kpfu.itis.khabibullin.dto.ReviewDto;

import java.util.List;
/**
 * @author Khabibullin Alisher
 */
public interface ReviewService {
    ReviewDto getReviewById(Long id);
    List<ReviewDto> getAllReviews();
    List<ReviewDto> getReviewsByDish(DishDto dish);
    void saveReview(ReviewDto review);
    void deleteReview(long id);
}

