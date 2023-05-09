/*
 * *
 *  Copyright (c) 2023.
 *  * @author Khabibullin Alisher (Alalaq)
 *  *
 *  * All rights are reserved by ITIS institute.
 *
 */

package ru.kpfu.itis.khabibullin.controllers.MVC;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.khabibullin.dto.CartDishDto;
import ru.kpfu.itis.khabibullin.dto.OrderDto;
import ru.kpfu.itis.khabibullin.dto.ReviewDto;
import ru.kpfu.itis.khabibullin.services.OrderService;
import ru.kpfu.itis.khabibullin.services.ReviewService;
import ru.kpfu.itis.khabibullin.utils.enums.StateOfOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final OrderService orderService;

    @GetMapping("/review/{orderId}/{userId}")
    public String review(@PathVariable Long orderId,
                         @PathVariable Long userId,
                         Model model) {
        try {
            reviewService.getReviewByOrderId(orderId);
            //TODO: handle
            return "redirect:/homepage";
        } catch (NullPointerException exc){
            OrderDto order = orderService.getOrderById(orderId);
            if (!Objects.equals(order.getUserId(), userId) || !order.getState().equals(StateOfOrder.DELIVERED)) {
                //TODO: handle
                return "redirect:/homepage";
            } else {
                model.addAttribute("order", order);
                return "review";
            }
        }
    }

    @PostMapping("/review/{orderId}/{userId}")
    public String postReview(@RequestParam Map<String, String> formParams,
                             @PathVariable Long orderId,
                             @PathVariable Long userId) {

        OrderDto order = orderService.getOrderById(orderId);
        List<ReviewDto> reviews = new ArrayList<>();

        for (CartDishDto dish : order.getDishes()) {
            Long dishId = dish.getId();
            Long restaurantId = dish.getRestaurantId();
            int rating = Integer.parseInt(formParams.get("ratings[" + dishId + "]"));
            String comment = formParams.get("comments[" + dishId + "]");
            ReviewDto review = ReviewDto.builder()
                    .rating(rating)
                    .comment(comment)
                    .dish(CartDishDto.toDish(dish))
                    .userId(userId)
                    .restaurantId(restaurantId)
                    .orderId(orderId)
                    .build();
            reviews.add(review);
        }


        reviewService.saveAll(reviews);

        order.setState(StateOfOrder.REVIEWED);
        orderService.saveOrder(order);

        return "redirect:/restaurants";
    }
}
