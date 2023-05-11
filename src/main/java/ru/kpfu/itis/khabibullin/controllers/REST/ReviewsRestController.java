///*
// * *
// *  Copyright (c) 2023.
// *  * @author Khabibullin Alisher (Alalaq)
// *  *
// *  * All rights are reserved by ITIS institute.
// *
// */
//
//package ru.kpfu.itis.khabibullin.controllers.REST;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//import ru.kpfu.itis.khabibullin.dto.ReviewDto;
//import ru.kpfu.itis.khabibullin.services.ReviewService;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//public class ReviewsRestController {
//
//    private final ReviewService reviewService;
//
//    @GetMapping("/reviews/{restaurantId}")
//    public ResponseEntity<ReviewDto> getReviews(@PathVariable Long restaurantId, Model model){
//        List<ReviewDto> reviews = reviewService.getReviewByRestaurantId(restaurantId);
//
//    }
//}
