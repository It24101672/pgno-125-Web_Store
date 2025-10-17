package com.example.web_store.repository;

import com.example.web_store.model.SellerMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SellerMessageRepository extends JpaRepository<SellerMessage, String> {
    List<SellerMessage> findBySellerIdAndStatus(String sellerId, String status);
    List<SellerMessage> findByProductIdAndStatus(String productId, String status);
    List<SellerMessage> findByStatus(String status);
}