package com.sneaksphere.service;

import com.sneaksphere.config.Dbconfig;
import com.sneaksphere.model.OrderModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetOrderService {
    private Connection dbConn;

    /**
     * @para None
     * Constructor initializes the database connection using Dbconfig.
     */
    public GetOrderService() {
        try {
            dbConn = Dbconfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @para None
     * Fetches all orders along with associated customer and sneaker names
     * by joining the order, user_sneaker_order, user, and sneaker tables.
     *
     * @return List<OrderModel> - List of all orders with customer and sneaker details.
     */
    public List<OrderModel> getAllOrders() {
        List<OrderModel> orders = new ArrayList<>();
        String query = "SELECT o.OrderID, o.Quantity, o.Size, o.OrderTotal, o.OrderStatus, o.OrderDate, " +
                "u.UserID, u.UserFirstName, u.UserLastName, " +
                "GROUP_CONCAT(s.SneakerName SEPARATOR ', ') AS SneakerNames " +
                "FROM `order` o " +
                "JOIN user_sneaker_order uso ON uso.OrderID = o.OrderID " +
                "JOIN user u ON uso.UserID = u.UserID " +
                "JOIN sneaker s ON uso.SneakerID = s.SneakerID " +
                "GROUP BY o.OrderID";

        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OrderModel order = new OrderModel();
                order.setOrderID(rs.getInt("OrderID"));
                order.setQuantity(rs.getInt("Quantity"));
                order.setSize(rs.getFloat("Size"));
                order.setOrderTotal(rs.getFloat("OrderTotal"));
                order.setOrderStatus(rs.getString("OrderStatus"));
                order.setOrderDate(rs.getDate("OrderDate"));
                order.setCustomerId(rs.getInt("UserID")); // from user_sneaker_order
                order.setCustomerFirstName(rs.getString("UserFirstName"));
                order.setCustomerLastName(rs.getString("UserLastName"));
                order.setSneakerNames(rs.getString("SneakerNames"));

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    /**
     * @para int customerId - The ID of the customer whose orders need to be fetched.
     * Fetches all orders for a specific customer by customerId, including sneaker names.
     *
     * @return List<OrderModel> - List of orders placed by the specified customer.
     */
    public List<OrderModel> getOrdersByCustomerId(int customerId) {
        List<OrderModel> orders = new ArrayList<>();
        String query = "SELECT o.OrderID, o.Quantity, o.Size, o.OrderTotal, o.OrderStatus, o.OrderDate, " +
                       "GROUP_CONCAT(s.SneakerName SEPARATOR ', ') AS SneakerNames " +
                       "FROM user_sneaker_order uso " +
                       "JOIN `order` o ON uso.OrderID = o.OrderID " +
                       "JOIN `sneaker` s ON uso.SneakerID = s.SneakerID " +
                       "WHERE uso.UserID = ? " +
                       "GROUP BY o.OrderID";

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderModel order = new OrderModel();
                order.setOrderID(rs.getInt("OrderID"));
                order.setQuantity(rs.getInt("Quantity"));
                order.setSize(rs.getFloat("Size"));
                order.setOrderTotal(rs.getFloat("OrderTotal"));
                order.setOrderStatus(rs.getString("OrderStatus"));
                order.setOrderDate(rs.getDate("OrderDate"));
                order.setCustomerId(customerId);
                order.setSneakerNames(rs.getString("SneakerNames"));

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    /**
     * @para int orderId - The ID of the order to update.
     * @para String newStatus - The new status to set for the order.
     * Updates the status of the order identified by orderId.
     *
     * @return boolean - true if the update was successful, false otherwise.
     */
    public boolean updateOrderStatus(int orderId, String newStatus) {
        String updateQuery = "UPDATE `order` SET OrderStatus = ? WHERE OrderID = ?";

        try (PreparedStatement stmt = dbConn.prepareStatement(updateQuery)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, orderId);
            int rowsUpdated = stmt.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
