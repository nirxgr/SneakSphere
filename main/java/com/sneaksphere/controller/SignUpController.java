package com.sneaksphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.http.Part;

import com.sneaksphere.model.UserModel;
import com.sneaksphere.service.SignUpService;
import com.sneaksphere.util.PasswordUtil;
import com.sneaksphere.util.ValidationUtil;
import com.sneaksphere.util.ImageUtil;

/**
 *  SignUpController handles user signup requests and processes form submissions.
 */
@WebServlet("/signup")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class SignUpController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final SignUpService signUpService = new SignUpService();
    private final ImageUtil imageUtil = new ImageUtil();

    /**
     * Handles GET requests by forwarding to the signup JSP page.
     *
     * @param req HttpServletRequest object containing client request
     * @param resp HttpServletResponse object for sending response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     *
     * @return void (forwards to signup JSP)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/SignUp.jsp").forward(req, resp);
    }

    /**
     * Handles POST requests for user signup form submission.
     * Validates input, creates user, uploads image, and handles success or error.
     *
     * @param req HttpServletRequest object containing form data and image upload
     * @param resp HttpServletResponse object for sending response or forwarding
     * @throws ServletException if a servlet error occurs during processing
     * @throws IOException if an input/output error occurs during processing
     *
     * @return void (forwards or redirects based on signup outcome)
     */
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
            	try {
					if (uploadImage(req)) {
						handleSuccess(req, resp, "Your account is successfully created!", "/WEB-INF/pages/login.jsp");
					} else {
						handleError(req, resp, "Could not upload the image. Please try again later!");
					}
				} catch (IOException | ServletException e) {
					handleError(req, resp, "An error occurred while uploading the image. Please try again later!");
					e.printStackTrace(); // Log the exception
				}
			} else {
				handleError(req, resp, "User details already in use!");
			}
		} catch (Exception e) {
			handleError(req, resp, "An unexpected error occurred. Please try again later!");
			e.printStackTrace(); // Log the exception
		}
	}

    /**
     * Validates the signup form inputs including first name, last name, email, phone, password, and image.
     *
     * @param req HttpServletRequest containing form parameters and image part
     * @return String a validation error message if any validation fails; null if all inputs are valid
     */
    private String validateSignupForm(HttpServletRequest req) {
        String UserFirstName = req.getParameter("UserFirstName");
        String UserLastName = req.getParameter("UserLastName");
        String UserEmail = req.getParameter("UserEmail");
        String UserPhone = req.getParameter("UserPhone");
        String UserPassword = req.getParameter("UserPassword");
        
        // Check for null or empty fields first
        if (ValidationUtil.isNullOrEmpty(UserFirstName))
            return "First name is required.";
        if (ValidationUtil.isNullOrEmpty(UserLastName))
            return "Last name is required.";
        if (ValidationUtil.isNullOrEmpty(UserEmail))
            return "Email is required.";
        if (ValidationUtil.isNullOrEmpty(UserPhone))
            return "Phone number is required.";
        if (ValidationUtil.isNullOrEmpty(UserPassword))
            return "Password is required.";

        if (!ValidationUtil.isValidEmail(UserEmail)) return "Invalid email format.";
        if (!ValidationUtil.isValidPhoneNumber(UserPhone)) return "Phone must be 10 digits and start with 98.";
        if (!ValidationUtil.isValidPassword(UserPassword)) return "Password must be at least 8 characters, with 1 uppercase letter, 1 number, and 1 symbol.";
        if (!ValidationUtil.isAlphabetic(UserFirstName)) return "Use only letters for name.";
        if (!ValidationUtil.isAlphabetic(UserLastName)) return "Use only letters for name.";
        
        // Check if the image is provided
        try {
            Part image = req.getPart("image");

            // If no image is uploaded
            if (image == null || image.getSize() == 0) {
                return "Image is required.";
            }

            System.out.println("File Name: " + image.getSubmittedFileName());
            System.out.println("Content Type: " + image.getContentType());
            System.out.println("Size: " + image.getSize());

            // Validate image format
            if (!ValidationUtil.isValidImageExtension(image))
                return "Invalid image format. Only jpg, jpeg, png, and gif are allowed.";
        } catch (IOException | ServletException e) {
            return "Error handling image file. Please ensure the file is valid.";
        }

        return null; // All valid
    }

    /**
     * Extracts user details from the request and creates a UserModel object.
     * Encrypts the user's password before creating the model.
     *
     * @param req HttpServletRequest containing user form data and image part
     * @return UserModel populated with user data and encrypted password
     * @throws ServletException if there is an error retrieving image part
     * @throws IOException if there is an error reading image part
     */
    private UserModel extractUserModel(HttpServletRequest req) throws ServletException, IOException {

        String UserFirstName = req.getParameter("UserFirstName");
        String UserLastName = req.getParameter("UserLastName");
        String UserEmail = req.getParameter("UserEmail");
        String UserPhone = req.getParameter("UserPhone");
        String UserAddress = req.getParameter("UserAddress");
        String UserPassword = req.getParameter("UserPassword");
        String encryptedPassword = PasswordUtil.encrypt(UserEmail, UserPassword);
        
        
        Part image = req.getPart("image");
		String imageUrl = imageUtil.getImageNameFromPart(image);
		
		return new UserModel(UserFirstName, UserLastName, UserEmail, UserPhone, encryptedPassword, UserAddress, imageUrl);

    }
    
    /**
     * Uploads the user image to the server.
     *
     * @param req HttpServletRequest containing the image part
     * @return boolean true if image upload succeeded, false otherwise
     * @throws IOException if an IO error occurs during upload
     * @throws ServletException if a servlet error occurs during upload
     */
    private boolean uploadImage(HttpServletRequest req) throws IOException, ServletException {
		Part image = req.getPart("image");
		return imageUtil.uploadImage(image, req.getServletContext().getRealPath("/"), "user");
	}

    /**
     * Handles errors by setting the error message and preserving entered form data,
     * then forwarding back to the signup JSP page.
     *
     * @param req HttpServletRequest containing form data
     * @param resp HttpServletResponse used to forward the request
     * @param message The error message to display
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an IO error occurs
     */
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

    /**
     * Handles success by setting a success message and forwarding to the given redirect page.
     *
     * @param req HttpServletRequest object
     * @param resp HttpServletResponse object
     * @param message Success message to display
     * @param redirectPage JSP page path to forward after success
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an IO error occurs
     */
    private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String redirectPage)
            throws ServletException, IOException {
        req.setAttribute("success", message);
        req.getRequestDispatcher(redirectPage).forward(req, resp);
    }
}
