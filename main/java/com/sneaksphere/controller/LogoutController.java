package com.sneaksphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.sneaksphere.util.CookieUtil;
import com.sneaksphere.util.SessionUtil;

/**
 * Servlet implementation class LogoutController
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Handles HTTP GET requests for logging out a user.
     * Deletes the role cookie, invalidates the session, and redirects the user to the home page.
     * 
     * @param request HttpServletRequest object containing the client request
     * @param response HttpServletResponse object used to send the response or redirect
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an input or output error occurs during the request handling
     * 
     * @return void (redirects to home page after logout)
     */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    CookieUtil.deleteCookie(response, "role");
	    SessionUtil.invalidateSession(request);
	    response.sendRedirect(request.getContextPath() + "/home");
	}
}
