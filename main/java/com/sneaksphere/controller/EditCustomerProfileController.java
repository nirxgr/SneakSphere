package com.sneaksphere.controller;

import com.sneaksphere.model.UserModel;
import com.sneaksphere.service.UserService;
import com.sneaksphere.util.ImageUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Controller to handle editing customer profile details, including image uploads.
 * 
 * Supports GET (to load existing customer data into the edit form) 
 * and POST (to process form submission and update user data).
 * 
 * @author Riya Basnet
 */
@WebServlet("/editCustomerProfileController")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,   // 2MB threshold before writing to disk
    maxFileSize = 1024 * 1024 * 10,        // Max individual file size: 10MB
    maxRequestSize = 1024 * 1024 * 50      // Max total request size: 50MB
)
public class EditCustomerProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserService userService;
    private final ImageUtil imageUtil = new ImageUtil();

    /**
     * Initializes the servlet and instantiates required services.
     * 
     * @throws ServletException if initialization fails
     */
    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    /**
     * Handles GET request to load customer data into the edit form.
     * Validates session and fetches user by email stored in session.
     * 
     * @param request HttpServletRequest containing session and context data
     * @param response HttpServletResponse for redirecting or forwarding
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs during processing
     * @return void
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the logged-in user's email from session
        String email = (String) request.getSession().getAttribute("username");

        // Redirect to login page if not authenticated
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Retrieve user details by email
        UserModel customer = userService.getUserByEmail(email);

        if (customer != null) {
            request.setAttribute("user", customer);
            request.setAttribute("userId", customer.getUserID());
            request.getRequestDispatcher("/WEB-INF/pages/editCustomerProfile.jsp").forward(request, response);
        } else {
            // Forward to error page if user not found
            request.setAttribute("errorMessage", "Customer profile not found.");
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    /**
     * Handles POST request to update customer profile data.
     * Validates form inputs and handles optional profile image uploads.
     * 
     * @param request HttpServletRequest containing form and file data
     * @param response HttpServletResponse for redirecting or forwarding
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs during processing
     * @return void
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Extract form input values
        int userId = Integer.parseInt(request.getParameter("userId"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String existingImage = request.getParameter("existingImage");
        StringBuilder errors = new StringBuilder();

        // Basic form validation
        if (firstName == null || firstName.trim().isEmpty()) {
            errors.append("First name is required.<br>");
        } else if (!firstName.matches("^[a-zA-Z]+$")) {
            errors.append("First name must contain only alphabets.<br>");
        }
        //last name validation
        if (lastName == null || lastName.trim().isEmpty()) {
            errors.append("Last name is required.<br>");
        }else if (!lastName.matches("^[a-zA-Z]+$")) {
            errors.append("Last name must contain only alphabets.<br>");
        }
        //phone number validation
        if (phone == null || !phone.matches("\\d{10}")) {
            errors.append("Valid 10-digit phone number is required.<br>");
        }
        	
        //address validation
        if (address == null || address.trim().isEmpty()) {
            errors.append("Address is required.<br>");
        }
        // Handle validation errors by returning form with error messages and previously filled data
        if (errors.length() > 0) {
            UserModel formUser = new UserModel();
            formUser.setUserId(userId);
            formUser.setFirstName(firstName);
            formUser.setLastName(lastName);
            formUser.setPhone(phone);
            formUser.setAddress(address);
            formUser.setUserImageURL(existingImage);
            
            request.setAttribute("user", formUser);
            request.setAttribute("errorMessage", errors.toString());
            request.getRequestDispatcher("/WEB-INF/pages/editCustomerProfile.jsp").forward(request, response);
            return;
        }

        // Handle optional image upload
        Part imagePart = request.getPart("imageUrl");
        String imageUrl = imageUtil.getImageNameFromPart(imagePart);
        //String existingImage = request.getParameter("existingImage");

        if (imagePart != null && imagePart.getSize() > 0) {
            boolean uploaded = imageUtil.uploadImage(imagePart, request.getServletContext().getRealPath("/"), "user");

            if (!uploaded) {
                request.setAttribute("errorMessage", "Failed to upload profile image.");
                request.getRequestDispatcher("/WEB-INF/pages/editCustomerProfile.jsp").forward(request, response);
                return;
            }
        } else {
            // Use existing image if no new image uploaded
            imageUrl = existingImage;
        }

        // Construct updated user model object
        UserModel updatedUser = new UserModel();
        updatedUser.setUserId(userId);
        updatedUser.setFirstName(firstName);
        updatedUser.setLastName(lastName);
        updatedUser.setPhone(phone);
        updatedUser.setAddress(address);
        updatedUser.setUserImageURL(imageUrl);

        // Persist updates via service layer
        boolean isUpdated = userService.updateUserProfile(updatedUser);

        if (isUpdated) {
            // Redirect to profile page after successful update
            response.sendRedirect(request.getContextPath() + "/customerProfileController");
        } else {
            // Return to form with error if update fails
            request.setAttribute("errorMessage", "Could not update profile. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/pages/editCustomerProfile.jsp").forward(request, response);
        }
    }
}
