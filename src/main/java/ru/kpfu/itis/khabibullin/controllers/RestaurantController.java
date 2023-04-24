package ru.kpfu.itis.khabibullin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.khabibullin.dto.RestaurantDto;
import ru.kpfu.itis.khabibullin.services.RestaurantService;
import ru.kpfu.itis.khabibullin.utils.Cuisine;
import ru.kpfu.itis.khabibullin.utils.Price;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    //TODO: add general rating
    @GetMapping
    public String getRestaurants(Model model) {
        List<RestaurantDto> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "restaurants";
    }

    @RequestMapping("/filtered")
    public String getRestaurantsWithFilters(@RequestParam(value = "rating", required = false) Double rating,
                                            @RequestParam(value = "cuisine", required = false) List<String> cuisines,
                                            @RequestParam(value = "price", required = false) List<String> prices,
                                            @RequestParam(value = "distance", required = false) Integer distance,
                                            Model model) {

        Set<Cuisine> cuisineSet = cuisines != null ? cuisines.stream().map(Cuisine::valueOf).collect(Collectors.toSet()) : null;
        Set<Price> priceSet = prices != null ? prices.stream().map(Price::valueOf).collect(Collectors.toSet()) : null;
        System.out.println("rating: " + rating + " cuisinesenes:" + cuisineSet + " prces: " + priceSet + " distance: " + distance);
        List<RestaurantDto> restaurants = restaurantService.getAllRestaurantsByFilters(cuisineSet, priceSet, distance,"Kazan", rating);
        System.out.println(restaurants);
        model.addAttribute("restaurants", restaurants);

        return "restaurants";
    }

}