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

    /**
     * Handles HTTP GET requests to search for products by their name.
     * Retrieves the search term from the request parameter "query".
     * If the search term is empty or null, sets an error message and forwards to the search page.
     * Otherwise, performs a search using the SearchService and sets the results and search term as request attributes.
     * Handles exceptions by setting an error message.
     *
     * @param request  HttpServletRequest containing client request data including the search query parameter
     * @param response HttpServletResponse used to forward the request and response to the JSP page
     * @throws ServletException if a servlet-specific error occurs during processing
     * @throws IOException if an input or output error occurs during request processing
     *
     * @return void (forwards to /WEB-INF/pages/search product.jsp with data or error message)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String searchTerm = request.getParameter("query");
        
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            request.setAttribute("error", "Please enter a search term");
            request.getRequestDispatcher("/WEB-INF/pages/search product.jsp").forward(request, response);
            return;
        }
        
        try {
            List<SneakerModel> results = searchService.searchByProductName(searchTerm);
            request.setAttribute("searchResults", results);
            request.setAttribute("searchTerm", searchTerm);
            
            if (results.isEmpty()) {
                request.setAttribute("message", "No products found for: " + searchTerm);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Search failed. Please try again.");
        }
        
        request.getRequestDispatcher("/WEB-INF/pages/search product.jsp").forward(request, response);
    }
}
