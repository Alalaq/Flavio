package ru.kpfu.itis.khabibullin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kpfu.itis.khabibullin.models.Restaurant;
import ru.kpfu.itis.khabibullin.utils.enums.Cuisine;
import ru.kpfu.itis.khabibullin.utils.enums.Price;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Khabibullin Alisher
 */
public interface RestaurantsRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findRestaurantById(Long id);

    Optional<Restaurant> findRestaurantByName(String name);

    @Query(value = "SELECT * FROM restaurants ORDER BY restaurant_id", nativeQuery = true)
    List<Restaurant> findAllOrderById();

    @Query(value = " SELECT distinct r from Restaurant r where (key(r.cuisineRatings) in :cuisines)" +
            "       AND (r.price IN (:price)) " +
            "       AND ((FUNCTION('earth_distance', function('ll_to_earth', r.longitude, r.latitude), function('ll_to_earth', :userLong, :userLat))) <= (:distance * 1000)) " +
            "       AND (r.generalRating >= :rating) " +
            " GROUP BY r.id" +
            " ORDER BY r.id ASC ")
    List<Restaurant> findByFilters(@Param("cuisines") Set<Cuisine> cuisines, @Param("price") Set<Price> price, @Param("distance") Integer distance, @Param("rating") Double rating, @Param("userLong") Double userLong, @Param("userLat") Double userLat);

}
