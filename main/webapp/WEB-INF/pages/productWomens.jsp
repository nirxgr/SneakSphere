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
    <title>Womens Product</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/product.css" />

</head>

<body>

 <jsp:include page="header.jsp" />
    
   <section class="brand-showcase">
        <div class="container">
            <div class="brand-row">
                <div class="brand-item">
                    <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/Pti 1.webp" alt="Nike">
                    <span class="brand-name">NIKE</span>
                </div>
                <div class="brand-item">
                    <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/Pti 2.webp" alt="Jordan">
                    <span class="brand-name">JORDAN</span>
                </div>
                <div class="brand-item">
                    <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/Pti 3.webp" alt="Adidas">
                    <span class="brand-name">ADIDAS</span>
                </div>
                <div class="brand-item">
                    <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/Pti 4.webp" alt="On">
                    <span class="brand-name">ON</span>
                </div>
                <div class="brand-item">
                    <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/Pti 5.webp" alt="Hoka">
                    <span class="brand-name">HOKA</span>
                </div>
                <div class="brand-item">
                    <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/Pti 6.webp" alt="Vans">
                    <span class="brand-name">VANS</span>
                </div>
                <div class="brand-item">
                    <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/Pti 7.webp" alt="Salcony">
                    <span class="brand-name">SALCONY</span>
                </div>
            </div>
        </div>
    </section>
    
    
    <section class="product-listing">
        <div class="container">
            <div class="breadcrumb">
                <a href="#">Home</a> > <a href="#">Men's Shoes</a>
            </div>

            <div class="page-header-container">
                <div class="page-header">
                    <h1>Men's Shoes</h1>
                   <div class="results-count">Showing ${resultsCount} results</div>
                </div>
               <div class="sorting-options">
    <form id="sortForm" method="get">
        <select class="sort-dropdown" name="sort" onchange="document.getElementById('sortForm').submit()">
            <option value="featured" ${param.sort == 'featured' ? 'selected' : ''}>Featured</option>
            <option value="price-low" ${param.sort == 'price-low' ? 'selected' : ''}>Price (Low to High)</option>
            <option value="price-high" ${param.sort == 'price-high' ? 'selected' : ''}>Price (High to Low)</option>
            <option value="alpha-az" ${param.sort == 'alpha-az' ? 'selected' : ''}>Ascending (A to Z)</option>
            <option value="brand-az" ${param.sort == 'brand-az' ? 'selected' : ''}>Brand Names (A to Z)</option>
        </select>
    </form>
</div>
            </div>
            <div class="filter-section">
                <div class="main-filer">
                    <div class="filter-sidebar">
                        <h3 class="filter-title">Refine Result</h3>

                        <div class="filter-group">
                            <div class="pick-up-option">
                                <div class="pick-up-container">
                                    <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/iconoir--shop.svg" alt="Store icon"
                                        class="pick-up-icon">
                                    <span class="pick-up-text">Pick up in store</span>
                                </div>
                                <a href="#" class="find-store-link">Find a store</a>
                            </div>
                        </div>

                        <hr class="filter-divider">

                        <div class="filter-group">
                            <h4 class="filter-subtitle">Brand Type</h4>
                            <div class="filter-options">
                                <label class="checkbox-container">
                                    <input type="checkbox">
                                    <span class="checkmark"></span>
                                    <span class="checkbox-label">NIKE (30)</span>
                                </label>
                                <label class="checkbox-container">
                                    <input type="checkbox">
                                    <span class="checkmark"></span>
                                    <span class="checkbox-label">ADIDAS (14)</span>
                                </label>
                                <label class="checkbox-container">
                                    <input type="checkbox">
                                    <span class="checkmark"></span>
                                    <span class="checkbox-label">NB (22)</span>
                                </label>
                                <label class="checkbox-container">
                                    <input type="checkbox">
                                    <span class="checkmark"></span>
                                    <span class="checkbox-label">UGG (13)</span>
                                </label>
                                <label class="checkbox-container">
                                    <input type="checkbox">
                                    <span class="checkmark"></span>
                                    <span class="checkbox-label">ASICS (31)</span>
                                </label>
                                <label class="checkbox-container">
                                    <input type="checkbox">
                                    <span class="checkmark"></span>
                                    <span class="checkbox-label">ON (03)</span>
                                </label>
                                <label class="checkbox-container">
                                    <input type="checkbox">
                                    <span class="checkmark"></span>
                                    <span class="checkbox-label">PUMA (21)</span>
                                </label>
                                <label class="checkbox-container">
                                    <input type="checkbox">
                                    <span class="checkmark"></span>
                                    <span class="checkbox-label">JORDAN (11)</span>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="product-banner">
                        <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/add.webp" alt="STAY IN ROTATION" class="banner-image">
                    </div>

                    <div class="product-banner">
                        <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/ADD2.webp" alt="STAY IN ROTATION" class="banner-image">
                    </div>

                    <div class="product-banner">
                        <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/New bball finder Desktop.webp" alt="STAY IN ROTATION"
                            class="banner-image">
                    </div>
                </div>
                
                
                
                
     <div class="product-grid-container">
    <div class="product-grid">
        <c:choose>
            <c:when test="${not empty products}">
                <c:forEach var="product" items="${products}">
                    <div class="product-card">
                            <div class="product-image">
                            <button class="wishlist-btn">
                                    <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/heart.svg" alt="Add to wishlist" class="wishlist-icon">
                                </button>
                                  <a href="${pageContext.request.contextPath}/individualProductPage?id=${product.sneakerID}">
                                <img src="${pageContext.request.contextPath}/resources/images/sneakers/${product.imageUrl}" alt="${product.sneakerName}">           
                            </div>
                            <div class="product-info">
                                <h3 class="product-name">${product.sneakerName}</h3>
                                <div class="product-rating">
                                    <div class="stars">
                                        <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/star.svg" alt="Star" class="star-icon">
                                        <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/star.svg" alt="Star" class="star-icon">
                                        <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/star.svg" alt="Star" class="star-icon">
                                        <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/star.svg" alt="Star" class="star-icon">
                                        <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/star.svg" alt="Star" class="star-icon">
                                    </div>
                                    <span class="review-count">(42)</span>
                                </div>
                                <p class="product-category">${product.category}</p>
                                <p class="product-price">Rs. ${product.price}</p>
                                 </a>
                                <button class="add-to-cart-btn">
                                    <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/add to cart.svg" alt="Add to cart" class="cart-icon">
                                    Add to Cart
                                </button>
                            </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p class="no-products">No products available at the moment.</p>
            </c:otherwise>
        </c:choose>
    </div>
</div>
    </section>
    
    <jsp:include page="footer.jsp" />
    
</body>
</html>