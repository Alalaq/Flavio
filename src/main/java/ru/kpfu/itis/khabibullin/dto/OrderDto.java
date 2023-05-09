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
import java.time.temporal.ChronoUnit;
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


    public String toJsonString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"id\":").append(id).append(",");
        sb.append("\"date\":\"").append(date.truncatedTo(ChronoUnit.SECONDS).toString().replace("T", " ")).append("\",");
        sb.append("\"total\":").append(total).append(",");
        sb.append("\"userId\":").append(userId).append(",");
        sb.append("\"state\":\"").append(Character.toUpperCase(state.toString().charAt(0))).append(state.toString().substring(1).replace("_", " ").toLowerCase()).append("\",");
        sb.append("\"restaurantId\":").append(restaurantId).append(",");
        sb.append("\"dishes\":[");
        for (int i = 0; i < dishes.size(); i++) {
            CartDishDto dish = dishes.get(i);
            sb.append("{");
            sb.append("\"id\":").append(dish.getId()).append(",");
            sb.append("\"name\":\"").append(dish.getName()).append("\",");
            sb.append("\"cuisine\":\"").append(dish.getCuisine()).append("\",");
            sb.append("\"description\":\"").append(dish.getDescription()).append("\",");
            sb.append("\"price\":").append(dish.getPrice()).append(",");
            sb.append("\"restaurantId\":").append(dish.getRestaurantId()).append(",");
            sb.append("\"quantity\":").append(dish.getQuantity()).append(",");
            sb.append("\"imageUrl\":\"").append(dish.getImageUrl()).append("\"");
            sb.append("}");
            if (i < dishes.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }

    public static String toJsonStringUsingToString(OrderDto orderDto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(orderDto.toJsonString());
    }

    public static String toJsonString(List<OrderDto> orderDtos) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < orderDtos.size(); i++) {
            sb.append(orderDtos.get(i).toJsonString());
            if (i < orderDtos.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static String toJsonStringUsingToString(List<OrderDto> orderDtos) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(toJsonString(orderDtos));
    }


}
