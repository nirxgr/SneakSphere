package com.sneaksphere.controller;

import com.sneaksphere.model.SneakerModel;
import com.sneaksphere.service.SearchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/searchProduct")
public class SearchProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final SearchService searchService = new SearchService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String searchTerm = request.getParameter("query");
        
        try {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                request.setAttribute("error", "Please enter a search term");
            } else {
                List<SneakerModel> results = searchService.searchByProductName(searchTerm);
                request.setAttribute("searchResults", results);
                request.setAttribute("searchTerm", searchTerm);
                
                if (results.isEmpty()) {
                    request.setAttribute("message", "No products found for: " + searchTerm);
                }
            }
        } catch (Exception e) {
            System.err.println("Search error: " + e.getMessage());
            request.setAttribute("error", "Search failed. Please try again.");
        }
        
        request.getRequestDispatcher("/WEB-INF/pages/search product.jsp").forward(request, response);
    }
}