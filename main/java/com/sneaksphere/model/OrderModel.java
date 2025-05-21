package com.sneaksphere.model;

import java.util.Date;

/**
 * OrderModel represents an order placed by a customer in the SneakSphere system.
 * It contains details about the order such as ID, quantity, size, total amount,
 * status, order date, associated customer, and user information.
 * 
 * @author 
 */
public class OrderModel {

    private int orderID;
    private int quantity;
    private float size;
    private float orderTotal;
    private String orderStatus;
    private Date orderDate;
    private int customerId;
    private int userID;

    private String customerFirstName;
    private String customerLastName;
    private String sneakerNames;

    /**
     * @return the first name of the customer who placed the order
     */
    public String getCustomerFirstName() {
        return customerFirstName;
    }

    /**
     * @param customerFirstName the customer's first name to set
     */
    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    /**
     * @return the last name of the customer who placed the order
     */
    public String getCustomerLastName() {
        return customerLastName;
    }

    /**
     * @param customerLastName the customer's last name to set
     */
    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    /**
     * @return the names of sneakers included in the order
     */
    public String getSneakerNames() {
        return sneakerNames;
    }

    /**
     * @param sneakerNames the sneaker names to set
     */
    public void setSneakerNames(String sneakerNames) {
        this.sneakerNames = sneakerNames;
    }

    /**
     * @return the ID of the customer who placed the order
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customer ID to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the unique order ID
     */
    public int getOrderID() {
        return orderID;
    }

    /**
     * @param orderId the order ID to set
     */
    public void setOrderID(int orderId) {
        this.orderID = orderId;
    }

    /**
     * @return the quantity of items ordered
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity of items to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the size associated with the order (e.g., shoe size)
     */
    public float getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(float size) {
        this.size = size;
    }

    /**
     * @return the total price of the order
     */
    public float getOrderTotal() {
        return orderTotal;
    }

    /**
     * @param orderTotal the total price to set
     */
    public void setOrderTotal(float orderTotal) {
        this.orderTotal = orderTotal;
    }

    /**
     * @return the current status of the order (e.g., pending, shipped)
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus the status to set
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * @return the user ID associated with this order (could be admin or staff)
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @param userID the user ID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @return the date when the order was placed
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the order date to set
     */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
