package com.sneaksphere.controller;

import com.sneaksphere.service.UpdateOrderStatusService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * UpdateOrderStatusController handles POST requests to update the status of an order.
 * 
 * @WebServlet("/updateOrderStatus") - Maps the servlet to handle requests at the specified URL.
 */
@WebServlet("/updateOrderStatus") // Servlet URL to handle the form submission
public class UpdateOrderStatusController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Processes POST requests to update an order's status.
     *
     * @param request  HttpServletRequest object containing client request parameters.
     * @param response HttpServletResponse object to send response back to client.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException      if an I/O error occurs during request handling.
     * 
     * @para orderId Extracted from request parameter, represents the ID of the order to update.
     * @para newStatus Extracted from request parameter, represents the new status to set for the order.
     * @return void - redirects the client to the admin order page after updating the order status.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Get the orderId and newStatus from the request
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String newStatus = request.getParameter("newStatus");

        // Call service to update the order status in the database
        UpdateOrderStatusService updateService = new UpdateOrderStatusService();
        boolean updated = updateService.updateOrderStatus(orderId, newStatus);

        HttpSession session = request.getSession();

        // After updating, redirect to the order page 
        if (updated) {
            session.setAttribute("successMessage", "✅ Order status updated successfully!");
        } else {
            session.setAttribute("errorMessage", "❌ Failed to update order status.");
        }

        response.sendRedirect(request.getContextPath() + "/adminOrder");
    }
}
