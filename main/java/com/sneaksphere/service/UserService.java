package com.sneaksphere.service;


import com.sneaksphere.model.UserModel;
import com.sneaksphere.config.Dbconfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserService {
	  private Connection dbConn;
	  private boolean isConnectionError = false;

	
	public UserService() {
		try {
            dbConn = Dbconfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
        	System.out.println(" Connection failed:");
            ex.printStackTrace();
            isConnectionError = true;
        }
	}
	 // Get user by Email
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

    // Get user by ID
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

 // Fetch admin profile by role= 'Admin'
    public UserModel getAdminProfile() {
        String query = "SELECT * FROM user WHERE Role = 'Admin'";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            	return extractUserFromResult(rs); //reusing the method 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
 // Fetch   details of the logged in user
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
        user.setUserImageURL(result.getString("UserImageURL")); //added this line to upload img in database 
        return user;
    }
    
 // Update user profile (used for admin editing)
    public boolean updateUserProfile(UserModel user) {
    	
    	// Validation logic
        if (!validateUserProfile(user)) {
            return false;
        }
        
    	// Logic to update user in the database
        if (isConnectionError) return false;

        String query = "UPDATE user SET UserFirstName = ?, UserLastName = ?, UserPhone = ?, UserAddress = ?, UserImageURL = ? WHERE UserID = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            //stmt.setString(3, user.getEmail());
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

 // Validation for the user profile
    private boolean validateUserProfile(UserModel user) {
        // Validate first name and last name (they should not be empty)
        if (user.getFirstName().isEmpty() || user.getLastName().isEmpty()) {
            System.out.println(" First name and last name cannot be empty.");
            return false;
        }

        // Validate phone number (should be 10 digits long)
        if (!isValidPhone(user.getPhone())) {
            System.out.println(" Invalid phone number. It must be 10 digits.");
            return false;
        }

   
        // Validate address (it should not be empty)
        if (user.getAddress().isEmpty()) {
            System.out.println(" Address cannot be empty.");
            return false;
        }

        return true;
    }

    // Helper method to validate phone number (only numbers, 10 digits)
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }
   
}