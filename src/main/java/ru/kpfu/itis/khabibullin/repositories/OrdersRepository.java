package ru.kpfu.itis.khabibullin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.khabibullin.models.Order;
import ru.kpfu.itis.khabibullin.models.User;

import java.time.LocalDateTime;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Order, Long> {
    Order findOrderById(Long id);
    Order findOrdersByDate(LocalDateTime date);
    List<Order> findByUser(User user);
}
