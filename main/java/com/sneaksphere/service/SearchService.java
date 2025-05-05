package com.sneaksphere.service;

import com.sneaksphere.config.Dbconfig;
import com.sneaksphere.model.SneakerModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchService {
    
    public List<SneakerModel> searchByProductName(String productName) {
        String sql = "SELECT SneakerID, SneakerName, Category, Brand, Price, ImageURL " +
                     "FROM sneaker WHERE " +
                     "LOWER(SneakerName) LIKE ? AND " +
                     "AvailabilityStatus = 'Available'";
        
        try (Connection conn = Dbconfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + productName.toLowerCase() + "%");
            
            List<SneakerModel> results = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SneakerModel sneaker = new SneakerModel();
                    sneaker.setSneakerID(rs.getInt("SneakerID"));
                    sneaker.setSneakerName(rs.getString("SneakerName"));
                    sneaker.setCategory(rs.getString("Category"));
                    sneaker.setBrand(rs.getString("Brand"));
                    sneaker.setPrice(rs.getFloat("Price"));
                    sneaker.setImageUrl(rs.getString("ImageURL"));
                    results.add(sneaker);
                }
            }
            return results;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[SearchService] Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}