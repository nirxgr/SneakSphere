package com.sneaksphere.service;

import com.sneaksphere.config.Dbconfig;
import com.sneaksphere.model.SneakerModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchService {
    
    /**
     * Searches for sneakers by product name in the database.
     * 
     * @para productName The name (or partial name) of the product to search for; search is case-insensitive.
     *                    Only products with AvailabilityStatus 'Available' will be returned.
     * @return A list of SneakerModel objects that match the product name criteria and are available.
     *         Returns an empty list if no matches found or if an error occurs during the database operation.
     */
	//A list of sneakerModel object that matches the product name and are available.
    public List<SneakerModel> searchByProductName(String productName) {
    	//make the search case-insensitive
    	//filters out unavailable products. 
        String sql = "SELECT SneakerID, SneakerName, Category, Brand, Price, ImageURL " +
                     "FROM sneaker WHERE " +
                     "LOWER(SneakerName) LIKE ? AND " +
                     "AvailabilityStatus = 'Available'";
        
        try (Connection conn = Dbconfig.getDbConnection();//Get db connection
             PreparedStatement stmt = conn.prepareStatement(sql)) {//prepare the sql statement
            //add wildcard (%) to allow partial matching 
            stmt.setString(1, "%" + productName.toLowerCase() + "%");//% for wildcard search
            
            List<SneakerModel> results = new ArrayList<>();//creates list to hold results
            try (ResultSet rs = stmt.executeQuery()) {//execute query
                while (rs.next()) {					//Loop through each row
                    SneakerModel sneaker = new SneakerModel();//create new model object
                    sneaker.setSneakerID(rs.getInt("SneakerID"));//set SneakerID
                    sneaker.setSneakerName(rs.getString("SneakerName"));//set name
                    sneaker.setCategory(rs.getString("Category"));// set category
                    sneaker.setBrand(rs.getString("Brand"));//set brand
                    sneaker.setPrice(rs.getFloat("Price"));// set price
                    sneaker.setImageUrl(rs.getString("ImageURL"));// set image
                    results.add(sneaker);//add to sneaker list
                }
            }
            return results;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[SearchService] Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
