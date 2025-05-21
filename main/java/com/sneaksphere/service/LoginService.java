package com.sneaksphere.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sneaksphere.config.Dbconfig;
import com.sneaksphere.model.UserModel;
import com.sneaksphere.util.PasswordUtil;

/**
 * Service class for handling login operations.
 * Connects to the database, verifies user credentials,
 * and returns login status.
 */
public class LoginService {

    private Connection dbConn;
    private boolean isConnectionError = false;

    /**
     * Constructor initializes the database connection.
     * Sets the connection error flag if the connection fails.
     */
    public LoginService() {
        try {
            dbConn = Dbconfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            isConnectionError = true;
        }
    }

    /**
     * Validates the user credentials against the database records.
     *
     * @param userModel the UserModel object containing user credentials
     * @return true if the user credentials are valid, false otherwise;
     *         null if a connection error occurs
     */
    public String loginUser(UserModel userModel) {
        if (isConnectionError) {
            System.out.println("Connection Error!");
            return null;
        }

        String query = "SELECT UserEmail, UserPassword, Role FROM User WHERE UserEmail = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, userModel.getEmail());
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return validatePassword(result, userModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }
    

    /**
     * Validates the password retrieved from the database.
     *
     * @param result    the ResultSet containing the username and password
     * @param userModel the UserModel object containing user credentials
     * @return true if the passwords match, false otherwise
     * @throws SQLException if a database access error occurs
     */
    private String validatePassword(ResultSet result, UserModel userModel) throws SQLException {
        String dbUsername = result.getString("UserEmail");
        String encryptedPassword = result.getString("UserPassword");
        String role = result.getString("Role");  // Get the role (admin or customer)

        String decryptedPassword = PasswordUtil.decrypt(encryptedPassword, dbUsername);

        if (dbUsername.equals(userModel.getEmail()) && decryptedPassword != null && decryptedPassword.equalsIgnoreCase(userModel.getPassword())) {
            return role;  // Return role
        }

        return null; // Incorrect credentials
        
    }

}