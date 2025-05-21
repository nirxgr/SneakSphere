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
 * Servlet for editing the admin profile.
 * This handles both loading the editable form (GET)
 * and saving the updated info (POST).
 *  
 * @author Riya Basnet
 */

// Maps this servlet to the /editAdminProfileController URL
@WebServlet("/editAdminProfileController")

/**
 * @MultipartConfig is used to handle file uploads (like profile image).
 * - fileSizeThreshold: Size threshold after which files will be written to disk (2MB here).
 * - maxFileSize: Max size for a single file upload (10MB here).
 * - maxRequestSize: Max size of the total request (50MB here).
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,     // 2MB
        maxFileSize = 1024 * 1024 * 10,                   // 10MB
        maxRequestSize = 1024 * 1024 * 50)                // 50MB
public class EditAdminProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;
    private final ImageUtil imageUtil = new ImageUtil(); // Handles image upload logic

    @Override
    public void init() throws ServletException {
        // Initialize service layer to handle DB logic
        userService = new UserService();
    }
    /**
     * Load the editable form with existing admin data (GET method)
     *
     * @param request  the HttpServletRequest object containing the client's request
     * @param response the HttpServletResponse object for sending response to the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs during request handling
     * @return void
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("username");

        // Get admin user details
        UserService userService = new UserService();
        UserModel adminUser = userService.getUserByEmail(email);

        // Set admin user in request attributes
        if (adminUser != null) {
            request.setAttribute("user", adminUser);
            session.setAttribute("loggedInUser", adminUser);
        }

        if (email == null) {
            // Fallback for cases where session expired or not set
            UserModel admin = userService.getUserById(1); // assuming admin is userID 1
            if (admin != null) {
                email = admin.getEmail();
                session.setAttribute("username", email); // simulate login
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
        }
        // Fetch the logged-in user using email
        UserModel admin = userService.getUserByEmail(email);

        if (admin != null) {
            // Send user data to editable profile JSP
            request.setAttribute("user", admin);
            request.setAttribute("userId", admin.getUserID());
            request.getRequestDispatcher("/WEB-INF/pages/editableAdminProfile.jsp").forward(request, response);
        } else {
            // If user not found, redirect to error page
            request.setAttribute("errorMessage", "Admin profile not found.");
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    /**
     * Handle form submission to update admin profile (POST method)
     *
     * @param request  the HttpServletRequest object containing the form submission data
     * @param response the HttpServletResponse object for sending response to the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs during request handling
     * @return void
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("username");

        // Get admin user details
        UserService userService = new UserService();
        UserModel adminUser = userService.getUserByEmail(email);

        // Set admin user in request attributes
        if (adminUser != null) {
            request.setAttribute("user", adminUser);
            session.setAttribute("loggedInUser", adminUser);
        }

        // Retrieve form data (submitted via POST)
        int userId = Integer.parseInt(request.getParameter("userId"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String existingImage = request.getParameter("existingImage");

        // Validation for first name
        StringBuilder errors = new StringBuilder();

        if (firstName == null || firstName.trim().isEmpty()) {
            errors.append("First name is required.<br>");
        } else if (!firstName.matches("^[a-zA-Z]+$")) {
            errors.append("First name must contain only alphabets.<br>");
        }
        // Validation for last name 
        if (lastName == null || lastName.trim().isEmpty()) {
            errors.append("Last name is required.<br>");
        } else if (!lastName.matches("^[a-zA-Z]+$")) {
            errors.append("Last name must contain only alphabets.<br>");
        }
        // Validation for phone number
        if (phone == null || !phone.matches("\\d{10}")) {
            errors.append("Valid 10-digit phone number is required.<br>");
        }

        // Validation for address
        if (address == null || address.trim().isEmpty()) {
            errors.append("Address is required.<br>");
        }
        // If errors exist, show the form again with error messages
        if (errors.length() > 0) {
            UserModel formUser = new UserModel();
            formUser.setUserId(userId);
            formUser.setFirstName(firstName);
            formUser.setLastName(lastName);
            formUser.setPhone(phone);
            formUser.setAddress(address);
            formUser.setUserImageURL(existingImage);
            formUser.setEmail(email);

            request.setAttribute("user", formUser);
            request.setAttribute("errorMessage", errors.toString());
            request.getRequestDispatcher("/WEB-INF/pages/editableAdminProfile.jsp").forward(request, response);
            return;
        }

        // Handle profile image upload
        // imageUrl will store the new or existing image
        Part imagePart = request.getPart("imageUrl"); // "imageUrl" is the name in <input type="file" ...>
        String imageUrl = null;
        // String existingImage = request.getParameter("existingImage");

        if (imagePart != null && imagePart.getSize() > 0) {
            // Get file name and upload the image
            imageUrl = imageUtil.getImageNameFromPart(imagePart);
            boolean uploaded = imageUtil.uploadImage(imagePart, request.getServletContext().getRealPath("/"), "user"); // subfolder to store images

            if (!uploaded) {
                request.setAttribute("errorMessage", "Failed to upload profile image.");
                request.getRequestDispatcher("/WEB-INF/pages/editableAdminProfile.jsp").forward(request, response);
                return;
            }
        } else {
            imageUrl = existingImage;
        }

        // Build updated user model
        UserModel updatedUser = new UserModel();
        updatedUser.setUserId(userId);
        updatedUser.setFirstName(firstName);
        updatedUser.setLastName(lastName);
        updatedUser.setPhone(phone);
        updatedUser.setAddress(address);

        // Set the image URL (if image exists, otherwise use existing image URL)
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            updatedUser.setUserImageURL(imageUrl);
        } else {
            // If no image was uploaded, use the existing image URL
            updatedUser.setUserImageURL(existingImage);
        }

        boolean isUpdated = userService.updateUserProfile(updatedUser); // Save to database

        if (isUpdated) {
            // Redirect back to admin profile view page (non-editable)
            response.sendRedirect(request.getContextPath() + "/adminProfileController");
        } else {
            // If update fails, show error
            request.setAttribute("errorMessage", "Could not update profile. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/pages/editableAdminProfile.jsp").forward(request, response);
        }
    }
}
