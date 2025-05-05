<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/css/addProductAdmin.css" />
</head>
<body>
<div class="main-container">
    <div class="sidebar">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/resources/images/system/logo.png" alt="SNE KSPHERE Logo" class="logo-image">
        </div>
        
        <a href="${pageContext.request.contextPath}/admin/profile">
        <div class="profile">
            <img src="${pageContext.request.contextPath}/resources/images/admin images/profile-admin.jpeg" alt="Profile Photo" class="profile-photo">
            <div class="profile-info">
                <span class="profile-name">John Doe</span>
                <span class="profile-role">Admin</span>
            </div>
        </div>
        </a>
        
        <ul class="nav-links">
            <li class="#">
                <a href="${pageContext.request.contextPath}/admin">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/blackdashboard-icon.svg" alt="Dashboard" class="nav-icon">
                    <span>Dashboard</span>
                </a>
            </li>
            <li class="active">
                <a href="${pageContext.request.contextPath}/adminProduct">
                    <img src="${pageContext.request.contextPath}/resources/images/Admin Product Page Image/Orange product.svg" alt="Products" class="nav-icon">
                    <span style="color: #F0671E;">Product</span>
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
    
    <!-- Main Content -->
    <div class="main-content">
        <div class="product-card">
            <div class="edit-profile-header-row">
                <h2>Add Product</h2>
            </div>
            
             <!-- Display error message if available -->
		        <c:if test="${not empty error}">
		            <p class="error-message">${error}</p>
		            <c:remove var="error" scope="session"/>
		        </c:if>
		
		        <!-- Display success message if available -->
		        <c:if test="${not empty success}">
		            <p class="success-message">${success}</p>
		            <c:remove var="success" scope="session"/>
		        </c:if> 
            
            <!-- Profile Edit Form -->
            <form class="product-form" method="post" action="${pageContext.request.contextPath}/addProductAdmin" enctype="multipart/form-data">

     
		        
						        
		        
                <input type="hidden" name="userId" value="1" />

                <div class="form-row">
                    <div class="form-group">
                        <label for="firstName">Product Name <span class="required">*</span></label>
                        <input type="text" id="sneakerName" name="sneakerName"required />
                    </div>
                    <div class="form-group">
                        <label for="lastName">Brand Name <span class="required">*</span></label>
                        <input type="text" id="brand" name="brand"  required/>
                    </div>
                </div>
                <div class="form-row-1">
                    <div class="form-group-1">
                        <label for="firstName">Sneaker Size <span class="required">*</span></label>
                        <input type="text" id="sneakerSize" name="sneakerSize"  required />
                       
                        
                    </div>
                    <div class="form-group-1">
                        <label for="lastName">Category Name <span class="required">*</span></label>
                        <input type="text" id="category" name="category"  required/>
                    </div>
                    <div class="form-group-1">
                        <label for="releaseDate">Release Date<span class="required">*</span></label>
                        <input type="date" id="releasedDate" name="releasedDate"  required>
                    </div>
                    <div class="form-group-1">
                        <label for="lastName">Price<span class="required">*</span></label>
                        <input type="text" id="price" name="price" required />
                    </div>
                </div>
                <div class="form-row-2">
                    <div class="form-group-2">
                    <label for="description">Description <span class="required">*</span></label>
                    <textarea id="description" name="description" placeholder="Description of the product....." required></textarea>
                    </div>
                </div>

                <div class="form-row-2">
                    <div class="form-group-2">
                        <label for="image">Upload Shoes Image <span class="required">*</span></label> 
                        <input type="file" id="imageUrl" name="imageUrl" required>
                    </div>
                </div>
    
                <div class="form-actions">
                    <button type="submit" class="save-btn">Save</button>
                     <a href="${pageContext.request.contextPath}/adminProduct">
                     	<button type="button" class="cancel-btn">Cancel</button>
                     </a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>