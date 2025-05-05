<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SNEAKSPHERE - Nike Dunk Low Retro Bttys</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/individualProductPage.css" />
</head>
<body>

<jsp:include page="header.jsp" />

    <main class="product-container">
        <div class="product-gallery">
            <div class="shoe-grid">
                <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/Nike Dunk Low Retro Bttys.webp" alt="Nike Side View Left"
                    class="shoe-img">
                <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/Nike Dunk Low Retro Bttys 2.webp" alt="Nike Side View Right"
                    class="shoe-img">
                <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/Nike Dunk Low Retro Bttys 3.webp" alt="Nike Top View"
                    class="shoe-img">
                <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/Nike Dunk Low Retro Bttys 4.webp" alt="Nike Sole View"
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

            <h1 class="product-title">Nike Dunk Low Retro Bttys</h1>
            <p class="product-category">Men's</p>

            <div class="product-description">
                <p>The Nike Dunk Low Retro BTTYS (Be True To Your School) pays homage to college basketball heritage
                    with bold, varsity-inspired colorways. Featuring classic leather construction and low-cut comfort,
                    it's a timeless silhouette rooted in '80s court style.</p>
            </div>

            <div class="product-price">Rs. 11,000</div>
            <div class="section-separator"></div>

            <div class="product-options">
                <div class="option-section">
                    <h3 class="option-title">Available Color</h3>
                    <div class="color-options">
                        <div class="color-option selected">
                           <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/Nike Dunk Low Retro Bttys.webp" alt="Nike Dunk Low Retro Bttys.webp">
                           <span class="color-indicator"></span>
                        </div>
                        <div class="color-option">
                            <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/Nike Dunk Low Retro Bttys blue.webp" alt="Nike Dunk Low Retro Bttys blue">
                        </div>
                        <div class="color-option">
                            <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/Nike Dunk Low Retro Bttys green.webp" alt="Nike Dunk Low Retro Bttys green">
                        </div>
                        <div class="color-option">
                            <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/Nike Dunk Low Retro Bttys purple.webp" alt="Nike Dunk Low Retro Bttys purple">
                        </div>
                        <div class="color-option">
                            <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/Nike Dunk Low Retro.webp" alt="Nike Dunk Low Retro">
                        </div>
                    </div>
                </div>

                <div class="section-separator"></div>

                <div class="option-section">
                    <h3 class="option-title">Available Sizes (UK)</h3>
                    <div class="size-options">
                        <div class="size-row">
                            <button class="size-option">7.5</button>
                            <button class="size-option">8</button>
                            <button class="size-option">8.5</button>
                            <button class="size-option">9</button>
                            <button class="size-option">9.5</button>
                            <button class="size-option">10</button>
                        </div>
                        <div class="size-row">
                            <button class="size-option">11</button>
                            <button class="size-option">11.5</button>
                            <button class="size-option">12</button>
                            <button class="size-option">12.5</button>
                            <button class="size-option">13</button>
                            <button class="size-option">14</button>
                        </div>
                    </div>
                </div>
            </div>

            <button class="add-to-cart">
                <img src="${pageContext.request.contextPath}/resources/images/Product Page Image/add to cart.svg" alt="Add to cart" class="cart-icon">
                Add to Cart
            </button>
        </div>
    </main>

    <section class="trending-products">
        <h2 class="trending-title">You May Also Like</h2>
        <div class="trending-wrapper">
            <div class="trending-track">
                <div class="trending-card">
                     <img src="${pageContext.request.contextPath}/resources/images/system/RED DUNK.webp" alt="Nike Dunk Low Retro">
                    <h3>Nike Dunk Low Retro</h3>
                    <p class="product-type">Sneakers</p>
                    <p class="product-price">Rs. 16,000</p>
                </div>
                <div class="trending-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/New Balance 2002R.webp" alt="New Balance 2002R">
                    <h3>New Balance 2002R</h3>
                    <p class="product-type">Men (Tan/Brown)</p>
                    <p class="product-price">Rs. 26,000</p>
                </div>
                <div class="trending-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/adidas Handball Spezial.webp" alt="adidas Handball Spezial">
                    <h3>adidas Handball Spezial</h3>
                    <p class="product-type">Women (Tan/Brown)</p>
                    <p class="product-price">Rs. 26,000</p>
                </div>
                <div class="trending-card">
                     <img src="${pageContext.request.contextPath}/resources/images/system/Puma MB.04 TMNT.webp" alt="Puma MB.04 TMNT">
                    <h3>Puma MB.04 TMNT</h3>
                    <p class="product-type">Men (Fluoro Green)</p>
                    <p class="product-price">Rs. 39,000</p>
                </div>
                <div class="trending-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/On Cloudtilt.webp" alt="On Cloudtilt">
                    <h3>On Cloudtilt</h3>
                    <p class="product-type">Unisex</p>
                    <p class="product-price">Rs. 22,000</p>
                </div>
                <div class="trending-card">
                   <img src="${pageContext.request.contextPath}/resources/images/system/Nike Dunk Low Retro.webp" alt="Nike Dunk Low Retro">
                    <h3>Nike Dunk Low Retro</h3>
                    <p class="product-type">Unisex</p>
                    <p class="product-price">Rs. 12,000</p>
                </div>
            </div>
        </div>
    </section>
    <jsp:include page="footer.jsp" />
</body>
</html>