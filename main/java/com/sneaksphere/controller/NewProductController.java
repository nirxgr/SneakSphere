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


/**
 * Servlet controller to handle displaying new sneaker products
 * with optional sorting and category filtering.
 * It also redirects POST requests to the wishlist handler.
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/newProduct"})
public class NewProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    //Initialize the ProductService to handle Product-related operations, such as fetching product data
    private final ProductService productService = new ProductService();
    //Initializes the WishListService to manage WishList operations, such as adding or removing items
    private WishListService wishListService = new WishListService();
    
    
    /**
     * Handles HTTP GET requests to fetch and display sneaker products,
     * optionally sorted by the provided 'sort' parameter.
     * 
     * @param request  the HttpServletRequest object containing client request data
     * @param response the HttpServletResponse object to send the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs during request processing
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Get sorting parameter from request
            String sortOption = request.getParameter("sort");
            System.out.println(">>> Sort option received: " + sortOption);

            // Fetch the currently logged-in user from the session
                  
            
            // Get products based on category and sorting option
            List<SneakerModel> products = productService.getProductsByCategories(
                    sortOption, "Mens", "Womens");
            
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
        
        request.getRequestDispatcher("/WEB-INF/pages/newProduct.jsp").forward(request, response);
    }
    
    
    /**
     * Handles HTTP POST requests by redirecting all to the WishlistController.
     * 
     * @param request  the HttpServletRequest object containing client request data
     * @param response the HttpServletResponse object to send the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs during request processing
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Redirect all POST requests to the WishlistController
        response.sendRedirect(request.getContextPath() + "/wishlist");
    }
}