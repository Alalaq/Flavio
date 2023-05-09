package ru.kpfu.itis.khabibullin.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.khabibullin.models.Order;
import ru.kpfu.itis.khabibullin.models.Restaurant;
import ru.kpfu.itis.khabibullin.models.User;
import ru.kpfu.itis.khabibullin.utils.enums.StateOfOrder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
/**
 * @author Khabibullin Alisher
 */
public interface OrdersRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO order_dish (order_id, dish_id) VALUES (:orderId, :dishId)", nativeQuery = true)
    void addDishToOrder(Long orderId, Long dishId);


    @Transactional
    default void save(Order orderToSave, List<Long> dishIds) {
        save(orderToSave);

        Order order = findByUserIdAndRestaurantIdAndTotalAndStateAndDate(orderToSave.getUser(), orderToSave.getRestaurant(), orderToSave.getTotal(), orderToSave.getState(), orderToSave.getDate());
        for (Long dishId : dishIds) {
            addDishToOrder(order.getId(), dishId);
        }
    }

    @Query(value = "SELECT * FROM orders " +
            " WHERE user_id = :userId" +
            "  AND restaurant_id = :restaurantId" +
            "  AND total = :total", nativeQuery = true)
    List<Order> findAllByUserIdAndRestaurantIdAndTotal(Long userId, Long restaurantId, int total);


    default Order findByUserIdAndRestaurantIdAndTotalAndStateAndDate(User user, Restaurant restaurant, int total, StateOfOrder state, LocalDateTime date) {
        List<Order> orders = findAllByUserIdAndRestaurantIdAndTotal(user.getId(), restaurant.getId(), total);
        for (Order order : orders){
            if (order.getDate().truncatedTo(ChronoUnit.SECONDS).equals(date) && order.getState().equals(state)){
                order.setRestaurant(restaurant);
                order.setUser(user);
                return order;
            }
        }
        return null;
    }

    List<Order> findAllByUserIdOrderByDateDesc(Long UserId);
}
