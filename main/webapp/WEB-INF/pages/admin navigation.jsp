<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.sneaksphere.model.UserModel" %>
<%
    UserModel user = (UserModel) request.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Navigation</title>
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
<link rel="stylesheet" type="text/css"
href="${pageContext.request.contextPath}/css/admin navigation.css" />
</head>
<body>
<!-- Sidebar Navigation -->
    <div class="sidebar">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/resources/images/system/logo.png" alt="SNE KSPHERE Logo" class="logo-image">
        </div>
        
        <div class="profile">
        <a href="${pageContext.request.contextPath}/admin/profile">
            <img src="${pageContext.request.contextPath}/resources/images/admin images/profile-admin.jpeg" alt="Profile Photo" class="profile-photo">
            <div class="profile-info">
                <span class="profile-name">${user != null ? user.firstName : ''} ${user != null ? user.lastName : ''} </span>
                <span class="profile-role">Admin</span>
            </div>
            </a>
        </div>
        
        <ul class="nav-links">
            <li class="active">
                <a href="${pageContext.request.contextPath}/admin">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/dashboard-icon.svg" alt="Dashboard" class="nav-icon">
                    <span style="color: #F0671E;">Dashboard</span>
                </a>
            </li>
            <li>
                <a href= "${pageContext.request.contextPath}/adminProduct">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/product-icon.svg" alt="Products" class="nav-icon">
                    <span>Products</span>
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/adminOrder">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/order-icon.svg" alt="Orders" class="nav-icon">
                    <span>Orders</span>
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/adminCustomer">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/customer-icon.svg" alt="Customers" class="nav-icon">
                    <span>Customers</span>
                </a>
            </li>
        </ul>
        
        <ul class="bottom-links">
            <li>
                <a href = "${pageContext.request.contextPath}/logout" style="text-decoration: none; color: inherit;"> 
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/logout-icon.svg" alt="LogOut" class="nav-icon">
                    <span>LogOut</span>
                </a>
            </li>
        </ul>
    </div>
</body>
</html>