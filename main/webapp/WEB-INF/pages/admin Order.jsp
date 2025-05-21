<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>SNEAKSPHERE - Order Dashboard</title>
  <link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/css/admin Order.css" />
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
</head>
<body>
<!-- Sidebar Navigation -->
 <div class="sidebar">
  <div class="logo">
     <img src="${pageContext.request.contextPath}/resources/images/system/logo.png" class="logo-image">
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
              <img src="${pageContext.request.contextPath}/resources/images/Admin Product Page Image/Admin black dashboard.svg" alt="Dashboard" class="nav-icon">
              <span>Dashboard</span>
          </a>
      </li>
      <li>
          <a href="${pageContext.request.contextPath}/adminProduct">
              <img src="${pageContext.request.contextPath}/resources/images/admin images/product-icon.svg" alt="Products" class="nav-icon">
              <span>Products</span>
          </a>
      </li>
      <li class="active">
          <a href="${pageContext.request.contextPath}/adminOrder">
              <img src="${pageContext.request.contextPath}/resources/images/admin images/order orange.svg" alt="Orders" class="nav-icon">
              <span style="color: #F0671E;">Orders</span>
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
              <img src="${pageContext.request.contextPath}/resources/images/admin images/logout-icon.svg"s alt="LogOut" class="nav-icon">
              <span>LogOut</span>
          </a>
      </li>
  </ul>
</div>

  <!-- Main Content -->
  <div class="main">
  
 <div class="header">
            <h1>Order</h1>
        </div>
        <!-- Display messages -->
        <c:if test="${not empty successMessage}">
        	<div style="color: green; margin-bottom: 10px;">
		          ${successMessage}
		          </div>
		  </c:if>
		  
		  <c:if test="${not empty errorMessage}">
		      <div style="color: red; margin-bottom: 10px;">
		          ${errorMessage}
		      </div>
		  </c:if>
        
    <div class="table-container">
      <table>
        <thead>
          <tr>
            <th>ID</th>
           <th>Customer</th>
           <th>Sneakers</th>
            <th>Quantity</th>
            <th>Size</th>
            <th>Order Total</th>
            <th>Order Status</th>
            <th>Order Date</th>
          </tr>
        </thead>
       <tbody>
       	<c:forEach var="order" items="${orders}">
       		<tr>
	            <td>${order.orderID}</td>
	           <td>${order.customerFirstName} ${order.customerLastName}</td>
	           <td>${order.sneakerNames}</td>
	            <td>${order.quantity}</td>
	            <td><fmt:formatNumber value="${order.size}" type="number" minFractionDigits="0" maxFractionDigits="0" /></td>
	            <td><fmt:formatNumber value="${order.orderTotal}" type="currency" currencySymbol="Rs. " minFractionDigits="0" maxFractionDigits="0" /></td>
	            <td>
                	<form action="${pageContext.request.contextPath}/adminOrder" method="POST">
					    <select name="orderStatus" id="orderStatus">
					        <option value="Pending" ${order.orderStatus == 'Pending' ? 'selected' : ''}>Pending</option>
					        <option value="Shipped" ${order.orderStatus == 'Shipped' ? 'selected' : ''}>Shipped</option>
					        <option value="Delivered" ${order.orderStatus == 'Delivered' ? 'selected' : ''}>Delivered</option>
					        <option value="Processing" ${order.orderStatus == 'Processing' ? 'selected' : ''}>Processing</option>
					        <option value="Cancelled" ${order.orderStatus == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
					        <option value="Returned" ${order.orderStatus == 'Returned' ? 'selected' : ''}>Returned</option>
					    </select>
					    <input type="hidden" name="orderID" value="${order.orderID}" />
					    <button type="submit">Update Status</button>
					</form>

            	</td>
	            <td>${order.orderDate}</td>
        	</tr>
        </c:forEach>
       </tbody>


      </table>
    </div>
  </div>
</body>
</html>