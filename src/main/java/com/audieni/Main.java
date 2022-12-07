package com.audieni;

import com.audieni.models.Ticket;
import com.audieni.models.TicketDAO;
import com.audieni.models.User;
import com.audieni.models.UserDAO;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        TicketDAO ticketDao = new TicketDAO();
        Set<Ticket> tickets = ticketDao.getAllTickets();

        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }
}