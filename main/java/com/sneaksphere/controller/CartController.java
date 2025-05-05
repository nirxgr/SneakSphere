package com.sneaksphere.controller;

import com.sneaksphere.model.SneakerModel;
import com.sneaksphere.service.CartService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/cart")
public class CartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer userID = (session != null) ? (Integer) session.getAttribute("userID") : null;

        // Handle success/error messages
        String message = request.getParameter("message");
        if ("item_removed".equals(message)) {
            request.setAttribute("successMessage", "Item successfully removed from cart");
        } else if ("checkout_success".equals(message)) {
            request.setAttribute("successMessage", "Order placed successfully! Your cart has been cleared");
        }

        String error = request.getParameter("error");
        if ("remove_failed".equals(error)) {
            request.setAttribute("errorMessage", "Failed to remove item from cart");
        } else if ("checkout_failed".equals(error)) {
            request.setAttribute("errorMessage", "Failed to process checkout");
        }

        if (userID != null) {
            try {
                List<SneakerModel> sneakerCartList = cartService.getCartByUserID(userID);
                request.setAttribute("sneakerCartList", sneakerCartList);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                request.setAttribute("error", "Unable to load cart items.");
            }
        } else {
            request.setAttribute("guestUser", true);
        }

        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        Integer userID = (session != null) ? (Integer) session.getAttribute("userID") : null;

        if (userID == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            if ("add".equals(action)) {
                handleAddToCart(request, response, userID);
            } else if ("remove".equals(action)) {
                handleRemoveFromCart(request, response, userID);
            } else if ("checkout".equals(action)) {
                handleCheckout(request, response, userID);
            } else {
                response.sendRedirect(request.getContextPath() + "/cart");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/cart?error=operation_failed");
        }
    }

    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response, int userID)
            throws IOException, SQLException, ClassNotFoundException {

        String sneakerIDStr = request.getParameter("sneakerID");
        String priceStr = request.getParameter("price");
        String quantityStr = request.getParameter("quantity");

        if (sneakerIDStr == null || priceStr == null || quantityStr == null) {
            response.sendRedirect(request.getContextPath() + "/cart?error=missing_parameters");
            return;
        }

        try {
            int sneakerID = Integer.parseInt(sneakerIDStr);
            double price = Double.parseDouble(priceStr);
            int quantity = Integer.parseInt(quantityStr);

            boolean success = cartService.addToCart(userID, sneakerID, quantity, price);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/cart?message=item_added");
            } else {
                response.sendRedirect(request.getContextPath() + "/cart?error=add_failed");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/cart?error=invalid_parameters");
        }
    }

    private void handleRemoveFromCart(HttpServletRequest request, HttpServletResponse response, int userID)
            throws IOException, SQLException, ClassNotFoundException {
        
        String cartIDStr = request.getParameter("cartID");
        if (cartIDStr == null) {
            response.sendRedirect(request.getContextPath() + "/cart?error=missing_cart_id");
            return;
        }

        try {
            int cartID = Integer.parseInt(cartIDStr);
            boolean success = cartService.removeFromCart(userID, cartID);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/cart?message=item_removed");
            } else {
                response.sendRedirect(request.getContextPath() + "/cart?error=remove_failed");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/cart?error=invalid_cart_id");
        }
    }
    
    private void handleCheckout(HttpServletRequest request, HttpServletResponse response, int userID)
            throws IOException, SQLException, ClassNotFoundException {
        
        boolean success = cartService.clearCart(userID);
        
        if (success) {
            response.sendRedirect(request.getContextPath() + "/cart?message=checkout_success");
        } else {
            response.sendRedirect(request.getContextPath() + "/cart?error=checkout_failed");
        }
    }
}