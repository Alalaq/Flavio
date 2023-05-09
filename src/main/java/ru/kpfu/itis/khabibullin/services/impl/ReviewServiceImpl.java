package ru.kpfu.itis.khabibullin.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.khabibullin.dto.DishDto;
import ru.kpfu.itis.khabibullin.dto.ReviewDto;
import ru.kpfu.itis.khabibullin.repositories.ReviewsRepository;
import ru.kpfu.itis.khabibullin.services.ReviewService;

import java.util.List;
/**
 * @author Khabibullin Alisher
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewsRepository reviewsRepository;


    @Override
    public ReviewDto getReviewById(Long id) {
        return ReviewDto.from(reviewsRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found")));
    }

    @Override
    public List<ReviewDto> getAllReviews() {
        return ReviewDto.from(reviewsRepository.findAll());
    }

    @Override
    public List<ReviewDto> getReviewsByDish(DishDto dish) {
        return ReviewDto.from(reviewsRepository.findByDish(DishDto.to(dish)));
    }

    @Override
    public void saveReview(ReviewDto review) {
        reviewsRepository.save(ReviewDto.to(review));
    }

    @Override
    public void deleteReview(long id) {
        reviewsRepository.deleteById(id);
    }

    @Override
    public void saveAll(List<ReviewDto> reviews) {
       reviewsRepository.saveAll(ReviewDto.to(reviews));
    }

    @Override
    public ReviewDto getReviewByOrderId(Long orderId) {
        return ReviewDto.from(reviewsRepository.findFirstByOrderId(orderId));
    }
}


