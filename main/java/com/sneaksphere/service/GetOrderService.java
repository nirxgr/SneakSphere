package com.sneaksphere.service;

import com.sneaksphere.config.Dbconfig;
import com.sneaksphere.model.OrderModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetOrderService {
    private Connection dbConn;

    public GetOrderService() {
        try {
            dbConn = Dbconfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public List<OrderModel> getAllOrders() {
        List<OrderModel> orders = new ArrayList<>();
        String query = "SELECT OrderID, Quantity, Size, OrderTotal, OrderStatus, OrderDate FROM `order` ⁠";

        try (Statement stmt = dbConn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                OrderModel order = new OrderModel();
                order.setOrderId(rs.getInt("OrderID"));
                order.setQuantity(rs.getInt("Quantity"));
                order.setSize(rs.getFloat("Size"));
                order.setOrderTotal(rs.getFloat("OrderTotal"));
                order.setOrderStatus(rs.getString("OrderStatus"));
                order.setOrderDate(rs.getDate("OrderDate"));

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        
        return orders;
    }
    
    public boolean updateOrderStatus(int orderId, String newStatus) {
        String updateQuery = "UPDATE `order` ⁠SET OrderStatus = ? WHERE OrderID = ?";

        try (PreparedStatement stmt = dbConn.prepareStatement(updateQuery)) {
            stmt.setString(1, newStatus);  // Set the new status
            stmt.setInt(2, orderId);  // Set the order ID
            int rowsUpdated = stmt.executeUpdate();

            return rowsUpdated > 0;  // Return true if update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false in case of failure
        }
    }


}