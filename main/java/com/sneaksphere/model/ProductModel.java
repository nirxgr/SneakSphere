package com.sneaksphere.model;

import java.time.LocalDate;

/**
 * ProductModel represents a product available in the SneakSphere system.
 * It stores details such as product ID, name, description, category, brand,
 * price, image URL, size, status, and release date.
 * 
 * This class provides constructors for creating product instances and
 * getters and setters for accessing and modifying the product attributes.
 * It also includes utility methods for formatted price display.
 * 
 * @author 
 */
public class ProductModel {
    private int productId;
    private String name;
    private String description;
    private String category;
    private String brand;
    private double price;
    private String imageUrl;
    private String size;
    private String status;
    private LocalDate releaseDate;

    /**
     * Default constructor.
     */
    public ProductModel() {}

    /**
     * Parameterized constructor to initialize all fields.
     * 
     * @param productId the unique identifier for the product
     * @param name the name of the product
     * @param description a brief description of the product
     * @param category the product category
     * @param brand the brand of the product
     * @param price the price of the product
     * @param imageUrl URL to the product image
     * @param size the size specification of the product
     * @param status the current status of the product (e.g., available, out of stock)
     * @param releaseDate the release date of the product
     */
    public ProductModel(int productId, String name, String description, String category, 
                      String brand, double price, String imageUrl, String size, 
                      String status, LocalDate releaseDate) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.imageUrl = imageUrl;
        this.size = size;
        this.status = status;
        this.releaseDate = releaseDate;
    }

    /**
     * @return the unique product ID
     */
    public int getProductId() {
        return productId;
    }

    /**
     * @param productId the product ID to set
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the product name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the product description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the product description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the product category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the product category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the product brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand the product brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the product price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the price formatted without decimals and with commas as thousand separators.
     * 
     * @return formatted price as a string without decimals (e.g., "1,000")
     */
    public String getFormattedPrice() {
        return String.format("%,.0f", this.price);
    }

    /**
     * Returns the price as a display string prefixed with currency "Rs."
     * 
     * @return the display price string (e.g., "Rs. 1,000")
     */
    public String getDisplayPrice() {
        return "Rs. " + getFormattedPrice();
    }

    /**
     * @return the URL of the product image
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl the product image URL to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * @return the size specification of the product
     */
    public String getSize() {
        return size;
    }

    /**
     * @param size the product size to set
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * @return the current status of the product
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the product status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the release date of the product
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate the release date to set
     */
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Returns a string representation of the product, including productId, name, price, and formatted price.
     * 
     * @return a string describing the product model
     */
    @Override
    public String toString() {
        return "ProductModel{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", formattedPrice='" + getFormattedPrice() + '\'' +
                '}';
    }
}
