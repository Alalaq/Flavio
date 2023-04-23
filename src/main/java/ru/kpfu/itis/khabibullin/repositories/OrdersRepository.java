package ru.kpfu.itis.khabibullin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.khabibullin.models.Order;
import ru.kpfu.itis.khabibullin.models.User;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
