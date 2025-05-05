package com.sneaksphere.service;

import com.sneaksphere.config.Dbconfig;
import com.sneaksphere.model.SneakerModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartService {

    public int getUserIDByEmail(String email) throws SQLException, ClassNotFoundException {
        String query = "SELECT UserID FROM users WHERE UserEmail = ?";
        try (Connection conn = Dbconfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("UserID");
            }
            throw new SQLException("User not found for email: " + email);
        }
    }

    public boolean addToCart(int userId, int sneakerId, int quantity, double price)
            throws SQLException, ClassNotFoundException {

        // First check if item already exists in cart
        String checkSql = "SELECT c.CartID, c.CartQuantity FROM cart c " +
                         "JOIN user_sneaker_cart usc ON c.CartID = usc.CartID " +
                         "WHERE usc.UserID = ? AND usc.SneakerID = ?";

        String updateSql = "UPDATE cart SET CartQuantity = ?, CartTotal = ? WHERE CartID = ?";
        String insertCartSql = "INSERT INTO cart (CartTotal, CartQuantity) VALUES (?, ?)";
        String insertRelationSql = "INSERT INTO user_sneaker_cart (UserID, SneakerID, CartID) VALUES (?, ?, ?)";

        try (Connection conn = Dbconfig.getDbConnection()) {
            conn.setAutoCommit(false);

            // Check for existing cart item
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, userId);
                checkStmt.setInt(2, sneakerId);
                
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    // Item exists - update quantity
                    int cartId = rs.getInt("CartID");
                    int currentQty = rs.getInt("CartQuantity");
                    int newQty = currentQty + quantity;
                    double newTotal = newQty * price;

                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, newQty);
                        updateStmt.setDouble(2, newTotal);
                        updateStmt.setInt(3, cartId);
                        updateStmt.executeUpdate();
                    }
                    conn.commit();
                    return true;
                }
            }

            // Item doesn't exist - create new entry
            int cartId;
            double total = price * quantity;

            try (PreparedStatement cartStmt = conn.prepareStatement(insertCartSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                cartStmt.setDouble(1, total);
                cartStmt.setInt(2, quantity);
                cartStmt.executeUpdate();

                try (ResultSet rs = cartStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        cartId = rs.getInt(1);
                    } else {
                        conn.rollback();
                        throw new SQLException("Failed to get generated cart ID");
                    }
                }
            }

            try (PreparedStatement relationStmt = conn.prepareStatement(insertRelationSql)) {
                relationStmt.setInt(1, userId);
                relationStmt.setInt(2, sneakerId);
                relationStmt.setInt(3, cartId);
                relationStmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public boolean removeFromCart(int userID, int cartID) throws SQLException, ClassNotFoundException {
        String deleteRelationSql = "DELETE FROM user_sneaker_cart WHERE UserID = ? AND CartID = ?";
        String deleteCartSql = "DELETE FROM cart WHERE CartID = ?";

        try (Connection conn = Dbconfig.getDbConnection()) {
            conn.setAutoCommit(false);

            // First delete from junction table
            try (PreparedStatement relationStmt = conn.prepareStatement(deleteRelationSql)) {
                relationStmt.setInt(1, userID);
                relationStmt.setInt(2, cartID);
                int affected = relationStmt.executeUpdate();
                if (affected == 0) {
                    conn.rollback();
                    return false;
                }
            }

            // Then delete from cart table
            try (PreparedStatement cartStmt = conn.prepareStatement(deleteCartSql)) {
                cartStmt.setInt(1, cartID);
                cartStmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean clearCart(int userID) throws SQLException, ClassNotFoundException {
        String deleteRelationSql = "DELETE FROM user_sneaker_cart WHERE UserID = ?";
        String deleteCartSql = "DELETE FROM cart WHERE CartID IN " +
                             "(SELECT usc.CartID FROM user_sneaker_cart usc WHERE usc.UserID = ?)";

        try (Connection conn = Dbconfig.getDbConnection()) {
            conn.setAutoCommit(false);

            // First delete all cart items
            try (PreparedStatement cartStmt = conn.prepareStatement(deleteCartSql)) {
                cartStmt.setInt(1, userID);
                cartStmt.executeUpdate();
            }

            // Then delete all user-cart relations
            try (PreparedStatement relationStmt = conn.prepareStatement(deleteRelationSql)) {
                relationStmt.setInt(1, userID);
                relationStmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<SneakerModel> getCartByUserID(int userID) throws SQLException, ClassNotFoundException {
        List<SneakerModel> sneakerCartList = new ArrayList<>();

        String query = "SELECT s.SneakerID, s.SneakerName, s.SneakerSize, s.Description, " +
                      "s.Category, s.Brand, s.Price, s.ReleasedDate, " +
                      "s.AvailabilityStatus, s.ImageUrl, c.CartID, " +
                      "c.CartTotal, c.CartQuantity " +
                      "FROM sneaker s " +
                      "JOIN user_sneaker_cart usc ON s.SneakerID = usc.SneakerID " +
                      "JOIN cart c ON usc.CartID = c.CartID " +
                      "WHERE usc.UserID = ?";

        try (Connection conn = Dbconfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SneakerModel sneaker = new SneakerModel();
                sneaker.setSneakerID(rs.getInt("SneakerID"));
                sneaker.setSneakerName(rs.getString("SneakerName"));
                sneaker.setSneakerSize(rs.getFloat("SneakerSize"));
                sneaker.setDescription(rs.getString("Description"));
                sneaker.setCategory(rs.getString("Category"));
                sneaker.setBrand(rs.getString("Brand"));
                sneaker.setPrice(rs.getFloat("Price"));
                sneaker.setReleasedDate(rs.getDate("ReleasedDate").toLocalDate());
                sneaker.setAvailabilityStatus(rs.getString("AvailabilityStatus"));
                sneaker.setImageUrl(rs.getString("ImageUrl"));
                sneaker.setCartID(rs.getInt("CartID"));
                sneaker.setCartTotal(rs.getFloat("CartTotal"));
                sneaker.setCartQuantity(rs.getInt("CartQuantity"));

                sneakerCartList.add(sneaker);
            }
        }
        return sneakerCartList;
    }
}