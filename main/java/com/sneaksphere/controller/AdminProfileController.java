package com.sneaksphere.controller;

import com.sneaksphere.model.UserModel;
import com.sneaksphere.service.UserService;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
/**
 * AdminProfileController
 * 
 * This servlet handles the logic to display the Admin's profile information.
 * It retrieves the admin details using session data (email stored during login)
 * and forwards it to the profile display JSP page.
 * 
 * If no session email is found, it defaults to admin ID 1 (as a fallback).
 * 
 *  @author Riya Basnet
 *  
 */
@WebServlet("/adminProfileController") // this URL loads the admin profile page
public class AdminProfileController extends HttpServlet {
	
    private static final long serialVersionUID = 1L;
    private UserService userService; // Service layer to interact with the database

    /**
     * Initializes the UserService during servlet startup.
     * Called once when the servlet is first loaded into memory.
     */
    @Override
    public void init() throws ServletException {
        userService = new UserService(); // Instantiate service to use database methods
    }
    /**
     * Handles GET requests to view the admin profile.
     * Retrieves the admin email from session, fetches the user from DB,
     * and forwards the data to a JSP page for display.
     *
     * @param request  HTTP request from the browser
     * @param response HTTP response to the browser
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// Retrieve the current session (create if doesn't exist)
    	HttpSession session = request.getSession();
    	// Retrieve the current session (create if doesn't exist)
    	String email = (String) session.getAttribute("username");

        if (email == null) {
            // If email not found in session, fallback to default admin ID with 1
            UserModel admin = userService.getUserById(1); // Gets admin with ID 1 from DB

            if (admin != null) {
            	// Use admin's email as session attribute for future use
                email = admin.getEmail();
                session.setAttribute("username", email); // Store it for future
            } else {
            	// If even default admin not found, redirect to login
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
        }

        // Fetch admin user details using the session email
        UserModel admin = userService.getUserByEmail(email);

        if (admin != null) {
        	// Pass the user object to JSP using request attribute
            request.setAttribute("user", admin);
            // Forward to profile display JSP (adminProfile.jsp)
            request.getRequestDispatcher("/WEB-INF/pages/adminProfile.jsp").forward(request, response);
        } else {
        	 // Handle case when admin is not found in DB
        	request.setAttribute("errorMessage", "Admin profile not found.");
        	request.getRequestDispatcher("/WEB-INF/pages/adminProfile.jsp").forward(request, response);
        }
    }
}