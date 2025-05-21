<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>


<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SNEAKSPHERE - Search Results</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/search product.css" />
</head>

<body>
    <jsp:include page="header.jsp" />
    
    <!-- Brand Showcase Section -->
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

    <!-- Search Results Section -->
    <main class="main-content">
        <section class="search-results">
            <div class="container">
                <c:if test="${not empty error}">
                    <div class="error-message">${error}</div>
                </c:if>
                
                <h2 class="search-results-title">Search Results for: "${searchTerm}"</h2>
                
                <c:choose>
                    <c:when test="${not empty searchResults}">
                        <div class="product-grid">
                            <c:forEach var="product" items="${searchResults}">
                                <div class="product-card">
                                        <div class="product-image">
                                        <a href="${contextPath}/individualProductPage?id=${product.sneakerID}">
                                            <img src="${pageContext.request.contextPath}/resources/images/sneakers/${product.imageUrl}" 
                                                 alt="${product.sneakerName}">
                                          </a>
                                            <form action="${contextPath}/wishlist" method="post">
						    <input type="hidden" name="sneakerID" value="${product.sneakerID}"/>
						    <input type="hidden" name="action" value="add">
						    <button type="submit" class="wishlist-btn">
						        <img src="${contextPath}/resources/images/Product Page Image/heart.svg" 
						             alt="Add to wishlist" class="wishlist-icon">
						    </button>
						</form>
                                        </div>
                                        <div class="product-info">
                                            <h3 class="product-name">${product.sneakerName}</h3>
                                            <p class="product-category">${product.brand} - ${product.category}</p>
                                            <p class="product-price">Rs. <fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="2"/></p>
                                             <form action="${contextPath}/cart" method="post" style="display:inline;">
										    <input type="hidden" name="sneakerID" value="${product.sneakerID}" />
										    <input type="hidden" name="price" value="${product.price}" />
										    <input type="hidden" name="quantity" value="1" />
										    <input type="hidden" name="action" value="add" />
										
										    <button type="submit" class="add-to-cart-btn">
										        <img src="${contextPath}/resources/images/Product Page Image/add to cart.svg" alt="Add to cart" class="cart-icon" />
										        Add to Cart
										    </button>
										</form>
                                        </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:when test="${not empty message}">
                        <p class="no-results">${message}</p>
                    </c:when>
                    <c:otherwise>
                        <p class="no-results">Enter a search term to find products</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </section>
        
        
    </main>
    
    <jsp:include page="footer.jsp" />
</body>
</html>