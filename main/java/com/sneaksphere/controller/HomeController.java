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

@WebServlet(urlPatterns = {"/home", "/"})
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            List<SneakerModel> trendingProducts = productService.getNewReleases(6);
            request.setAttribute("products", trendingProducts);
            
            if (trendingProducts.isEmpty()) {
                request.setAttribute("message", "No new releases found. Check back soon!");
            }
            
        } catch (Exception e) {
            System.err.println("Error in HomeController: " + e.getMessage());
            request.setAttribute("error", "Could not load products. Please try again later.");
            // Log the full stack trace for debugging
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
    }
}