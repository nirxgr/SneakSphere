<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
    
<%@ page import="com.sneaksphere.model.UserModel" %>
<%
    UserModel user = (UserModel) request.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Edit-Profile</title>
     <link rel="stylesheet" href="profile.css">
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
            <img src="${pageContext.request.contextPath}/resources/images/admin images/profile-admin.jpeg" alt="Profile Photo" class="profile-photo">
            <div class="profile-info">
                <span class="profile-name">${user.firstName} ${user.lastName}</span>
                <span class="profile-role">Admin</span>
            </div>
        </div>
        
        <ul class="nav-links">
            <li class="#">
                <a href="#">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/blackdashboard-icon.svg" alt="Dashboard" class="nav-icon">
                    <span >Dashboard</span>
                </a>
            </li>
            <li>
                <a href="#">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/product-icon.svg" alt="Products" class="nav-icon">
                    <span>Products</span>
                </a>
            </li>
            <li>
                <a href="#">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/order-icon.svg" alt="Orders" class="nav-icon">
                    <span>Orders</span>
                </a>
            </li>
            <li>
                <a href="#">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/customer-icon.svg" alt="Customers" class="nav-icon">
                    <span>Customers</span>
                </a>
            </li>
            <li>
                <a href="#">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/brand-icon.svg" alt="Brands" class="nav-icon">
                    <span>Brands</span>
                </a>
            </li>
        </ul>
        
        <ul class="bottom-links">
            <li>
                <a href="#">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/logout-icon.svg" alt="LogOut" class="nav-icon">
                    <span>LogOut</span>
                </a>
            </li>
        </ul>
    </div>
    <!-- Main Content -->
        <div class="profile-main-content">
            <div class="profile-card">
                <div class="edit-profile-header-row">
                    <h2>Edit Profile</h2>
                    <div class="edit-profile-actions">
                        <img src="${pageContext.request.contextPath}/resources/images/admin images/profile-admin.jpeg" alt="Profile Picture" class="edit-profile-image">
                    </div>
                </div>
				<c:if test="${not empty errorMessage}">
			    <div class="error-message" style="color: red; margin-bottom: 15px;">
			        <c:out value="${errorMessage}" escapeXml="false"/>
			    </div>
				</c:if>
                <!-- Profile Edit Form -->
                <form class="profile-form" method="post" action="${pageContext.request.contextPath}/EditAdminProfileController" enctype="multipart/form-data">
            <!-- Hidden fields -->
            <input type="hidden" name="existingImage" value="${user.userImageURL}" />
            <input type="hidden" name="userId" value="${user.userID}" />

            <div class="form-group">
                <label for="imageUrl">Change Profile Picture</label>
                <input type="file" id="imageUrl" name="imageUrl" class="upload-photo-input" />
            </div>
                   
                  <div class="form-group">
                       <label for="firstName">First Name <span class="required">*</span></label>
                       <input type="text" id="firstName" name="firstName" value="${user.firstName}" />
                    </div>
                    <div class="form-group">
                        <label for="lastName">Last Name <span class="required">*</span></label>
                        <input type="text" id="lastName" name="lastName" value="${user.lastName}" />
                    </div>
          
                    <div class="form-group">
                        <label for="phone">Phone Number <span class="required">*</span></label>
                        <input type="text" id="phone" name="phone" value="${user.phone}"  />
                    </div>

                    <div class="form-group">
                        <label for="email">Email <span class="required">*</span></label>
                        <p>${user.email}</p>
                    </div>

                    <div class="form-group">
                        <label for="country">Country / Region</label>
                        <p>Nepal</p> <!-- You can make this dynamic later if needed -->
                    </div>

                    <div class="form-group">
                        <label for="address">Address <span class="required">*</span></label>
                        <input type="text" id="address" name="address" value="${user.address}"/>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="save-btn" onclick="window.location.href='${pageContext.request.contextPath}/admin/profile'">Save</button>
                        <button type="button" class="cancel-btn" onclick="window.location.href='${pageContext.request.contextPath}/admin/profile'">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>