package com.sneaksphere.util;

import java.util.regex.Pattern;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.time.LocalDate;

public class ValidationUtil {

    /**
     * Checks if the input string contains only alphabetic characters (a-z, A-Z).
     * @param value the string to check
     * @return true if the string is alphabetic and not null, false otherwise
     */
    public static boolean isAlphabetic(String value) {
        return value != null && value.matches("^[a-zA-Z]+$");
    }
    
    /**
     * Validates if the input string is a valid email address.
     * @param email the email string to validate
     * @return true if the email matches the regex pattern, false otherwise
     */
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && Pattern.matches(emailRegex, email);
    }

    /**
     * Validates if the phone number is exactly 10 digits and starts with '98'.
     * @param number the phone number string to validate
     * @return true if the number matches the pattern, false otherwise
     */
    public static boolean isValidPhoneNumber(String number) {
        return number != null && number.matches("^98\\d{8}$");
    }

    /**
     * Validates if the password contains at least one uppercase letter, one digit, one special symbol, and is at least 8 characters long.
     * @param password the password string to validate
     * @return true if the password meets the complexity requirements, false otherwise
     */
    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$!%*?&])[A-Za-z\\d@$!%*?&#]{8,}$";
        return password != null && password.matches(passwordRegex);
    }

    /**
     * Validates if the given string can be parsed as a float.
     * @param value the string to validate
     * @return true if the string is a valid float number, false otherwise
     */
    public static boolean isValidFloat(String value) {
        if (value == null) return false;
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates the price string and returns an error message if invalid.
     * @param value the price string to validate
     * @return error message if invalid, null if valid
     */
    public static String validatePrice(String value) {
        if (!isValidFloat(value)) {
            return "Price must be a valid number.";
        }
        return null; 
    }

    /**
     * Validates the size string and returns an error message if invalid.
     * @param value the size string to validate
     * @return error message if invalid, null if valid
     */
    public static String validateSize(String value) {
        if (!isValidFloat(value)) {
            return "Size must be a valid number.";
        }
        return null;
    }
    
    /**
     * Checks if the given date is in the past relative to the current date.
     * @param date the LocalDate to check
     * @return true if the date is before today, false otherwise
     */
    public static boolean isPastDate(LocalDate date) {
        return date != null && date.isBefore(LocalDate.now());
    }

    /**
     * Validates if the filename has an image extension (jpg, jpeg, png, webp).
     * @param fileName the file name string to validate
     * @return true if the file extension matches image formats, false otherwise
     */
    public static boolean isValidImageFormat(String fileName) {
        return fileName != null && fileName.toLowerCase().matches(".*\\.(jpg|jpeg|png|webp)$");
    }
    
    /**
     * Validates if a Part's submitted file name has a valid image extension (jpg, jpeg, png, gif).
     * @param imagePart the Part object representing the uploaded file
     * @return true if the file extension is valid image format, false otherwise
     */
    public static boolean isValidImageExtension(Part imagePart) {
        if (imagePart == null || isNullOrEmpty(imagePart.getSubmittedFileName())) {
            return false;
        }
        String fileName = imagePart.getSubmittedFileName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif");
    }

    /**
     * Checks if the input string is null or empty after trimming whitespace.
     * @param value the string to check
     * @return true if the string is null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    
    /**
     * Checks if any required fields in the HTTP request or the image part are empty or missing.
     * @param req the HttpServletRequest containing form parameters
     * @param imagePart the Part object representing the uploaded image file
     * @return true if any required field or the image is missing or empty, false otherwise
     */
    public static boolean hasEmptyRequiredFields(HttpServletRequest req, Part imagePart) {
        return isNullOrEmpty(req.getParameter("sneakerName")) ||
               isNullOrEmpty(req.getParameter("brand")) ||
               isNullOrEmpty(req.getParameter("sneakerSize")) ||
               isNullOrEmpty(req.getParameter("category")) ||
               isNullOrEmpty(req.getParameter("releasedDate")) ||
               isNullOrEmpty(req.getParameter("price")) ||
               isNullOrEmpty(req.getParameter("description")) ||
               imagePart == null || isNullOrEmpty(imagePart.getSubmittedFileName());
    }

}
