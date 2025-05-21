package com.sneaksphere.controller;

import com.sneaksphere.model.SneakerModel;
import com.sneaksphere.service.CartService;
import com.sneaksphere.service.*;
import com.sneaksphere.model.OrderModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

// This servlet handles all operations related to the shopping cart
@WebServlet("/cart")
public class CartController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Create an instance of the CartService to interact with the cart-related business logic
    private CartService cartService = new CartService();

    /**
     * Handles GET requests (e.g., viewing the cart).
     * Retrieves the current user's cart items and handles success/error messages.
     *
     * @param request  HttpServletRequest object containing client request
     * @param response HttpServletResponse object for sending response to client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the current user session
        HttpSession session = request.getSession(false);

        // Retrieve userID from session if user is logged in
        Integer userID = (session != null) ? (Integer) session.getAttribute("userID") : null;

        // Handle success messages for displaying feedback on the cart page
        String message = request.getParameter("message");
        if ("item_removed".equals(message)) {
            request.setAttribute("successMessage", "Item successfully removed from cart");
        } else if ("checkout_success".equals(message)) {
            request.setAttribute("successMessage", "Order placed successfully! Your cart has been cleared");
        }

        // Handle error messages for feedback
        String error = request.getParameter("error");
        if ("remove_failed".equals(error)) {
            request.setAttribute("errorMessage", "Failed to remove item from cart");
        } else if ("checkout_failed".equals(error)) {
            request.setAttribute("errorMessage", "Failed to process checkout");
        }

        if (userID != null) {
            try {
                // Fetch the list of sneakers in the user's cart
                List<SneakerModel> sneakerCartList = cartService.getCartByUserID(userID);
                request.setAttribute("sneakerCartList", sneakerCartList); // Pass the list to JSP
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                request.setAttribute("error", "Unable to load cart items.");
            }
        } else {
            // Mark the user as a guest if not logged in
            request.setAttribute("guestUser", true);
        }

        // Forward the request to the cart JSP page
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    /**
     * Handles POST requests (e.g., add, remove, or checkout actions).
     * Delegates specific operations based on 'action' parameter.
     *
     * @param request  HttpServletRequest object containing client request
     * @param response HttpServletResponse object for sending response to client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the action parameter (add, remove, or checkout)
        String action = request.getParameter("action");

        // Get the current session and retrieve user ID
        HttpSession session = request.getSession(false);
        Integer userID = (session != null) ? (Integer) session.getAttribute("userID") : null;

        // If user is not logged in, redirect to login page
        if (userID == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // Handle different actions based on the value of the 'action' parameter
            if ("add".equals(action)) {
                handleAddToCart(request, response, userID);
            } else if ("remove".equals(action)) {
                handleRemoveFromCart(request, response, userID);
            } else if ("checkout".equals(action)) {
                handleCheckout(request, response, userID);
            } else {
                // If action is invalid or missing, redirect back to cart
                response.sendRedirect(request.getContextPath() + "/cart");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Redirect with a generic operation failure error
            response.sendRedirect(request.getContextPath() + "/cart?error=operation_failed");
        }
    }

    /**
     * Handles adding an item to the cart.
     *
     * @param request  HttpServletRequest containing parameters sneakerID, price, and quantity
     * @param response HttpServletResponse used for redirecting after operation
     * @param userID   ID of the current logged-in user
     * @throws IOException           if an I/O error occurs during redirect
     * @throws SQLException          if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response, int userID)
            throws IOException, SQLException, ClassNotFoundException {

        // Retrieve sneaker ID, price, and quantity from request parameters
        String sneakerIDStr = request.getParameter("sneakerID");
        String priceStr = request.getParameter("price");
        String quantityStr = request.getParameter("quantity");

        // If any parameter is missing, redirect with error
        if (sneakerIDStr == null || priceStr == null || quantityStr == null) {
            response.sendRedirect(request.getContextPath() + "/cart?error=missing_parameters");
            return;
        }

        try {
            // Parse the input parameters
            int sneakerID = Integer.parseInt(sneakerIDStr);
            double price = Double.parseDouble(priceStr);
            int quantity = Integer.parseInt(quantityStr);

            // Add the item to the cart using CartService
            boolean success = cartService.addToCart(userID, sneakerID, quantity, price);

            // Redirect with appropriate message based on success
            if (success) {
                response.sendRedirect(request.getContextPath() + "/cart?message=item_added");
            } else {
                response.sendRedirect(request.getContextPath() + "/cart?error=add_failed");
            }
        } catch (NumberFormatException e) {
            // Redirect if the input format is invalid
            response.sendRedirect(request.getContextPath() + "/cart?error=invalid_parameters");
        }
    }

    /**
     * Handles removing an item from the cart.
     *
     * @param request  HttpServletRequest containing parameter cartID
     * @param response HttpServletResponse used for redirecting after operation
     * @param userID   ID of the current logged-in user
     * @throws IOException           if an I/O error occurs during redirect
     * @throws SQLException          if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    private void handleRemoveFromCart(HttpServletRequest request, HttpServletResponse response, int userID)
            throws IOException, SQLException, ClassNotFoundException {

        // Get cart ID from the request
        String cartIDStr = request.getParameter("cartID");
        if (cartIDStr == null) {
            response.sendRedirect(request.getContextPath() + "/cart?error=missing_cart_id");
            return;
        }

        try {
            // Parse cart ID and remove the item from cart
            int cartID = Integer.parseInt(cartIDStr);
            boolean success = cartService.removeFromCart(userID, cartID);

            // Redirect with appropriate message based on success
            if (success) {
                response.sendRedirect(request.getContextPath() + "/cart?message=item_removed");
            } else {
                response.sendRedirect(request.getContextPath() + "/cart?error=remove_failed");
            }
        } catch (NumberFormatException e) {
            // Handle invalid cart ID input
            response.sendRedirect(request.getContextPath() + "/cart?error=invalid_cart_id");
        }
    }

    /**
     * Handles the checkout process.
     * Calculates the total order amount, places the order, and clears the cart.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse used for redirecting after operation
     * @param userID   ID of the current logged-in user
     * @throws IOException           if an I/O error occurs during redirect
     * @throws SQLException          if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    private void handleCheckout(HttpServletRequest request, HttpServletResponse response, int userID)
            throws IOException, SQLException, ClassNotFoundException {

        // Get all items from the user's cart
        List<SneakerModel> cartItems = cartService.getCartByUserID(userID);

        // If cart is empty, redirect with error
        if (cartItems == null || cartItems.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart?error=empty_cart");
            return;
        }

        // To Calculate total order amount
        float totalAmount = 0;
        for (SneakerModel item : cartItems) {
            totalAmount += item.getPrice() * item.getCartQuantity(); // Multiply item price by quantity
        }

        // To Create an OrderModel object to store order details
        OrderModel order = new OrderModel();
        order.setUserID(userID);
        order.setOrderDate(new java.util.Date()); // Current date
        order.setOrderTotal(totalAmount);

        //  To Place the order using OrderService
        OrderService orderService = new OrderService();
        int orderID = orderService.placeOrder(order, cartItems);

        // If the order is successfully placed, clear the cart and show success message
        if (orderID > 0) {
            cartService.clearCart(userID); // Remove all items from cart
            response.sendRedirect(request.getContextPath() + "/cart?message=checkout_success");
        } else {
            // Show error if order placement failed
            response.sendRedirect(request.getContextPath() + "/cart?error=checkout_failed");
        }
    }
}
