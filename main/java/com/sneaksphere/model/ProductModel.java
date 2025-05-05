package com.sneaksphere.model;

import java.time.LocalDate;

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

    // Constructors
    public ProductModel() {}

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

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFormattedPrice() {
        return String.format("%,.0f", this.price);
    }

    public String getDisplayPrice() {
        return "Rs. " + getFormattedPrice();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

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