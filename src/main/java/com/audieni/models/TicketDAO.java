package com.audieni.models;

import com.audieni.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class TicketDAO {
    private final Connection connection;

    public TicketDAO() {
        this.connection = ConnectionManager.getConnection();
    }

    /**
     * Create a ticket into the database.
     *
     * @param ticket Ticket Object
     */
    public void create(Ticket ticket) {
        try {
            String sql = "INSERT INTO tickets (user_id, amount, description) VALUES (?,?,?);";
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setInt(1, ticket.getUserId());
            pstmt.setDouble(2, ticket.getAmount());
            pstmt.setString(3, ticket.getDescription());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns all tickets (as a Set) belonging to every user in the database.
     *
     * @return tickets Set of Ticket Objects belonging to every user.
     */
    public Set<Ticket> selectAllTickets() {
        Set<Ticket> tickets = new HashSet<>();

        try {
            String sql = "SELECT * FROM tickets;";
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getString("status")
                );
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    /**
     * Returns all tickets (as a Set) belonging to all users by their status.
     *
     * @param status Status of a Ticket Object.
     * @return tickets Set of Ticket Objects belonging to all users with a certain status.
     */
    public Set<Ticket> selectAllTicketsByStatus(String status) {
        Set<Ticket> tickets = new HashSet<>();

        try {
            String sql = "SELECT * FROM tickets WHERE lower(status) = ?;";
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getString("status")
                );
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    /**
     * Returns all tickets (as a Set) belonging to a user by their user ID.
     *
     * @param userId ID of a User Object.
     * @return tickets Set of Ticket Objects belonging a user based on their user ID.
     */
    public Set<Ticket> selectAllTicketsByUserID(Integer userId) {
        Set<Ticket> tickets = new HashSet<>();

        try {
            String sql = "SELECT * FROM tickets WHERE user_id = ?;";
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getString("status")
                );
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    /**
     * Returns a ticket based on their ticket ID.
     *
     * @param id Ticket ID of Ticket Object.
     * @return ticket Ticket Object based on their ticket ID.
     */
    public Ticket selectTicketByID(Integer id) {
        try {
            String sql = "SELECT * FROM tickets WHERE id = ?;";
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Ticket(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Returns a set of tickets based on its user ID and its status.
     *
     * @param userId User ID of User Object.
     * @param status Status of Ticket Object.
     * @return tickets Set of Ticket Objects belonging to a user and based on its status.
     */
    public Set<Ticket> selectTicketsByUserIDStatus(Integer userId, String status) {
        Set<Ticket> tickets = new HashSet<>();

        try {
            String sql = "SELECT * FROM tickets WHERE user_id = ? AND lower(status) = ?;";
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setString(2, status);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getString("status")
                );
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    /**
     * Returns a ticket based on its ticket ID and its status.
     *
     * @param ticketId Ticket ID of Ticket Object.
     * @param status Status of Ticket Object.
     * @return ticket Ticket Object based on ticket ID and its status.
     */
    public Ticket selectTicketByTicketIDStatus(Integer ticketId, String status) {
        try {
            String sql = "SELECT * FROM tickets WHERE id = ? AND lower(status) = ?;";
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setInt(1, ticketId);
            pstmt.setString(2, status);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getString("status")
                );
                return ticket;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /** Updates a ticket in the database to the new Ticket Object based on the ticket ID.
     *
     * @param ticket Ticket Object.
     */
    public void update(Ticket ticket) {
        try {
            String sql = "UPDATE tickets SET user_id = ?, amount = ?, description = ?, status = ? WHERE id = ?;";
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setInt(1, ticket.getUserId());
            pstmt.setDouble(2, ticket.getAmount());
            pstmt.setString(3, ticket.getDescription());
            pstmt.setString(4, ticket.getStatus());
            pstmt.setInt(5, ticket.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete a ticket from the database based on the ticket ID from passed Ticket Object.
     *
     * @param ticket Ticket Object.
     */
    public void delete(Ticket ticket) {
        try {
            String sql = "DELETE FROM tickets WHERE id = ?;";
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setInt(1, ticket.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
