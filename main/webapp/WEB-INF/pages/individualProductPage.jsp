<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SNEAKSPHERE - ${sneaker.sneakerName}</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/css/individualProductPage.css" />
</head>
<body>

<jsp:include page="header.jsp" />

<main class="product-container">
    <div class="product-gallery">
        <div class="shoe-grid">
            <!-- Main product image -->
            <img src="${pageContext.request.contextPath}/resources/images/sneakers/${sneaker.imageUrl}" alt="${sneaker.sneakerName}"
                class="shoe-img">
        </div>
    </div>

    <div class="product-details">
        <div class="product-badge">Best Seller</div>

        <div class="product-rating">
            <div class="stars">
                <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/star.svg" alt="Filled star" class="star-icon">
                <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/star.svg" alt="Filled star" class="star-icon">
                <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/star.svg" alt="Filled star" class="star-icon">
                <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/star.svg" alt="Filled star" class="star-icon">
                <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/star.svg" alt="Half star" class="star-icon">
            </div>
            <span class="rating-count">(67)</span>
        </div>

        <h1 class="product-title">${sneaker.sneakerName}</h1>
        <p class="product-category">${sneaker.category}</p>

        <div class="product-description">
            <p>${sneaker.description}</p>
        </div>

        <div class="product-price">Rs. <fmt:formatNumber value="${sneaker.price}" pattern="#,##0"/></div>
        <div class="section-separator"></div>

       <div class="option-section">
    <h3 class="option-title">Available Sizes (UK)</h3>
    <div class="size-options">
        <div class="size-row">
            <c:forEach items="${availableSizes}" var="size">
                <button class="size-option" data-size="${size}">${size}</button>
            </c:forEach>
        </div>
    </div>
</div>

       <div class="button-group">
    <form action="${pageContext.request.contextPath}/cart" method="post" style="display:inline;">
        <input type="hidden" name="sneakerID" value="${sneaker.sneakerID}" />
        <input type="hidden" name="price" value="${sneaker.price}" />
        <input type="hidden" name="quantity" value="1" />
        <input type="hidden" name="action" value="add" />
        
        <button type="submit" class="add-to-cart">
            <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/add to cart.svg" 
                 alt="Add to cart" class="cart-icon" />
            Add to Cart
        </button>
    </form>
        <form action="${pageContext.request.contextPath}/individualProductPage" method="post" style="display:inline;">
    <input type="hidden" name="action" value="buyNow">
    <input type="hidden" name="sneakerID" value="${sneaker.sneakerID}">
    <input type="hidden" name="id" value="${sneaker.sneakerID}">
    <input type="hidden" name="quantity" value="1"> <!-- Explicitly set quantity to 1 -->
    <button type="submit" class="add-to-cart">Buy Now</button>
</form>
</div>
</main>

<section class="trending-products">
    <h2 class="trending-title">You May Also Like</h2>
    <div class="trending-wrapper">
        <div class="trending-track">
            <c:forEach items="${relatedProducts}" var="related">
                    <div class="trending-card">
                     <a href="${pageContext.request.contextPath}/individualProductPage?id=${related.sneakerID}" class="trending-card-link">
                        <img src="${pageContext.request.contextPath}/resources/images/sneakers/${related.imageUrl}" alt="${related.sneakerName}">
                       </a>
                        <h3>${related.sneakerName}</h3>
                        <p class="product-type">${related.category}</p>
                        <p class="product-price">Rs. <fmt:formatNumber value="${related.price}" pattern="#,##0"/></p>
                    </div>
            </c:forEach>
        </div>
    </div>
</section>


<jsp:include page="footer.jsp" />
<div id="messagePopup" class="message-popup">
    <div class="message-content">
        <span id="messageText"></span>
        <a href = "${pageContext.request.contextPath}/viewOrderHistory" style="text-decoration: none;color: inherit;"> 
        <button class="message-ok-btn" onclick="closeMessage()">OK</button>
        </a>
    </div>
</div>


<script>


//Handle messages from server
document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const message = urlParams.get('message');
    const error = urlParams.get('error');

    if (message === 'order_placed') {
        document.getElementById('messageText').textContent = "Order placed successfully! Thank you for Shopping with us!!";
        document.getElementById('messagePopup').style.display = 'flex';
    } else if (error === 'order_failed') {
        document.getElementById('messageText').textContent = "Failed to place order. Please try again.";
        document.getElementById('messagePopup').style.display = 'flex';
    }
});

function closeMessage() {
    document.getElementById('messagePopup').style.display = 'none';
}


</script>



</body>
</html>