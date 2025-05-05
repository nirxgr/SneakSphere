<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AboutUs</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css"href="${pageContext.request.contextPath}/css/about us.css" />
</head>
<body>
	<jsp:include page="header.jsp" />
    <div class="top-div">
        <h2>About SneakSphere</h2>
        <p class="tagline">Elevate Your Style. Define Your Sphere.</p>
        <p class="tagdesc">At SneakSphere, we're dedicated to delivering the ultimate online sneaker shopping experience—where style meets comfort, and every purchase boosts your confidence.</p>
    </div>
    <div class="second-div">
        <div class="left-second">
            <p class="titleline">About Sneaksphere</p>
            <h2>SneakSphere: Your Ultimate Hub for Style and Comfort</h2>
            <p class="linedesc">SneakSphere is a rising footwear and apparel platform dedicated to unlocking the "inner sneakerhead" in everyone. With a passion for sneaker culture and a vision to create a seamless online shopping experience, SneakSphere offers a curated selection of the latest and most iconic sneakers from top brands. Our mission is to spark discovery and fuel the energy of sneaker enthusiasts across the globe. Through our user-friendly e-commerce platform, SneakSphere brings together style, performance, and exclusivity, creating a digital destination that celebrates the true spirit of sneaker culture.</p>
        </div>
        <div class="right-second">
            <img src="${pageContext.request.contextPath}/resources/images/about/venue.png" alt="SneakSphere Venue">
        </div>    

    </div>

    <div class="third-div">
        <img src="${pageContext.request.contextPath}/resources/images/about/about1.png" alt="Sneakers 1">
        <img src="${pageContext.request.contextPath}/resources/images/about/about2.png" alt="Sneakers 2">
        <img src="${pageContext.request.contextPath}/resources/images/about/about3.png" alt="Sneakers 3">
    </div>

    <div class="fourth-div">
        <div class="left-fourth">
            <h3>OUR JOURNEY & MILESTONES</h3> 
            <p>SneakSphere has grown remarkably since its inception, becoming a brand recognized for quality, style, and comfort in the sneaker world.</p>   
            <p>We take pride in our journey and the milestones we've achieved. SneakSphere continues to innovate, set new trends, and deliver exceptional sneaker experiences to enthusiasts around the globe.</p>
        </div>
        <div class="right-fourth">
            <h3>40K+</h3><p>Happy Customer</p>
            <h3>300+</h3><p>Dedicated Employee</p>
            <h3>80%</h3><p>Repeat Customer</p>
            <h3>500+</h3><p>Unique Style</p>

        </div>   
    </div>

    <div class="fifth-div">
        <div class="left-fifth">
            <img src="${pageContext.request.contextPath}/resources/images/about/logo.png" alt="Logo">
        </div>
        <div class="right-fifth">
            <p class="title">Why Choose SneakSphere?</p>
            <h1>Experience Comfort & Style with Us</h1>
            <p class="desc">Our team at SneakSphere is driven by creativity, passion, and expertise. This powerful combination allows us to deliver a unique sneaker experience that truly stands out. With years of dedication and industry knowledge, we’ve perfected the balance of comfort and style—making sure every pair we offer is both functional and fashionable. Trust SneakSphere to be your ultimate destination for style, performance, and everyday comfort.</p>
        </div>
    </div>
    <div class="sixth-div">
        <h2>Our Services</h2>
        <p class="main">Seamless Shopping & Fast Delivery</p>
        <p class="sub">You can now shop your favorite styles directly on Instagram and Facebook! Simply explore our collection, pick the looks you love, and send us a direct message to place your order. We’ll take care of the rest, ensuring a smooth and fast delivery right to your doorstep.</p>

    </div>
    <jsp:include page="footer.jsp" />


</body>
</html>