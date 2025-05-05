<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Contact Us - Sneaksphere</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #ffffff;
        }

        .container {
            max-width: 1200px;
            margin: auto;
            padding: 40px 20px;
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
        }

        .left, .right {
            flex: 1;
            min-width: 300px;
            margin: 20px;
        }

        .left h2 {
            margin-bottom: 10px;
        }

        .left p {
            margin: 5px 0;
            font-size: 16px;
            color: #333;
        }

        form {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
        }

        form input, form textarea {
            flex: 1 1 48%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
        }

        form textarea {
            flex-basis: 100%;
        }

        form button {
            flex-basis: 100%;
            padding: 12px;
            background-color: black;
            color: white;
            font-size: 16px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        form button:hover {
            background-color: #333;
        }

        .map-section {
            background: #f7f7f7;
            padding: 40px 20px;
        }

        .map-section h3 {
            text-align: center;
            margin-bottom: 20px;
        }

        .map-section iframe {
            width: 100%;
            height: 300px;
            border: none;
            border-radius: 10px;
        }

        footer {
            background-color: #f8f8f8;
            padding: 50px 20px;
        }

        .footer-content {
            max-width: 1200px;
            margin: auto;
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            gap: 20px;
        }

        .footer-content div {
            flex: 2;
            min-width: 200px;
        }

        .footer-content h4 {
            margin-bottom: 11px;
            font-size: 16px;
        }

        .footer-content p, .footer-content a {
            font-size: 14px;
            margin: 4px 15;
            text-decoration: none;
            color: #444;
        }

        .footer-content a:hover {
            color: #333;
        }

        .footer-social {
            display: flex;
            gap: 15px;
            align-items: center;
            margin-top: 10px;
        }

        .footer-social img {
            width: 30px;
            height: 30px;
            cursor: pointer;
        }

        .footer-bottom {
            text-align: center;
            margin-top: 40px;
            font-size: 13px;
            color: #999;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp" />

<div class="container">
    <div class="left">
        <h2>Contact Us</h2>
        <p><strong>Phone:</strong> +977-9708555024</p>
        <p><strong>Email:</strong> support@sneaksphere.com</p>
        <p><strong>Address:</strong> Kathmandu, Nepal</p>
    </div>

    <div class="right">
        <!-- Form Action changed to contact.jsp -->
   <form action="contactus" method="post">

    <input type="text" name="firstName" placeholder="First Name" required>
    <input type="text" name="lastName" placeholder="Last Name" required>
    <input type="email" name="email" placeholder="Email" required>
    <input type="text" name="phone" placeholder="Phone" required>
    <input type="text" name="subject" placeholder="Subject" required>
    <textarea name="message" rows="5" placeholder="Message" required></textarea>
    <button type="submit">Submit Form</button>
</form>

    </div>
</div>

<div class="map-section">
    <h3>Find our store</h3>
    <iframe src="https://www.google.com/maps?q=Kathmandu%20Nepal&output=embed" loading="lazy"></iframe>
</div>

<jsp:include page="footer.jsp" />


</body>
</html>