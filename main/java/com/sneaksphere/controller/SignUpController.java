package com.sneaksphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.sneaksphere.model.UserModel;
import com.sneaksphere.service.SignUpService;
import com.sneaksphere.util.PasswordUtil;
import com.sneaksphere.util.ValidationUtil;

/**
 *  SignUpController handles user signup requests and processes form submissions.
 */
@WebServlet("/signup")
public class SignUpController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final SignUpService signUpService = new SignUpService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/SignUp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    
        try {
            // Validate form input
            String validationMessage = validateSignupForm(req);
            if (validationMessage != null) {
                handleError(req, resp, validationMessage);
                return;
            }

            // Extract user model
            UserModel userModel = extractUserModel(req);

            Boolean isCreated = signUpService.registerUser(userModel);

            if (isCreated == null) {
                handleError(req, resp, "Server error. Please try again later!");
            } else if (isCreated) {
                handleSuccess(req, resp, "Your account was successfully created!", "/WEB-INF/pages/login.jsp");
            } else {
                handleError(req, resp, "Account could not be created. Please try again later.");
            }

        } catch (Exception e) {
            handleError(req, resp, "An unexpected error occurred.");
            e.printStackTrace(); // Log error
        }
    }

    private String validateSignupForm(HttpServletRequest req) {
        String UserFirstName = req.getParameter("UserFirstName");
        String UserLastName = req.getParameter("UserLastName");
        String UserEmail = req.getParameter("UserEmail");
        String UserPhone = req.getParameter("UserPhone");
        String UserPassword = req.getParameter("UserPassword");
        


        if (!ValidationUtil.isValidEmail(UserEmail)) return "Invalid email format.";
        if (!ValidationUtil.isValidPhoneNumber(UserPhone)) return "Phone must be 10 digits and start with 98.";
        if (!ValidationUtil.isValidPassword(UserPassword)) return "Password must be at least 8 characters, with 1 uppercase letter, 1 number, and 1 symbol.";
        if (!ValidationUtil.isAlphabetic(UserFirstName)) return "Use only letters for name. ";
        if (!ValidationUtil.isAlphabetic(UserLastName)) return "Use only letters for name. ";
       
        
        return null; // All valid
    }

    private UserModel extractUserModel(HttpServletRequest req) {
        String UserFirstName = req.getParameter("UserFirstName");
        String UserLastName = req.getParameter("UserLastName");
        String UserEmail = req.getParameter("UserEmail");
        String UserPhone = req.getParameter("UserPhone");
        String UserAddress = req.getParameter("UserAddress");
        String UserPassword = req.getParameter("UserPassword");

        String encryptedPassword = PasswordUtil.encrypt(UserEmail, UserPassword);

        return new UserModel(UserFirstName, UserLastName, UserEmail, UserPhone, UserAddress, encryptedPassword);
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        req.setAttribute("error", message);
        req.setAttribute("UserFirstName", req.getParameter("UserFirstName"));
        req.setAttribute("UserLastName", req.getParameter("UserLastName"));
        req.setAttribute("UserEmail", req.getParameter("UserEmail"));
        req.setAttribute("UserAddress", req.getParameter("UserAddress"));
        req.setAttribute("UserPhone", req.getParameter("UserPhone"));
        req.getRequestDispatcher("/WEB-INF/pages/SignUp.jsp").forward(req, resp);
    }

    private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String redirectPage)
            throws ServletException, IOException {
        req.setAttribute("success", message);
        req.getRequestDispatcher(redirectPage).forward(req, resp);
    }
}