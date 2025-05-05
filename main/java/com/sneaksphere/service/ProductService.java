package com.sneaksphere.service;

import com.sneaksphere.config.Dbconfig;
import com.sneaksphere.model.SneakerModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static final int DEFAULT_LIMIT = 6;

 
    
    /**
     * Fetches products by category with default sorting (newest first)
     * @param category The category to filter by
     * @return List of SneakerModel objects, or empty list if error occurs
     */
    public List<SneakerModel> getProductsByCategory(String category) {
        // Default to featured sorting
        return getProductsByCategory(category, "featured");
    }

    /**
     * Fetches products by category with specified sorting
     * @param category The category to filter by
     * @param sortOption The sorting preference (price-low, price-high, etc.)
     * @return List of SneakerModel objects, or empty list if error occurs
     */
    public List<SneakerModel> getProductsByCategory(String category, String sortOption) {
        String sql = "SELECT SneakerID, SneakerName, Category, Brand, Price, ImageURL " +
                     "FROM sneaker WHERE AvailabilityStatus = 'Available' " +
                     "AND Category = ? ";
        
        // Add sorting based on the option
        switch(sortOption != null ? sortOption : "featured") {
            case "price-low":
                sql += "ORDER BY Price ASC";
                break;
            case "price-high":
                sql += "ORDER BY Price DESC";
                break;
            case "alpha-az":
                sql += "ORDER BY SneakerName ASC";
                break;
            case "brand-az":
                sql += "ORDER BY Brand ASC";
                break;
            default: // "featured" or any other case
                sql += "ORDER BY ReleasedDate DESC";
        }

        try (Connection conn = Dbconfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category);
            return executeSneakerQuery(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[ERROR] getProductsByCategory failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    
    /**
     * Fetches the latest available sneakers (new releases).
     * @param limit Maximum number of products to fetch (defaults to 6 if <= 0).
     * @return List of SneakerModel objects, or empty list if error occurs.
     */
    public List<SneakerModel> getNewReleases(int limit) {
        int queryLimit = (limit > 0) ? limit : DEFAULT_LIMIT;
        String sql = "SELECT SneakerID, SneakerName, Category, Brand, Price, ImageURL " +
                     "FROM sneaker WHERE AvailabilityStatus = 'Available' " +
                     "ORDER BY ReleasedDate DESC LIMIT ?";

        try (Connection conn = Dbconfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, queryLimit);
            return executeSneakerQuery(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[ERROR] getNewReleases failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Fetches a single sneaker by ID (useful for product details page).
     * @param sneakerID The ID of the sneaker to fetch.
     * @return SneakerModel if found, null otherwise.
     */
    public SneakerModel getSneakerById(int sneakerID) {
        String sql = "SELECT SneakerID, SneakerName, Description, Category, " +
                     "Brand, Price, ImageURL, ReleasedDate " +
                     "FROM sneaker WHERE SneakerID = ?";

        try (Connection conn = Dbconfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, sneakerID);
            List<SneakerModel> result = executeSneakerQuery(stmt);
            return result.isEmpty() ? null : result.get(0);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[ERROR] getSneakerById failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Fetches products by multiple categories.
     * @param categories The categories to filter by
     * @return List of SneakerModel objects, or empty list if error occurs
     */
    public List<SneakerModel> getProductsByCategories(String sortOption, String... categories) {
        if (categories == null || categories.length == 0) {
            return new ArrayList<>();
        }

        StringBuilder sql = new StringBuilder("SELECT SneakerID, SneakerName, Category, Brand, Price, ImageURL ")
                .append("FROM sneaker WHERE AvailabilityStatus = 'Available' ")
                .append("AND Category IN (");

        for (int i = 0; i < categories.length; i++) {
            sql.append("?");
            if (i < categories.length - 1) {
                sql.append(",");
            }
        }
        sql.append(") ");

        // Add sorting based on the option
        switch(sortOption != null ? sortOption : "featured") {
            case "price-low":
                sql.append("ORDER BY Price ASC");
                break;
            case "price-high":
                sql.append("ORDER BY Price DESC");
                break;
            case "alpha-az":
                sql.append("ORDER BY SneakerName ASC");
                break;
            case "brand-az":
                sql.append("ORDER BY Brand ASC");
                break;
            default: // "featured" or any other case
                sql.append("ORDER BY ReleasedDate DESC");
        }

        try (Connection conn = Dbconfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            for (int i = 0; i < categories.length; i++) {
                stmt.setString(i + 1, categories[i]);
            }
            
            return executeSneakerQuery(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[ERROR] getProductsByCategories failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    
    private List<SneakerModel> executeSneakerQuery(PreparedStatement stmt) throws SQLException {
        List<SneakerModel> sneakers = new ArrayList<>();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                sneakers.add(mapResultSetToSneaker(rs));
            }
        }
        return sneakers;
    }

    private SneakerModel mapResultSetToSneaker(ResultSet rs) throws SQLException {
        SneakerModel sneaker = new SneakerModel();
        sneaker.setSneakerID(rs.getInt("SneakerID"));
        sneaker.setSneakerName(rs.getString("SneakerName"));
        sneaker.setCategory(rs.getString("Category"));
        sneaker.setBrand(rs.getString("Brand"));
        sneaker.setPrice(rs.getFloat("Price"));
        sneaker.setImageUrl(rs.getString("ImageURL"));
        
        if (columnExists(rs, "Description")) {
            sneaker.setDescription(rs.getString("Description"));
        }
        if (columnExists(rs, "ReleasedDate")) {
            sneaker.setReleasedDate(rs.getDate("ReleasedDate").toLocalDate());
        }
        return sneaker;
    }

    private boolean columnExists(ResultSet rs, String columnName) {
        try {
            rs.findColumn(columnName);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}