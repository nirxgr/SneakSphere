package com.sneaksphere.controller;

import com.sneaksphere.model.UserModel;
import com.sneaksphere.service.UserService;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
/**
 * Servlet implementation class UserProfileController
 */
@WebServlet("/admin/profile") // this URL loads the admin profile page
public class AdminProfileController extends HttpServlet {
	
    private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserService userService = new UserService();

        // For now, we are just fetching UserID = 1 (admin only)
        UserModel user = userService.getAdminProfile();

       if (user != null) {
            request.setAttribute("user", user); // setting user object to forward to JSP
            request.getRequestDispatcher("/WEB-INF/pages/adminProfile.jsp").forward(request, response);
        } else {
            // Optional: Show simple error message if user not found
            response.getWriter().println("Admin profile not found.");
        }
    }
}