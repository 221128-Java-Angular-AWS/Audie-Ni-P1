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

        for (Ticket t : tickets) {
            System.out.println(t);
        }

        User user = new User(2, "mary@gmail.com", "password2", false);
        Ticket ticket = new Ticket(5678.90, "I wish I did not spend money.");
        ticketDao.createNewTicket(user, ticket);
    }
}