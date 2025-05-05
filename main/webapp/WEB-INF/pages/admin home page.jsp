<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
<link rel="stylesheet" type="text/css"
href="${pageContext.request.contextPath}/css/admin dashboard.css" />
</head>
<body>
   <jsp:include page="admin navigation.jsp" />
     <!-- Main Content Area -->
    <div class="main-content">
        <h1 class="dashboard-title">Dashboard</h1>
        
        <!-- Statistics Cards -->
        <div class="stats-container">
            <div class="stat-card">
                <div class="stat-icon">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/sales-icon.svg" alt="Sales">
                </div>
                <div class="stat-info">
                    <h3>Total Sales</h3>
                    <p>Rs. ${totalSales}</p>
                </div>
            </div>
            
            <div class="stat-card">
                <div class="stat-icon">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/Totalproducts-icon.svg" alt="Products">
                </div>
                <div class="stat-info">
                    <h3>Total Products</h3>
                    <p>${totalProducts}</p>
                </div>
            </div>
            
            <div class="stat-card">
                <div class="stat-icon">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/Total Order-icon.svg" alt="Orders">
                </div>
                <div class="stat-info">
                    <h3>Total Orders</h3>
                    <p>${totalOrders}</p>
                </div>
            </div>
            
            <div class="stat-card">
                <div class="stat-icon">
                    <img src="${pageContext.request.contextPath}/resources/images/admin images/TotalCustomer-icon.svg" alt="Customers">
                </div>
                <div class="stat-info">
                    <h3>Total Customers</h3>
                    <p>${totalCustomers}</p>
                </div>
            </div>
        </div>

        <!-- Top Product Performance Section -->
        <div class="table-section">
            <h2 class="section-title">Top Product Performance</h2>
            <div class="table-container">
                <table class="performance-table">
                    <thead>
                        <tr>
                            <th>Product Name</th>
                            <th>Price</th>
                            <th>Sales</th>
                            <th>Revenue</th>
                        </tr>
                    </thead>
                    <tbody>
                     <c:forEach var="sneaker" items="${topSneakers}">
					<tr>
					    <td>
					        <div class="product-info">
					            <img src="${pageContext.request.contextPath}/resources/images/sneakers/${sneaker.imageUrl}" class="product-image" />
					            <span>${sneaker.sneakerName}</span>
					        </div>
					    </td>
					    <td>Rs. ${sneaker.price}</td>
					    <td>${sneaker.sales}</td>
					    <td>Rs. ${sneaker.revenue}</td>
					</tr>
					</c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Most Buying Customer Section -->
        <div class="table-section">
            <h2 class="section-title">Most Buying Customer</h2>
            <div class="table-container">
                <table class="customer-table">
                    <thead>
                        <tr>
                            <th>Customer Name</th>
                            <th>Sneakers Purchased</th>
                            <th>Total Spending</th>
                        </tr>
                    </thead>
                    <tbody>
						<c:forEach var="customer" items="${topCustomers}">
						    <tr>
						        <td>${customer.firstName} ${customer.lastName}</td>
						        <td>${customer.totalPurchases}</td>
						        <td>Rs. ${customer.totalSpent}</td>
						    </tr>
						</c:forEach>
                        
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>