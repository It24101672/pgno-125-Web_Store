package com.example.web_store.repository;

import com.example.web_store.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    
    // Find orders by customer ID (email)
    List<Order> findByCustomerIdOrderByOrderDateDesc(String customerId);
    
    // Find orders by status
    List<Order> findByStatus(String status);
    
    // Find orders by customer and status
    List<Order> findByCustomerIdAndStatus(String customerId, String status);
    
    // Find orders containing products from a specific seller
    @Query("SELECT DISTINCT o FROM Order o JOIN o.orderItems oi WHERE oi.sellerId = :sellerId ORDER BY o.orderDate DESC")
    List<Order> findOrdersBySellerId(@Param("sellerId") String sellerId);
    
    // Find orders containing a specific product
    @Query("SELECT DISTINCT o FROM Order o JOIN o.orderItems oi WHERE oi.productId = :productId ORDER BY o.orderDate DESC")
    List<Order> findOrdersByProductId(@Param("productId") String productId);
}