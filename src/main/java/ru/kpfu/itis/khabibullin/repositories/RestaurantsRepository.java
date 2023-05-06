package ru.kpfu.itis.khabibullin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.khabibullin.models.Restaurant;
import ru.kpfu.itis.khabibullin.utils.API.AddressUtil;
import ru.kpfu.itis.khabibullin.utils.enums.Cuisine;
import ru.kpfu.itis.khabibullin.utils.enums.Price;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RestaurantsRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findRestaurantById(Long id);

    Optional<Restaurant> findRestaurantByName(String name);

    default List<Restaurant> findByFilters(Set<Cuisine> cuisines, Set<Price> price, Integer distance, String address, Double rating) {
        List<Restaurant> restaurants = findAll();
        if (cuisines.isEmpty() && price.isEmpty() && distance == null && address == null && rating == null) {
            return restaurants;
        } else {
            List<Restaurant> filteredRestaurants = new ArrayList<>();
            for (Restaurant restaurant : restaurants) {
                boolean addRestaurant = true;
                if (!cuisines.isEmpty()) {
                    if (cuisines.size() > restaurant.getCuisineRatings().keySet().size()){
                        if (!cuisines.containsAll(restaurant.getCuisineRatings().keySet())) {
                            addRestaurant = false;
                        }
                    } else {
                        if (!restaurant.getCuisineRatings().keySet().containsAll(cuisines)) {
                            addRestaurant = false;
                        }
                    }
                }
                if ((!price.isEmpty() && !price.contains(restaurant.getPrice()))) {
                    addRestaurant = false;
                }
                if (address != null && distance != null) {
                    double dist = AddressUtil.calculateDistance(address, restaurant.getAddress());
                    if (dist > distance) {
                        addRestaurant = false;
                    }
                }
                if (rating != null){
                    if (restaurant.getGeneralRating() < rating){
                        addRestaurant = false;
                    }
                }
                if (addRestaurant) {
                    filteredRestaurants.add(restaurant);
                }
            }
            return filteredRestaurants;
        }
    }

}
