package com.sneaksphere.controller;

import com.sneaksphere.model.UserModel;
import com.sneaksphere.service.UserService;
import com.sneaksphere.util.ImageUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/EditAdminProfileController")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,     // 2MB
        maxFileSize = 1024 * 1024 * 10,                   // 10MB
        maxRequestSize = 1024 * 1024 * 50)                // 50MB
public class EditAdminProfileController extends HttpServlet {
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


        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("username");

        if (email == null) {
            // fallback to default admin (e.g., ID 1)
            UserModel admin = userService.getUserById(1); // assuming admin is userID 1
            if (admin != null) {
                email = admin.getEmail();
                session.setAttribute("username", email); // simulate login
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
        }

        UserModel admin = userService.getUserByEmail(email);

        if (admin != null) {
            request.setAttribute("user", admin);
            request.setAttribute("userId", admin.getUserID());
            request.getRequestDispatcher("/WEB-INF/pages/editableAdminProfile.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Admin profile not found.");
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
       

        // Validation
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

        if (errors.length() > 0) {
            UserModel formUser = new UserModel();
            formUser.setUserId(userId);
            formUser.setFirstName(firstName);
            formUser.setLastName(lastName);
            formUser.setPhone(phone);
            formUser.setAddress(address);
            

            request.setAttribute("user", formUser);
            request.setAttribute("errorMessage", errors.toString());
            request.getRequestDispatcher("/WEB-INF/pages/editableAdminProfile.jsp").forward(request, response);
            return;
        }

        // Handle profile image upload
        Part imagePart = request.getPart("imageUrl");
        String imageUrl = null;
        String existingImage = request.getParameter("existingImage");

        if (imagePart != null && imagePart.getSize() > 0) {
            imageUrl = imageUtil.getImageNameFromPart(imagePart);
            boolean uploaded = imageUtil.uploadImage(imagePart, request.getServletContext().getRealPath("/"), "users");

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
            updatedUser.setUserImageURL("resources/images/users/" + imageUrl);
        } else {
            // If no image was uploaded, use the existing image URL
            updatedUser.setUserImageURL(existingImage);
        }

        boolean isUpdated = userService.updateUserProfile(updatedUser);

        if (isUpdated) {
            response.sendRedirect(request.getContextPath() + "/AdminProfileController");
        } else {
            request.setAttribute("errorMessage", "Could not update profile. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/pages/editableAdminProfile.jsp").forward(request, response);
        }
    }
}