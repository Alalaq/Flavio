package ru.kpfu.itis.khabibullin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.khabibullin.models.Dish;
import ru.kpfu.itis.khabibullin.models.Restaurant;
import ru.kpfu.itis.khabibullin.utils.enums.Cuisine;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author Khabibullin Alisher
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishDto {
    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String cuisine;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    private Long restaurantId;

    @NotNull
    @PositiveOrZero
    private int quantity;

    @NotBlank
    private String imageUrl;
    private List<Long> reviewIds;
    private boolean isVegetarian;

    //TODO: handle case with null reviews
    public static DishDto from(Dish dish) {
        return DishDto.builder()
                .id(dish.getId())
                .name(dish.getName())
                .cuisine(dish.getCuisine().name())
                .description(dish.getDescription())
                .price(dish.getPrice())
                .restaurantId(dish.getRestaurant().getId())
               // .reviewIds(ReviewDto.from(dish.getReviews()))
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

    @Override
    public String toString() {
        return "DishDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cuisine='" + cuisine + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", restaurantId=" + restaurantId +
                ", reviewIds=" + reviewIds +
                ", isVegetarian=" + isVegetarian +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

}
