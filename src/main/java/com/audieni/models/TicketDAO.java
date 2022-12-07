package com.audieni.models;

import com.audieni.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
