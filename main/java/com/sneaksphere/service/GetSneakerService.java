package com.sneaksphere.service;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;


import com.sneaksphere.config.Dbconfig;
import com.sneaksphere.model.SneakerModel;
import com.sneaksphere.model.UserModel;

public class GetSneakerService {

    private Connection dbConn;
    private boolean isConnectionError = false;

    /**
     * Constructor initializes the database connection.
     */
    public GetSneakerService() {
        try {
            this.dbConn = Dbconfig.getDbConnection(); // Initialize connection from Dbconfig
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Database connection error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Gets all sneaker data from database.
     */
    public List<SneakerModel> getAllSneakers() {
        List<SneakerModel> sneakers = new ArrayList<>();
        String query = "SELECT * FROM sneaker"; // SELECT statement to get all sneakers

        // Use dbConn for database connection
        try (Statement stmt = dbConn.createStatement(); // Use the connection from the constructor
             ResultSet rs = stmt.executeQuery(query)) {

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

                sneakers.add(sneaker); // Add each sneaker to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sneakers; // Return the list of sneakers
    }
    
    
    /**
     * Gets the top 5 sneakers based on how many times they were purchased.
     * It calculates sales count and revenue.
     */
    	public List<SneakerModel> getTopPerformingSneakers() {
        List<SneakerModel> topSneakers = new ArrayList<>();

        String query = """
                SELECT s.SneakerID, s.SneakerName, s.Price, s.ImageUrl, COUNT(uso.SneakerID) AS Sales, 
                       COUNT(uso.SneakerID) * s.Price AS Revenue
                FROM sneaker s
                JOIN user_sneaker_order uso ON s.SneakerID = uso.SneakerID
                GROUP BY s.SneakerID, s.SneakerName, s.Price, s.ImageUrl
                ORDER BY Sales DESC
                LIMIT 5
        		""";

        try (Statement stmt = dbConn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                SneakerModel sneaker = new SneakerModel();
                sneaker.setSneakerID(rs.getInt("SneakerID"));
                sneaker.setSneakerName(rs.getString("SneakerName"));
                sneaker.setPrice(rs.getFloat("Price"));
                sneaker.setImageUrl(rs.getString("ImageUrl"));

                // These fields don't exist in your model yet – add if needed
                sneaker.setSales(rs.getInt("Sales"));
                sneaker.setRevenue(rs.getFloat("Revenue"));

                topSneakers.add(sneaker);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topSneakers;
    }
    	
    	
    	/**
         * Gets the top 5 most buying customers.
         * 
         */
    	
    	public List<UserModel> getTopCustomers() {
    	    List<UserModel> topCustomers = new ArrayList<>();

    	    String query = """
    	        SELECT u.UserFirstName, u.UserLastName, COUNT(uso.SneakerID) AS TotalPurchases,
    	               SUM(s.Price) AS TotalSpent
    	        FROM user u
    	        JOIN user_sneaker_order uso ON u.UserID = uso.UserID
    	        JOIN sneaker s ON uso.SneakerID = s.SneakerID
    	        GROUP BY u.UserID, u.UserFirstName, u.UserLastName
    	        ORDER BY TotalSpent DESC
    	        LIMIT 5
    	    		""";

    	    try (Statement stmt = dbConn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
    	        while (rs.next()) {
    	            UserModel user = new UserModel();
    	            user.setFirstName(rs.getString("UserFirstName"));
    	            user.setLastName(rs.getString("UserLastName"));
    	            user.setTotalPurchases(rs.getInt("TotalPurchases"));
    	            user.setTotalSpent(rs.getFloat("TotalSpent"));
    	            topCustomers.add(user);
    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }

    	    return topCustomers;
    	}
    	
    	/**
         * For statistics
         * 
         */
    	
    	
    	public String getTotalSales() {
    		if (isConnectionError) { 
    			return null;
    		}

    		String query = "SELECT SUM(OrderTotal) AS total FROM `order`;";
    		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
    			ResultSet result = stmt.executeQuery();
    			if (result.next()) {
    				return result.getString("total");
    			} else {
    				return "";
    			}
    		} 
    		catch (SQLException e) {
    			e.printStackTrace();
    			return null;
    		}
    	
    	}

    	
    	
    	public String getTotalProducts() {
    		if (isConnectionError) {
    			return null;
    		}
    		
    		String query = "SELECT COUNT(SneakerID) AS total FROM sneaker";
    		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
    			ResultSet result = stmt.executeQuery();
    			if (result.next()) {
    				return result.getString("total");
    			}
    			else {
    				return "";
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    			return null;
    		}
    		
    	}
    	
    	

    	public String getTotalOrders() {
    		if (isConnectionError) {
    			return null;
    		}
    		String query = "SELECT COUNT(OrderTotal) AS total FROM `order`;";
    		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
    			ResultSet result = stmt.executeQuery();
    			if (result.next()) {
    				return result.getString("total");
    			}
    			else {
    				return "";
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    			return null;
    		}
    		
    	}
    	
    	

    	public String getTotalCustomers() {
    		if (isConnectionError) {
    			return null;
    		}
    		String query = "SELECT COUNT(UserID) AS total FROM user WHERE Role = 'Customer'";
    		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
    			ResultSet result = stmt.executeQuery();
    			if (result.next()) {
    				return result.getString("total");
    			}
    			else {
    				return "";
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    			return null;
    		}
    			
    		
    	}
    	
    	
    	public SneakerModel getSpecificSneakerInfo(int sneakerID) {
    		if (isConnectionError) {
    			System.out.println("Connection Error!");
    			return null;
    		}
    		// SQL query to join student and program tables
    		String query = "SELECT * FROM sneaker WHERE SneakerID = ?";
    		SneakerModel sneaker = null;


    	    try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
    	        stmt.setInt(1, sneakerID);
    	        ResultSet rs = stmt.executeQuery();

    	        if (rs.next()) {
    	            sneaker = new SneakerModel();
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
    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
    	    return sneaker;
    	}
    	
    	
    	public String deleteSneaker(int sneakerID) {
    	    if (isConnectionError)
    	        return "Database connection error.";

    	    try {
    	        // Check if sneaker is in cart
    	        String checkCart = "SELECT 1 FROM user_sneaker_cart WHERE SneakerID = ? LIMIT 1";
    	        try (PreparedStatement cartCheck = dbConn.prepareStatement(checkCart)) {
    	            cartCheck.setInt(1, sneakerID);
    	            if (cartCheck.executeQuery().next()) {
    	             
    	                return "Cannot delete: Sneaker is in someone's cart.";
    	            }
    	        	}

    	        // Check in user_sneaker_order
    	        String checkOrder = "SELECT 1 FROM user_sneaker_order WHERE SneakerID = ? LIMIT 1";
    	        try (PreparedStatement orderCheck = dbConn.prepareStatement(checkOrder)) {
    	            orderCheck.setInt(1, sneakerID);
    	            if (orderCheck.executeQuery().next()) {
    	                
    	                return "Cannot delete: Sneaker has already been ordered.";
    	            }
    	            }
    	        

    	        // Safe to delete
    	        String deleteQuery = "DELETE FROM sneaker WHERE SneakerID = ?";
    	        try (PreparedStatement stmt = dbConn.prepareStatement(deleteQuery)) {
    	            stmt.setInt(1, sneakerID);
    	            int rows = stmt.executeUpdate();
    	            if (rows > 0) {
    	                return null; // Success
    	            } else {
    	                return "Deletion failed. Sneaker not found.";
    	            }
    	        }

    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	        return "Internal server error during deletion.";
    	    }
    	}

    	
    
    
}