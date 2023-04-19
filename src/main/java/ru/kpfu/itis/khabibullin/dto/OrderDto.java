package ru.kpfu.itis.khabibullin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.khabibullin.models.Order;
import ru.kpfu.itis.khabibullin.models.Restaurant;
import ru.kpfu.itis.khabibullin.models.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private LocalDateTime date;
    private BigDecimal total;
    private UserDto user;
    private RestaurantDto restaurant;
    private List<DishDto> dishes;

    public static Order to(OrderDto dto) {
        return Order.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .total(dto.getTotal())
                .user(User.builder().id(dto.getUser().getId()).build())
                .restaurant(Restaurant.builder().id(dto.getRestaurant().getId()).build())
                .dishes(dto.getDishes().stream().map(DishDto::to).collect(Collectors.toList()))
                .build();
    }

    public static List<Order> to(List<OrderDto> dtos) {
        return dtos.stream()
                .map(OrderDto::to)
                .collect(Collectors.toList());
    }

    public static OrderDto from(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .date(order.getDate())
                .total(order.getTotal())
                .user(UserDto.from(order.getUser()))
                .restaurant(RestaurantDto.from(order.getRestaurant()))
                .dishes(order.getDishes().stream()
                        .map(DishDto::from)
                        .collect(Collectors.toList()))
                .build();
    }

    public static List<OrderDto> from(List<Order> orders) {
        return orders.stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
    }
}
