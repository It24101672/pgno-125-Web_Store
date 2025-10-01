package com.example.web_store.controller;

import com.example.web_store.model.Ticket;
import com.example.web_store.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{ticketID}")
    public Ticket getTicket(@PathVariable String ticketID) {
        return ticketService.getTicketById(ticketID);
    }

    @PostMapping("/")
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);
    }

    @PutMapping("/{ticketID}")
    public Ticket updateTicket(@PathVariable String ticketID, @RequestBody Ticket ticket) {
        return ticketService.updateTicket(ticketID, ticket);
    }

    @DeleteMapping("/{ticketID}")
    public void deleteTicket(@PathVariable String ticketID) {
        ticketService.deleteTicket(ticketID);
    }
}
