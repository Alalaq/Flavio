package ru.kpfu.itis.khabibullin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kpfu.itis.khabibullin.models.Dish;
import ru.kpfu.itis.khabibullin.models.Review;

import java.util.List;
/**
 * @author Khabibullin Alisher
 */
public interface ReviewsRepository extends JpaRepository<Review, Long> {
    List<Review> findByDish(Dish dish);

    @Query(value = "SELECT * FROM flavio_db.public.reviews r" +
            " WHERE r.order_id = :orderId limit 1", nativeQuery = true)
    Review findFirstByOrderId(Long orderId);

}
