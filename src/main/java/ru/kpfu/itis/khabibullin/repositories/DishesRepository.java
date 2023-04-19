package ru.kpfu.itis.khabibullin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.khabibullin.models.Dish;
import ru.kpfu.itis.khabibullin.models.User;

import java.util.List;
import java.util.Optional;

public interface DishesRepository extends JpaRepository<Dish, Long> {
    Dish findByName(String name);
    Dish findDishById(Long id);
    List<Dish> findDishesByPriceBetween(Integer firstNumber, Integer secondNumber);
    List<Dish> findByNameContainingIgnoreCase(String name);
}
