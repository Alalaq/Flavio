package ru.kpfu.itis.khabibullin.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.khabibullin.dto.RestaurantDto;
import ru.kpfu.itis.khabibullin.exceptions.NotFoundException;
import ru.kpfu.itis.khabibullin.models.Restaurant;
import ru.kpfu.itis.khabibullin.repositories.RestaurantsRepository;
import ru.kpfu.itis.khabibullin.services.RestaurantService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantsRepository restaurantsRepository;

    @Override
    public List<RestaurantDto> getAllRestaurants() {
        return RestaurantDto.from(restaurantsRepository.findAll());
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
        Restaurant existingRestaurant = restaurantsRepository.findRestaurantById(id);
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
