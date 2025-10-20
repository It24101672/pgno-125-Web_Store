package com.example.web_store.service;

import com.example.web_store.model.Rating;
import com.example.web_store.repository.RatingRepository;
import com.example.web_store.repository.OrderItemRepository;
import com.example.web_store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<Rating> getAllRatings() {
        return ratingRepository.findAllOrderByDateDesc();
    }

    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    public Optional<Rating> getRatingById(String id) {
        return ratingRepository.findById(id);
    }

    public List<Rating> getRatingsByProductId(String productId) {
        return ratingRepository.findByProductId(productId);
    }

    public boolean hasCustomerRatedOrderProduct(String orderId, String productId) {
        return ratingRepository.existsByOrderIdAndProductId(orderId, productId);
    }
    
    public boolean hasCustomerRatedProductInOrder(String customerId, String productId, String orderId) {
        return ratingRepository.existsByCustomerIdAndProductIdAndOrderId(customerId, productId, orderId);
    }
    
    public List<Map<String, Object>> getEligibleProductsForRating(String customerId) {
        // Get all products the customer has purchased
        List<Object[]> purchasedProducts = orderItemRepository.findPurchasedProductsByCustomer(customerId);
        
        return purchasedProducts.stream()
            .filter(product -> {
                String productId = (String) product[0];
                // Check if customer hasn't rated this product yet
                return !ratingRepository.existsByCustomerIdAndProductId(customerId, productId);
            })
            .map(product -> {
                return Map.of(
                    "productId", product[0],
                    "productName", product[1],
                    "productImage", product[2] != null ? product[2] : "",
                    "orderId", product[3]
                );
            })
            .collect(Collectors.toList());
    }

    public void deleteRating(String id) {
        ratingRepository.deleteById(id);
    }
}