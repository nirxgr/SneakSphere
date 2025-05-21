package com.sneaksphere.service;

import com.sneaksphere.config.Dbconfig;
import com.sneaksphere.model.SneakerModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartService {

    /**
     * Get the UserID from the database using the user's email.
     *
     * @param email the email of the user whose ID is to be fetched
     * @return the UserID associated with the given email
     * @throws SQLException if a database access error occurs or user is not found
     * @throws ClassNotFoundException if database driver class is not found
     */
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

    /**
     * Add a sneaker to the cart for a specific user.
     * If the sneaker already exists in the user's cart, update the quantity and total price.
     * Otherwise, insert a new cart record and link it to the user and sneaker.
     *
     * @param userId the ID of the user
     * @param sneakerId the ID of the sneaker to add
     * @param quantity the quantity of the sneaker to add
     * @param price the price of a single sneaker unit
     * @return true if the operation was successful, false otherwise
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if database driver class is not found
     */
    public boolean addToCart(int userId, int sneakerId, int quantity, double price)
            throws SQLException, ClassNotFoundException {

        // SQL to check if this sneaker is already in the user's cart
        String checkSql = "SELECT c.CartID, c.CartQuantity FROM cart c " +
                         "JOIN user_sneaker_cart usc ON c.CartID = usc.CartID " +
                         "WHERE usc.UserID = ? AND usc.SneakerID = ?";

        // SQL to update existing cart item
        String updateSql = "UPDATE cart SET CartQuantity = ?, CartTotal = ? WHERE CartID = ?";

        // SQL to insert a new item into cart
        String insertCartSql = "INSERT INTO cart (CartTotal, CartQuantity) VALUES (?, ?)";

        // SQL to link user and sneaker to the newly created cart item
        String insertRelationSql = "INSERT INTO user_sneaker_cart (UserID, SneakerID, CartID) VALUES (?, ?, ?)";

        try (Connection conn = Dbconfig.getDbConnection()) {
            conn.setAutoCommit(false); // Enable transaction

            // To Check if item already exists in the user's cart
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, userId);
                checkStmt.setInt(2, sneakerId);

                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    // Sneaker is already in cart – update quantity and total
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

            //  Sneaker not in cart – add it as a new entry
            int cartId;
            double total = price * quantity;

            // Insert into cart table
            try (PreparedStatement cartStmt = conn.prepareStatement(insertCartSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                cartStmt.setDouble(1, total);
                cartStmt.setInt(2, quantity);
                cartStmt.executeUpdate();

                // Retrieve generated CartID
                try (ResultSet rs = cartStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        cartId = rs.getInt(1);
                    } else {
                        conn.rollback();
                        throw new SQLException("Failed to get generated cart ID");
                    }
                }
            }

            // Link user and sneaker to new cart entry
            try (PreparedStatement relationStmt = conn.prepareStatement(insertRelationSql)) {
                relationStmt.setInt(1, userId);
                relationStmt.setInt(2, sneakerId);
                relationStmt.setInt(3, cartId);
                relationStmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            throw e; // Let the caller handle the exception
        }
    }

    /**
     * Remove a specific item from the user's cart.
     *
     * @param userID the ID of the user
     * @param cartID the ID of the cart item to remove
     * @return true if the removal was successful, false if no matching record was found
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if database driver class is not found
     */
    public boolean removeFromCart(int userID, int cartID) throws SQLException, ClassNotFoundException {
        String deleteRelationSql = "DELETE FROM user_sneaker_cart WHERE UserID = ? AND CartID = ?";
        String deleteCartSql = "DELETE FROM cart WHERE CartID = ?";

        try (Connection conn = Dbconfig.getDbConnection()) {
            conn.setAutoCommit(false);

            // : Remove entry from user_sneaker_cart table
            try (PreparedStatement relationStmt = conn.prepareStatement(deleteRelationSql)) {
                relationStmt.setInt(1, userID);
                relationStmt.setInt(2, cartID);
                int affected = relationStmt.executeUpdate();
                if (affected == 0) {
                    conn.rollback();
                    return false; // No match found
                }
            }

            // Delete the cart record itself
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

    /**
     * Clear the entire cart for a specific user by deleting all related cart items.
     *
     * @param userID the ID of the user whose cart is to be cleared
     * @return true if the cart was successfully cleared
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if database driver class is not found
     */
    public boolean clearCart(int userID) throws SQLException, ClassNotFoundException {
        String selectCartIdsSql = "SELECT CartID FROM user_sneaker_cart WHERE UserID = ?";
        String deleteRelationSql = "DELETE FROM user_sneaker_cart WHERE UserID = ?";
        String deleteCartSql = "DELETE FROM cart WHERE CartID = ?";

        try (Connection conn = Dbconfig.getDbConnection()) {
            conn.setAutoCommit(false);

            List<Integer> cartIds = new ArrayList<>();

            //  Get all CartIDs associated with the user
            try (PreparedStatement selectStmt = conn.prepareStatement(selectCartIdsSql)) {
                selectStmt.setInt(1, userID);
                ResultSet rs = selectStmt.executeQuery();
                while (rs.next()) {
                    cartIds.add(rs.getInt("CartID"));
                }
            }

            // Delete all relations between user and cart
            try (PreparedStatement deleteRelationStmt = conn.prepareStatement(deleteRelationSql)) {
                deleteRelationStmt.setInt(1, userID);
                deleteRelationStmt.executeUpdate();
            }

            // Delete each cart record
            try (PreparedStatement deleteCartStmt = conn.prepareStatement(deleteCartSql)) {
                for (int cartId : cartIds) {
                    deleteCartStmt.setInt(1, cartId);
                    deleteCartStmt.executeUpdate();
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Get a list of sneakers currently in the user's cart, including sneaker details and cart information.
     *
     * @param userID the ID of the user whose cart is to be retrieved
     * @return a list of SneakerModel objects representing the sneakers in the user's cart
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if database driver class is not found
     */
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

            // Create SneakerModel objects and fill them with cart + sneaker info
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
