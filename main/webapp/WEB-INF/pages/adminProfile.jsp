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
<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Profile</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin profile.css" />
</head>
<body>
    <div class="dashboard-container">
     <!-- Sidebar Navigation -->
    <div class="sidebar">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/resources/images/system/logo.png" alt="SNE KSPHERE Logo" class="logo-image">
        </div>
        
           <div class="profile">
   <a href="${pageContext.request.contextPath}/adminProfileController">
    <img src="${pageContext.request.contextPath}/resources/images/user/${user.userImageURL}" 
         alt="Profile Photo" class="profile-photo">
    </a>
    <div class="profile-info">
        <span class="profile-name">${user.firstName} ${user.lastName}</span>
        <span class="profile-role">Admin</span>
    </div>
</div>
        
        <ul class="nav-links">
            <li class="#">
                <a href="${pageContext.request.contextPath}/admin">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/blackdashboard-icon.svg" alt="Dashboard" class="nav-icon">
                    <span>Dashboard</span>
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/adminProduct">
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
                <a href="${pageContext.request.contextPath}/login">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/logout-icon.svg" alt="LogOut" class="nav-icon">
                    <span>LogOut</span>
                </a>
            </li>
        </ul>
    </div>
   <!-- Main Content Area -->
	<div class="profile-main-content">
	    <div class="profile-card">
	      
	      <!-- Profile Top Row -->
	     <div class="profile-header-row">
	       <h2>Profile</h2>
	       	<div class="profile-actions">
	         
				        <img src="${pageContext.request.contextPath}/resources/images/user/${user.userImageURL}" alt="Profile Photo" class="profile-photo">
				    
	         <a href="${pageContext.request.contextPath}/editAdminProfileController" class="edit-link"><i class="fas fa-pen"></i> Edit</a>
	       </div>
	     </div>
	 
	     <!-- Profile Form -->
	     <form class="profile-form">
	       <div class="form-row">
	         <div class="form-group">
	           <label for="firstName">First Name <span class="required">*</span></label>
	           <input type="text" id="firstName" value="${user != null ? user.firstName : ''}" disabled/>
	         </div>
	         <div class="form-group">
	           <label for="lastName">Last Name <span class="required">*</span></label>
	           <input type="text" id="lastName" value="${user != null ? user.lastName : ''}" disabled/>
	         </div>
	       </div>
	 
	       <div class="form-group">
	         <label for="phone">Phone Number <span class="required">*</span></label>
	         <input type="text" id="phone" value="${user != null ? user.phone : ''}" disabled/>
	       </div>
	 
	       <div class="form-group">
	         <label for="email">Email <span class="required">*</span></label>
	         <p>${user != null ? user.email : ''} <p>
	       </div>
	 
	       <div class="form-group">
	         <label for="country">Country / Region</label>
	         <p>Nepal</p>
	       </div>
	 
	       <div class="form-group">
	         <label for="address">Address <span class="required">*</span></label>
	         <input type="text" id="address" value="${user != null ? user.address : ''}" disabled/>
	       </div>
	     </form>
	   </div>
	 </div>
	 </div>
  </body>
</html>