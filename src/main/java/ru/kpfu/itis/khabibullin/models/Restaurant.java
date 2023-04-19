package ru.kpfu.itis.khabibullin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.khabibullin.utils.Cuisine;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaraunt_id")
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name is required")
    private String name;

    //Represents what exact cuisines dishes this restaurant cooks and rating for each cuisine
    @ElementCollection
    @CollectionTable(name = "restaurant_cuisine_rating",
            joinColumns = @JoinColumn(name = "restaurant_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "rating")
    @MapKeyColumn(name = "cuisine")
    private Map<Cuisine, Double> cuisineRatings = new HashMap<>();

    @Column(name = "address", nullable = false)
    @NotBlank(message = "Address is required")
    private String address;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dish> dishes = new ArrayList<>();
}
