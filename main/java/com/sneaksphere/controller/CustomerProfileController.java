package com.sneaksphere.controller;

import com.sneaksphere.model.UserModel;
import com.sneaksphere.service.UserService;
import com.sneaksphere.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class CustomerProfileController
 */
@WebServlet("/CustomerProfileController")
public class CustomerProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private final UserService userService = new UserService();

    /**
     * @see HttpServlet#HttpServlet()
     */
	 @Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	  throws ServletException, IOException {

	  String email = (String) SessionUtil.getAttribute(req, "username");

        if (email == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        UserModel customer = userService.getUserByEmail(email);

        if (customer != null) {
            req.setAttribute("user", customer);
            req.getRequestDispatcher("/WEB-INF/pages/CustomerProfile.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Could not fetch customer details.");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }
}