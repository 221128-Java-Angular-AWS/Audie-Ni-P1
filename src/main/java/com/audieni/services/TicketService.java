package com.audieni.services;

import com.audieni.models.Ticket;
import com.audieni.models.TicketDAO;

public class TicketService {
    private final TicketDAO dao;

    public TicketService(TicketDAO dao) {
        this.dao = dao;
    }

    public void createTicket(Ticket ticket) {
        dao.create(ticket);
    }

    public void viewTicket(Ticket ticket) {
        
    }

    public void deleteTicket(Ticket ticket) {
        dao.delete(ticket);
    }
}
