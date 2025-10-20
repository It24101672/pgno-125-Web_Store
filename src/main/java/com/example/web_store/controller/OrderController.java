package com.example.web_store.controller;

import com.example.web_store.model.Order;
import com.example.web_store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody Map<String, String> checkoutData, 
                                     Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not authenticated"));
            }

            String customerId = authentication.getName();
            String shippingName = checkoutData.get("shippingName");
            String shippingAddress = checkoutData.get("shippingAddress");
            String shippingCity = checkoutData.get("shippingCity");
            String postalCode = checkoutData.get("postalCode");
            String phoneNumber = checkoutData.get("phoneNumber");

            // Validate required fields
            if (shippingName == null || shippingName.trim().isEmpty() ||
                shippingAddress == null || shippingAddress.trim().isEmpty() ||
                shippingCity == null || shippingCity.trim().isEmpty() ||
                postalCode == null || postalCode.trim().isEmpty() ||
                phoneNumber == null || phoneNumber.trim().isEmpty()) {
                
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "All shipping fields are required"));
            }

            Order order = orderService.processCheckout(customerId, shippingName, 
                                                      shippingAddress, shippingCity, 
                                                      postalCode, phoneNumber);

            return ResponseEntity.ok(Map.of(
                "message", "Order placed successfully",
                "orderId", order.getId(),
                "totalAmount", order.getTotalAmount()
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/my-orders")
    public ResponseEntity<?> getMyOrders(Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not authenticated"));
            }

            String customerId = authentication.getName();
            List<Order> orders = orderService.getCustomerOrders(customerId);
            return ResponseEntity.ok(orders);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable String orderId, 
                                           Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not authenticated"));
            }

            Optional<Order> orderOptional = orderService.getOrderById(orderId);
            if (orderOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Order order = orderOptional.get();
            String customerId = authentication.getName();

            // Check if the order belongs to the authenticated customer
            if (!order.getCustomerId().equals(customerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Access denied"));
            }

            return ResponseEntity.ok(order);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders(Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not authenticated"));
            }

            List<Order> orders = orderService.getAllOrders();
            return ResponseEntity.ok(orders);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/seller-orders")
    public ResponseEntity<?> getSellerOrders(Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not authenticated"));
            }

            String sellerId = authentication.getName();
            List<Order> orders = orderService.getSellerOrders(sellerId);
            return ResponseEntity.ok(orders);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String orderId,
                                              @RequestBody Map<String, String> statusData,
                                              Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not authenticated"));
            }

            String status = statusData.get("status");
            if (status == null || status.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Status is required"));
            }

            Order updatedOrder = orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok(Map.of(
                "message", "Order status updated successfully",
                "order", updatedOrder
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }
}