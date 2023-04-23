package ru.kpfu.itis.khabibullin.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.khabibullin.dto.RestaurantDto;
import ru.kpfu.itis.khabibullin.exceptions.NotFoundException;
import ru.kpfu.itis.khabibullin.models.Restaurant;
import ru.kpfu.itis.khabibullin.repositories.RestaurantsRepository;
import ru.kpfu.itis.khabibullin.services.RestaurantService;
import ru.kpfu.itis.khabibullin.utils.Cuisine;
import ru.kpfu.itis.khabibullin.utils.Price;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantsRepository restaurantsRepository;

    @Override
    public List<RestaurantDto> getAllRestaurants() {
        return RestaurantDto.from(restaurantsRepository.findAll());
    }

    @Override
    public List<RestaurantDto> getAllRestaurantsByFilters(Set<Cuisine> cuisines, Set<Price> price, Integer distance, String address) {
        return RestaurantDto.from(restaurantsRepository.findByFilters(cuisines, price, distance, address));
    }

    @Override
    public RestaurantDto getRestaurantById(Long id) {
        return RestaurantDto.from(restaurantsRepository.findById(id).orElseThrow(() -> new NotFoundException("Restaurant not found")));
    }

    @Override
    public void addRestaurant(RestaurantDto restaurant) {
        restaurantsRepository.save(RestaurantDto.to(restaurant));
    }

    @Override
    public void editRestaurant(Long id, RestaurantDto restaurant) {
        Restaurant existingRestaurant = restaurantsRepository.findRestaurantById(id).orElseThrow(() -> new NotFoundException("Restaurant with id: <" + restaurant.getId() + "> not found"));
        existingRestaurant.setName(restaurant.getName());
        existingRestaurant.setDescription(restaurant.getDescription());
        existingRestaurant.setAddress(restaurant.getAddress());
        restaurantsRepository.save(existingRestaurant);
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantsRepository.deleteById(id);
    }
}
