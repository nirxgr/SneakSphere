<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>

<%
    // Initialize session and current user
    HttpSession userSession = request.getSession(false);
    String currentUser = (String) (userSession != null ? userSession.getAttribute("username") : null);
    pageContext.setAttribute("currentUser", currentUser);
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>View Order History</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewOrderHistory.css" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>

<jsp:include page="header.jsp" />

<div class="cart-container">
    <h1 class="cart-title">Your Order History</h1>

    <table class="cart-table">
        <thead>
            <tr>
                <th>Product Name</th>
                <th>Price</th>
                <th>Order Status</th>
      
            </tr>
        </thead>
        <tbody>
            <!-- Dynamically populate orders -->
            <c:forEach var="order" items="${orderDetails}">
    <tr>
        <td>
            <div class="product-info">
                <img src="${pageContext.request.contextPath}/resources/images/sneakers/${order.SneakerImagePath}" alt="Product Image" />
                <div class="product-details">
                    <p class="product-name">${order.SneakerName}</p>
                    <p class="product-desc">${order.SneakerDescription}</p>
                </div>
            </div>
        </td>
        <td>Rs. ${order.SneakerPrice}</td>
        <td>${order.OrderStatus}</td>
        <td>
        <form action="${contextPath}/viewOrderHistory" method="post" onsubmit="return confirm('Are you sure you want to delete this product from the order?');">
    <input type="hidden" name="orderId" value="${order.OrderID}" />
    <input type="hidden" name="sneakerId" value="${order.SneakerID}" />

</form>

        </td>
        
    </tr>
</c:forEach>

        </tbody>
    </table>

  
</div>

<jsp:include page="footer.jsp" />

<!-- JavaScript for delete action -->
<script>
    function deleteRow(btn) {
        const row = btn.closest('tr');
        row.remove();
    }
</script>

</body>
</html>