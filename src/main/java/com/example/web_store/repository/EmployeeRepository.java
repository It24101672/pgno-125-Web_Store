package com.example.web_store.repository;

import com.example.web_store.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    boolean existsByEmail(String email);
}