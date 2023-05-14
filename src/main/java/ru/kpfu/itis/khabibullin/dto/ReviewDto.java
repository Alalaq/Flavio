package ru.kpfu.itis.khabibullin.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.khabibullin.models.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Khabibullin Alisher
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long restaurantId;

    @NotNull
    private Long orderId;

    @NotNull
    private String comment;

    @Min(value = 1)
    @Max(value = 5)
    private int rating;

    private Dish dish;

    public static Review to(ReviewDto dto) {
        return Review.builder()
                .id(dto.getId())
                .user(User.builder().id(dto.getUserId()).build())
                .restaurant(Restaurant.builder().id(dto.restaurantId).build())
                .order(Order.builder().id(dto.getOrderId()).build())
                .comment(dto.getComment())
                .rating(dto.getRating())
                .dish(Dish.builder().id(dto.getDish().getId()).build())
                .build();
    }

    public static List<Review> to(List<ReviewDto> dtos) {
        return dtos.stream()
                .map(ReviewDto::to)
                .collect(Collectors.toList());
    }

    public static ReviewDto from(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .restaurantId(review.getRestaurant().getId())
                .orderId(review.getOrder().getId())
                .comment(review.getComment())
                .rating(review.getRating())
                .dish(review.getDish())
                .build();
    }

    public static List<ReviewDto> from(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewDto::from)
                .collect(Collectors.toList());
    }
}
