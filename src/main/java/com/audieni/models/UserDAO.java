package com.audieni.models;

import com.audieni.exceptions.ExistingUserException;
import com.audieni.exceptions.IncorrectPasswordException;
import com.audieni.exceptions.UserNotFoundException;
import com.audieni.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class UserDAO {
    private final Connection connection;

    public UserDAO() {
        this.connection = ConnectionManager.getConnection();
    }

    /**
     * Create a new user in the database with User Object.
     *
     * @param user User Object.
     */
    public void create(User user) {
        try {
            String sql = "INSERT INTO users (email, password) VALUES (?,?);";
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if the user email and password is valid in the database.
     *
     * @param email Email of user account.
     * @param password Password of user account.
     * @return user User Object containing user information fitting the email and password given.
     * @throws UserNotFoundException Exception thrown if the user is not found in the database.
     * @throws IncorrectPasswordException Exception thrown if the user's password is incorrect.
     */
    public User authenticate(String email, String password) throws UserNotFoundException, IncorrectPasswordException {
        try {
            String sql = "SELECT * FROM users WHERE email = ?;";
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                throw new UserNotFoundException("This user was not found.");
            }

            User user = new User(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getBoolean("manager")
            );

            if (user.getPassword().equals(password)) {
                return user;
            }

            throw new IncorrectPasswordException("This password is incorrect.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Checks if a user account already exists with the email provided by the user.
     *
     * @param email Email of user account.
     * @return True or False depending on if the user email already exists in the database.
     * @throws ExistingUserException Exception thrown if the user email already has an account.
     */
    public boolean checkExistingAccount(String email) throws ExistingUserException {
        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                throw new ExistingUserException("This user already exists.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Returns a set of User Objects for all users in the system.
     *
     * @return users Set of User Objects of all users in the database.
     */
    public Set<User> selectAllUsers() {
        Set<User> users = new HashSet<>();

        try {
            String sql = "SELECT * FROM users;";
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

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

    /**
     * Return a User Object based on the user's ID.
     *
     * @param id User ID of User Object.
     * @return user User Object from the database based on user's ID.
     */
    public User selectUserByID(Integer id) {
        try {
            String sql = "SELECT * FROM users WHERE id = ?;";
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getBoolean("manager")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Returns a user in the database based on the session ID.
     *
     * @param sessionId Session ID of User Object
     * @return user User Object based on the session ID.
     */
    public User selectUserBySessionID(String sessionId) {
        try {
            String sql = "SELECT * FROM users WHERE session_id = ?;";
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setString(1, sessionId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getBoolean("manager"),
                        rs.getString("session_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Updates existing user in the database based on a new User Object.
     *
     * @param user User Object.
     */
    public void update(User user) {
        try {
            String sql = "UPDATE users SET email = ?, password = ?, manager = ?, session_id = ? WHERE id = ?;";
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setBoolean(3, user.isManager());
            pstmt.setString(4, user.getSessionId());
            pstmt.setInt(5, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete a user from the database based on a user ID.
     *
     * @param user User Object.
     */
    public void delete(User user) {
        try {
            String sql = "DELETE FROM users WHERE user_id = ?;";
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            pstmt.setInt(1, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

