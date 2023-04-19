package ru.kpfu.itis.khabibullin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.khabibullin.models.Restaurant;

public interface RestaurantsRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findRestaurantById(Long id);
    Restaurant findRestaurantByName(String name);
}
