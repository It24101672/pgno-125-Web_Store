package com.example.web_store.repository;

import com.example.web_store.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
    
    // Find order items by order ID
    List<OrderItem> findByOrderId(String orderId);
    
    // Find order items by product ID
    List<OrderItem> findByProductId(String productId);
    
    // Find order items by seller ID
    List<OrderItem> findBySellerIdOrderByOrderOrderDateDesc(String sellerId);
    
    // Find order items for a specific customer and product (for rating validation)
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.customerId = :customerId AND oi.productId = :productId")
    List<OrderItem> findByCustomerIdAndProductId(@Param("customerId") String customerId, @Param("productId") String productId);
    
    // Check if customer has purchased a specific product
    @Query("SELECT COUNT(oi) > 0 FROM OrderItem oi WHERE oi.order.customerId = :customerId AND oi.productId = :productId")
    boolean hasCustomerPurchasedProduct(@Param("customerId") String customerId, @Param("productId") String productId);
    
    @Query("SELECT DISTINCT oi.productId, oi.productName, oi.productImage, o.id FROM OrderItem oi JOIN oi.order o WHERE o.customerId = :customerId ORDER BY o.orderDate DESC")
    List<Object[]> findPurchasedProductsByCustomer(@Param("customerId") String customerId);
}