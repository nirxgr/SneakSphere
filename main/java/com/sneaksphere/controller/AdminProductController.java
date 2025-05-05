package com.sneaksphere.controller;



import com.sneaksphere.model.SneakerModel;
import com.sneaksphere.service.GetSneakerService;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

import java.util.List;


@WebServlet("/adminProduct") // URL pattern
public class AdminProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to display products
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        //Create service instance
        GetSneakerService service = new GetSneakerService();

        //  Get list of sneakers
        List<SneakerModel> sneakerList = service.getAllSneakers();

        //  Set the list in request scope
        request.setAttribute("sneakerList", sneakerList);

        //  Forward to JSP
        request.getRequestDispatcher("/WEB-INF/pages/admin Product.jsp").forward(request, response);

    }
    
    /**
	 * Handles HTTP POST requests for various actions such as update, delete, or
	 * redirecting to the update form. Processes the request parameters based on the
	 * specified action.
	 * 
	 * @param request  The HttpServletRequest object containing the request data.
	 * @param response The HttpServletResponse object used to return the response.
	 * @throws ServletException If an error occurs during request processing.
	 * @throws IOException      If an input or output error occurs.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

	    if (action == null || action.isEmpty()) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing.");
	        return;
	    }
	    
		int sneakerID = Integer.parseInt(request.getParameter("sneakerID"));

		switch (action) {
		case "updateForm":
			handleUpdateForm(request, response, sneakerID);
			break;

		case "delete":
			handleDelete(request, response, sneakerID);
			break;

		default:
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
		}
	}
	/**
	 * Handles the update form action by setting sneaker data in the session and
	 * redirecting to the update page.
	 * 
	 * @param request   The HttpServletRequest object containing the request data.
	 * @param response  The HttpServletResponse object used to return the response.
	 * 
	 * @throws IOException If an input or output error occurs.
	 */
	private void handleUpdateForm(HttpServletRequest request, HttpServletResponse response, int sneakerID)
			throws ServletException, IOException {
		
		 GetSneakerService sneakerService = new GetSneakerService();
		 
		// Retrieve the sneaker information from the service
		SneakerModel sneaker = sneakerService.getSpecificSneakerInfo(sneakerID);
	    
		if (sneaker != null) {
			// Store the sneaker object in the session
			request.getSession().setAttribute("sneaker", sneaker);

			// Redirect to the update URL
			response.sendRedirect(request.getContextPath() + "/updateProduct");
		} else {
			// Handle case where sneaker info is not found
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Sneaker not found with ID: " + sneakerID);
		}
	}
	
	
	private void handleDelete(HttpServletRequest request, HttpServletResponse response, int sneakerID)
	        throws ServletException, IOException {

	    GetSneakerService sneakerService = new GetSneakerService(); // ← Create instance
	 

	    String errorMessage = sneakerService.deleteSneaker(sneakerID);

	    if (errorMessage == null) {
	        
	        request.setAttribute("successMessage", "Sneaker deleted successfully.");
	        
	    } else {
	        
	        request.setAttribute("errorMessage", errorMessage);
	        
	    }
	 // Forward to the dashboard to reflect changes
	    doGet(request, response);
	  
	}

	
}
