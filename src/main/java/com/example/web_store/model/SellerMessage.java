package com.example.web_store.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "seller_messages")
public class SellerMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    @Column(name = "message_text", length = 2000)
    private String messageText;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "created_time")
    private LocalTime createdTime;

    @Column(name = "status")
    private String status = "ACTIVE"; // ACTIVE, DELETED

    // Constructors
    public SellerMessage() {}

    public SellerMessage(String productId, String sellerId, String messageText) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.messageText = messageText;
        this.createdDate = LocalDate.now();
        this.createdTime = LocalTime.now();
        this.status = "ACTIVE";
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }

    public String getMessageText() { return messageText; }
    public void setMessageText(String messageText) { this.messageText = messageText; }

    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }

    public LocalTime getCreatedTime() { return createdTime; }
    public void setCreatedTime(LocalTime createdTime) { this.createdTime = createdTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}