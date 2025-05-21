package com.sneaksphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.sneaksphere.service.ProductService;
import com.sneaksphere.service.OrderService;
import com.sneaksphere.model.SneakerModel;
import com.sneaksphere.model.OrderModel;

/**
 * Controller for displaying individual sneaker product details
 * and handling 'Buy Now' order requests.
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/individualProductPage"})
public class IndividualProductPageController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;
    
    
    /**
     * Initializes the servlet and sets up the ProductService instance.
     *
     * @throws ServletException if an error occurs during servlet initialization
     */
    @Override
    public void init() throws ServletException {
        super.init();
        productService = new ProductService();
    }
   
    /**
     * Handles HTTP GET requests to display product details, available sizes,
     * related products, and any success/error messages.
     *
     * @param request  the HttpServletRequest object that contains the request the client made
     * @param response the HttpServletResponse object that contains the response the servlet returns
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error occurs while handling the request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Get product ID from request parameter
            int sneakerId = Integer.parseInt(request.getParameter("id"));
            
            // Fetch the sneaker details
            SneakerModel sneaker = productService.getSneakerById(sneakerId);
            
            if (sneaker == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }
            
            // Fetch available sizes
            List<String> availableSizes = productService.getAvailableSizes(sneakerId);
            
            // Fetch related products (same brand or category)
            List<SneakerModel> relatedProducts = productService.getRelatedProducts(
                sneaker.getBrand(), 
                sneaker.getCategory(), 
                sneakerId, 
                6
            );
            
            // Handle success/error messages
            String message = request.getParameter("message");
            if ("order_placed".equals(message)) {
                request.setAttribute("successMessage", "Order placed successfully! Thank you for shopping with us!");
            }
            
            String error = request.getParameter("error");
            if ("order_failed".equals(error)) {
                request.setAttribute("errorMessage", "Failed to place order. Please try again.");
            }
            
            // Set attributes for JSP
            request.setAttribute("sneaker", sneaker);
            request.setAttribute("availableSizes", availableSizes);
            request.setAttribute("relatedProducts", relatedProducts);
            
            request.getRequestDispatcher("/WEB-INF/pages/individualProductPage.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error");
        }
    }

    
    /**
     * Handles HTTP POST requests to process 'Buy Now' orders.
     * Checks user authentication and routes to appropriate actions.
     *
     * @param request  the HttpServletRequest object that contains the request the client made
     * @param response the HttpServletResponse object that contains the response the servlet returns
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error occurs while handling the request
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Integer userID = (session != null) ? (Integer) session.getAttribute("userID") : null;
        
        if (userID == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            String action = request.getParameter("action");
            String sneakerIdParam = request.getParameter("id");
            int sneakerId = (sneakerIdParam != null) ? Integer.parseInt(sneakerIdParam) : 0;
            
            if ("buyNow".equals(action)) {
                handleBuyNow(request, response, userID, sneakerId);
            } else {
                response.sendRedirect(request.getContextPath() + "/individualProductPage?id=" + sneakerId);
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/individualProductPage?error=invalid_id");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/individualProductPage?error=operation_failed");
        }
    }

    
    /**
     * Processes the 'Buy Now' action by creating an order for a single sneaker with quantity = 1.
     *
     * @param request   the HttpServletRequest object
     * @param response  the HttpServletResponse object
     * @param userID    the ID of the currently logged-in user
     * @param sneakerId the ID of the sneaker to purchase
     * @throws ServletException      if a servlet-specific error occurs
     * @throws IOException           if an I/O error occurs during redirection
     * @throws SQLException          if a database access error occurs
     * @throws ClassNotFoundException if the database driver class is not found
     */
    private void handleBuyNow(HttpServletRequest request, HttpServletResponse response, int userID, int sneakerId)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        
        // Get the sneaker with all details
        SneakerModel sneaker = productService.getSneakerById(sneakerId);
        if (sneaker == null) {
            response.sendRedirect(request.getContextPath() + "/individualProductPage?id=" + sneakerId + "&error=product_not_found");
            return;
        }
        
        // Debug logging - verify sneaker details
        System.out.println("[DEBUG] Buy Now - Sneaker ID: " + sneaker.getSneakerID());
        System.out.println("[DEBUG] Buy Now - Sneaker Size: " + sneaker.getSneakerSize());
        System.out.println("[DEBUG] Buy Now - Sneaker Price: " + sneaker.getPrice());

        // Explicitly set quantity to 1 for Buy Now
        sneaker.setCartQuantity(1);
        
        // Create and configure the order
        OrderModel order = new OrderModel();
        order.setUserID(userID);
        order.setOrderDate(new java.util.Date());
        order.setOrderTotal(sneaker.getPrice() * sneaker.getCartQuantity());
        order.setQuantity(sneaker.getCartQuantity()); // Set quantity to 1
        
        // Use the sneaker's default size
        float sneakerSize = sneaker.getSneakerSize();
        System.out.println("[DEBUG] Using sneaker default size: " + sneakerSize);
        order.setSize(sneakerSize);

        OrderService orderService = new OrderService();
        int orderID = orderService.placeOrderForSingleItem(order, sneaker);

        if (orderID > 0) {
            response.sendRedirect(request.getContextPath() + "/individualProductPage?id=" + sneakerId + "&message=order_placed");
        } else {
            response.sendRedirect(request.getContextPath() + "/individualProductPage?id=" + sneakerId + "&error=order_failed");
        }
    }
}