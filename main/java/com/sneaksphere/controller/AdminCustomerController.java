package com.sneaksphere.controller;

import com.sneaksphere.service.GetCustomerService;
import com.sneaksphere.service.UserService;
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
 * AdminCustomerController handles requests related to managing customers on the admin side.
 *
 * <p>This servlet fetches the list of all registered customers from the database
 * and forwards the data to the "admin Customer.jsp" page for display. It also retrieves
 * the admin user’s information based on session data and makes it available in the request
 * and session attributes.</p>
 * 
 * @author 
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/adminCustomer"})
public class AdminCustomerController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to the /adminCustomer URL.
     *
     * <p>Retrieves the admin user by session email, fetches all customer records,
     * and forwards them to the appropriate JSP for admin display.</p>
     *
     * @param request  the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an input or output error is detected when the servlet handles the request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("username");

        // Get admin user details
        UserService userService = new UserService();
        UserModel adminUser = userService.getUserByEmail(email);

        // Set admin user in request and session
        if (adminUser != null) {
            request.setAttribute("user", adminUser);
            session.setAttribute("loggedInUser", adminUser);
        }

        // Fetch all customers
        GetCustomerService getCustomerService = new GetCustomerService();
        List<UserModel> customers = getCustomerService.getAllCustomers();

        // Set customers in request attribute
        request.setAttribute("customers", customers);

        // Forward to admin customer JSP
        request.getRequestDispatcher("/WEB-INF/pages/admin Customer.jsp").forward(request, response);
    }
}
