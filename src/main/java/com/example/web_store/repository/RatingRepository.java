package com.example.web_store.repository;

import com.example.web_store.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, String> {
    List<Rating> findByProductId(String productId);
    List<Rating> findByOrderId(String orderId);

    @Query("SELECT r FROM Rating r WHERE r.productId IN (SELECT p.id FROM Product p WHERE p.sellerId = :sellerId)")
    List<Rating> findRatingsForSellerProducts(@Param("sellerId") String sellerId);

    @Query("SELECT r FROM Rating r ORDER BY r.submitDate DESC, r.submitTime DESC")
    List<Rating> findAllOrderByDateDesc();

    boolean existsByOrderIdAndProductId(String orderId, String productId);
    
    boolean existsByCustomerIdAndProductIdAndOrderId(String customerId, String productId, String orderId);
    
    boolean existsByCustomerIdAndProductId(String customerId, String productId);
}