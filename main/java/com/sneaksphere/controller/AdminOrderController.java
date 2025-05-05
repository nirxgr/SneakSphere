package com.sneaksphere.controller;

import com.sneaksphere.service.GetOrderService;
import com.sneaksphere.service.UpdateOrderStatusService;
import com.sneaksphere.model.OrderModel;  

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet to manage order-related admin tasks.
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/adminOrder"})
public class AdminOrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

    	// Fetch orders from the database
    	GetOrderService getOrderService = new GetOrderService();
    	List<OrderModel> orders = getOrderService.getAllOrders();

    	// Pass the orders data to the JSP
    	request.setAttribute("orders", orders);

    	// Forward the request to adminOrder.jsp
    	request.getRequestDispatcher("/WEB-INF/pages/admin Order.jsp").forward(request, response);
    }
    
 // Handle POST request (update order status)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String orderStatus = request.getParameter("orderStatus");
        
        // Update the order status in the database
        UpdateOrderStatusService updateOrderService = new UpdateOrderStatusService();
        updateOrderService.updateOrderStatus(orderId, orderStatus);

        // Redirect back to the admin order page after the update
        response.sendRedirect(request.getContextPath() + "/adminOrder");
    }

}