package com.sneaksphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.time.LocalDate;


import com.sneaksphere.model.SneakerModel;
import com.sneaksphere.service.AddSneakerService;
import com.sneaksphere.util.ImageUtil;
import com.sneaksphere.util.ValidationUtil;

/**
 * @author Niraj Gurung
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/addProductAdmin"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class AddProductAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final ImageUtil imageUtil = new ImageUtil();
	private final AddSneakerService sneakerService = new AddSneakerService();
   
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/addProductAdmin.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
	try {
		String sizeError = ValidationUtil.validateSize(req.getParameter("sneakerSize"));
		String priceError = ValidationUtil.validatePrice(req.getParameter("price"));
		String dateStr = req.getParameter("releasedDate");
		Part image = req.getPart("imageUrl");
		String imageUrl = imageUtil.getImageNameFromPart(image);

		if (sizeError != null || priceError != null) {
		    req.setAttribute("error", sizeError != null ? sizeError : priceError);
		    req.getRequestDispatcher("/WEB-INF/pages/addProductAdmin.jsp").forward(req, resp);
		    return;
		}
		// Validate date
		LocalDate releasedDate = LocalDate.parse(dateStr);
		if (!ValidationUtil.isPastDate(releasedDate)) {
		    handleError(req, resp, "Release date must be before today.");
		    return;
		}
		

		// Validate image format
		if (!ValidationUtil.isValidImageFormat(imageUrl)) {
		    handleError(req, resp, "Only JPG, JPEG, PNG, or WEBP images are allowed.");
		    return;
		}


		
    
		SneakerModel sneakerModel = extractSneakerModel(req);
		Boolean isAdded = sneakerService.addSneaker(sneakerModel);

		if (isAdded == null) {
			handleError(req, resp, "Our server is under maintenance. Please try again later!");
		} else if (isAdded) {
			if (uploadImage(req)) {
				handleSuccess(req, resp, "Sneaker added successfully!", "/WEB-INF/pages/addProductAdmin.jsp");
			} else {
				handleError(req, resp, "Could not upload the image. Please try again later!");
			}
		} else {
			handleError(req, resp, "Could not add sneaker. Please try again later!");
		}
	} catch (Exception e) {
		handleError(req, resp, "An unexpected error occurred. Please try again later!");
		e.printStackTrace(); // Log the exception
	}
	
	}
	private SneakerModel extractSneakerModel(HttpServletRequest req) throws Exception {
		String sneakerName = req.getParameter("sneakerName");
		float sneakerSize = Float.parseFloat(req.getParameter("sneakerSize"));
		String description = req.getParameter("description");
		String category = req.getParameter("category");
		String brand = req.getParameter("brand");
		float price = Float.parseFloat(req.getParameter("price"));
		LocalDate releasedDate = LocalDate.parse(req.getParameter("releasedDate"));


		
		Part image = req.getPart("imageUrl");
		String imageUrl = imageUtil.getImageNameFromPart(image);
		
		return new SneakerModel(sneakerName, sneakerSize, description, category,
				brand, price, releasedDate, imageUrl);
	}
	private boolean uploadImage(HttpServletRequest req) throws IOException, ServletException {
		Part image = req.getPart("imageUrl");
		return imageUtil.uploadImage(image, req.getServletContext().getRealPath("/"), "sneakers");
	}
	private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String redirectPage)
			throws ServletException, IOException {
		req.setAttribute("success", message);
		req.getRequestDispatcher(redirectPage).forward(req, resp);
	}

	private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.setAttribute("error", message);
		req.getRequestDispatcher("/WEB-INF/pages/addProductAdmin.jsp").forward(req, resp);
	}
	
	
	

	
	
	
	

	
	
}