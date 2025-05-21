package com.sneaksphere.controller;

import com.sneaksphere.model.SneakerModel;
import com.sneaksphere.model.UserModel;
import com.sneaksphere.service.ProductService;
import com.sneaksphere.service.WishListService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/womensProduct")
public class ProductWomensController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductService productService = new ProductService();
    // Initializes the WishListService to manage WishList operations, such as adding or removing items
    private WishListService wishListService = new WishListService();

    /**
     * Handles HTTP GET requests to fetch and display women's products.
     * Retrieves products filtered by the category "Womens" and optional sorting options.
     * Also retrieves logged-in user information from the session.
     * Sets necessary attributes for rendering the product listing JSP page.
     *
     * @param request HttpServletRequest containing client request data, including sorting parameter
     * @param response HttpServletResponse used to send the response or forward to JSP
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an input or output error occurs during request processing
     *
     * @return void (forwards to /WEB-INF/pages/product.jsp with data)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        try {
            // Get sorting parameter from request
            String sortOption = request.getParameter("sort");
            System.out.println(">>> Sort option received: " + sortOption);

            // Fetch the currently logged-in user from the session
            HttpSession session = request.getSession();
            UserModel loggedInUser = (UserModel) session.getAttribute("loggedInUser");

            if (loggedInUser != null) {
                System.out.println(">>> Logged-in user: " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
            } else {
                System.out.println(">>> No user logged in.");
            }

            // Default category is "Womens"
            String category = "Womens";

            // Get products based on category and sorting option
            List<SneakerModel> products = productService.getProductsByCategory(category, sortOption);

            // Set attributes for JSP
            request.setAttribute("products", products);
            request.setAttribute("resultsCount", products.size());
            request.setAttribute("currentSort", sortOption); // Maintain selected sort option

            if (products.isEmpty()) {
                request.setAttribute("message", "No women's shoes found.");  // fixed message to reflect womens category
            }

        } catch (Exception e) {
            System.err.println("Error in ProductWomensController: " + e.getMessage());
            request.setAttribute("error", "Could not load women's shoes. Please try again later."); // fixed message
            e.printStackTrace();
        }

        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests by calling doGet and then redirecting to WishlistController.
     * Note: Redirect after forward may cause IllegalStateException; typically you should do one or the other.
     *
     * @param request HttpServletRequest containing client request data
     * @param response HttpServletResponse used to redirect the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an input or output error occurs during request processing
     *
     * @return void
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
        // Redirect all POST requests to the WishlistController
        response.sendRedirect(request.getContextPath() + "/wishlist");
    }
}
