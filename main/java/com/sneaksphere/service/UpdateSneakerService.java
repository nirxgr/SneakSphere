package com.sneaksphere.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import java.sql.SQLException;

import com.sneaksphere.config.Dbconfig;
import com.sneaksphere.model.SneakerModel;

public class UpdateSneakerService {
     private Connection dbConn;

    /**
     * Constructor initializes the database connection.
     */

    public UpdateSneakerService() {
        try {
            this.dbConn = Dbconfig.getDbConnection(); // Initialize connection from Dbconfig
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Database connection error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    /**
     * Updates the sneaker record in the database with the details provided in the SneakerModel object.
     * 
     * @para sneaker The SneakerModel object containing updated sneaker details.
     * @return true if the update was successful (at least one row affected), false otherwise.
     */
    public boolean updateSneaker(SneakerModel sneaker) {
        String query = "UPDATE sneaker SET SneakerName = ?, SneakerSize = ?, Description = ?, Category = ?, " +
                       "Brand = ?, Price = ?, ReleasedDate = ?, AvailabilityStatus = ?, ImageUrl = ? WHERE SneakerID = ?";

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, sneaker.getSneakerName());
            stmt.setFloat(2, sneaker.getSneakerSize());
            stmt.setString(3, sneaker.getDescription());
            stmt.setString(4, sneaker.getCategory());
            stmt.setString(5, sneaker.getBrand());
            stmt.setFloat(6, sneaker.getPrice());
            stmt.setDate(7, Date.valueOf(sneaker.getReleasedDate()));
            stmt.setString(8, sneaker.getAvailabilityStatus());
            stmt.setString(9, sneaker.getImageUrl());
            stmt.setInt(10, sneaker.getSneakerID());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Return true if at least one row was updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
