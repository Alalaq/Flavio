package ru.kpfu.itis.khabibullin.services;

import ru.kpfu.itis.khabibullin.dto.OrderDto;
import ru.kpfu.itis.khabibullin.dto.UserDto;
import ru.kpfu.itis.khabibullin.models.Order;
import ru.kpfu.itis.khabibullin.models.User;

import java.util.List;

public interface OrderService {
    OrderDto getOrderById(Long id);
    List<OrderDto> getAllOrders();
    List<OrderDto> getOrdersByUser(UserDto user);
    void saveOrder(OrderDto order);
    void deleteOrder(OrderDto order);
}
