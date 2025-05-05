package com.sneaksphere.model;

import java.time.LocalDate;

public class SneakerModel {

    private int sneakerID;
    private String sneakerName;
    private float sneakerSize;
    private String description;
    private String category;
    private String brand;
    private float price;
    private LocalDate releasedDate;
    private String availabilityStatus;
    private String imageUrl;
    private int sales;
    private float revenue;
    private int cartID;
    private float cartTotal;
    private int cartQuantity;

    // Default Constructor
    public SneakerModel() {}

    // All-args Constructor
    public SneakerModel(int sneakerID, String sneakerName, float sneakerSize, String description, String category,
                        String brand, float price, LocalDate releasedDate, String availabilityStatus, String imageUrl) {
        this.sneakerID = sneakerID;
        this.sneakerName = sneakerName;
        this.sneakerSize = sneakerSize;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.releasedDate = releasedDate;
        this.availabilityStatus = availabilityStatus;
        this.imageUrl = imageUrl;
    }

    // Overloaded Constructors
    public SneakerModel(String sneakerName, float sneakerSize, String description, String category,
                        String brand, float price, LocalDate releasedDate, String availabilityStatus, String imageUrl) {
        this.sneakerName = sneakerName;
        this.sneakerSize = sneakerSize;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.releasedDate = releasedDate;
        this.availabilityStatus = availabilityStatus;
        this.imageUrl = imageUrl;
    }

    public SneakerModel(String sneakerName, float sneakerSize, String description, String category,
                        String brand, float price, LocalDate releasedDate, String imageUrl) {
        this.sneakerName = sneakerName;
        this.sneakerSize = sneakerSize;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.releasedDate = releasedDate;
        this.imageUrl = imageUrl;
    }

    public SneakerModel(int sneakerID, String sneakerName, String description, String category,
                        String brand, float price, String imageUrl, float sneakerSize,
                        String availabilityStatus, LocalDate releasedDate) {
        this.sneakerID = sneakerID;
        this.sneakerName = sneakerName;
        this.sneakerSize = sneakerSize;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.imageUrl = imageUrl;
        this.releasedDate = releasedDate;
        this.availabilityStatus = availabilityStatus;
    }

    public SneakerModel(String imageUrl, String sneakerName,  float price,int cartQuantity, float cartTotal) {
    	this.imageUrl = imageUrl;
    	this.sneakerName = sneakerName;
    	this.price = price;
    	this.cartQuantity = cartQuantity;
        this.cartTotal = cartTotal;
        
    }
    

        
    public SneakerModel(int cartID, float cartTotal,  int cartQuantity) {
    	this.cartID= cartID;
        this.cartTotal = cartTotal;
        this.cartQuantity = cartQuantity;
    }
    
    public SneakerModel (int sneakerID, int cartID) {
    	
        this.sneakerID = sneakerID;
        this.cartID = cartID;
    }
    
    
    public int getCartID() { 
    	return cartID; 
    }
    public void setCartID(int cartID) { 
    	this.cartID = cartID;
    }

    public double getCartTotal() { 
    	return cartTotal;
    }
    
    public void setCartTotal(float cartTotal) {
    	this.cartTotal = cartTotal;
    }
    
    public int getCartQuantity() { 
    	return cartQuantity;
    }
    
    public void setCartQuantity(int cartQuantity) {
    	this.cartQuantity = cartQuantity; 
    }
    
    // Getters and Setters
    public int getSneakerID() {
        return sneakerID;
    }

    public void setSneakerID(int sneakerID) {
        this.sneakerID = sneakerID;
    }

    public String getSneakerName() {
        return sneakerName;
    }

    public void setSneakerName(String sneakerName) {
        this.sneakerName = sneakerName;
    }

    public float getSneakerSize() {
        return sneakerSize;
    }

    public void setSneakerSize(float sneakerSize) {
        this.sneakerSize = sneakerSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDate getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(LocalDate releasedDate) {
        this.releasedDate = releasedDate;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
 // Getters and setters
    public int getSales() { 
    	return sales; 
    }
    public void setSales(int sales) { 
    	this.sales = sales; 
    }

    public float getRevenue() { 
    	return revenue; 
    }
    public void setRevenue(float revenue) { 
    	this.revenue = revenue; 
    	}
}