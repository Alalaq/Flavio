package ru.kpfu.itis.khabibullin.services;

import ru.kpfu.itis.khabibullin.dto.DishDto;
import ru.kpfu.itis.khabibullin.models.Dish;

import java.math.BigDecimal;
import java.util.List;

public interface DishService {
    DishDto getDishById(Long id);
    List<DishDto> getAllDishes();
    List<DishDto> getDishesByName(String name);
    List<DishDto> getDishesByPriceRange(Integer minPrice, Integer maxPrice);
    void saveDish(DishDto dish);
    void deleteDish(DishDto dish);
}
