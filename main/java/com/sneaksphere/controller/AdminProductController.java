package com.sneaksphere.controller;

import com.sneaksphere.model.SneakerModel;
import com.sneaksphere.model.UserModel;
import com.sneaksphere.service.GetSneakerService;
import com.sneaksphere.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

/**
 * Servlet to manage sneaker products in the admin panel.
 * 
 * <p>Handles displaying the list of sneakers, redirecting to the update form,
 * and deleting sneakers.</p>
 * 
 * URL pattern: /adminProduct
 * 
 * @author
 */
@WebServlet("/adminProduct")
public class AdminProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to fetch and display the list of sneakers and admin user details.
     *
     * @param request  HttpServletRequest object containing the client request
     * @param response HttpServletResponse object used to send the response
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs during processing
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("username");
        
        // Get admin user details
        UserService userService = new UserService();
        UserModel adminUser = userService.getUserByEmail(email);
        
        if (adminUser != null) {
            request.setAttribute("user", adminUser);
            session.setAttribute("loggedInUser", adminUser);
        }
        
        // Fetch sneaker list
        GetSneakerService service = new GetSneakerService();
        List<SneakerModel> sneakerList = service.getAllSneakers();
        
        // Pass sneaker list to JSP
        request.setAttribute("sneakerList", sneakerList);
        
        // Forward to JSP page
        request.getRequestDispatcher("/WEB-INF/pages/admin Product.jsp").forward(request, response);
    }
    
    /**
     * Handles POST requests for actions on sneakers such as updating or deleting.
     * Expects an "action" parameter to determine the requested operation.
     *
     * @param request  HttpServletRequest containing client data
     * @param response HttpServletResponse to send back to the client
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs during processing
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing.");
            return;
        }
        
        int sneakerID;
        try {
            sneakerID = Integer.parseInt(request.getParameter("sneakerID"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid sneaker ID.");
            return;
        }
        
        switch (action) {
            case "updateForm":
                handleUpdateForm(request, response, sneakerID);
                break;
                
            case "delete":
                handleDelete(request, response, sneakerID);
                break;
                
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
        }
    }
    
    /**
     * Processes the update form request by fetching the sneaker details,
     * storing it in session, and redirecting to the update page.
     *
     * @param request   HttpServletRequest containing client data
     * @param response  HttpServletResponse for sending response
     * @param sneakerID The ID of the sneaker to update
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs during processing
     */
    private void handleUpdateForm(HttpServletRequest request, HttpServletResponse response, int sneakerID)
            throws ServletException, IOException {
        
        GetSneakerService sneakerService = new GetSneakerService();
        SneakerModel sneaker = sneakerService.getSpecificSneakerInfo(sneakerID);
        
        if (sneaker != null) {
            request.getSession().setAttribute("sneaker", sneaker);
            response.sendRedirect(request.getContextPath() + "/updateProduct");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Sneaker not found with ID: " + sneakerID);
        }
    }
    
    /**
     * Handles deleting a sneaker by its ID and forwards back to the product page with success/error message.
     *
     * @param request   HttpServletRequest containing client data
     * @param response  HttpServletResponse for sending response
     * @param sneakerID The ID of the sneaker to delete
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs during processing
     */
    private void handleDelete(HttpServletRequest request, HttpServletResponse response, int sneakerID)
            throws ServletException, IOException {
        
        GetSneakerService sneakerService = new GetSneakerService();
        String errorMessage = sneakerService.deleteSneaker(sneakerID);
        
        if (errorMessage == null) {
            request.setAttribute("successMessage", "Sneaker deleted successfully.");
        } else {
            request.setAttribute("errorMessage", errorMessage);
        }
        
        // Refresh the product list page after delete
        doGet(request, response);
    }
}
