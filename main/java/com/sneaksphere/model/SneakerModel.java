package com.sneaksphere.model;

import java.time.LocalDate;

/**
 * Model class representing a Sneaker entity with details such as sneaker info, pricing, sales, and cart data.
 */
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

    /**
     * Default constructor.
     */
    public SneakerModel() {}

    /**
     * Constructor to initialize all sneaker details including sneakerID and availability status.
     * 
     * @param sneakerID unique ID of the sneaker
     * @param sneakerName name of the sneaker
     * @param sneakerSize size of the sneaker
     * @param description description of the sneaker
     * @param category category of the sneaker
     * @param brand brand of the sneaker
     * @param price price of the sneaker
     * @param releasedDate release date of the sneaker
     * @param availabilityStatus availability status of the sneaker
     * @param imageUrl image URL of the sneaker
     */
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

    /**
     * Constructor to initialize sneaker details without sneakerID (e.g., for new sneaker creation).
     * 
     * @param sneakerName name of the sneaker
     * @param sneakerSize size of the sneaker
     * @param description description of the sneaker
     * @param category category of the sneaker
     * @param brand brand of the sneaker
     * @param price price of the sneaker
     * @param releasedDate release date of the sneaker
     * @param availabilityStatus availability status of the sneaker
     * @param imageUrl image URL of the sneaker
     */
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

    /**
     * Constructor for product addition (admin side) without availability status.
     * 
     * @param sneakerName name of the sneaker
     * @param sneakerSize size of the sneaker
     * @param description description of the sneaker
     * @param category category of the sneaker
     * @param brand brand of the sneaker
     * @param price price of the sneaker
     * @param releasedDate release date of the sneaker
     * @param imageUrl image URL of the sneaker
     */
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

    /**
     * Constructor for cart view with selected sneaker details.
     * 
     * @param imageUrl image URL of the sneaker
     * @param sneakerName name of the sneaker
     * @param price price of the sneaker
     * @param cartQuantity quantity of this sneaker in the cart
     * @param cartTotal total price for this sneaker in the cart
     */
    public SneakerModel(String imageUrl, String sneakerName,  float price,int cartQuantity, float cartTotal) {
        this.imageUrl = imageUrl;
        this.sneakerName = sneakerName;
        this.price = price;
        this.cartQuantity = cartQuantity;
        this.cartTotal = cartTotal;
    }

    /**
     * Constructor for cart with ID, total, and quantity.
     * 
     * @param cartID unique ID of the cart
     * @param cartTotal total price of items in the cart
     * @param cartQuantity total quantity of items in the cart
     */
    public SneakerModel(int cartID, float cartTotal,  int cartQuantity) {
        this.cartID= cartID;
        this.cartTotal = cartTotal;
        this.cartQuantity = cartQuantity;
    }

    /**
     * Constructor linking sneakerID and cartID.
     * 
     * @param sneakerID unique ID of the sneaker
     * @param cartID unique ID of the cart
     */
    public SneakerModel (int sneakerID, int cartID) {
        this.sneakerID = sneakerID;
        this.cartID = cartID;
    }

    /**
     * Gets the cart ID.
     * 
     * @return cartID the unique identifier for the cart
     */
    public int getCartID() { 
        return cartID; 
    }

    /**
     * Sets the cart ID.
     * 
     * @param cartID the unique identifier to set for the cart
     */
    public void setCartID(int cartID) { 
        this.cartID = cartID;
    }

    /**
     * Gets the total price of items in the cart.
     * 
     * @return cartTotal the total cart price
     */
    public double getCartTotal() { 
        return cartTotal;
    }

    /**
     * Sets the total price of items in the cart.
     * 
     * @param cartTotal the total cart price to set
     */
    public void setCartTotal(float cartTotal) {
        this.cartTotal = cartTotal;
    }

    /**
     * Gets the quantity of sneakers in the cart.
     * 
     * @return cartQuantity quantity of sneakers in the cart
     */
    public int getCartQuantity() { 
        return cartQuantity;
    }

    /**
     * Sets the quantity of sneakers in the cart.
     * 
     * @param cartQuantity quantity of sneakers to set in the cart
     */
    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity; 
    }

    // Getters and setters for sneaker properties

    /**
     * Gets the sneaker ID.
     * 
     * @return sneakerID unique identifier of the sneaker
     */
    public int getSneakerID() {
        return sneakerID;
    }

    /**
     * Sets the sneaker ID.
     * 
     * @param sneakerID unique identifier to set for the sneaker
     */
    public void setSneakerID(int sneakerID) {
        this.sneakerID = sneakerID;
    }

    /**
     * Gets the sneaker name.
     * 
     * @return sneakerName name of the sneaker
     */
    public String getSneakerName() {
        return sneakerName;
    }

    /**
     * Sets the sneaker name.
     * 
     * @param sneakerName name to set for the sneaker
     */
    public void setSneakerName(String sneakerName) {
        this.sneakerName = sneakerName;
    }

    /**
     * Gets the sneaker size.
     * 
     * @return sneakerSize size of the sneaker
     */
    public float getSneakerSize() {
        return sneakerSize;
    }

    /**
     * Sets the sneaker size.
     * 
     * @param sneakerSize size to set for the sneaker
     */
    public void setSneakerSize(float sneakerSize) {
        this.sneakerSize = sneakerSize;
    }

    /**
     * Gets the sneaker description.
     * 
     * @return description description of the sneaker
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the sneaker description.
     * 
     * @param description description to set for the sneaker
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the category of the sneaker.
     * 
     * @return category category of the sneaker
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the sneaker.
     * 
     * @param category category to set for the sneaker
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the sneaker brand.
     * 
     * @return brand brand of the sneaker
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the sneaker brand.
     * 
     * @param brand brand to set for the sneaker
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets the sneaker price.
     * 
     * @return price price of the sneaker
     */
    public float getPrice() {
        return price;
    }

    /**
     * Sets the sneaker price.
     * 
     * @param price price to set for the sneaker
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Gets the released date of the sneaker.
     * 
     * @return releasedDate release date of the sneaker
     */
    public LocalDate getReleasedDate() {
        return releasedDate;
    }

    /**
     * Sets the released date of the sneaker.
     * 
     * @param releasedDate release date to set for the sneaker
     */
    public void setReleasedDate(LocalDate releasedDate) {
        this.releasedDate = releasedDate;
    }

    /**
     * Gets the availability status of the sneaker.
     * 
     * @return availabilityStatus availability status of the sneaker
     */
    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    /**
     * Sets the availability status of the sneaker.
     * 
     * @param availabilityStatus availability status to set for the sneaker
     */
    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    /**
     * Gets the image URL of the sneaker.
     * 
     * @return imageUrl image URL of the sneaker
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the image URL of the sneaker.
     * 
     * @param imageUrl image URL to set for the sneaker
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Gets the sales count of the sneaker.
     * 
     * @return sales number of sneakers sold
     */
    public int getSales() {
        return sales;
    }

    /**
     * Sets the sales count of the sneaker.
     * 
     * @param sales number of sneakers sold to set
     */
    public void setSales(int sales) {
        this.sales = sales;
    }

    /**
     * Gets the revenue generated by the sneaker.
     * 
     * @return revenue revenue generated by sales of the sneaker
     */
    public float getRevenue() {
        return revenue;
    }

    /**
     * Sets the revenue generated by the sneaker.
     * 
     * @param revenue revenue to set for the sneaker
     */
    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }
}
