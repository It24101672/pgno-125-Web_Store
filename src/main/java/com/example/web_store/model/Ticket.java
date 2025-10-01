package com.example.web_store.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ticket")
public class Ticket {
    private String ticketID;
    private String description;

    // Getters and Setters
    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
