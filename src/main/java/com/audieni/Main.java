package com.audieni;

import com.audieni.api.TicketAPI;
import com.audieni.api.UserAPI;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);

        app.get("/ping", ctx -> {
            ctx.result("Pong!");
            ctx.status(200);
        });

        app.put("/login", UserAPI::login);
        app.put("/logout", UserAPI::logout);
        app.post("/register", UserAPI::register);

        app.get("/tickets", TicketAPI::tickets);
        app.get("/tickets/all", TicketAPI::ticketsAll);
        app.post("/tickets/submit", TicketAPI::ticketsSubmit);
        app.get("/tickets/{status}", TicketAPI::ticketsStatus);

        app.put("/admin/tickets", TicketAPI::adminTickets);
        app.get("/admin/tickets/all", TicketAPI::adminTicketsAll);
        app.get("/admin/tickets/{status}", TicketAPI::adminTicketsAllStatus);

        app.error(404, ctx -> {
            ctx.result("404 Error");
        });
    }
}
