package ru.kpfu.itis.khabibullin.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.khabibullin.dto.DishDto;
import ru.kpfu.itis.khabibullin.repositories.DishesRepository;
import ru.kpfu.itis.khabibullin.services.DishService;
import ru.kpfu.itis.khabibullin.utils.API.ImageUtil;

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
    }

    @Override
    public void saveDish(DishDto dish) {
        if (dish.getImageUrl() == null || dish.getImageUrl().equals("")){
           dish.setImageUrl(ImageUtil.getImages(dish.getName(), 1).get(0));
        }
        dishRepository.save(DishDto.to(dish));
    }

    @Override
    public void deleteDish(DishDto dish) {
        dishRepository.delete(DishDto.to(dish));
    }

    @Override
    public List<DishDto> getDishesByRestaurantId(Long restaurantId) {
        return DishDto.from(dishRepository.findDishesByRestaurantId(restaurantId));
    }

    @Override
    public List<DishDto> getDishesByNames(List<String> dishNames) {
        return DishDto.from(dishRepository.findAllByNameIn(dishNames));
    }
}
