package com.sneaksphere.controller;

import com.sneaksphere.model.SneakerModel;
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
    private final ProductService productService = new ProductService();
    private WishListService wishListService = new WishListService();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Fetch only men's shoes
            List<SneakerModel> products = productService.getProductsByCategory("Mens");
            request.setAttribute("products", products);
            
            // Set the count of results
            request.setAttribute("resultsCount", products.size());
            
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Redirect all POST requests to the WishlistController
        response.sendRedirect(request.getContextPath() + "/wishlist");
    }
}