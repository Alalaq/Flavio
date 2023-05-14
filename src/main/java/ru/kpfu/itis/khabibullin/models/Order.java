package ru.kpfu.itis.khabibullin.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.khabibullin.utils.enums.StateOfOrder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Khabibullin Alisher
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false, name = "date")
    private LocalDateTime date;

    @Column(nullable = false, precision = 10, scale = 2, name = "total")
    private int total;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "state")
    private StateOfOrder state;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToMany
    @JoinTable(name = "order_dish",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private List<Dish> dishes = new ArrayList<>();


    @PrePersist
    private void setOrderDate() {
        this.date = (LocalDateTime.now());
    }


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", total=" + total +
                ", userId=" + user.getId() +
                ", state=" + state +
                ", restaurantId=" + restaurant.getId() +
                ", dishes=" + dishes +
                '}';
    }

}
