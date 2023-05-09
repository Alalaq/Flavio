package ru.kpfu.itis.khabibullin.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.khabibullin.models.Order;
import ru.kpfu.itis.khabibullin.models.Restaurant;
import ru.kpfu.itis.khabibullin.models.User;
import ru.kpfu.itis.khabibullin.utils.enums.StateOfOrder;

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
    private int total;
    private Long userId;
    private Long restaurantId;
    private List<CartDishDto> dishes;
    private StateOfOrder state;

    public OrderDto(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        OrderDto order = mapper.readValue(json, OrderDto.class);
        this.id = order.id;
        this.date = order.date;
        this.total = order.total;
        this.userId = order.userId;
        this.state = order.state;
        this.restaurantId = order.restaurantId;
        this.dishes = order.dishes;
    }

    public static Order to(OrderDto dto) {
        return Order.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .total(dto.getTotal())
                .user(User.builder().id(dto.getUserId()).build())
                .restaurant(Restaurant.builder().id(dto.getRestaurantId()).build())
                .dishes(dto.getDishes().stream()
                        .map(item -> DishDto.to(DishDto.builder()
                                .id(item.getId())
                                .name(item.getName())
                                .price(item.getPrice())
                                .description(item.getDescription())
                                .cuisine(item.getCuisine())
                                .imageUrl(item.getImageUrl())
                                .restaurantId(item.getRestaurantId()).build()))
                        .collect(Collectors.toList()))
                .state(dto.state)
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
                .userId((order.getUser().getId()))
                .restaurantId((order.getRestaurant().getId()))
                .dishes(order.getDishes().stream().map(item -> (CartDishDto.builder()
                                .id(item.getId())
                                .name(item.getName())
                                .price(item.getPrice())
                                .description(item.getDescription())
                                .cuisine(item.getCuisine().getName())
                                .imageUrl(item.getImageUrl())
                                .restaurantId(item.getRestaurant().getId()).build())).collect(Collectors.toList()))
                .state(order.getState())
                .build();
    }

    public static List<OrderDto> from(List<Order> orders) {
        return orders.stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
    }


    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", date=" + date +
                ", total=" + total +
                ", userId=" + userId +
                ", restaurantId=" + restaurantId +
                ", dishes=" + dishes +
                ", state=" + state +
                '}';
    }
}
