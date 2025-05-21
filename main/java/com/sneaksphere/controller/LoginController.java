package com.sneaksphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.http.HttpSession;

import com.sneaksphere.util.CookieUtil;
import com.sneaksphere.util.SessionUtil;
import com.sneaksphere.model.UserModel;
import com.sneaksphere.service.LoginService;
import com.sneaksphere.util.ValidationUtil;

import com.sneaksphere.service.UserService;
/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final LoginService loginService;

    /**
     * Constructor initializes the LoginService instance used for authenticating users.
     */
    public LoginController() {
        this.loginService = new LoginService();
    }

    /**
     * Handles HTTP GET requests by forwarding the user to the login page.
     * 
     * @param request HttpServletRequest object containing the client request
     * @param response HttpServletResponse object used to send the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an input or output error occurs during the request handling
     * 
     * @return void (forwards to login.jsp)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests for user login.
     * Validates input, authenticates the user, manages session and cookies, 
     * and redirects based on user role.
     * 
     * @param req HttpServletRequest object containing the login form data
     * @param resp HttpServletResponse object used to send the response or redirect
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an input or output error occurs during the request handling
     * 
     * @return void (forwards or redirects based on login success or failure)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

     // Check if email is empty
        if (ValidationUtil.isNullOrEmpty(username)) {
            req.setAttribute("error", "Please fill in your email.");
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
            return;
        }

        // Check if password is empty
        if (ValidationUtil.isNullOrEmpty(password)) {
            req.setAttribute("error", "Please fill in your password.");
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
            return;
        }
        
        // ValidationUtil usage 
        if (!ValidationUtil.isValidEmail(username) || !ValidationUtil.isValidPassword(password)) {
            req.setAttribute("error", "Invalid email or password format.");
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
            return;
        }
        
        UserModel userModel = new UserModel(username, password);
       
        String role = loginService.loginUser(userModel);  // Now it returns the role ("admin" or "customer")

        
        if (role != null) {
            // Set session and cookie
            SessionUtil.setAttribute(req, "username", username);
            CookieUtil.addCookie(resp, "role", role, 30 * 60);
            UserService userService = new UserService();
            UserModel loggedInUser = userService.getUserByEmail(username);
            
            HttpSession session = req.getSession();
            session.setAttribute("username", loggedInUser.getEmail());
            session.setAttribute("userID", loggedInUser.getUserID()); 
            SessionUtil.setSessionTimeout(req,30);

            // Redirect based on the user's role
            if ("Admin".equals(role)) {
                resp.sendRedirect(req.getContextPath() + "/admin");  // Admin redirection
            } else {
                resp.sendRedirect(req.getContextPath() + "/home");  // Customer redirection
            }
        } else {
            handleLoginFailure(req, resp, role);
        }
    }

    /**
     * Handles login failure by setting an appropriate error message and forwarding
     * the user back to the login page.
     * 
     * @param req HttpServletRequest object
     * @param resp HttpServletResponse object
     * @param role String indicating user role or null if login failed
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an input or output error occurs during forwarding
     * 
     * @return void (forwards to login.jsp with error message)
     */
    private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp,String role)
            throws ServletException, IOException {
        String errorMessage;
        if (role == null) {
            errorMessage = "User credential mismatch. Please try again!";
        } else {
            errorMessage = "Our server is under maintenance. Please try again later!";
        }
        req.setAttribute("error", errorMessage);
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
   
    }
}
