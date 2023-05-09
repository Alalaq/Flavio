package ru.kpfu.itis.khabibullin.controllers.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.khabibullin.dto.RestaurantDto;
import ru.kpfu.itis.khabibullin.services.RestaurantService;
/**
 * @author Khabibullin Alisher
 */
@RestController
public class RestaurantRestController {
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurant/{restaurantName}")
    public RestaurantDto getRestaurantByName(@PathVariable String restaurantName){
        return restaurantService.getRestaurantByName(restaurantName.replace("%20", " "));
    }

    @GetMapping("/restaurant/get/{restaurantId}")
    public RestaurantDto getRestaurantById(@PathVariable Long restaurantId){
        return restaurantService.getRestaurantById(restaurantId);
    }
}
