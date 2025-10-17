package com.example.web_store.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "rating_value")
    private Integer ratingValue;

    @Column(name = "review_text", length = 1000)
    private String reviewText;

    @Column(name = "submit_date")
    private LocalDate submitDate;

    @Column(name = "submit_time")
    private LocalTime submitTime;

    @Column(name = "customer_id")
    private String customerId;

    // Constructors
    public Rating() {}

    public Rating(String productId, String orderId, Integer ratingValue, String reviewText, String customerId) {
        this.productId = productId;
        this.orderId = orderId;
        this.ratingValue = ratingValue;
        this.reviewText = reviewText;
        this.customerId = customerId;
        this.submitDate = LocalDate.now();
        this.submitTime = LocalTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public Integer getRatingValue() { return ratingValue; }
    public void setRatingValue(Integer ratingValue) { this.ratingValue = ratingValue; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }

    public LocalDate getSubmitDate() { return submitDate; }
    public void setSubmitDate(LocalDate submitDate) { this.submitDate = submitDate; }

    public LocalTime getSubmitTime() { return submitTime; }
    public void setSubmitTime(LocalTime submitTime) { this.submitTime = submitTime; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
}