package com.sneaksphere.controller;


import com.sneaksphere.model.SneakerModel;
import com.sneaksphere.model.UserModel;
import com.sneaksphere.service.GetSneakerService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Niraj Gurung
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/admin"})
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		
		
		
		GetSneakerService getSneakerService = new GetSneakerService();
		
		request.setAttribute("totalSales", getSneakerService.getTotalSales());
		request.setAttribute("totalProducts", getSneakerService.getTotalProducts());
		request.setAttribute("totalOrders", getSneakerService.getTotalOrders());
		request.setAttribute("totalCustomers", getSneakerService.getTotalCustomers());

		
		List<SneakerModel> topSneakers = getSneakerService.getTopPerformingSneakers();
		request.setAttribute("topSneakers", topSneakers);
		
		List<UserModel> topCustomers = getSneakerService.getTopCustomers();
		request.setAttribute("topCustomers", topCustomers);
		
		
		request.getRequestDispatcher("/WEB-INF/pages/admin home page.jsp").forward(request, response);	
	
	}
	
	
}
