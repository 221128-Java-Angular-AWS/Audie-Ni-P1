package com.audieni.services;

import com.audieni.models.Ticket;
import com.audieni.models.TicketDAO;

import java.util.Set;

public class TicketService {
    private final TicketDAO dao;

    public TicketService(TicketDAO dao) {
        this.dao = dao;
    }

    public void createTicket(Ticket ticket) {
        dao.create(ticket);
    }

    public void updateTicket(Ticket ticket) {
        dao.update(ticket);
    }

    public Set<Ticket> viewAllTickets() {
        return dao.selectAllTicketsByUserID();
    }

    public Set<Ticket> viewAllTicketsByStatus(String status) {
        return dao.selectAllTicketsByStatus(status);
    }

    public Ticket viewAllTicketById(Integer id) {
        return dao.selectTicketByID(id);
    }

    public Set<Ticket> viewTicketsById(Integer userId, String status) {
        return dao.selectTicketsByUserIDStatus(userId, status);
    }

    public Ticket viewTicketById(Integer ticketId, String status) {
        return dao.selectTicketByTicketIDStatus(ticketId, status);
    }

    public Set<Ticket> viewAllTickets(Integer userId) {
        return dao.selectAllTicketsByUserID(userId);
    }

    public Set<Ticket> viewPendingTickets() {
        return dao.selectAllTicketsByStatus("Pending");
    }

    public void deleteTicket(Ticket ticket) {
        dao.delete(ticket);
    }
}
