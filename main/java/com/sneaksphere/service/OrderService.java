package com.sneaksphere.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sneaksphere.model.OrderModel;
import com.sneaksphere.model.SneakerModel;
import com.sneaksphere.config.Dbconfig;

public class OrderService {

    /**
     * This method handles the placing of an order
     * 
     * @param order The OrderModel containing order details like userID, orderTotal, orderDate, etc.
     * @param cartItems List of SneakerModel objects representing items in the user's cart
     * @return the generated OrderID if successful, or -1 if insertion failed
     * @throws SQLException if any SQL error occurs during database operations
     * @throws ClassNotFoundException if the database driver class is not found
     */
    public int placeOrder(OrderModel order, List<SneakerModel> cartItems) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement orderStmt = null;
        PreparedStatement mappingStmt = null;
        ResultSet rs = null;

        int orderID = -1; // Placeholder for the new OrderID

        try {
            // Open database connection
            conn = Dbconfig.getDbConnection();
            conn.setAutoCommit(false); // Start transaction manually to ensure atomicity

            // SQL to insert order details
            String insertOrderSQL = "INSERT INTO `order` (Quantity, Size, OrderTotal, OrderStatus, OrderDate) VALUES (?, ?, ?, ?, ?)";
            orderStmt = conn.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS); // Retrieve generated OrderID

            // Calculate total quantity and average size from cart items
            int totalQuantity = 0;
            float avgSize = 0;
            for (SneakerModel item : cartItems) {
                totalQuantity += item.getCartQuantity();
                avgSize += item.getSneakerSize();
            }
            avgSize = avgSize / cartItems.size(); // Basic average calculation for size

            // Set values in prepared statement
            orderStmt.setInt(1, totalQuantity);
            orderStmt.setFloat(2, avgSize);
            orderStmt.setDouble(3, order.getOrderTotal());
            orderStmt.setString(4, "Pending"); // Default status
            orderStmt.setDate(5, new java.sql.Date(order.getOrderDate().getTime())); // Convert java.util.Date to sql.Date

            // Execute the insert
            int rowsInserted = orderStmt.executeUpdate();

            // If no row was inserted, rollback and return error
            if (rowsInserted == 0) {
                conn.rollback();
                return -1;
            }

            // Get the generated OrderID
            rs = orderStmt.getGeneratedKeys();
            if (rs.next()) {
                orderID = rs.getInt(1);
            } else {
                conn.rollback();
                return -1;
            }

            // SQL to map each sneaker item to the order
            String insertMappingSQL = "INSERT INTO user_sneaker_order (UserID, SneakerID, OrderID) VALUES (?, ?, ?)";
            mappingStmt = conn.prepareStatement(insertMappingSQL);

            // For each sneaker in the cart, create an entry in the mapping table
            for (SneakerModel item : cartItems) {
                mappingStmt.setInt(1, order.getUserID());
                mappingStmt.setInt(2, item.getSneakerID());
                mappingStmt.setInt(3, orderID);
                mappingStmt.addBatch(); // Add to batch for efficiency
            }

            mappingStmt.executeBatch(); // Execute all batched inserts

