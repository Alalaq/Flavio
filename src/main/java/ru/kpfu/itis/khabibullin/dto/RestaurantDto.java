package ru.kpfu.itis.khabibullin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.khabibullin.models.Restaurant;
import ru.kpfu.itis.khabibullin.utils.enums.Cuisine;
import ru.kpfu.itis.khabibullin.utils.enums.Price;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * @author Khabibullin Alisher
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDto {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Map<Cuisine, Double> cuisineRatings;

    private Double generalRating;

    @NotNull
    private Price price;

    @NotBlank
    private String address;

    private Double latitude;

    private Double longitude;

    @NotBlank
    private String description;

    @NotBlank
    private String imageUrl;

    private List<DishDto> dishes;
    public static Restaurant to(RestaurantDto dto) {
        return Restaurant.builder()
                .id(dto.getId())
                .name(dto.getName())
                .cuisineRatings(dto.getCuisineRatings())
                .address(dto.getAddress())
                .description(dto.getDescription())
                .imageUrl(dto.imageUrl)
                .dishes(DishDto.toList(dto.getDishes()))
                .build();
    }

    public static List<Restaurant> to(List<RestaurantDto> dtos) {
        return dtos.stream()
                .map(RestaurantDto::to)
                .collect(Collectors.toList());
    }

    public static RestaurantDto from(Restaurant restaurant) {
        return RestaurantDto.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .cuisineRatings(restaurant.getCuisineRatings())
                .generalRating(restaurant.getGeneralRating())
                .price(restaurant.getPrice())
                .address(restaurant.getAddress())
                .description(restaurant.getDescription())
                .imageUrl(restaurant.getImageUrl())
                .dishes(DishDto.from(restaurant.getDishes()))
                .build();
    }

    public static List<RestaurantDto> from(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantDto::from)
                .collect(Collectors.toList());
    }
}
