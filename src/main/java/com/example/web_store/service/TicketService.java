package com.example.web_store.service;

import com.example.web_store.model.Ticket;
import com.example.web_store.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(String ticketID) {
        return ticketRepository.findById(ticketID).orElse(null);
    }

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(String ticketID, Ticket ticketDetails) {
        Ticket ticket = ticketRepository.findById(ticketID).orElse(null);
        if (ticket != null) {
            // Update ticket fields as needed
            ticket.setDescription(ticketDetails.getDescription());
            // Add other fields to update
            return ticketRepository.save(ticket);
        }
        return null;
    }

    public void deleteTicket(String ticketID) {
        ticketRepository.deleteById(ticketID);
    }
}