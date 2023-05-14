package ru.kpfu.itis.khabibullin.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.khabibullin.dto.RestaurantDto;
import ru.kpfu.itis.khabibullin.exceptions.NotFoundException;
import ru.kpfu.itis.khabibullin.models.Restaurant;
import ru.kpfu.itis.khabibullin.repositories.RestaurantsRepository;
import ru.kpfu.itis.khabibullin.services.RestaurantService;
import ru.kpfu.itis.khabibullin.utils.API.AddressUtil;
import ru.kpfu.itis.khabibullin.utils.enums.Cuisine;
import ru.kpfu.itis.khabibullin.utils.enums.Price;

import java.util.List;
import java.util.Set;
/**
 * @author Khabibullin Alisher
 */
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantsRepository restaurantsRepository;

    @Override
    public List<RestaurantDto> getAllRestaurants() {
        return RestaurantDto.from(restaurantsRepository.findAllOrderById());
    }

    @Override
    public List<RestaurantDto> getAllRestaurantsByFilters(Set<Cuisine> cuisines, Set<Price> price, Integer distance, String address, Double rating) {
        AddressUtil.Coordinates coordinates = AddressUtil.getCoordinates(address);
        return RestaurantDto.from(restaurantsRepository.findByFilters(cuisines, price, distance, rating, coordinates.longitude(), coordinates.latitude()));
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
        if (restaurant.getImageUrl() != null && !restaurant.getImageUrl().equals("")){
            existingRestaurant.setImageUrl(restaurant.getImageUrl());
        }
        restaurantsRepository.save(existingRestaurant);
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantsRepository.deleteById(id);
    }

    @Override
    public RestaurantDto getRestaurantByName(String name) {
        return RestaurantDto.from(restaurantsRepository.findRestaurantByName(name).orElseThrow(() -> new NotFoundException("Restaurant with name: <" + name + "> not found")));
    }

    @Override
    public void save(RestaurantDto restaurant){
        //TODO: something with image idk
        AddressUtil.Coordinates coordinates = AddressUtil.getCoordinates(restaurant.getAddress());
        restaurant.setLatitude(coordinates.latitude());
        restaurant.setLongitude(coordinates.longitude());
        restaurantsRepository.save(RestaurantDto.to(restaurant));
    }
}
