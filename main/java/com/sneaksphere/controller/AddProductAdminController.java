package com.sneaksphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.time.LocalDate;

import com.sneaksphere.model.SneakerModel;
import com.sneaksphere.model.UserModel;
import com.sneaksphere.service.AddSneakerService;
import com.sneaksphere.service.UserService;
import com.sneaksphere.util.ImageUtil;
import com.sneaksphere.util.ValidationUtil;

/**
 * Controller servlet responsible for handling sneaker addition functionality by the admin.
 * Handles GET and POST requests for displaying the add product page and saving sneaker data.
 * 
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

    /**
     * Handles GET request to display the Add Product page to the admin.
     *
     * @param req  the HttpServletRequest object that contains the request the client made
     * @param resp the HttpServletResponse object that contains the response the servlet sends
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error is detected
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("username");

        UserService userService = new UserService();
        UserModel adminUser = userService.getUserByEmail(email);

        if (adminUser != null) {
            req.setAttribute("user", adminUser);
            session.setAttribute("loggedInUser", adminUser);
        }

        req.getRequestDispatcher("/WEB-INF/pages/addProductAdmin.jsp").forward(req, resp);
    }

    /**
     * Handles POST request for adding a new sneaker by the admin.
     * Validates input data, processes image upload, and stores sneaker details in the database.
     *
     * @param req  the HttpServletRequest object containing form data
     * @param resp the HttpServletResponse object to send feedback to the user
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error is detected
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            String email = (String) session.getAttribute("username");

            UserService userService = new UserService();
            UserModel adminUser = userService.getUserByEmail(email);

            if (adminUser != null) {
                req.setAttribute("user", adminUser);
                session.setAttribute("loggedInUser", adminUser);
            }

            String sizeError = ValidationUtil.validateSize(req.getParameter("sneakerSize"));
            String priceError = ValidationUtil.validatePrice(req.getParameter("price"));
            String dateStr = req.getParameter("releasedDate");
            Part image = req.getPart("imageUrl");
            String imageUrl = imageUtil.getImageNameFromPart(image);

            if (ValidationUtil.hasEmptyRequiredFields(req, image)) {
                handleError(req, resp, "All fields are required.");
                return;
            }

            if (sizeError != null || priceError != null) {
                req.setAttribute("error", sizeError != null ? sizeError : priceError);
                req.getRequestDispatcher("/WEB-INF/pages/addProductAdmin.jsp").forward(req, resp);
                return;
            }

            LocalDate releasedDate = LocalDate.parse(dateStr);
            if (!ValidationUtil.isPastDate(releasedDate)) {
                handleError(req, resp, "Release date must be before today.");
                return;
            }

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

    /**
     * Extracts and constructs a SneakerModel object from the HTTP request.
     *
     * @param req the HttpServletRequest containing sneaker data
     * @return a SneakerModel object built from the request parameters
     * @throws Exception if any parsing or input error occurs
     */
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

    /**
     * Uploads the image file provided in the request to the server directory.
     *
     * @param req the HttpServletRequest containing the image file
     * @return true if the upload was successful, false otherwise
     * @throws IOException      if an I/O error occurs during upload
     * @throws ServletException if servlet-specific issues occur
     */
    private boolean uploadImage(HttpServletRequest req) throws IOException, ServletException {
        Part image = req.getPart("imageUrl");
        return imageUtil.uploadImage(image, req.getServletContext().getRealPath("/"), "sneakers");
    }

    /**
     * Handles successful operations by forwarding a success message.
     *
     * @param req          the HttpServletRequest object
     * @param resp         the HttpServletResponse object
     * @param message      the success message to display
     * @param redirectPage the JSP page to forward to
     * @throws ServletException if servlet-related error occurs
     * @throws IOException      if an input or output error occurs
     */
    private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String redirectPage)
            throws ServletException, IOException {
        req.setAttribute("success", message);
        req.getRequestDispatcher(redirectPage).forward(req, resp);
    }

    /**
     * Handles error scenarios by setting an error message and forwarding to the add product page.
     *
     * @param req     the HttpServletRequest object
     * @param resp    the HttpServletResponse object
     * @param message the error message to display
     * @throws ServletException if servlet-related error occurs
     * @throws IOException      if an input or output error occurs
     */
    private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        req.setAttribute("error", message);
        req.getRequestDispatcher("/WEB-INF/pages/addProductAdmin.jsp").forward(req, resp);
    }
}
