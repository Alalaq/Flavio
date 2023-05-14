package ru.kpfu.itis.khabibullin.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.khabibullin.models.Dish;
import ru.kpfu.itis.khabibullin.models.Restaurant;
import ru.kpfu.itis.khabibullin.utils.enums.Cuisine;

import java.math.BigDecimal;
/**
 * @author Khabibullin Alisher
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDishDto {
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

    public static Dish toDish(CartDishDto dishDto){
        return Dish.builder()
                .id(dishDto.getId())
                .name(dishDto.getName())
                .cuisine(Cuisine.valueOf(dishDto.getCuisine().toUpperCase()))
                .description(dishDto.getDescription())
                .price(dishDto.getPrice())
                .restaurant(Restaurant.builder().id(dishDto.getRestaurantId()).build())
                .imageUrl(dishDto.getImageUrl())
                .build();
    }
}
