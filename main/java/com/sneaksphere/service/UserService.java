package com.sneaksphere.service;

import com.sneaksphere.model.UserModel;
import com.sneaksphere.config.Dbconfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Service class for handling user-related operations such as
 * fetching user details, updating user profiles, and validating user input.
 */
public class UserService {
    private Connection dbConn;
    private boolean isConnectionError = false;

    // Constructor: Initializes the DB connection using Dbconfig class
    public UserService() {
        try {
            dbConn = Dbconfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Connection failed:");
            ex.printStackTrace();
            isConnectionError = true;
        }
    }

    /**
     * Fetches a user from the database by their email address.
     * @param email User's email address
     * @return UserModel if found, otherwise null
     */
    public UserModel getUserByEmail(String email) {
        if (isConnectionError) return null;

        String query = "SELECT * FROM user WHERE userEmail = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                System.out.println(">>> User found in DB: " + email);
                UserModel user = extractUserFromResult(result);
                System.out.println(">>> Extracted userID: " + user.getUserID());
                return user;
            } else {
                System.out.println(">>> No user found with email: " + email);
            }

        } catch (SQLException e) {
            System.err.println(">>> SQL Exception in getUserByEmail()");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Fetches a user from the database by their unique user ID.
     * @param userId User's ID
     * @return UserModel if found, otherwise null
     */
    public UserModel getUserById(int userId) {
        if (isConnectionError) return null;

        String query = "SELECT * FROM user WHERE userID = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return extractUserFromResult(result);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fetches the admin profile from the database (assumes one admin exists).
     * @return UserModel of admin, or null if not found
     */
    public UserModel getAdminProfile() {
        String query = "SELECT * FROM user WHERE Role = 'Admin'";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractUserFromResult(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fetches any one customer profile (used for display or testing).
     * @return UserModel of a customer, or null if none found
     */
    public UserModel getCustomerProfile() {
        String query = "SELECT * FROM user WHERE Role = 'Customer' LIMIT 1";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractUserFromResult(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Helper method to extract user data from a ResultSet object
     * @param result SQL ResultSet
     * @return UserModel with values populated
     */
    private UserModel extractUserFromResult(ResultSet result) throws SQLException {
        UserModel user = new UserModel();
        user.setUserId(result.getInt("UserID"));
        user.setFirstName(result.getString("UserFirstName"));
        user.setLastName(result.getString("UserLastName"));
        user.setEmail(result.getString("UserEmail"));
        user.setPassword(result.getString("UserPassword"));
        user.setPhone(result.getString("UserPhone"));
        user.setAddress(result.getString("UserAddress"));
        user.setRole(result.getString("Role"));
        user.setUserImageURL(result.getString("UserImageURL")); // Image URL for profile picture
        return user;
    }

    /**
     * Updates an existing user profile (mainly used for admin)
     * @param user UserModel containing updated details
     * @return true if update was successful, false otherwise
     */
    public boolean updateUserProfile(UserModel user) {

        // Validate fields before attempting to update
        if (!validateUserProfile(user)) {
            return false;
        }

        if (isConnectionError) return false;

        String query = "UPDATE user SET UserFirstName = ?, UserLastName = ?, UserPhone = ?, UserAddress = ?, UserImageURL = ? WHERE UserID = ?";

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getUserImageURL());
            stmt.setInt(6, user.getUserID());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates user profile fields before update (used in updateUserProfile)
     * @param user UserModel to validate
     * @return true if valid, false if any validation fails
     */
    private boolean validateUserProfile(UserModel user) {
        // Check first name and last name
        if (user.getFirstName().isEmpty() || user.getLastName().isEmpty()) {
            System.out.println("First name and last name cannot be empty.");
            return false;
        }

        // Phone number must be 10 digits
        if (!isValidPhone(user.getPhone())) {
            System.out.println("Invalid phone number. It must be 10 digits.");
            return false;
        }

        // Address must not be empty
        if (user.getAddress().isEmpty()) {
            System.out.println("Address cannot be empty.");
            return false;
        }

        return true;
    }

    /**
     * Validates that a phone number contains exactly 10 digits
     * @param phone Phone number string
     * @return true if valid, false otherwise
     */
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }
}
