package com.sneaksphere.util;

import java.util.regex.Pattern;



import java.time.LocalDate;


public class ValidationUtil {

    public static boolean isAlphabetic(String value) {
        return value != null && value.matches("^[a-zA-Z]+$");
    }
    

    //  Validate if a string is a valid email address
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && Pattern.matches(emailRegex, email);
    }

    //  Validate if a number is of 10 digits and starts with 98
    public static boolean isValidPhoneNumber(String number) {
        return number != null && number.matches("^98\\d{8}$");
    }

    //  Validate if a password is composed of at least 1 capital letter, 1 number, and 1 symbol
    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$!%*?&])[A-Za-z\\d@$!%*?&#]{8,}$";
        return password != null && password.matches(passwordRegex);
    }
    //  Validate if a string is a valid float (used for price and size)
    public static boolean isValidFloat(String value) {
        if (value == null) return false;
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Validate price and return error message if invalid
    public static String validatePrice(String value) {
        if (!isValidFloat(value)) {
            return "Price must be a valid number.";
        }
        return null; 
    }

    // Validate size and return error message if invalid
    public static String validateSize(String value) {
        if (!isValidFloat(value)) {
            return "Size must be a valid number.";
        }
        return null;
    }
    
    public static boolean isPastDate(LocalDate date) {
        return date != null && date.isBefore(LocalDate.now());
    }

    
    public static boolean isValidImageFormat(String fileName) {
        return fileName != null && fileName.toLowerCase().matches(".*\\.(jpg|jpeg|png|webp)$");
    }
    
    
    // Check if a string is empty
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }



    
    

}