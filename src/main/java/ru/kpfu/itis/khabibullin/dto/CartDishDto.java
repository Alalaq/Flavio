package ru.kpfu.itis.khabibullin.dto;

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
    private Long id;
    private String name;
    private String description;
    private String cuisine;
    private BigDecimal price;
    private Long restaurantId;
    private int quantity;
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
