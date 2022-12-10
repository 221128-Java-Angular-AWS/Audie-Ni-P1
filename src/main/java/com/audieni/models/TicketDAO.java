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

    public Set<Ticket> getAllTickets() {
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

    public Set<Ticket> getUserTickets(Integer id, String status) {
        Set<Ticket> tickets = new HashSet<>();

        try {
            String sql = "SELECT * FROM tickets WHERE id = ? AND status = ?;";
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

    public void update(Ticket ticket) {
        try {
            String sql = "UPDATE tickets SET user_id = ?, amount = ?, description = ?, status = ? WHERE id = ?;";
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setInt(1, ticket.getUserId());
            pstmt.setDouble(2, ticket.getAmount());
            pstmt.setString(3, ticket.getDescription());
            pstmt.setString(4, ticket.getStatus());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