            conn.commit(); // Commit the entire transaction

        } catch (SQLException e) {
            if (conn != null) conn.rollback(); // If any error, rollback changes
            throw e; // Rethrow the exception to be handled at a higher level
        } finally {
            // Clean up all JDBC resources
            if (rs != null) rs.close();
            if (orderStmt != null) orderStmt.close();
            if (mappingStmt != null) mappingStmt.close();
            if (conn != null) conn.close();
        }

        return orderID; // Return the new order's ID
    }
    
    /**
     * Places an order for a single sneaker item. If a pending order exists for the user and sneaker,
     * updates the quantity and total. Otherwise, creates a new order.
     * 
     * @param order The OrderModel containing order details like userID, quantity, size, total, and date
     * @param sneaker The SneakerModel representing the sneaker being ordered
     * @return the OrderID of the updated or newly created order, or -1 if operation failed
     * @throws SQLException if any SQL error occurs during database operations
     * @throws ClassNotFoundException if the database driver class is not found
     */
    public int placeOrderForSingleItem(OrderModel order, SneakerModel sneaker) 
            throws SQLException, ClassNotFoundException {
        
        System.out.println("[DEBUG] OrderService - Received Order:");
        System.out.println("[DEBUG]   UserID: " + order.getUserID());
        System.out.println("[DEBUG]   Quantity: " + order.getQuantity());
        System.out.println("[DEBUG]   Size: " + order.getSize());
        System.out.println("[DEBUG]   Total: " + order.getOrderTotal());
        System.out.println("[DEBUG]   Sneaker ID: " + sneaker.getSneakerID());
        System.out.println("[DEBUG]   Sneaker Size: " + sneaker.getSneakerSize());
        
        Connection conn = null;
        PreparedStatement orderStmt = null;
        PreparedStatement checkStmt = null;
        PreparedStatement updateStmt = null;
        PreparedStatement mappingStmt = null;
        ResultSet rs = null;
        int orderID = -1;

        try {
            conn = Dbconfig.getDbConnection();
            conn.setAutoCommit(false);
            
            // 1. Check for existing order for this user
            String checkSQL = "SELECT o.OrderID, o.Quantity FROM `order` o " +
                             "JOIN user_sneaker_order uso ON o.OrderID = uso.OrderID " +
                             "WHERE uso.UserID = ? AND uso.SneakerID = ? " +
                             "AND o.OrderStatus = 'Pending' LIMIT 1";
            
            checkStmt = conn.prepareStatement(checkSQL);
            checkStmt.setInt(1, order.getUserID());
            checkStmt.setInt(2, sneaker.getSneakerID());
            rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                // Existing order found - update quantity
                orderID = rs.getInt("OrderID");
                int currentQuantity = rs.getInt("Quantity");
                
                String updateSQL = "UPDATE `order` SET Quantity = ?, OrderTotal = ? " +
                                  "WHERE OrderID = ?";
                updateStmt = conn.prepareStatement(updateSQL);
                updateStmt.setInt(1, currentQuantity + order.getQuantity());
                updateStmt.setDouble(2, order.getOrderTotal() * (currentQuantity + order.getQuantity()));
                updateStmt.setInt(3, orderID);
                
                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated == 0) {
                    conn.rollback();
                    return -1;
                }
            } else {
                // No existing order - create new one
                String insertOrderSQL = "INSERT INTO `order` (Quantity, Size, OrderTotal, OrderStatus, OrderDate) " +
                                      "VALUES (?, ?, ?, ?, ?)";
                orderStmt = conn.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS);
                
                orderStmt.setInt(1, order.getQuantity());
                orderStmt.setFloat(2, order.getSize());
                orderStmt.setDouble(3, order.getOrderTotal());
                orderStmt.setString(4, "Pending");
                orderStmt.setDate(5, new java.sql.Date(order.getOrderDate().getTime()));

                int rowsInserted = orderStmt.executeUpdate();
                if (rowsInserted == 0) {
                    conn.rollback();
                    return -1;
                }

                rs = orderStmt.getGeneratedKeys();
                if (rs.next()) {
                    orderID = rs.getInt(1);
                } else {
                    conn.rollback();
                    return -1;
                }
            }

            // Always create new mapping in user_sneaker_order
            String insertMappingSQL = "INSERT INTO user_sneaker_order (UserID, SneakerID, OrderID) " +
                                    "VALUES (?, ?, ?)";
            mappingStmt = conn.prepareStatement(insertMappingSQL);
            
            mappingStmt.setInt(1, order.getUserID());
            mappingStmt.setInt(2, sneaker.getSneakerID());
            mappingStmt.setInt(3, orderID);
            mappingStmt.executeUpdate();

            conn.commit();
            return orderID;
            
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            // Clean up resources
            if (rs != null) rs.close();
            if (checkStmt != null) checkStmt.close();
            if (updateStmt != null) updateStmt.close();
            if (orderStmt != null) orderStmt.close();
            if (mappingStmt != null) mappingStmt.close();
            if (conn != null) conn.close();
        }
    }
    
    /**
     * Retrieves all orders for a given user along with their associated sneaker details.
     * 
     * @param userId the ID of the user whose orders are to be fetched
     * @return a List of Maps, each representing an order joined with sneaker details; 
     *         each map contains keys like "OrderID", "Quantity", "Size", "OrderTotal", 
     *         "OrderStatus", "OrderDate", "SneakerID", "SneakerName", "SneakerDescription", 
     *         "SneakerPrice", "SneakerBrand", and "SneakerImagePath"
     * @throws SQLException if any SQL error occurs during database operations
     * @throws ClassNotFoundException if the database driver class is not found
     */
    public List<Map<String, Object>> getOrdersWithSneakersByUserID(int userId) throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> result = new ArrayList<>(); // List to store result rows

        // SQL joins order, mapping table, and sneaker to retrieve all necessary details
        String sql = "SELECT o.OrderID, o.Quantity AS totalQuantity, o.Size, o.OrderTotal, o.OrderStatus, o.OrderDate, " +
                     "s.SneakerID, s.SneakerName, s.Description, s.Price, s.Brand, s.ImageUrl " +
                     "FROM `order` o " +
                     "JOIN user_sneaker_order uso ON o.OrderID = uso.OrderID " +
                     "JOIN sneaker s ON s.SneakerID = uso.SneakerID " +
                     "WHERE uso.UserID = ?";

        try (Connection conn = Dbconfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId); // Set the user ID to filter orders

            ResultSet rs = stmt.executeQuery();

            // Iterate through each result row and map the values
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("OrderID", rs.getInt("OrderID"));
                row.put("Quantity", rs.getInt("totalQuantity"));
                row.put("Size", rs.getFloat("Size"));
                row.put("OrderTotal", rs.getFloat("OrderTotal"));
                row.put("OrderStatus", rs.getString("OrderStatus"));
                row.put("OrderDate", rs.getDate("OrderDate"));

                // Sneaker details
                row.put("SneakerID", rs.getInt("SneakerID"));
                row.put("SneakerName", rs.getString("SneakerName"));
                row.put("SneakerDescription", rs.getString("Description"));
                row.put("SneakerPrice", rs.getFloat("Price"));
                row.put("SneakerBrand", rs.getString("Brand"));
                row.put("SneakerImagePath", rs.getString("ImageUrl"));

                result.add(row); // Add the mapped data to the result list
            }
        }

        return result; // Return the list of orders and associated sneaker data
    }
}
