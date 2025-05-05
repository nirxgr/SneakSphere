<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.sneaksphere.model.UserModel" %>
<%
    UserModel user = (UserModel) request.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SNEAKSPHERE - Customer Profile</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/CustomerProfile.css" />
</head>
<body>
    <!-- Customer Header Include -->
    <jsp:include page="header.jsp" />

    <!-- Profile Section -->
    <div class="profile-container">
        <div class="profile-header">
            <div class="profile-heading-text">
                <h2>Profile</h2>
                <p>Personal Information</p>
            </div>
            <div class="profile-image-section">
                <img src="${pageContext.request.contextPath}/${user.userImageURL}" alt="Profile Picture" class="profile-image">
                <a href="${pageContext.request.contextPath}/EditCustomerProfileController" class="edit-link"><i class="fas fa-pen"></i> Edit</a>
            </div>
        </div>

        <div class="profile-details">
            <form class="profile-form">
                <div class="form-row">
                    <div class="form-group">
                        <label for="firstName">First Name</label>
                        <input type="text" id="firstName" value="${user != null ? user.firstName : ''}" disabled />
                    </div>
                    <div class="form-group">
                        <label for="lastName">Last Name</label>
                        <input type="text" id="lastName" value="${user != null ? user.lastName : ''}" disabled />
                    </div>
                </div>

                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <input type="text" id="phone" value="${user != null ? user.phone : ''}" disabled />
                </div>

                <div class="form-group">
                    <label for="email">Email</label>
                    <p>${user != null ? user.email : ''}</p>
                </div>

                <div class="form-group">
                    <label for="country">Country / Region</label>
                    <p>Nepal</p>
                </div>

                <div class="form-group">
                    <label for="address">Address</label>
                    <input type="text" id="address" value="${user != null ? user.address : ''}" disabled />
                </div>
            </form>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
</body>
</html>