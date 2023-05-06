package ru.kpfu.itis.khabibullin.services;

import ru.kpfu.itis.khabibullin.dto.DishDto;

import java.util.List;

public interface DishService {
    DishDto getDishById(Long id);
    List<DishDto> getAllDishes();
    List<DishDto> getDishesByName(String name);
    List<DishDto> getDishesByPriceRange(Integer minPrice, Integer maxPrice);
    void saveDish(DishDto dish);
    void deleteDish(DishDto dish);

    List<DishDto> getDishesByRestaurantId(Long restaurantId);

    List<DishDto> getDishesByNames(List<String> dishNames);
}
