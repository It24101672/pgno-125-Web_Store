package com.example.web_store.service;

import com.example.web_store.model.*;
import com.example.web_store.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Order processCheckout(String customerId, String shippingName, String shippingAddress, 
                                String shippingCity, String postalCode, String phoneNumber) {
        
        // Get customer's cart items
        List<CartItem> cartItems = cartItemRepository.findByUserId(customerId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Validate stock and calculate total
        double totalAmount = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            Optional<Product> productOptional = productRepository.findById(cartItem.getProductId());
            if (productOptional.isEmpty()) {
                throw new RuntimeException("Product not found: " + cartItem.getProductId());
            }

            Product product = productOptional.get();
            
            // Check stock availability
            if (product.getProductCount() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getProductName() + 
                                         ". Available: " + product.getProductCount() + 
                                         ", Requested: " + cartItem.getQuantity());
            }

            totalAmount += cartItem.getProductPrice() * cartItem.getQuantity();
        }

        // Create order
        Order order = new Order(customerId, totalAmount, shippingName, shippingAddress, 
                               shippingCity, postalCode, phoneNumber);
        order = orderRepository.save(order);

        // Create order items and update inventory
        for (CartItem cartItem : cartItems) {
            Product product = productRepository.findById(cartItem.getProductId()).get();
            
            // Create order item
            OrderItem orderItem = new OrderItem(order, cartItem.getProductId(), 
                                               cartItem.getProductName(), cartItem.getProductPrice(),
                                               cartItem.getQuantity(), cartItem.getImage(), 
                                               product.getSellerId());
            orderItems.add(orderItem);
            
            // Update product inventory
            product.setProductCount(product.getProductCount() - cartItem.getQuantity());
            productRepository.save(product);
        }

        // Save order items
        orderItemRepository.saveAll(orderItems);

        // Clear customer's cart
        cartItemRepository.deleteAll(cartItems);

        return order;
    }

    public List<Order> getCustomerOrders(String customerId) {
        return orderRepository.findByCustomerIdOrderByOrderDateDesc(customerId);
    }

    public Optional<Order> getOrderById(String orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> getSellerOrders(String sellerId) {
        return orderRepository.findOrdersBySellerId(sellerId);
    }

    public Order updateOrderStatus(String orderId, String status) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new RuntimeException("Order not found: " + orderId);
        }

        Order order = orderOptional.get();
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}