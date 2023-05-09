package ru.kpfu.itis.khabibullin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.khabibullin.models.Dish;
import ru.kpfu.itis.khabibullin.models.Review;

import java.util.List;
/**
 * @author Khabibullin Alisher
 */
public interface ReviewsRepository extends JpaRepository<Review, Long> {
    List<Review> findByDish(Dish dish);
}
