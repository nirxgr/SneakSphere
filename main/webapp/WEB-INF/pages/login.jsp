<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login - SNEAKSPHERE</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
    />
  </head>
  <body>
    <header>
      <a href="${pageContext.request.contextPath}/home"><img class="logo-image" src="${pageContext.request.contextPath}/resources/images/system/logo.png" alt="Logo" ></a>
    </header>

    <div class="form-container">
      <div class="auth-card">
        <div class="tab-header">
          <a class="inactive-tab" href="${pageContext.request.contextPath}/signup">Sign Up</a>
          <a class="active-tab" href="${pageContext.request.contextPath}/login">Log In</a>
        </div>

        <div class="login-form">
          <!-- LOGIN FORM START -->
          <form action="${pageContext.request.contextPath}/login" method="post">
            <p>Log In</p>
            <input type="email" name="username" placeholder="Email *" required />
            <input type="password" name="password" placeholder="Password *" required />
            <div class="forgot">Forgot Password?</div>
            <button type="submit">Log In</button>
            <p class="terms">
              By logging in, you agree to the
              <a href="#">Terms of Service</a> and
              <a href="#">Privacy Policy</a>
            </p>
          </form>
          <!-- LOGIN FORM END -->

          <!-- ERROR DISPLAY -->
          
        <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
        </c:if>

          <div class="divider">
            <hr />
            <span class="or-text">OR</span>
            <hr />
          </div>

          <div class="social-login">
            <p>Log in with</p>
            <div class="icons">
              <a href="https://www.google.com/"><i class="fa-brands fa-google"></i></a>
              <a href="https://www.facebook.com/suraj.sah.597847"><i class="fa-brands fa-facebook" style="color: #1877f2"></i></a>
              <a href="https://www.apple.com/"><i class="fa-brands fa-apple" style="color: #000000"></i></a>
              <a href="https://x.com/?lang=en"><i class="fa-brands fa-x-twitter" style="color: #000000"></i></a>
            </div>
          </div>

          <p class="signup-link">
            Need an account? <a href="${pageContext.request.contextPath}/signup">Sign Up</a>
          </p>
        </div>
      </div>
     </div>

    <script src="${pageContext.request.contextPath}/js/script.js"></script>
  </body>
</html>