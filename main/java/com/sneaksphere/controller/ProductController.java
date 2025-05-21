package com.sneaksphere.controller;

import com.sneaksphere.model.SneakerModel;
import com.sneaksphere.model.UserModel;
import com.sneaksphere.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import com.sneaksphere.service.WishListService;
import jakarta.servlet.http.HttpSession;

@WebServlet("/product")
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    //Initialize the ProductService to handle Product-related operations, such as fetching product data
    private final ProductService productService = new ProductService();
    //Initializes the WishListService to manage WishList operations, such as adding or removing items
    private WishListService wishListService = new WishListService();

    /**
     * Handles HTTP GET requests to fetch and display products.
     * Retrieves products filtered by a default category ("Mens") and optional sorting options.
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

            // Default category is "Mens"
            String category = "Mens";

            // Get products based on category and sorting option
            List<SneakerModel> products = productService.getProductsByCategory(category, sortOption);

            // Set attributes for JSP
            request.setAttribute("products", products);
            request.setAttribute("resultsCount", products.size());
            request.setAttribute("currentSort", sortOption); // Maintain selected sort option

            if (products.isEmpty()) {
                request.setAttribute("message", "No men's shoes found.");
            }

        } catch (Exception e) {
            System.err.println("Error in ProductController: " + e.getMessage());
            request.setAttribute("error", "Could not load men's shoes. Please try again later.");
            e.printStackTrace();
        }

        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests by redirecting them to the WishlistController.
     * This is used for wishlist related operations like adding/removing items.
     * 
     * @param request HttpServletRequest containing client request data
     * @param response HttpServletResponse used to redirect the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an input or output error occurs during request processing
     * 
     * @return void (redirects to /wishlist)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Redirect all POST requests to the WishlistController
        response.sendRedirect(request.getContextPath() + "/wishlist");
    }
}
