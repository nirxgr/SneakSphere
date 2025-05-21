<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>

<%
    // Initialize necessary objects and variables
    HttpSession userSession = request.getSession(false);
    String currentUser = (String) (userSession != null ? userSession.getAttribute("username") : null);
    // need to add data in attribute to select it in JSP code using JSTL core tag
    pageContext.setAttribute("currentUser", currentUser);
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SNEAKSPHERE</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
</head>

<body>
    <header class="header">
        <div class="header-container">
            <!-- Logo -->
            <div class="logo-container">
               <a href ="${pageContext.request.contextPath}/home"> <img src="${pageContext.request.contextPath}/resources/images/system/logo.png" alt="SNEAKSPHERE Logo" class="logo"></a>
            </div>

           <!-- Search Bar -->
<div class="search-container">
    <form action="${pageContext.request.contextPath}/searchProduct" method="get">
        <div class="search-wrapper">
            <input type="text" name="query" placeholder="Search" class="search-bar" required>
            <button type="submit" class="search-button">
                <img src="${pageContext.request.contextPath}/resources/images/system/search icon.svg" 
                     alt="Search Icon" width="26" height="26">
            </button>
        </div>
    </form>
</div>

            <!-- Navigation Links -->
                 <nav class="nav-links">
                <a href="${pageContext.request.contextPath}/aboutus" class="nav-text">About Us</a>
                <a href="${pageContext.request.contextPath}/contactUs" class="nav-text">Contact Us</a>
                <span class="login-btn">

                   
                   <c:choose>
	                   <c:when test="${not empty currentUser}">
		                   <a href = "${pageContext.request.contextPath}/customerProfileController" style="text-decoration: none;"> 
			                   <img src="${pageContext.request.contextPath}/resources/images/system/Profile.svg" alt="Profile Icon" />
	                    	</a>
	                    </c:when>
                    	<c:otherwise>
                    		<a href = "${pageContext.request.contextPath}/login" style="text-decoration: none; color: inherit;">
                    			Login /
                    		</a>
                    	</c:otherwise>
                	</c:choose>
                	
                	<c:choose>
	                   <c:when test="${not empty currentUser}">
		                   <a href = "${pageContext.request.contextPath}/logout" style="text-decoration: none;color: inherit;"> 
			                  Logout
	                    	</a>
	                    </c:when>
                    	<c:otherwise>
                    		<a href = "${pageContext.request.contextPath}/signup" style="text-decoration: none;color: inherit;"  >
                    			Sign Up
                    		</a>
                    	</c:otherwise>
                	</c:choose>
                	 </span>
                <a href ="${pageContext.request.contextPath}/wishlist" class="nav-icon" aria-label="Wishlist">
                    <img src="${pageContext.request.contextPath}/resources/images/system/heart.svg" alt="Wishlist Icon" />
                </a>
                <a href ="${pageContext.request.contextPath}/cart" class="nav-icon cart-icon" aria-label="Cart">
                   <img src="${pageContext.request.contextPath}/resources/images/system/cart.svg" alt="Cart Icon" />
                </a>
            </nav>
        </div>
    </header>
                	

    <!-- Sub Navigation Bar -->
    <nav class="sub-nav">
        <div class="sub-nav-container">
            <a href="${pageContext.request.contextPath}/newProduct" class="sub-nav-link">New</a>
            <a href="${pageContext.request.contextPath}/product" class="sub-nav-link">Men's</a>
            <a href="${pageContext.request.contextPath}/womensProduct" class="sub-nav-link">Women's</a>
            <span class="separator"></span>
            <a href="${pageContext.request.contextPath}/futureWorks" class="sub-nav-link">Brands</a>
            <a href="${pageContext.request.contextPath}/futureWorks" class="sub-nav-link">Releases</a>
            <a href="${pageContext.request.contextPath}/futureWorks" class="sub-nav-link">New Arrivals</a>
            <span class="separator"></span>
            <a href="${pageContext.request.contextPath}/futureWorks" class="sub-nav-link">SneakSphere Rewards</a>
        </div>
    </nav>

    </body>
    </html>