package ru.kpfu.itis.khabibullin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.khabibullin.dto.DishDto;
import ru.kpfu.itis.khabibullin.dto.ReviewDto;
import ru.kpfu.itis.khabibullin.services.DishService;

@RestController
@RequestMapping("/dishes")
@RequiredArgsConstructor
public class DishesController {

    private final DishService dishService;


    @GetMapping("/{id}")
    public DishDto getDishById(@PathVariable Long id) {
        return (dishService.getDishById(id));
    }

//    @PostMapping("/{id}/reviews")
//    public ReviewDto addReview(@PathVariable Long id, @RequestBody ReviewDto reviewDto) {
//      //  return dishService.addReview(id, reviewDto);
//    }
}
