package com.sneaksphere.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sneaksphere.model.SneakerModel;
import com.sneaksphere.config.Dbconfig;

public class AddSneakerService {
	
	
	private Connection dbConn;

	/**
	 * Constructor initializes the database connection.
	 */
	public AddSneakerService() {
		try {
			this.dbConn = Dbconfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("Database connection error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * Adds a new sneaker data in database.
	 */
	public Boolean addSneaker(SneakerModel sneakerModel) {
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}

		String insertQuery = "INSERT INTO sneaker (SneakerName, SneakerSize, Description, Category, Brand, Price, ReleasedDate, AvailabilityStatus, ImageURL) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		
		try(PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {


			// Insert student details
			insertStmt.setString(1, sneakerModel.getSneakerName());
			insertStmt.setFloat(2, sneakerModel.getSneakerSize());
			insertStmt.setString(3, sneakerModel.getDescription());
			insertStmt.setString(4, sneakerModel.getCategory());
			insertStmt.setString(5, sneakerModel.getBrand());
			insertStmt.setFloat(6, sneakerModel.getPrice());
			insertStmt.setDate(7, Date.valueOf(sneakerModel.getReleasedDate()));
			insertStmt.setString(8, "Available");
			insertStmt.setString(9, sneakerModel.getImageUrl());

			return insertStmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error during sneaker registration: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}


