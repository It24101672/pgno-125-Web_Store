package com.example.web_store.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Order order;

    @NotBlank(message = "Product ID is required")
    @Column(name = "product_id")
    private String productId;

    @NotBlank(message = "Product name is required")
    @Column(name = "product_name")
    private String productName;

    @NotNull(message = "Product price is required")
    @Positive(message = "Product price must be positive")
    @Column(name = "product_price")
    private Double productPrice;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "seller_id")
    private String sellerId;

    public OrderItem() {}

    public OrderItem(Order order, String productId, String productName, Double productPrice, 
                     Integer quantity, String productImage, String sellerId) {
        this.id = UUID.randomUUID().toString();
        this.order = order;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.productImage = productImage;
        this.sellerId = sellerId;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Double getProductPrice() { return productPrice; }
    public void setProductPrice(Double productPrice) { this.productPrice = productPrice; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getProductImage() { return productImage; }
    public void setProductImage(String productImage) { this.productImage = productImage; }

    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }

    public Double getSubtotal() {
        return productPrice * quantity;
    }
}