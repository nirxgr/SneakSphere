<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>

<%@ page import="jakarta.servlet.ServletException" %>



<%
    // Initialize necessary objects and variables
    HttpSession userSession = request.getSession(false);
    String currentUser = (String) (userSession != null ? userSession.getAttribute("username") : null);
    Integer userID = (Integer) (userSession != null ? userSession.getAttribute("userID") : null);
    // need to add data in attribute to select it in JSP code using JSTL core tag
    pageContext.setAttribute("currentUser", currentUser);
    pageContext.setAttribute("userID", userID);
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Wishlist</title>
 <link rel="stylesheet" type="text/css"
href="${pageContext.request.contextPath}/css/wishlist.css" />
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>

<jsp:include page="header.jsp" />
                	

  <div class="cart-container">
    <h1 class="cart-title">Welcome to Wishlist</h1>
  
    <table class="cart-table">
      <thead>
        <tr>
          <th>Product Name</th>
          <th>Price</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <c:choose>
            <c:when test="${empty userID}">
                <tr>
                    <td colspan="3" style="text-align: center;">
                        Please <a href="${contextPath}/login">login</a> to view your wishlist
                    </td>
                </tr>
            </c:when>
            <c:when test="${empty wishlistItems}">
                <tr>
                    <td colspan="3" style="text-align: center;">Your wishlist is empty</td>
                </tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="item" items="${wishlistItems}">
                    <tr>
                        <td>
                            <div class="product-info">
                                <img src="${contextPath}/resources/images/sneakers/${item.imageUrl}" alt="Product Image" 
                                     onerror="this.src='${contextPath}/resources/images/system/default-sneaker.jpg'" />
                                <div class="product-details">
                                    <p class="product-name">${item.sneakerName}</p>
                                    <p class="product-desc">${item.description}</p>
                                </div>
                            </div>
                        </td>
                        <td>Rs. ${item.price}</td>
                        <td>
                            <form action="${contextPath}/wishlist" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="remove">
                                <input type="hidden" name="sneakerID" value="${item.sneakerID}">
                                <button type="submit" class="delete-btn">
                                    <img src="https://img.icons8.com/?size=100&id=85081&format=png&color=000000" 
                                         alt="Delete Icon" class="delete-icon" />
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
      </tbody>
    </table>
    
    <!--<c:if test="${not empty wishlistItems}">
        <div class="checkout-btn-container">
            <form action="${contextPath}/cart" method="post">
                <input type="hidden" name="action" value="addAll">
                <button type="submit" class="add-to-cart-btn">
                    <img src="${contextPath}/resources/images/Product Page Image/add to cart.svg" alt="Add to cart" class="cart-icon">
                    Add All to Cart
                </button>
            </form>
        </div>
    </c:if> -->
  </div>

  <!-- Include Footer -->
  <jsp:include page="footer.jsp" />

  
  <script>
      function deleteRow(btn) {
          const row = btn.closest('tr');
          row.remove();
      }
  </script>
  
</body>
</html>