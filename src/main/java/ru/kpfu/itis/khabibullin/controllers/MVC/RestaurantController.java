package ru.kpfu.itis.khabibullin.controllers.MVC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.khabibullin.dto.RestaurantDto;
import ru.kpfu.itis.khabibullin.services.RestaurantService;
import ru.kpfu.itis.khabibullin.utils.API.AddressUtil;
import ru.kpfu.itis.khabibullin.utils.enums.Cuisine;
import ru.kpfu.itis.khabibullin.utils.enums.Price;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * @author Khabibullin Alisher
 */
@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    private String userAddress;

    @GetMapping
    public String getAddress(Model model) {
        List<RestaurantDto> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "restaurants";
    }

    @PostMapping
    public String getAddress(@RequestParam(name = "userAddress") String address,
                             Model model) {
        userAddress = address;
        model.addAttribute("userAddress", address);
        return "restaurants";
    }

    @RequestMapping("/filtered")
    public String getRestaurantsWithFilters(@RequestParam(value = "rating", required = false) Double rating,
                                            @RequestParam(value = "cuisine", required = false) List<String> cuisines,
                                            @RequestParam(value = "price", required = false) List<String> prices,
                                            @RequestParam(value = "distance", required = false) Integer distance,
                                            Model model) {
        Set<Cuisine> cuisineSet = (cuisines != null && !cuisines.isEmpty()) ? cuisines.stream().map(Cuisine::valueOf).collect(Collectors.toSet()) : Set.of(Cuisine.values());
        Set<Price> priceSet = (prices != null && !prices.isEmpty()) ? prices.stream().map(Price::valueOf).collect(Collectors.toSet()) : Set.of(Price.values());
        Double finalRating = rating != null ? rating : 1;
        Integer finalDistance = distance != null ? distance : 12756;
        List<RestaurantDto> restaurants = restaurantService.getAllRestaurantsByFilters(cuisineSet, priceSet, finalDistance, userAddress, finalRating);
        model.addAttribute("restaurants", restaurants);
        return "restaurants";
    }


    //TODO: handle delivery time
    @GetMapping("/{restaurantName}")
    public String getRestaurantPage(@PathVariable String restaurantName,
                                    Model model){
        double speed = 12.0;
        if (userAddress != null) {
            double distance = AddressUtil.calculateDistance(userAddress, restaurantService.getRestaurantByName(restaurantName.replace("%20", " ")).getAddress());
            double roundedTimeInMinutes = Math.round(((distance / speed) * 60) / 5) * 5;
            int lowerBound = (int) roundedTimeInMinutes - 5;
            int upperBound = (int) roundedTimeInMinutes;

            String deliveryTime = lowerBound + "-" + upperBound;

            System.out.println(deliveryTime);

            model.addAttribute("restaurant-delivery-time", deliveryTime);
        } else {
            model.addAttribute("restaurant-delivery-time", -1);
        }
        model.addAttribute("restaurant-name", restaurantName);
        model.addAttribute("restaurantId", restaurantService.getRestaurantByName(restaurantName).getId());
        return "restaurant";
    }

}