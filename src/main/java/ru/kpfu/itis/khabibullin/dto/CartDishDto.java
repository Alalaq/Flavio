package ru.kpfu.itis.khabibullin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDishDto {
    private Long id;
    private String name;
    private String description;
    private String cuisine;
    private BigDecimal price;
    private Long restaurantId;
    private int quantity;
    private String imageUrl;
}
