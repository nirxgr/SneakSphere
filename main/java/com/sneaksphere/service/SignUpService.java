package com.sneaksphere.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sneaksphere.config.Dbconfig;
import com.sneaksphere.model.UserModel;

/**
 * SignupService handles the registration of new users. It manages database
 * interactions for user registration.
 */
public class SignUpService {

	private Connection dbConn;

	/**
	 * Constructor initializes the database connection.
	 */
	public SignUpService() {
		try {
			this.dbConn = Dbconfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("Database connection error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Registers a new user in the database.
	 *
	 * @param userModel the user details to be registered
	 * @return Boolean indicating the success of the operation
	 */
	public Boolean registerUser(UserModel userModel) {
	    if (dbConn == null) {
	        System.err.println("Database connection is not available.");
	        return null;
	    }

	    String checkEmailQuery = "SELECT UserID FROM user WHERE UserEmail = ?";
	    String checkPhoneQuery = "SELECT UserID FROM user WHERE UserPhone = ?";
	    String insertQuery = "INSERT INTO user (UserFirstName, UserLastName, UserEmail, UserPassword, UserPhone, UserAddress, Role, UserImageURL) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    try (
	        PreparedStatement checkEmailStmt = dbConn.prepareStatement(checkEmailQuery);
	        PreparedStatement checkPhoneStmt = dbConn.prepareStatement(checkPhoneQuery);
	        PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)
	    ) {
	        // Check for existing email
	        checkEmailStmt.setString(1, userModel.getEmail());
	        ResultSet emailRs = checkEmailStmt.executeQuery();
	        if (emailRs.next()) {
	            System.err.println("Email already exists: " + userModel.getEmail());
	            return false;
	        }

	        // Check for existing phone number
	        checkPhoneStmt.setString(1, userModel.getPhone());
	        ResultSet phoneRs = checkPhoneStmt.executeQuery();
	        if (phoneRs.next()) {
	            System.err.println("Phone number already exists: " + userModel.getPhone());
	            return false;
	        }

	        // Insert new user
	        insertStmt.setString(1, userModel.getFirstName());
	        insertStmt.setString(2, userModel.getLastName());
	        insertStmt.setString(3, userModel.getEmail());
	        insertStmt.setString(4, userModel.getPassword());
	        insertStmt.setString(5, userModel.getPhone());
	        insertStmt.setString(6, userModel.getAddress());
	        insertStmt.setString(7, "Customer"); // Make sure Role is correct
	        insertStmt.setString(8, userModel.getUserImageURL());

	        int rowsAffected = insertStmt.executeUpdate();
	        System.out.println("Rows affected by insert: " + rowsAffected);

	        return rowsAffected > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}



}