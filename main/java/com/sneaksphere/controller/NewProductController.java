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

@WebServlet("/newProduct")
public class NewProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductService productService = new ProductService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Get sorting parameter
            String sortOption = request.getParameter("sort");
            
            // Fetch both men's and women's shoes with sorting
            List<SneakerModel> products = productService.getProductsByCategories(
                sortOption, "Mens", "Womens");
            
            request.setAttribute("products", products);
            request.setAttribute("resultsCount", products.size());
            request.setAttribute("currentSort", sortOption); // For maintaining selected option
            
            if (products.isEmpty()) {
                request.setAttribute("message", "No shoes found in these categories.");
            }
            
        } catch (Exception e) {
            System.err.println("Error in NewProductController: " + e.getMessage());
            request.setAttribute("error", "Could not load shoes. Please try again later.");
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("/WEB-INF/pages/newProduct.jsp").forward(request, response);
    }
}