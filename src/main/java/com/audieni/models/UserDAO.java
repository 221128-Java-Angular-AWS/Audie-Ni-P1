package com.audieni.models;

import com.audieni.utils.ConnectionManager;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UserDAO {
    public Set<User> getAllUsers() {
        Set<User> users = new HashSet<>();

        try {
            Connection connection = ConnectionManager.getConnection();
            String sql = "SELECT * FROM users;";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getBoolean("manager")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public int createNewUser(User user) {
        try {
            Connection connection = ConnectionManager.getConnection();
            String sql = "INSERT INTO users (email, password) VALUES (?,?);";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());

            return pstmt.executeUpdate();
        } catch (SQLException e) {
//            e.printStackTrace();
            System.err.println(user.getEmail() + " is already registered.");
        }

        return -1;
    }
}
