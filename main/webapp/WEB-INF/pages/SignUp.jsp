<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>SNEAKSPHERE - Sign Up</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/SignUp.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" crossorigin="anonymous" />
</head>
<body>
<header>
    <a href="${pageContext.request.contextPath}/home"><img class="logo-image" src="${pageContext.request.contextPath}/resources/images/system/logo.png" alt="Logo"></a>
</header>

<div class="form-container">
    <div class="auth-card">
        <div class="tab-header">
            <a class="active-tab" href="${pageContext.request.contextPath}/signup">Sign Up</a>
            <a class="inactive-tab" href="${pageContext.request.contextPath}/login">Log In</a>
        </div>

        <div class="signup-form">
            <c:if test="${not empty error}">
                <p class="error-message">${error}</p>
            </c:if>
            <c:if test="${not empty success}">
                <p class="success-message">${success}</p>
            </c:if>

            <form action="${pageContext.request.contextPath}/signup" method="post" enctype="multipart/form-data">
                <p>Sign Up</p>

                <div class="input-group">
                    <input type="text" id="firstname" name="UserFirstName" placeholder="First Name*" />
                </div>

                <div class="input-group">
                    <input type="text" id="lastname" name="UserLastName" placeholder="Last Name*" />
                </div>

                <div class="input-group">
                    <input type="email" id="email" name="UserEmail" placeholder="Email *" />
                </div>

                <div class="input-group">
                    <input type="tel" id="phone" name="UserPhone" placeholder="Phone Number *" />
                </div>

                <div class="input-group">
                    <input type="text" id="address" name="UserAddress" placeholder="Address *" />
                </div>

                  <div class="file-upload-container">
				    <label>Profile Picture:</label>
				    <p >Please upload an image for the profile picture</p>
				    <input type="file" id="profilepicture" name="image" />
				</div>
				
				


                <div class="input-group">
                    <input type="password" id="password" name="UserPassword" placeholder="Password *" />
                </div>

                <p class="password-hint">
                    At least 8 characters, 1 uppercase letter, 1 number & 1 symbol
                </p>
               

                <div class="terms">
                    <input type="checkbox" id="agree" required/>
                    <label for="agree" class="terms-label">
                        I have read and agree to the
                        <a href="#">Terms and Conditions</a> and
                        <a href="#">Privacy and Policy</a>
                    </label>
                </div>

                <button type="submit" class="submit-btn">Sign Up</button>

                <div class="divider">
                    <hr />
                    <span class="or-text">OR</span>
                    <hr />
                </div>

                <div class="social-text">
                    <p>Sign up With</p>
                </div>

                <div class="social-icons">
                    <a href="#"><i class="fa-brands fa-google"></i></a>
                    <a href="#"><i class="fa-brands fa-facebook" style="color: #1877f2"></i></a>
                    <a href="#"><i class="fa-brands fa-apple" style="color: #000000"></i></a>
                    <a href="#"><i class="fa-brands fa-x-twitter" style="color: #000000"></i></a>
                </div>

                <div class="bottom-text">
                    <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Log In</a></p>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>