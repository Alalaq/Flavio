package ru.kpfu.itis.khabibullin.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.khabibullin.utils.enums.Cuisine;
import ru.kpfu.itis.khabibullin.utils.enums.Price;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author Khabibullin Alisher
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "general_rating", columnDefinition = "numeric(5,2)")
    private Double generalRating;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "price")
    private Price price;

    //Represents what exact cuisines dishes this restaurant cooks and rating for each cuisine
    @ElementCollection
    @CollectionTable(name = "restaurant_cuisine_rating",
            joinColumns = @JoinColumn(name = "restaurant_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "rating")
    @MapKeyColumn(name = "cuisine")
    private Map<Cuisine, Double> cuisineRatings = new HashMap<>();

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dish> dishes = new ArrayList<>();


    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", generalRating=" + generalRating +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", cuisineRatings=" + cuisineRatings +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", dishes=" + dishes +
                '}';
    }

}
