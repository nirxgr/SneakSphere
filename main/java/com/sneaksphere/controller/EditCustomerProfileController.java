package com.sneaksphere.controller;

import com.sneaksphere.model.UserModel;
import com.sneaksphere.service.UserService;
import com.sneaksphere.util.ImageUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/EditCustomerProfileController")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
	maxFileSize = 1024 * 1024 * 10,       // 10MB
	maxRequestSize = 1024 * 1024 * 50)    // 50MB

public class EditCustomerProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserService userService;
    private final ImageUtil imageUtil = new ImageUtil();

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = (String) request.getSession().getAttribute("username");

        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        UserModel customer = userService.getUserByEmail(email);

        if (customer != null) {
            request.setAttribute("user", customer);
            request.setAttribute("userId", customer.getUserID());
            request.getRequestDispatcher("/WEB-INF/pages/EditCustomerProfile.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Customer profile not found.");
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        StringBuilder errors = new StringBuilder();

        if (firstName == null || firstName.trim().isEmpty()) {
            errors.append("First name is required.<br>");
        }

        if (lastName == null || lastName.trim().isEmpty()) {
            errors.append("Last name is required.<br>");
        }

        if (phone == null || !phone.matches("\\d{10}")) {
            errors.append("Valid 10-digit phone number is required.<br>");
        }

        if (address == null || address.trim().isEmpty()) {
            errors.append("Address is required.<br>");
        }

        //Handle form errors
        if (errors.length() > 0) {
            UserModel formUser = new UserModel();
            formUser.setUserId(userId);
            formUser.setFirstName(firstName);
            formUser.setLastName(lastName);
            formUser.setPhone(phone);
            formUser.setAddress(address);

            request.setAttribute("user", formUser);
            request.setAttribute("errorMessage", errors.toString());
            request.getRequestDispatcher("/WEB-INF/pages/EditCustomerProfile.jsp").forward(request, response);
            return;
        } 
        
     // Handle profile image upload
        Part imagePart = request.getPart("imageUrl");
       String imageUrl = imageUtil.getImageNameFromPart(imagePart);
        String existingImage = request.getParameter("existingImage"); // Get existing image if no new image is uploaded

        if (imagePart != null && imagePart.getSize() > 0) {
            imageUrl = imageUtil.getImageNameFromPart(imagePart); 
            boolean uploaded = imageUtil.uploadImage(imagePart, request.getServletContext().getRealPath("/"), "users");

            if (!uploaded) {
                request.setAttribute("errorMessage", "Failed to upload profile image.");
                request.getRequestDispatcher("/WEB-INF/pages/EditCustomerProfile.jsp").forward(request, response);
                return;
            }
        } else {
            // If no image is uploaded, use the existing image
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
            updatedUser.setUserImageURL("resources/images/users/" + imageUrl);
        } else {
            // If no image was uploaded, use the existing image URL
            updatedUser.setUserImageURL(existingImage);
        }

        // Save changes
        boolean isUpdated = userService.updateUserProfile(updatedUser);

        if (isUpdated) {
            response.sendRedirect(request.getContextPath() + "/CustomerProfileController");
        } else {
            request.setAttribute("errorMessage", "Could not update profile. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/pages/EditCustomerProfile.jsp").forward(request, response);
        }
    }
}