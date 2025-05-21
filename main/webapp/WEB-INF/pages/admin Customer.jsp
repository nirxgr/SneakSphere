<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SNEAKSPHERE - Customers</title>
    <link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/css/admin Customer.css" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
</head>
<body>
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
            <li>
                <a href="${pageContext.request.contextPath}/admin">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/blackdashboard-icon.svg" alt="Dashboard" class="nav-icon">
                    <span>Dashboard</span>
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
            <li  class="active">
                <a href="${pageContext.request.contextPath}/adminCustomer">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/TotalCustomer-icon.svg" alt="Customers" class="nav-icon">
                    <span style="color: #F0671E;">Customers</span>
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
        <div class="header">
            <h1>Customers</h1>
        </div>

        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Customer Name</th>
                        <th>Email Address</th>
                        <th>Phone Number</th>
                        <th>Address</th>
                    </tr>
                </thead>
               <tbody>
				    <c:if test="${not empty customers}">
					    <c:forEach var="customer" items="${customers}">
					        <tr>
					            <td>${customer.userID}</td>
					            <td>${customer.firstName} ${customer.lastName}</td>
					            <td>${customer.email}</td>
					            <td>${customer.phone}</td>
					            <td>${customer.address}</td>
					        </tr>
					    </c:forEach>
					</c:if>
					<c:if test="${empty customers}">
					    <tr><td colspan="5">No customers found.</td></tr>
					</c:if>


				</tbody>


            </table>
        </div>
    </div>

</body>
</html>