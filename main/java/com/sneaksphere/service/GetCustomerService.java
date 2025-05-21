package com.sneaksphere.service;

import com.sneaksphere.config.Dbconfig;
import com.sneaksphere.model.UserModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetCustomerService {
    private Connection dbConn;

    /**
     * @para None
     * Constructor initializes database connection using Dbconfig class.
     */
    public GetCustomerService() {
        try {
            dbConn = Dbconfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @para None
     * Retrieves a list of all users with the role 'Customer' from the database.
     *
     * @return List<UserModel> - A list of UserModel objects representing customers.
     */
    public List<UserModel> getAllCustomers() {
        List<UserModel> customers = new ArrayList<>();
        String query = "SELECT UserID, UserFirstName, UserLastName, UserEmail, UserPhone, UserAddress FROM user WHERE Role = 'Customer'";

        try (Statement stmt = dbConn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                UserModel user = new UserModel();
                user.setUserId(rs.getInt("UserID"));
                user.setFirstName(rs.getString("UserFirstName"));
                user.setLastName(rs.getString("UserLastName"));
                user.setEmail(rs.getString("UserEmail"));
                user.setPhone(rs.getString("UserPhone"));
                user.setAddress(rs.getString("UserAddress"));
                customers.add(user);

            }
        } catch (SQLException e) {
            System.out.println("Error fetching customers: " + e.getMessage());
        }
        return customers;
    }

}
