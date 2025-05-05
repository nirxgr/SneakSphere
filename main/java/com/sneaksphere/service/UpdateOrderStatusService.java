package com.sneaksphere.service;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.sneaksphere.config.Dbconfig;

public class UpdateOrderStatusService {

    public boolean updateOrderStatus(int orderId, String newStatus) {
        String query = "UPDATE `order` SET OrderStatus = ? WHERE OrderID = ?";

        try (Connection connection = Dbconfig.getDbConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, newStatus);  // Set the new status
            stmt.setInt(2, orderId);       // Set the order ID to update

            int rowsUpdated = stmt.executeUpdate();  // Execute the update
            return rowsUpdated > 0;  // Return true if update was successful
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;  // Return false if there was an error
    }
}