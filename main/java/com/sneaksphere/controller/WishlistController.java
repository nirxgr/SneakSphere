package com.sneaksphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import com.sneaksphere.model.SneakerModel;
import com.sneaksphere.service.WishListService;
import java.util.List;

@WebServlet("/wishlist")
public class WishlistController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private WishListService wishListService = new WishListService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Integer userID = (session != null) ? (Integer) session.getAttribute("userID") : null;
        
        if (userID != null) {
            // For logged-in users: show their wishlist
            List<SneakerModel> wishlistItems = wishListService.getWishlistByUserID(userID);
            request.setAttribute("wishlistItems", wishlistItems);
        } else {
            // For guests: show empty wishlist or featured items
            request.setAttribute("guestUser", true);
        }
        
        request.getRequestDispatcher("/WEB-INF/pages/wishlist.jsp").forward(request, response);
    }
 // Handles POST request to add/remove from wishlist
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println(">>> POST request received in WishlistController");

        String action = request.getParameter("action");
        String sneakerIDStr = request.getParameter("sneakerID");

        HttpSession session = request.getSession(false);
        Integer userID = (session != null) ? (Integer) session.getAttribute("userID") : null;

        if (userID == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            int sneakerID = Integer.parseInt(sneakerIDStr);

            if ("add".equals(action)) {
                // Add to wishlist
                wishListService.addToWishlist(userID, sneakerID);
            } else if ("remove".equals(action)) {
                //  Remove from wishlist (NEW FEATURE)
                wishListService.removeFromWishlist(userID, sneakerID);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                return;
            }

            response.sendRedirect(request.getContextPath() + "/wishlist");

        } catch (NumberFormatException e) {
            //  Handle invalid sneakerID
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid sneaker ID format.");
        }
    }
}