package com.example.web_store.service;

import com.example.web_store.model.Product;
import com.example.web_store.model.Rating;
import com.example.web_store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MarketingExecutiveService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RatingService ratingService;

    public Map<String, Object> getProductWithRatings(String productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            List<Rating> ratings = ratingService.getRatingsByProductId(productId);

            Map<String, Object> result = new HashMap<>();
            result.put("product", product);
            result.put("ratings", ratings);

            // Calculate average rating
            if (!ratings.isEmpty()) {
                double average = ratings.stream()
                        .mapToInt(Rating::getRatingValue)
                        .average()
                        .orElse(0.0);
                result.put("averageRating", Math.round(average * 10.0) / 10.0);
            } else {
                result.put("averageRating", 0.0);
            }

            return result;
        }
        return null;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}