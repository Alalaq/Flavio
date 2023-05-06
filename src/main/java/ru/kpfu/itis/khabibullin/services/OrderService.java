package ru.kpfu.itis.khabibullin.services;

import ru.kpfu.itis.khabibullin.dto.OrderDto;
import ru.kpfu.itis.khabibullin.dto.RestaurantDto;
import ru.kpfu.itis.khabibullin.dto.UserDto;
import ru.kpfu.itis.khabibullin.utils.enums.StateOfOrder;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    OrderDto getOrderById(Long id);
    List<OrderDto> getAllOrders();
    List<OrderDto> getOrdersByUserId(Long id);
    void saveOrder(OrderDto order);
    void deleteOrder(OrderDto order);

    OrderDto findByUserIdAndRestaurantIdAndTotalAndState(UserDto user, RestaurantDto restaurant, int total, StateOfOrder state, LocalDateTime date);
}
