package com.example.web_store.controller;

import com.example.web_store.model.Rating;
import com.example.web_store.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public List<Rating> getAllRatings() {
        return ratingService.getAllRatings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rating> getRatingById(@PathVariable String id) {
        Optional<Rating> rating = ratingService.getRatingById(id);
        return rating.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Rating createRating(@RequestBody Rating rating) {
        return ratingService.saveRating(rating);
    }

    @GetMapping("/product/{productId}")
    public List<Rating> getRatingsByProduct(@PathVariable String productId) {
        return ratingService.getRatingsByProductId(productId);
    }

    @GetMapping("/check-rating")
    public ResponseEntity<Boolean> checkIfRated(@RequestParam String orderId, @RequestParam String productId) {
        boolean hasRated = ratingService.hasCustomerRatedOrderProduct(orderId, productId);
        return ResponseEntity.ok(hasRated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable String id) {
        ratingService.deleteRating(id);
        return ResponseEntity.ok().build();
    }
}