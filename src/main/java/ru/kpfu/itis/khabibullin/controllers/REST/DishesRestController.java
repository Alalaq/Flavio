package ru.kpfu.itis.khabibullin.controllers.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.khabibullin.dto.DishDto;
import ru.kpfu.itis.khabibullin.services.DishService;

import java.util.List;
/**
 * @author Khabibullin Alisher
 */
@RestController
public class DishesRestController {
    @Autowired
    private DishService dishService;

    @GetMapping("/dishes/{restaurantId}")
    public List<DishDto> dishesFromRestaurant(@PathVariable(name = "restaurantId") Long restaurantId){
        return dishService.getDishesByRestaurantId(restaurantId);
    }
}
