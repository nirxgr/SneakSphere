package com.sneaksphere.controller;

import com.sneaksphere.model.SneakerModel;
import com.sneaksphere.model.UserModel;
import com.sneaksphere.service.GetSneakerService;
import com.sneaksphere.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * AdminController handles requests to the admin dashboard.
 * 
 * <p>This servlet retrieves various statistics related to sneakers,
 * users, and sales, and forwards the data to the admin home JSP page
 * for display.</p>
 *
 * <p>It also ensures that the logged-in admin user's details are
 * available in the session and request attributes.</p>
 * 
 * @author 
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/admin"})
public class AdminController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to the /admin URL.
     * 
     * <p>Fetches the admin user by their email from the session,
     * retrieves dashboard statistics (sales, products, orders, customers),
     * and lists of top-performing sneakers and top customers.</p>
     * 
     * @param request  the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("username");

        // Get admin user details
        UserService userService = new UserService();
        UserModel adminUser = userService.getUserByEmail(email);

        // Set admin user in request attributes and session
        if (adminUser != null) {
            request.setAttribute("user", adminUser);
            session.setAttribute("loggedInUser", adminUser);
        }

        // Retrieve dashboard statistics
        GetSneakerService getSneakerService = new GetSneakerService();

        request.setAttribute("totalSales", getSneakerService.getTotalSales());
        request.setAttribute("totalProducts", getSneakerService.getTotalProducts());
        request.setAttribute("totalOrders", getSneakerService.getTotalOrders());
        request.setAttribute("totalCustomers", getSneakerService.getTotalCustomers());

        // Retrieve top-performing sneakers and top customers
        List<SneakerModel> topSneakers = getSneakerService.getTopPerformingSneakers();
        request.setAttribute("topSneakers", topSneakers);

        List<UserModel> topCustomers = getSneakerService.getTopCustomers();
        request.setAttribute("topCustomers", topCustomers);

        // Forward to admin home JSP
        request.getRequestDispatcher("/WEB-INF/pages/admin home page.jsp").forward(request, response);
    }
}
