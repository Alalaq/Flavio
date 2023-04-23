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

    @GetMapping(params = {"address", "cuisine", "distance", "price"})
    public String getRestaurantsWithFilters(@RequestParam(value = "address", required = false) String address,
                                            @RequestParam(value = "cuisine", required = false) Set<Cuisine> cuisines,
                                            @RequestParam(value = "distance", required = false) Integer distance,
                                            @RequestParam(value = "price", required = false) Set<Price> price,
                                            Model model){
        List<RestaurantDto> restaurants = restaurantService.getAllRestaurantsByFilters(cuisines, price, distance, address);
        model.addAttribute("restaurants", restaurants);
        return "restaurants";
    }
}