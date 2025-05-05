package com.sneaksphere.controller;

import com.sneaksphere.service.GetCustomerService;

import com.sneaksphere.model.UserModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet to manage customer-related admin tasks.
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/adminCustomer"})
public class AdminCustomerController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Fetch customers from the database
    	GetCustomerService getCustomerService = new GetCustomerService();
    	List<UserModel> customers = getCustomerService.getAllCustomers();
    
    	request.setAttribute("customers", customers);  // Set the attribute
    	request.getRequestDispatcher("/WEB-INF/pages/admin Customer.jsp").forward(request, response);

    }
}