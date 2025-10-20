package com.example.web_store.controller;

import com.example.web_store.model.Rating;
import com.example.web_store.service.RatingService;
import com.example.web_store.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;
    
    @Autowired
    private OrderItemRepository orderItemRepository;

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
    public ResponseEntity<?> createRating(@RequestBody Rating rating, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Authentication required"));
        }
        
        String customerId = authentication.getName();
        
        
        boolean hasPurchased = orderItemRepository.hasCustomerPurchasedProduct(customerId, rating.getProductId());
        if (!hasPurchased) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("error", "You can only rate products you have purchased"));
        }
        
        
        rating.setCustomerId(customerId);
        
        Rating savedRating = ratingService.saveRating(rating);
        return ResponseEntity.ok(savedRating);
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
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/eligible-products")
    public ResponseEntity<List<Map<String, Object>>> getEligibleProductsForRating(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        String customerId = authentication.getName();
        List<Map<String, Object>> eligibleProducts = ratingService.getEligibleProductsForRating(customerId);
        return ResponseEntity.ok(eligibleProducts);
    }
}