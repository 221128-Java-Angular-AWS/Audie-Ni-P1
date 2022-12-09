package com.audieni.services;

import com.audieni.models.Ticket;
import com.audieni.models.TicketDAO;

public class TicketService {
    private TicketDAO dao;

    public TicketService(TicketDAO dao) {
        this.dao = dao;
    }
}
