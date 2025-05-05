/**
 * 
 */
package com.sneaksphere.service;


import com.sneaksphere.config.Dbconfig;
import com.sneaksphere.model.SneakerModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 */
public class WishListService {

	/**
	 * 
	 */
	public boolean addToWishlist(int userID, int sneakerID) {
		 if (!isAlreadyInWishlist(userID, sneakerID)) {
		        // Only insert if not already in wishlist
		String sql = "INSERT INTO wishlist (UserID, SneakerID) VALUES (?, ?)";
	    try (Connection conn = Dbconfig.getDbConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, userID);
	        stmt.setInt(2, sneakerID);
	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0; // Returns true if the insert was successful
	    } catch (SQLException | ClassNotFoundException e) {
	        System.err.println("[ERROR] addToWishlist failed: " + e.getMessage());
	    }
	    return false;
	}
		 return false; 
}
	public boolean isAlreadyInWishlist(int userID, int sneakerID) {
	    String sql = "SELECT COUNT(*) FROM wishlist WHERE UserID = ? AND SneakerID = ?";
	    try (Connection conn = Dbconfig.getDbConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, userID);
	        stmt.setInt(2, sneakerID);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) > 0; // true if already exists
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        System.err.println("[ERROR] isAlreadyInWishlist failed: " + e.getMessage());
	    }
	    return false;
	}

	public  boolean removeFromWishlist(int userID, int sneakerID) {
	    String sql = "DELETE FROM wishlist WHERE UserID = ? AND SneakerID = ?";
	    try (Connection conn = Dbconfig.getDbConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, userID);
	        stmt.setInt(2, sneakerID);
	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0; // Returns true if the delete was successful
	    } catch (SQLException | ClassNotFoundException e) {
	        System.err.println("[ERROR] removeFromWishlist failed: " + e.getMessage());
	    }
	    return false;
	}

	public boolean isInWishlist(int userID, int sneakerID) {
	    String sql = "SELECT COUNT(*) FROM wishlist WHERE UserID = ? AND SneakerID = ?";
	    try (Connection conn = Dbconfig.getDbConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, userID);
	        stmt.setInt(2, sneakerID);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0; // If count > 0, product is in wishlist
	            }
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        System.err.println("[ERROR] isInWishlist failed: " + e.getMessage());
	    }
	    return false;
	}

	public List<SneakerModel> getWishlistByUserID(int userID) {
	    List<SneakerModel> wishlist = new ArrayList<>();
	    String sql = "SELECT s.* FROM sneaker s " +
	                 "JOIN wishlist w ON s.SneakerID = w.SneakerID " +
	                 "WHERE w.UserID = ?";

	    try (Connection conn = Dbconfig.getDbConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, userID);
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                SneakerModel sneaker = new SneakerModel();
	                sneaker.setSneakerID(rs.getInt("SneakerID"));
	                sneaker.setSneakerName(rs.getString("SneakerName"));
	                sneaker.setCategory(rs.getString("Category"));
	                sneaker.setBrand(rs.getString("Brand"));
	                sneaker.setPrice(rs.getFloat("Price"));
	                sneaker.setImageUrl(rs.getString("ImageURL"));
	                sneaker.setDescription(rs.getString("Description"));
	                wishlist.add(sneaker);
	            }
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        System.err.println("[ERROR] getWishlistByUserID failed: " + e.getMessage());
	    }
	    return wishlist;
	}
}