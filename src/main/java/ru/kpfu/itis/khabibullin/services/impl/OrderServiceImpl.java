package ru.kpfu.itis.khabibullin.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.khabibullin.dto.OrderDto;
import ru.kpfu.itis.khabibullin.dto.UserDto;
import ru.kpfu.itis.khabibullin.models.Order;
import ru.kpfu.itis.khabibullin.models.User;
import ru.kpfu.itis.khabibullin.repositories.OrdersRepository;
import ru.kpfu.itis.khabibullin.services.OrderService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;

    @Override
    public OrderDto getOrderById(Long id) {
        return OrderDto.from(ordersRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found")));
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return OrderDto.from(ordersRepository.findAll());
    }

    @Override
    public List<OrderDto> getOrdersByUser(UserDto user) {
        return OrderDto.from(ordersRepository.findByUser(UserDto.to(user)));
    }

    @Override
    public void saveOrder(OrderDto order) {
        ordersRepository.save(OrderDto.to(order));
    }

    @Override
    public void deleteOrder(OrderDto order) {
        ordersRepository.delete(OrderDto.to(order));
    }
}
