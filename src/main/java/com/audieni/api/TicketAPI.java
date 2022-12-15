package com.audieni.api;

import com.audieni.models.Ticket;
import com.audieni.models.TicketDAO;
import com.audieni.models.User;
import com.audieni.models.UserDAO;
import com.audieni.services.TicketService;
import com.audieni.services.UserService;
import io.javalin.http.Context;

import java.util.Set;

public class TicketAPI {
    static UserService userService = new UserService(new UserDAO());
    static TicketService ticketService = new TicketService(new TicketDAO());

    public static void tickets(Context ctx) {
        String sessionId = ctx.cookie("session_id");
        if (sessionId != null) {
            User user = userService.getUserBySessionId(sessionId);
            Set<Ticket> tickets = ticketService.viewTicketsById(user.getId(), "pending");
            ctx.json(tickets);
            ctx.status(200);
        } else {
            ctx.result("Not logged in.");
            ctx.status(401);
        }
    }

    public static void ticketsAll(Context ctx) {
        String sessionId = ctx.cookie("session_id");
        if (sessionId != null) {
            User user = userService.getUserBySessionId(sessionId);
            Set<Ticket> tickets = ticketService.viewAllTickets(user.getId());
            ctx.json(tickets);
            ctx.status(200);
        } else {
            ctx.result("Not logged in.");
            ctx.status(401);
        }
    }

    public static void ticketsSubmit(Context ctx) {
        String sessionId = ctx.cookie("session_id");
        if (sessionId != null) {
            User user = userService.getUserBySessionId(sessionId);
            Ticket ticket = ctx.bodyAsClass(Ticket.class);
            ticket.setUserId(user.getId());
            ticketService.createTicket(ticket);
            ctx.json(ticket);
            ctx.status(200);
        } else {
            ctx.result("Not logged in.");
            ctx.status(401);
        }
    }

    public static void ticketsStatus(Context ctx) {
        String sessionId = ctx.cookie("session_id");
        String status = ctx.pathParam("status").toLowerCase();
        if (sessionId != null && (status.equalsIgnoreCase("pending") ||
                status.equalsIgnoreCase("approved") || status.equalsIgnoreCase("denied"))) {
            User user = userService.getUserBySessionId(sessionId);
            Set<Ticket> tickets = ticketService.viewTicketsById(user.getId(), status);
            ctx.json(tickets);
            ctx.status(200);
        } else {
            if (sessionId == null) {
                ctx.result("Not logged in.");
            } else {
                ctx.result("Invalid status.");
            }
        }
    }

    public static void adminTickets(Context ctx) {
        String sessionId = ctx.cookie("session_id");
        String ticketId = ctx.queryParam("ticket_id");
        String status = ctx.queryParam("status");

        if (sessionId != null) {
            User user = userService.getUserBySessionId(sessionId);
            if (user.isManager()) {
                if (ticketId != null && status != null) {
                    Ticket ticket = ticketService.viewTicketById(Integer.parseInt(ticketId), "pending");
                    if (ticket != null) {
                        ticket.setStatus(status.substring(0, 1).toUpperCase() + status.substring(1));
                        ticketService.updateTicket(ticket);
                        ctx.json(ticket);
                        ctx.status(200);
                    } else {
                        ctx.result("Ticket is not pending.");
                        ctx.status(400);
                    }
                } else if (ticketId != null) {
                    Ticket ticket = ticketService.viewAllTicketById(Integer.parseInt(ticketId));
                    ctx.json(ticket);
                    ctx.status(200);
                } else {
                    Set<Ticket> tickets = ticketService.viewAllTicketsByStatus("pending");
                    ctx.json(tickets);
                    ctx.status(200);
                }
            } else {
                ctx.result("Invalid permissions.");
                ctx.status(401);
            }
        } else {
            ctx.result("Not logged in.");
            ctx.status(401);
        }
    }

    public static void adminTicketsAll(Context ctx) {
        String sessionId = ctx.cookie("session_id");
        if (sessionId != null) {
            User user = userService.getUserBySessionId(sessionId);
            if (user.isManager()) {
                Set<Ticket> tickets = ticketService.viewAllTickets();
                ctx.json(tickets);
                ctx.status(200);
            } else {
                ctx.result("Invalid permissions.");
                ctx.status(401);
            }
        } else {
            ctx.result("Not logged in.");
            ctx.status(401);
        }
    }

    public static void adminTicketsAllStatus(Context ctx) {
        String sessionId = ctx.cookie("session_id");
        String status = ctx.pathParam("status").toLowerCase();
        if (sessionId != null && (status.equalsIgnoreCase("pending") ||
                status.equalsIgnoreCase("approved") || status.equalsIgnoreCase("denied"))) {
            User user = userService.getUserBySessionId(sessionId);
            if (user.isManager()) {
                Set<Ticket> tickets = ticketService.viewAllTicketsByStatus(status);
                ctx.json(tickets);
                ctx.status(200);
            } else {
                ctx.result("Invalid permissions.");
                ctx.status(401);
            }
        } else {
            if (sessionId == null) {
                ctx.result("Not logged in.");
            } else {
                ctx.result("Invalid status.");
            }
            ctx.status(401);
        }
    }
}
