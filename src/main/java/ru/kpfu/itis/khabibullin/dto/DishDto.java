package ru.kpfu.itis.khabibullin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.khabibullin.models.Dish;
import ru.kpfu.itis.khabibullin.models.Restaurant;
import ru.kpfu.itis.khabibullin.models.Review;
import ru.kpfu.itis.khabibullin.utils.Cuisine;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishDto {
    private Long id;
    private String name;
    private String cuisine;
    private String description;
    private BigDecimal price;
    private Long restaurantId;
    private List<Long> reviewIds;
    private boolean isVegetarian;
    private String imageUrl;

    public static DishDto from(Dish dish) {
        return DishDto.builder()
                .id(dish.getId())
                .name(dish.getName())
                .cuisine(dish.getCuisine().name())
                .description(dish.getDescription())
                .price(dish.getPrice())
                .restaurantId(dish.getRestaurant().getId())
                .reviewIds(dish.getReviews().stream()
                        .map(Review::getId)
                        .collect(Collectors.toList()))
                .isVegetarian(dish.isVegetarian())
                .imageUrl(dish.getImageUrl())
                .build();
    }

    public static Dish to(DishDto dishDto) {
        return Dish.builder()
                .id(dishDto.getId())
                .name(dishDto.getName())
                .cuisine(Cuisine.valueOf(dishDto.getCuisine().toUpperCase()))
                .description(dishDto.getDescription())
                .price(dishDto.getPrice())
                .restaurant(Restaurant.builder().id(dishDto.getRestaurantId()).build())
                .isVegetarian(dishDto.isVegetarian())
                .imageUrl(dishDto.getImageUrl())
                .build();
    }

    public static List<DishDto> from(List<Dish> dishes) {
        return dishes.stream()
                .map(DishDto::from)
                .collect(Collectors.toList());
    }

    public static List<Dish> toList(List<DishDto> dishDtos) {
        return dishDtos.stream()
                .map(DishDto::to)
                .collect(Collectors.toList());
    }
}
