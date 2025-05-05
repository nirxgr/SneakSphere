<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.sneaksphere.model.UserModel" %>

<%
    UserModel user = (UserModel) request.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Profile - SneakSphere</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/EditCustomerProfile.css" />
</head>
<body>

    <!-- Header Include -->
    <jsp:include page="header.jsp" />

    <!-- Main Content Area -->
    <div class="profile-container">
        <div class="profile-header">
            <div class="profile-heading-text">
                <h2>Edit Profile</h2>
                <p>Update your personal information</p>
            </div>

            <div class="profile-image-section">
                <c:choose>
                    <c:when test="${not empty user.userImageURL}">
                        <img src="${pageContext.request.contextPath}/${user.userImageURL}" alt="Profile Picture" class="profile-image">
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/resources/images/admin images/profile-admin.jpeg" alt="Default Profile Picture" class="profile-image">
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- Error Message Block -->
        <c:if test="${not empty errorMessage}">
            <div class="error-message" style="color: red; margin-bottom: 15px;">
                <c:out value="${errorMessage}" escapeXml="false"/>
            </div>
        </c:if>

        <form class="edit-profile-form" method="post" action="${pageContext.request.contextPath}/EditCustomerProfileController" enctype="multipart/form-data">

            <!-- Hidden fields -->
            <input type="hidden" name="existingImage" value="${user.userImageURL}" />
            <input type="hidden" name="userId" value="${user.userID}" />

            <div class="form-group">
                <label for="imageUrl">Change Profile Picture</label>
                <input type="file" id="imageUrl" name="imageUrl" class="upload-photo-input" />
            </div>

            <!-- Name row -->
            <div class="form-row">
                <div class="form-group">
                    <label for="firstName">First Name <span class="required">*</span></label>
                    <input type="text" id="firstName" name="firstName" value="${user.firstName}" />
                </div>
                <div class="form-group">
                    <label for="lastName">Last Name <span class="required">*</span></label>
                    <input type="text" id="lastName" name="lastName" value="${user.lastName}" />
                </div>
            </div>

            <!-- Phone -->
            <div class="form-group">
                <label for="phone">Phone Number <span class="required">*</span></label>
                <input type="text" id="phone" name="phone" value="${user.phone}" />
            </div>

            <!-- Email (readonly display) -->
            <div class="form-group">
                <label for="email">Email <span class="required">*</span></label>
                <p>${user.email}</p> <!-- Uneditable field -->
            </div>

            <!-- Address -->
            <div class="form-group">
                <label for="address">Address <span class="required">*</span></label>
                <input type="text" id="address" name="address" value="${user.address}" />
            </div>

            <!-- Action buttons -->
            <div class="form-actions">
                <button type="submit" class="save-btn">Save</button>
                <button type="button" class="cancel-btn" onclick="window.location.href='${pageContext.request.contextPath}/CustomerProfileController'">Cancel</button>
            </div>
        </form>
    </div>

    <!-- Footer Include -->
    <jsp:include page="footer.jsp" />

</body>
</html>