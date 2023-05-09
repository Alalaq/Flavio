package ru.kpfu.itis.khabibullin.services;

import ru.kpfu.itis.khabibullin.dto.RestaurantDto;
import ru.kpfu.itis.khabibullin.utils.enums.Cuisine;
import ru.kpfu.itis.khabibullin.utils.enums.Price;

import java.util.List;
import java.util.Set;
/**
 * @author Khabibullin Alisher
 */
public interface RestaurantService {
    List<RestaurantDto> getAllRestaurants();

    List<RestaurantDto> getAllRestaurantsByFilters(Set<Cuisine> cuisines, Set<Price> price, Integer distance, String address, Double rating);

    RestaurantDto getRestaurantById(Long id);

    void addRestaurant(RestaurantDto restaurant) ;

    void editRestaurant(Long id, RestaurantDto restaurant);

    void deleteRestaurant(Long id) ;

    RestaurantDto getRestaurantByName(String name);

    void save(RestaurantDto restaurant);
}
