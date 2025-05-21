package com.sneaksphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import com.sneaksphere.model.SneakerModel;
import com.sneaksphere.service.WishListService;

/**
 * WishlistController handles requests related to viewing, adding, and removing items 
 * from a user's wishlist on the SneakSphere platform.
 *
 * @author Riya Basnet
 * 
 */
@WebServlet("/wishlist")
public class WishlistController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final WishListService wishListService = new WishListService();

    /**
     * Handles GET requests to display the wishlist page.
     * If a user is logged in, retrieves and displays their wishlist items.
     * If not logged in, displays a guest view.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Integer userID = (session != null) ? (Integer) session.getAttribute("userID") : null;

        if (userID != null) {
            // Retrieve and display wishlist items for the logged-in user
            List<SneakerModel> wishlistItems = wishListService.getWishlistByUserID(userID);
            request.setAttribute("wishlistItems", wishlistItems);
        } else {
            // Show guest view if the user is not logged in
            request.setAttribute("guestUser", true);
        }

        request.getRequestDispatcher("/WEB-INF/pages/wishlist.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to add or remove sneakers from the user's wishlist.
     * The "action" parameter determines whether to add or remove the item.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        System.out.println(">>> POST request received in WishlistController");

        String action = request.getParameter("action");
        String sneakerIDStr = request.getParameter("sneakerID");

        HttpSession session = request.getSession(false);
        Integer userID = (session != null) ? (Integer) session.getAttribute("userID") : null;

        // Redirect to login if user is not logged in
        if (userID == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            int sneakerID = Integer.parseInt(sneakerIDStr);

            // Perform action based on request parameter
            if ("add".equals(action)) {
                wishListService.addToWishlist(userID, sneakerID);
            } else if ("remove".equals(action)) {
                wishListService.removeFromWishlist(userID, sneakerID);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                return;
            }

            // Redirect to wishlist page after processing
            response.sendRedirect(request.getContextPath() + "/wishlist");

        } catch (NumberFormatException e) {
            // Handle non-integer sneakerID gracefully
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid sneaker ID format.");
        }
    }
}
