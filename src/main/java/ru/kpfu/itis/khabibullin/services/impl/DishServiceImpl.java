package ru.kpfu.itis.khabibullin.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.khabibullin.dto.DishDto;
import ru.kpfu.itis.khabibullin.repositories.DishesRepository;
import ru.kpfu.itis.khabibullin.services.DishService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishesRepository dishRepository;

    @Override
    public DishDto getDishById(Long id) {
        return DishDto.from(dishRepository.findById(id).orElseThrow(() -> new RuntimeException("Dish not found")));
    }

    @Override
    public List<DishDto> getAllDishes() {
        return DishDto.from(dishRepository.findAll());
    }

    @Override
    public List<DishDto> getDishesByName(String name) {
        return DishDto.from(dishRepository.findByNameContainingIgnoreCase(name));
    }

    @Override
    public List<DishDto> getDishesByPriceRange(Integer minPrice, Integer maxPrice) {
        return DishDto.from(dishRepository.findDishesByPriceBetween(minPrice, maxPrice));
    }//TODO: maybe I need to return the added entity?...
    @Override
    public void saveDish(DishDto dish) {
        dishRepository.save(DishDto.to(dish));
    }

    @Override
    public void deleteDish(DishDto dish) {
        dishRepository.delete(DishDto.to(dish));
    }
}
