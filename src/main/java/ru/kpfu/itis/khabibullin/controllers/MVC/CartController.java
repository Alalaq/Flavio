package ru.kpfu.itis.khabibullin.controllers.MVC;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kpfu.itis.khabibullin.dto.CartDishDto;
import ru.kpfu.itis.khabibullin.dto.DishDto;
import ru.kpfu.itis.khabibullin.dto.OrderDto;
import ru.kpfu.itis.khabibullin.services.DishService;
import ru.kpfu.itis.khabibullin.services.OrderService;
import ru.kpfu.itis.khabibullin.services.RestaurantService;
import ru.kpfu.itis.khabibullin.services.UserService;
import ru.kpfu.itis.khabibullin.utils.converters.DtoToJsonConverter;
import ru.kpfu.itis.khabibullin.utils.enums.StateOfOrder;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.kpfu.itis.khabibullin.aspects.ExceptionHandler.handleException;

/**
 * @author Khabibullin Alisher
 */
@Controller
@RequiredArgsConstructor
public class CartController {

    private final DishService dishService;
    private final OrderService orderService;
    private final RestaurantService restaurantService;
    private final UserService userService;

    @GetMapping("/cart")
    public String getCartPage(Model model, @CookieValue(value = "currentOrder", defaultValue = "") String encodedDishesCookie, HttpServletResponse response) {
        List<CartDishDto> dishes = new ArrayList<>();
        if (!encodedDishesCookie.isEmpty()) {
            String decodedOrderCookie = URLDecoder.decode(encodedDishesCookie, StandardCharsets.UTF_8);
            try {
                dishes = (new ObjectMapper().readValue(decodedOrderCookie, OrderDto.class)).getDishes();
            } catch (JsonProcessingException e) {
                handleException(e);
                throw new RuntimeException(e);
            }
        }
        model.addAttribute("dishes", dishes);

        return "cart";
    }


    //TODO: somehow simplify this code because it's kinda bulky
    //TODO: I think this method might just return responseBody
    @PostMapping("/cart")
    public String setCartPage(@RequestBody List<CartDishDto> dishes, HttpServletResponse response, Principal principal) {
        List<String> dishesNames = new ArrayList<>();
        Map<String, Integer> quantities = new HashMap<>();
        int total = 0;
        Long restId;
        for (CartDishDto dish : dishes) {
            dishesNames.add(dish.getName());
            quantities.put(dish.getName(), dish.getQuantity());
            total += dish.getQuantity() * dish.getPrice().intValue();
        }
        List<DishDto> dishDtos = dishService.getDishesByNames(dishesNames);

        restId = dishDtos.get(0).getRestaurantId();

        LocalDateTime currentDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        dishes = dishDtos.stream()
                .map(item -> new CartDishDto(item.getId(), item.getName(), item.getDescription(), item.getCuisine(), item.getPrice(), item.getRestaurantId(), quantities.get(item.getName()), item.getImageUrl()))
                .collect(Collectors.toList());


        orderService.saveOrder(OrderDto.builder()
                .dishes(dishes)
                .total((total))
                .userId(userService.getUserByEmail(principal.getName()).getId())
                .restaurantId((dishDtos.get(0).getRestaurantId()))
                .date((currentDate))
                .state(StateOfOrder.NOT_CONFIRMED)
                .build());

        OrderDto order = orderService.findByUserIdAndRestaurantIdAndTotalAndState(userService.getUserByEmail(principal.getName()),
                restaurantService.getRestaurantById(restId),
                (total),
                StateOfOrder.NOT_CONFIRMED,
                (currentDate));

        order.setDishes(dishes);
        try {
            String jsonCurrentOrderCookie = DtoToJsonConverter.toJsonStringUsingToString(order);
            String encodedCurrentOrderCookie = URLEncoder.encode(jsonCurrentOrderCookie, StandardCharsets.UTF_8);
            Cookie currentOrderCookie = new Cookie("currentOrder", encodedCurrentOrderCookie);
            currentOrderCookie.setPath("/");
            response.addCookie(currentOrderCookie);
        } catch (JsonProcessingException | IllegalAccessException e) {
            handleException(e);
            throw new RuntimeException(e);
        }

        return "redirect:/cart";
    }
}
