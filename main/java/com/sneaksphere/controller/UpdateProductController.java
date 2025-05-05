package com.sneaksphere.controller;


import com.sneaksphere.model.SneakerModel;
import com.sneaksphere.service.UpdateSneakerService;
import com.sneaksphere.util.ImageUtil;
import com.sneaksphere.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet(urlPatterns = {"/updateProduct"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UpdateProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ImageUtil imageUtil = new ImageUtil();
    
    // Service for updating student information
    private UpdateSneakerService updateService;

    /**
     * Default constructor initializes the UpdateService instance.
     */
    public UpdateProductController() {
        this.updateService = new UpdateSneakerService();
    }
    

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.getRequestDispatcher("/WEB-INF/pages/productUpdate.jsp").forward(request, response);
	

        
    }

    // Handle update form submission
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get sneakerID from the request (typically passed as a hidden field)
        int sneakerID = Integer.parseInt(request.getParameter("sneakerID"));
        
        // Retrieve form data from request
        String sneakerName = request.getParameter("sneakerName");
        String sneakerSizeStr = request.getParameter("sneakerSize");  
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String brand = request.getParameter("brand");
        String priceStr = request.getParameter("price");  
        String releasedDateStr = request.getParameter("releasedDate");
        String availabilityStatus = request.getParameter("availability");

        // Handle image upload
        Part image = request.getPart("imageUrl");
        String imageUrl = imageUtil.getImageNameFromPart(image); // Assuming imageUtil handles file uploads

        // Validate the form fields
        boolean hasErrors = false;
        StringBuilder errorMessage = new StringBuilder();

        // Validate price
        String priceError = ValidationUtil.validatePrice(priceStr);
        if (priceError != null) {
            hasErrors = true;
            errorMessage.append(priceError).append("<br>");
        }

        // Validate size
        String sizeError = ValidationUtil.validateSize(sneakerSizeStr);
        if (sizeError != null) {
            hasErrors = true;
            errorMessage.append(sizeError).append("<br>");
        }

        // Validate sneaker name (optional alphabetic check)
        if (ValidationUtil.isNullOrEmpty(sneakerName)) {
            hasErrors = true;
            errorMessage.append("Sneaker name is required.<br>");
        }
        
  

        // Validate released date
        LocalDate releasedDate = null;

        try {
            releasedDate = LocalDate.parse(releasedDateStr);
            if (!ValidationUtil.isPastDate(releasedDate)) {
                hasErrors = true;
                errorMessage.append("Release date cannot be in the future.<br>");
            }
        } catch (Exception e) {
            hasErrors = true;
            errorMessage.append("Invalid release date format.<br>");
        }



        // Validate image format
        if (image != null && !ValidationUtil.isValidImageFormat(imageUrl)) {
            hasErrors = true;
            errorMessage.append("Invalid image format. Only JPG, JPEG, PNG, and WebP are allowed.<br>");
        }

        // If there are errors, set them in the request and forward back to the form
        if (hasErrors) {
            request.setAttribute("errorMessage", errorMessage.toString());
            request.getRequestDispatcher("/WEB-INF/pages/productUpdate.jsp").forward(request, response);
            return;
        }

        // Convert valid inputs to appropriate types
        float sneakerSize = Float.parseFloat(sneakerSizeStr);
        float price = Float.parseFloat(priceStr);

        // Create SneakerModel object
        SneakerModel sneaker = new SneakerModel(sneakerID,sneakerName,sneakerSize,description,category,
                brand,price,releasedDate,availabilityStatus,imageUrl);

       
        updateService.updateSneaker(sneaker);
        
        request.getSession().setAttribute("successMessage", "Product updated successfully.");

        // Forward to the admin product page (or wherever you want)
        response.sendRedirect(request.getContextPath() + "/adminProduct");
    }

}
