package com.sneaksphere.controller;

// Import necessary models and services

import com.sneaksphere.service.OrderService;
import com.sneaksphere.config.Dbconfig;

// Servlet and Java utility imports
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Servlet to handle viewing and modifying the order history for the logged-in user.
 * 
 * @WebServlet("/viewOrderHistory") - Maps this servlet to the given URL.
 */
@WebServlet("/viewOrderHistory")
public class ViewOrderHistoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to display the order history for the currently logged-in user.
     * 
     * @param request  HttpServletRequest containing client request data.
     * @param response HttpServletResponse used to send the response.
     * @throws ServletException if a servlet error occurs.
     * @throws IOException      if an I/O error occurs.
     * 
     * @para username Retrieved from session, represents the logged-in user's email.
     * @return void - forwards request to JSP displaying order history or sends error status.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Retrieve the currently logged-in user's username (email) from the session
        String username = (String) request.getSession().getAttribute("username");

        // If no user is logged in, send a 401 Unauthorized error
        if (username == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in.");
            return;
        }

        try {
            // Find the user ID based on their email (username)
            int userId = getUserIdByUsername(username);

            // If the user doesn't exist in the database, send a 404 error
            if (userId == -1) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
                return;
            }

            // Fetch the order history, including sneakers, for this user
            OrderService orderService = new OrderService();
            List<Map<String, Object>> orderDetails = orderService.getOrdersWithSneakersByUserID(userId);

            // Add the order details to the request scope so the JSP can display them
            request.setAttribute("orderDetails", orderDetails);

            // Forward the request to the JSP page that shows the order history
            request.getRequestDispatcher("/WEB-INF/pages/viewOrderHistory.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            // Print the exception to the console for debugging
            e.printStackTrace();

            // Let the client know something went wrong on the server
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching order history.");
        }
    }

    /**
     * Helper method to get the user ID from the database using their email.
     * 
     * @param username Email address of the user.
     * @throws SQLException           if a database access error occurs.
     * @throws ClassNotFoundException if database driver class not found.
     * 
     * @para username The email of the user to search.
     * @return int Returns the UserID if found; -1 if user does not exist.
     */
    private int getUserIdByUsername(String username) throws SQLException, ClassNotFoundException {
        int userId = -1;

        // SQL query to find the user ID based on email
        String sql = "SELECT UserID FROM User WHERE UserEmail = ?";

        // Use try-with-resources to safely open and close the database connection
        try (java.sql.Connection conn = Dbconfig.getDbConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the email parameter in the query
            stmt.setString(1, username);

            // Execute the query and check if a result is found
            try (java.sql.ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getInt("UserID"); // Get the UserID from the result
                }
            }
        }

        return userId; // Return the user ID or -1 if not found
    }

    /**
     * Handles POST requests to delete an item (sneaker) from a user's order.
     * If all sneakers in the order are deleted, the order itself is deleted.
     * 
     * @param request  HttpServletRequest containing form data including orderId and sneakerId.
     * @param response HttpServletResponse used to send redirection or error status.
     * @throws ServletException if a servlet error occurs.
     * @throws IOException      if an I/O error occurs.
     * 
     * @para username The logged-in user's email from session.
     * @para orderId The ID of the order from which to delete the sneaker.
     * @para sneakerId The ID of the sneaker to delete from the order.
     * @return void - redirects to order history page with success or error messages.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the logged-in user's email from the session
        String username = (String) request.getSession().getAttribute("username");

        // If not logged in, return an unauthorized error
        if (username == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in.");
            return;
        }

        // Get the order ID and sneaker ID from the form submission
        String orderIdStr = request.getParameter("orderId");
        String sneakerIdStr = request.getParameter("sneakerId");

        // If required parameters are missing, redirect with an error message
        if (orderIdStr == null || sneakerIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/viewOrderHistory?error=missing_parameters");
            return;
        }

        try {
            // Convert the order ID and sneaker ID to integers
            int orderId = Integer.parseInt(orderIdStr);
            int sneakerId = Integer.parseInt(sneakerIdStr);

            // Connect to the database to start modifying order details
            try (java.sql.Connection conn = Dbconfig.getDbConnection()) {
                conn.setAutoCommit(false); // Start transaction manually

                // Delete the selected sneaker from the order
                String deleteItemSql = "DELETE FROM user_sneaker_order WHERE OrderID = ? AND SneakerID = ?";
                try (java.sql.PreparedStatement stmt = conn.prepareStatement(deleteItemSql)) {
                    stmt.setInt(1, orderId);
                    stmt.setInt(2, sneakerId);
                    stmt.executeUpdate();
                }

                // Check if there are any sneakers left in the order
                String checkRemainingSql = "SELECT COUNT(*) FROM user_sneaker_order WHERE OrderID = ?";
                try (java.sql.PreparedStatement stmt = conn.prepareStatement(checkRemainingSql)) {
                    stmt.setInt(1, orderId);
                    try (java.sql.ResultSet rs = stmt.executeQuery()) {
                        if (rs.next() && rs.getInt(1) == 0) {
                            // If no sneakers are left, delete the order itself
                            String deleteOrderSql = "DELETE FROM order WHERE OrderID = ?";
                            try (java.sql.PreparedStatement deleteStmt = conn.prepareStatement(deleteOrderSql)) {
                                deleteStmt.setInt(1, orderId);
                                deleteStmt.executeUpdate();
                            }
                        }
                    }
                }

                // Commit all changes to the database
                conn.commit();
            }

            // After deletion, redirect to the order history with a success message
            response.sendRedirect(request.getContextPath() + "/viewOrderHistory?message=item_deleted");

        } catch (NumberFormatException e) {
            // Handle invalid ID format from user input
            response.sendRedirect(request.getContextPath() + "/viewOrderHistory?error=invalid_parameters");
        } catch (SQLException | ClassNotFoundException e) {
            // If something goes wrong in the DB, show error
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting item.");
        }
    }
}
