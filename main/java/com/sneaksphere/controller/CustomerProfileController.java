package com.sneaksphere.controller;

import com.sneaksphere.model.UserModel;
import com.sneaksphere.service.UserService;
import com.sneaksphere.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller responsible for handling customer profile view requests.
 * It retrieves the current user's profile information using their session email
 * and forwards the data to the customer profile JSP for rendering.
 * 
 * @author Riya Basnet
 */
@WebServlet("/customerProfileController")
public class CustomerProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // Instance of UserService to interact with user-related business logic and database access 
    private final UserService userService = new UserService();

    /**
     * Handles GET requests to view the customer profile.
     * Retrieves user email from session, fetches user data from the service layer,
     * and forwards the user object to the JSP page for display.
     *
     * @param req  the HttpServletRequest object that contains the request the client made of the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs during request handling
     * @return void
     */
     @Override
      protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // Retrieve the logged-in user's email from the session
      String email = (String) SessionUtil.getAttribute(req, "username");
      
    // If no user is logged in, redirect to the login page
        if (email == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

     // Fetch user details from the database using the email
        UserModel customer = userService.getUserByEmail(email);
       //Debugging log to verify if user is successfully retrieved.
        System.out.println("Controller received user: " + customer);
        
        if (customer != null) {
            // Log if customer object is null or not
            System.err.println("Customer object retrieved: " + customer);
            // Log the Image URL to debug
            System.out.println("Image URL: " + customer.getUserImageURL());
            
         // Store the user object in the request scope for use in JSP
            req.setAttribute("user", customer);
            
         // Forward the request to the customer profile JSP page
            req.getRequestDispatcher("/WEB-INF/pages/customerProfile.jsp").forward(req, resp);
        } else {
            resp.getWriter().println("Customer profile not found.");
        }
    }
     
}
