package com.example.web_store.repository;

import com.example.web_store.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    boolean existsByProductID(String productID);
}