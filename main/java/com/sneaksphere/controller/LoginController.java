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

    public LoginController() {
        this.loginService = new LoginService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // ValidationUtil usage 
        if (!ValidationUtil.isValidEmail(username) || !ValidationUtil.isValidPassword(password)) {
            req.setAttribute("error", "Invalid email or password format.");
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
            return;
        }
        
        UserModel userModel = new UserModel(username, password);
        Boolean loginStatus = loginService.loginUser(userModel);

        
        if (Boolean.TRUE.equals(loginStatus)) {
            UserService userService = new UserService();
            UserModel loggedInUser = userService.getUserByEmail(username);

            HttpSession session = req.getSession();
            session.setAttribute("username", loggedInUser.getEmail());
            session.setAttribute("userID", loggedInUser.getUserID()); 

            CookieUtil.addCookie(resp, "role", "customer", 5 * 30);
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            handleLoginFailure(req, resp, loginStatus);
        }
    }

    private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, Boolean loginStatus)
            throws ServletException, IOException {
        String errorMessage;
        if (loginStatus == null) {
            errorMessage = "Our server is under maintenance. Please try again later!";
        } else {
            errorMessage = "User credential mismatch. Please try again!";
        }
        req.setAttribute("error", errorMessage);
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }
}