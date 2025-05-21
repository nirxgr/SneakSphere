<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SNEAKSPHERE - Product Dashboard</title>
    <link rel="stylesheet" type="text/css"
href="${pageContext.request.contextPath}/css/admin Product.css" />
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
            <li class="active">
                <a href= "${pageContext.request.contextPath}/adminProduct">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/Totalproducts-icon.svg" alt="Products" class="nav-icon">
                    <span style="color: #F0671E;">Products</span>
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
		<c:if test="${not empty successMessage}">
		    <div style="color: green;">
		        ${successMessage}
		    </div>
		    <c:remove var="successMessage" scope="session"/>
		</c:if>
		
		<c:if test="${not empty errorMessage}">
		    <div style="color: red;">
		        ${errorMessage}
		    </div>
		    <c:remove var="errorMessage" scope="session"/>
		</c:if>
		
		    
        <div class="header">
            <h1>Products</h1>
            <a href = "${pageContext.request.contextPath}/addProductAdmin">
            <button class="add-product-btn">+ Add Product</button>
            </a>
        </div>

        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>Product</th>
                        <th>Size</th>
                        <th>Category</th>
                        <th>Brand</th>
                        
                        <th>Price (Rs.)</th>
                        <th>Availability Status</th>
                        <th>Actions</th>
                        <th> </th>
                    </tr>
                </thead>
                <tbody>
			    <c:forEach var="sneaker" items="${sneakerList}">
			        <tr>
			            <td>
			                <div class="product-cell">
			                    <img src="${pageContext.request.contextPath}/resources/images/sneakers/${sneaker.imageUrl}" alt="${sneaker.sneakerName}" class="product-image">
			                    <span>${sneaker.sneakerName}</span>
			                </div>
			            </td>
			            <td>${sneaker.sneakerSize}</td>
			            <td>${sneaker.category}</td>
			            <td>${sneaker.brand}</td>
			            <td>${sneaker.price}</td>
			            <td>${sneaker.availabilityStatus}</td>
			           <td>
				            <form action="${pageContext.request.contextPath}/adminProduct" method="post" style="display: inline;">
									<input type="hidden" name="sneakerID" value="${sneaker.sneakerID}">
									<input type="hidden" name="action" value="updateForm">
									<button class="action-btn" type="submit">Edit</button>
							</form>
							<form action="${pageContext.request.contextPath}/adminProduct" method="post" style="display: inline;">
									<input type="hidden" name="sneakerID" value="${sneaker.sneakerID}">
									<input type="hidden" name="action" value="delete">
									<button class="action-btn" type="submit">Delete</button>
							</form>
						</td>
			        </tr>
			    </c:forEach>
			</tbody>

            </table>
        </div>
    </div>    

</body>
</html>