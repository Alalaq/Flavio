package ru.kpfu.itis.khabibullin.services;

import ru.kpfu.itis.khabibullin.dto.RestaurantDto;
import ru.kpfu.itis.khabibullin.utils.Cuisine;
import ru.kpfu.itis.khabibullin.utils.Price;

import java.util.List;
import java.util.Set;

public interface RestaurantService {
    List<RestaurantDto> getAllRestaurants();

    List<RestaurantDto> getAllRestaurantsByFilters(Set<Cuisine> cuisines, Set<Price> price, Integer distance, String address, Double rating);

    RestaurantDto getRestaurantById(Long id);

    void addRestaurant(RestaurantDto restaurant) ;

    void editRestaurant(Long id, RestaurantDto restaurant);

    void deleteRestaurant(Long id) ;
}
