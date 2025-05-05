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

		String checkQuery = "SELECT UserID FROM user WHERE UserEmail = ?";
		String insertQuery = "INSERT INTO user (UserFirstName, UserLastName, UserEmail,UserPassword, UserPhone , UserID, UserAddress, Role) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement checkStmt = dbConn.prepareStatement(checkQuery);
			 PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {

			// Check if user already exists by email
			checkStmt.setString(1, userModel.getEmail());
			ResultSet rs = checkStmt.executeQuery();
			if (rs.next()) {
				System.err.println("User with this email already exists.");
				return false;
			}

			insertStmt.setString(1, userModel.getFirstName());
			insertStmt.setString(2, userModel.getLastName());
			insertStmt.setString(3, userModel.getEmail());
			insertStmt.setString(4, userModel.getAddress());
			insertStmt.setString(5, userModel.getPhone());
			insertStmt.setInt(6, userModel.getUserID());
			insertStmt.setString(7, userModel.getPassword());
			insertStmt.setString(8, "Customer");

			return insertStmt.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error during user registration: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}
