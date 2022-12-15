package com.audieni;

import com.audieni.api.TicketAPI;
import com.audieni.api.UserAPI;
import com.audieni.models.Ticket;
import com.audieni.models.TicketDAO;
import com.audieni.models.User;
import com.audieni.models.UserDAO;
import com.audieni.services.TicketService;
import com.audieni.services.UserService;
import io.javalin.Javalin;

import java.util.Base64;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);
        UserService userService = new UserService(new UserDAO());
        TicketService ticketService = new TicketService(new TicketDAO());

        app.get("/ping", ctx -> {
            ctx.result("Pong!");
            ctx.status(200);
        });

        app.get("/login", UserAPI::login);
        app.get("/logout", UserAPI::logout);
        app.post("/register", UserAPI::register);

        app.get("/tickets", TicketAPI::tickets);
        app.get("/tickets/all", TicketAPI::ticketsAll);
        app.post("/tickets/submit", TicketAPI::ticketsSubmit);
        app.get("/tickets/{status}", TicketAPI::ticketsStatus);

        app.post("/admin/tickets", TicketAPI::adminTickets);
        app.get("/admin/tickets/all", TicketAPI::adminTicketsAll);
        app.get("/admin/tickets/{status}", TicketAPI::adminTicketsAllStatus);
    }
}
