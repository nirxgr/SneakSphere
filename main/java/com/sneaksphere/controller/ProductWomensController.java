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

@WebServlet("/womensProduct")
public class ProductWomensController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductService productService = new ProductService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Get sorting parameter from request
            String sortOption = request.getParameter("sort");
            
            // Default category is "Mens"
            String category = "Womens";
            
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
        
        // Forward to JSP
        request.getRequestDispatcher("/WEB-INF/pages/productWomens.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}