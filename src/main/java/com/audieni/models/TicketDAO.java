package com.audieni.models;

import com.audieni.utils.ConnectionManager;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class TicketDAO {
    public Set<Ticket> getAllTickets() {
        Set<Ticket> tickets = new HashSet<>();

        try {
            Connection connection = ConnectionManager.getConnection();
            String sql = "SELECT * FROM tickets;";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

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

    public int createNewTicket(User user, Ticket ticket) {
        try {
            Connection connection = ConnectionManager.getConnection();
            String sql = "INSERT INTO tickets (user_id, amount, description) VALUES (?,?,?);";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user.getId());
            pstmt.setDouble(2, ticket.getAmount());
            pstmt.setString(3, ticket.getDescription());

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
