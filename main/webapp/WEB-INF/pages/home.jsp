<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SNEAKSPHERE</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/home.css" />
</head>

<body>

<div class="body">
   <jsp:include page="header.jsp" />
	
	   <!-- Image Slider -->
<section class="slider-section">
    <div class="slider-container">
        <div class="slider">
            <div class="slide active">
                <img src="${pageContext.request.contextPath}/resources/images/system/image1.webp" alt="Slider Image 1">
            </div>
            <div class="slide">
                <img src="${pageContext.request.contextPath}/resources/images/system/image2.webp" alt="Slider Image 2">
            </div>
            <div class="slide">
                <img src="${pageContext.request.contextPath}/resources/images/system/image3.webp" alt="Slider Image 3">
            </div>
        </div>
    </div>
</section>
<script src="${pageContext.request.contextPath}/js/Bannerchanger1.js"></script>

    
  <!-- Brand Section -->
    <section class="brand-section">
        <h2>SHOP OUR TOP BRAND</h2>
        <div class="brand-grid">
            <div class="brand-card"><img src="${pageContext.request.contextPath}/resources/images/system/nike copy.png" alt="Nike" /></div>
            <div class="brand-card"><img src="${pageContext.request.contextPath}/resources/images/system/nb-icon.png" alt="New Balance" /></div>
            <div class="brand-card"><img src="${pageContext.request.contextPath}/resources/images/system/asics copy.png" alt="Asics" /></div>
            <div class="brand-card"><img src="${pageContext.request.contextPath}/resources/images/system/ugg copy.png" alt="UGG" /></div>
            <div class="brand-card"><img src="${pageContext.request.contextPath}/resources/images/system/puma copy.png" alt="Puma" /></div>
            <div class="brand-card"><img src="${pageContext.request.contextPath}/resources/images/system/one8 copy.png" alt="On Running" /></div>
            <div class="brand-card"><img src="${pageContext.request.contextPath}/resources/images/system/addidas copy.png" alt="Adidas" /></div>
            <div class="brand-card"><img src="${pageContext.request.contextPath}/resources/images/system/jordan copy.png" alt="Jordan" /></div>
        </div>
    </section>

    <section class="promo-section">
        <div class="promo-grid">
            <img src="${pageContext.request.contextPath}/resources/images/system/first.webp" alt="FREEH FOR SPRING">
            <img src="${pageContext.request.contextPath}/resources/images/system/second.webp" alt="SHOX ARE BACK">
            <img src="${pageContext.request.contextPath}/resources/images/system/third.webp" alt="REFRESH YOUR ROTATION">
            <img src="${pageContext.request.contextPath}/resources/images/system/Four.webp" alt="ALL-TIME STYLES">
        </div>
    </section>
    
    <section class="shoe-section">
        <h2>FIND YOUR MAX</h2>
        <div class="shoe-grid">
            <div class="shoe-card">
                <img src="${pageContext.request.contextPath}/resources/images/system/shoe1.jpg" alt="Air Max 90 LV8">
                <h3>Air Max 90 LV8</h3>
            </div>
            <div class="shoe-card">
                <img src="${pageContext.request.contextPath}/resources/images/system/shoe2.jpg" alt="Air Max Plus">
                <h3>Air Max Plus</h3>
            </div>
            <div class="shoe-card">
                <img src="${pageContext.request.contextPath}/resources/images/system/shoe3.jpg" alt="Air Max On">
                <h3>Air Max On</h3>
            </div>
            <div class="shoe-card">
                <img src="${pageContext.request.contextPath}/resources/images/system/shoe4.jpg" alt="Air Max 90">
                <h3>Air Max 90</h3>
            </div>
        </div>
    </section>
    
     <!-- Luka Section -->
    <section class="luka-section">
        <div class="luka-container">
            <img src="${pageContext.request.contextPath}/resources/images/system/LUKA.png" alt="Luka 4 Space Navigator" class="luka-image">
            <div class="luka-content">
                <span class="coming-soon">Coming Soon</span>
                <h1 class="luka-title">LUKA</h1>
                <p class="luka-description">Stability. Zoom Air. Shoes on nice, they can't stay mad. Meet the Luka 4
                    Space Navigator.</p>
            </div>
        </div>
    </section>
    
     <section class="icon-sneakers-carousel">
        <h2 class="icon-sneakers-title">SHOP BY ICONS</h2>
        <div class="icon-sneakers-wrapper">
            <div class="icon-sneakers-track">
                <div class="icon-sneaker-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/NB 530.webp" alt="NB 9060">
                    <h3>NB 9060</h3>
                </div>
                <div class="icon-sneaker-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/VEMERO 5.webp" alt="VOMERO5">
                    <h3>VOMERO5</h3>
                </div>
                <div class="icon-sneaker-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/Speed Cat.webp" alt="SPEEDCAT">
                    <h3>SPEEDCAT</h3>
                </div>
                <div class="icon-sneaker-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/Air Force 1.webp" alt="AIR FORCE I">
                    <h3>AIR FORCE I</h3>
                </div>
                <div class="icon-sneaker-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/ASICS GEL-1130.webp" alt="ASICS GEL-1130">
                    <h3>ASICS GEL-1130</h3>
                </div>
                <div class="icon-sneaker-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/Puma Nd.webp" alt="PUMA Suede">
                    <h3>PUMA SUEDE</h3>
                </div>
                <div class="icon-sneaker-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/Adidas samba.webp" alt="ADIDAS SAMBA">
                    <h3>ADIDAS SAMBA</h3>
                </div>
                <div class="icon-sneaker-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/ASICS EX89 Omakase.webp" alt="ASICS EX89 OMAKASE">
                    <h3>ASICS EX89 OMAKASE</h3>
                </div>
            </div>
        </div>
    </section>
    
    <div class="dynamic-image-slider">
        <div class="dynamic-slider-container">
            <div class="dynamic-slider">
                <img src="${pageContext.request.contextPath}/resources/images/system/changer1.webp" alt="Dynamic Slider Image">
                <img src="${pageContext.request.contextPath}/resources/images/system/changer2.webp" alt="Dynamic Slider Image">
            </div>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/js/Bannerchanger2.js"></script>
    
    <section class="spotlight-section">
        <h2 class="spotlight-heading">IN THE SPOTLIGHT</h2>
        <div class="spotlight-images">
            <img src="${pageContext.request.contextPath}/resources/images/system/Spot2.png" alt="Spotlight 1">
            <img src="${pageContext.request.contextPath}/resources/images/system/Spot1.jpg" alt="Spotlight 2">
        </div>
    </section>
    
<!-- NEW RELEASES SHOES Section -->
<section class="trending-products">
    <div class="section-header">
        <h2 class="trending-title">NEW RELEASES SHOES</h2>
    </div>
    
    <div class="trending-wrapper">
        <div class="trending-track">
            <c:choose>
                <c:when test="${not empty products}">
                    <c:forEach var="product" items="${products}">
                        <div class="trending-card">
                            <a href="${pageContext.request.contextPath}/ProductController?action=view&id=${product.sneakerID}">
                               <img src="${pageContext.request.contextPath}/resources/images/system/${product.imageUrl}" alt="${sneaker.sneakerName}" />
                            </a>
                            <h3>${product.sneakerName}</h3>
                            <p class="product-type">${product.category}</p>
                            <p class="product-price">Rs. <fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="2"/></p>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p class="no-products">No new releases found</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</section>
    
    <jsp:include page="footer.jsp" />
</div>

</body>
</html>