package com.example.web_store.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private String id;

    @NotBlank(message = "Customer ID is required")
    @Column(name = "customer_id")
    private String customerId;

    @NotNull(message = "Order date is required")
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @NotNull(message = "Total amount is required")
    @Positive(message = "Total amount must be positive")
    @Column(name = "total_amount")
    private Double totalAmount;

    @NotBlank(message = "Order status is required")
    @Column(name = "status")
    private String status; // PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED

    @NotBlank(message = "Shipping name is required")
    @Column(name = "shipping_name")
    private String shippingName;

    @NotBlank(message = "Shipping address is required")
    @Column(name = "shipping_address", length = 500)
    private String shippingAddress;

    @NotBlank(message = "Shipping city is required")
    @Column(name = "shipping_city")
    private String shippingCity;

    @NotBlank(message = "Postal code is required")
    @Column(name = "postal_code")
    private String postalCode;

    @NotBlank(message = "Phone number is required")
    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    public Order() {}

    public Order(String customerId, Double totalAmount, String shippingName, String shippingAddress, 
                 String shippingCity, String postalCode, String phoneNumber) {
        this.id = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.orderDate = LocalDateTime.now();
        this.totalAmount = totalAmount;
        this.status = "PENDING";
        this.shippingName = shippingName;
        this.shippingAddress = shippingAddress;
        this.shippingCity = shippingCity;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getShippingName() { return shippingName; }
    public void setShippingName(String shippingName) { this.shippingName = shippingName; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getShippingCity() { return shippingCity; }
    public void setShippingCity(String shippingCity) { this.shippingCity = shippingCity; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
}