package com.sneaksphere.controller;

import com.sneaksphere.service.UpdateOrderStatusService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/updateOrderStatus") // Servlet URL to handle the form submission
public class UpdateOrderStatusController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Get the orderId and newStatus from the request
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String newStatus = request.getParameter("newStatus");

        // Call service to update the order status in the database
        UpdateOrderStatusService updateService = new UpdateOrderStatusService();
        boolean updated = updateService.updateOrderStatus(orderId, newStatus);

        // After updating, redirect to the order page 
        if (updated) {
            response.sendRedirect("/SneakSphere/adminOrder");  // Redirect to the order page after update
        } else {
            response.getWriter().write("Error updating status");
        }
    }
}