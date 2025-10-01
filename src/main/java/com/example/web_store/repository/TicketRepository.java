package com.example.web_store.repository;

import com.example.web_store.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepository extends MongoRepository<Ticket, String> {

    // Custom queries can be added here if needed
}
