package ru.kpfu.itis.khabibullin.services;

import ru.kpfu.itis.khabibullin.dto.RestaurantDto;
import ru.kpfu.itis.khabibullin.models.Restaurant;

import java.util.List;

public interface RestaurantService {
    List<RestaurantDto> getAllRestaurants();

    RestaurantDto getRestaurantById(Long id);

    void addRestaurant(RestaurantDto restaurant) ;

    void editRestaurant(Long id, RestaurantDto restaurant);

    void deleteRestaurant(Long id) ;
}
