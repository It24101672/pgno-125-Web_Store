package com.example.web_store.service;

import com.example.web_store.model.Rating;
import com.example.web_store.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

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

    public void deleteRating(String id) {
        ratingRepository.deleteById(id);
    }
}