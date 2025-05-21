package com.sneaksphere.controller;

import com.sneaksphere.service.GetOrderService;
import com.sneaksphere.service.UserService;
import com.sneaksphere.model.OrderModel;
import com.sneaksphere.model.UserModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Servlet to manage order-related admin tasks.
 *
 * <p>This servlet handles the retrieval of all orders for display on the admin page,
 * as well as updating order statuses via POST requests.</p>
 * 
 * <p>GET requests fetch all orders and admin user information,
 * then forward to the "admin Order.jsp" page.</p>
 * 
 * <p>POST requests update the status of a specific order and redirect back to the admin order page.</p>
 * 
 * @author
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/adminOrder"})
public class AdminOrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to retrieve and display all orders to the admin.
     *
     * @param request  the HttpServletRequest object containing client request data
     * @param response the HttpServletResponse object for sending response data
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs during request processing
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("username");
        
        // Get admin user details
        UserService userService = new UserService();
        UserModel adminUser = userService.getUserByEmail(email);
        
        // Set admin user in request attributes
        if (adminUser != null) {
            request.setAttribute("user", adminUser);
            session.setAttribute("loggedInUser", adminUser);
        }
        
        // Fetch orders from the database
        GetOrderService getOrderService = new GetOrderService();
        List<OrderModel> orders = getOrderService.getAllOrders();
        
        // Pass orders data to JSP
        request.setAttribute("orders", orders);
        
        // Forward request to adminOrder.jsp
        request.getRequestDispatcher("/WEB-INF/pages/admin Order.jsp").forward(request, response);
    }
    
    /**
     * Handles POST requests to update the status of a specific order.
     *
     * @param request  the HttpServletRequest object containing client request data
     * @param response the HttpServletResponse object for sending response data
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs during request processing
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int orderID = Integer.parseInt(request.getParameter("orderID"));
        String orderStatus = request.getParameter("orderStatus");
        
        // Update order status in the database
        GetOrderService getOrderService = new GetOrderService();
        boolean updated = getOrderService.updateOrderStatus(orderID, orderStatus);
        
        HttpSession session = request.getSession();
        if (updated) {
            session.setAttribute("successMessage", "✅ Order status updated successfully!");
        } else {
            session.setAttribute("errorMessage", "❌ Failed to update order status.");
        }
        
        // Redirect back to the admin order page
        response.sendRedirect(request.getContextPath() + "/adminOrder");
    }
}
