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

    /**
     * Displays all pending tickets belonging to the logged-in user.
     *
     * @param ctx Context Object, for handling http-request, which contains the servlet request and response.
     */
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

    /**
     * Displays all tickets (regardless of status) belonging to the logged-in user.
     *
     * @param ctx Context Object, for handling http-request, which contains the servlet request and response.
     */
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

    /**
     * Submits a ticket (belonging to the logged-in user), which is defaulted pending. It requires a
     * request body with an amount and description of the reimbursement for submission.
     *
     * @param ctx Context Object, for handling http-request, which contains the servlet request and response.
     */
    public static void ticketsSubmit(Context ctx) {
        String sessionId = ctx.cookie("session_id");
        if (sessionId != null) {
            User user = userService.getUserBySessionId(sessionId);
            try {
                Ticket ticket = ctx.bodyAsClass(Ticket.class);
                ticket.setUserId(user.getId());
                ticketService.createTicket(ticket);
                ctx.json(ticket);
                ctx.status(200);
            } catch (Exception e) {
                ctx.result("Not enough information for ticket submission.");
                ctx.status(401);
            }
        } else {
            ctx.result("Not logged in.");
            ctx.status(401);
        }
    }

    /**
     * Displays certain tickets (based on status) belonging to the logged-in user.
     *
     * @param ctx Context Object, for handling http-request, which contains the servlet request and response.
     */
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

    /**
     * Display all pending tickets belonging to all users in the system to a logged-in user with eligible
     * permissions (manager role). If valid form parameters are provided, managers can approve or deny tickets
     * through their ticket ID.
     *
     * @param ctx Context Object, for handling http-request, which contains the servlet request and response.
     */
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

    /**
     * Displays all tickets (regardless of status) belonging to all users in the system to a logged-in user
     * with eligible permissions (manager role).
     *
     * @param ctx Context Object, for handling http-request, which contains the servlet request and response.
     */
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

    /**
     * Displays certain tickets (based on status) belonging to all users in the system to a logged-in user
     * with eligible permissions (manager role).
     *
     * @param ctx Context Object, for handling http-request, which contains the servlet request and response.
     */
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
