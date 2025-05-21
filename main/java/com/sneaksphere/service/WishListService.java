/**
 * Service class for managing wishlist-related operations for users in SneakSphere.
 * Handles adding, removing, checking, and retrieving sneakers from a user's wishlist.
 */
package com.sneaksphere.service;

import com.sneaksphere.config.Dbconfig;
import com.sneaksphere.model.SneakerModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides services to manage user wishlists,
 * such as adding sneakers, removing them, and checking their existence.
 */
public class WishListService {

    /**
     * Adds a sneaker to the wishlist of a user if it's not already there.
     *
     * @param userID    ID of the user.
     * @param sneakerID ID of the sneaker to add.
     * @return true if added successfully, false otherwise.
     */
    public boolean addToWishlist(int userID, int sneakerID) {
        if (!isAlreadyInWishlist(userID, sneakerID)) {
            // SQL statement to insert a new wishlist item
            String sql = "INSERT INTO wishlist (UserID, SneakerID) VALUES (?, ?)";
            try (Connection conn = Dbconfig.getDbConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userID);
                stmt.setInt(2, sneakerID);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0; // Return true if insertion succeeded
            } catch (SQLException | ClassNotFoundException e) {
                System.err.println("[ERROR] addToWishlist failed: " + e.getMessage());
            }
        }
        return false;
    }

    /**
     * Checks if a sneaker is already present in the user's wishlist.
     *
     * @param userID    ID of the user.
     * @param sneakerID ID of the sneaker.
     * @return true if already in wishlist, false otherwise.
     */
    public boolean isAlreadyInWishlist(int userID, int sneakerID) {
        String sql = "SELECT COUNT(*) FROM wishlist WHERE UserID = ? AND SneakerID = ?";
        try (Connection conn = Dbconfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, sneakerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if count > 0
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[ERROR] isAlreadyInWishlist failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * Removes a sneaker from the user's wishlist.
     *
     * @param userID    ID of the user.
     * @param sneakerID ID of the sneaker to remove.
     * @return true if removed successfully, false otherwise.
     */
    public boolean removeFromWishlist(int userID, int sneakerID) {
        String sql = "DELETE FROM wishlist WHERE UserID = ? AND SneakerID = ?";
        try (Connection conn = Dbconfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, sneakerID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if deletion succeeded
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[ERROR] removeFromWishlist failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * Verifies if a specific sneaker is in a user's wishlist.
     * Similar to isAlreadyInWishlist; kept for clarity or future refactoring.
     *
     * @param userID    ID of the user.
     * @param sneakerID ID of the sneaker.
     * @return true if the sneaker exists in the wishlist, false otherwise.
     */
    public boolean isInWishlist(int userID, int sneakerID) {
        String sql = "SELECT COUNT(*) FROM wishlist WHERE UserID = ? AND SneakerID = ?";
        try (Connection conn = Dbconfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, sneakerID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[ERROR] isInWishlist failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * Retrieves the wishlist items of a specific user by joining the wishlist and sneaker tables.
     *
     * @param userID ID of the user whose wishlist is to be fetched.
     * @return List of SneakerModel objects that are in the user's wishlist.
     */
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
