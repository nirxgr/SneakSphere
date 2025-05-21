package com.sneaksphere.controller;

import com.sneaksphere.model.SneakerModel;
import com.sneaksphere.service.ProductService;
//Throws servlet related errors
import jakarta.servlet.ServletException;
//Used to define URL patterns for this servlet
import jakarta.servlet.annotation.WebServlet;
//Base classes for handling HTTP requests and responses 
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//For input/Output errors
import java.io.IOException;
//For sorting a list of SneakerModel Object
import java.util.List;

@WebServlet(urlPatterns = {"/home", "/"})
public class HomeController extends HttpServlet {
	
    private static final long serialVersionUID = 1L;
 // Creates an instance of ProductSetvice to be used for fetching product data from a database
    private final ProductService productService = new ProductService();

    /**
     * Handles HTTP GET requests to load the home page with the latest sneaker products.
     * 
     * @param request HttpServletRequest object containing the client request
     * @param response HttpServletResponse object used to send the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an input or output error occurs during the request handling
     * 
     * @return void (forwards the request and response to the home.jsp page with product data)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
        	//calls getNewReleases(6) from the service class to fetch the latest 6 sneakers products
        	//stores them in the list called trendingProducts
            List<SneakerModel> trendingProducts = productService.getNewReleases(6);
            //stores the trending product list as a request attribute so it can be accessed in the JSP
            request.setAttribute("products", trendingProducts);
            
            //check it the list is empty or not
            //if no products are found which means empty list then a message is displayed
            if (trendingProducts.isEmpty()) {
                request.setAttribute("message", "No new releases found. Check back soon!");
            }
            
            //Catches and logs any exceptions that might occur (like database issue)
        } catch (Exception e) {
        	//sets an error message so the jsp can display it to the user
            System.err.println("Error in HomeController: " + e.getMessage());
            request.setAttribute("error", "Could not load products. Please try again later.");
            //prints the error details in the console for debugging
            e.printStackTrace();
        }
        //forward the request and response to the JSP page
        //this page will render the UI using the data (messages, product) set in the request
        request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
    }
}
