package ru.kpfu.itis.khabibullin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.khabibullin.models.Dish;
import ru.kpfu.itis.khabibullin.models.Review;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {
    private Long id;
    private String username;
    private String comment;
    private int rating;
    private Dish dish;

    public static Review to(ReviewDto dto) {
        return Review.builder()
                .id(dto.getId())
                .username(dto.getUsername())
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
                .username(review.getUsername())
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
