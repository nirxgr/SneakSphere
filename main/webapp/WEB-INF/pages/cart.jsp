<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Add to Cart</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cart.css" />
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
  <style>
    .message-popup {
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0,0,0,0.5);
      display: none;
      justify-content: center;
      align-items: center;
      z-index: 1000;
    }

    .message-content {
      background: white;
      padding: 30px;
      border-radius: 8px;
      text-align: center;
      width: 300px;
    }

    .message-text {
      display: block;
      margin-bottom: 20px;
      font-size: 18px;
    }

    .message-ok-btn {
      margin: 0 auto;
      padding: 8px 25px;
      cursor: pointer;
      background-color: #000;
      color: white;
      border: none;
      border-radius: 4px;
      font-size: 16px;
    }

    .message-ok-btn:hover {
      background-color: #333;
    }
  </style>
</head>
<body>

<jsp:include page="header.jsp" />

<div class="cart-container">
  <h1 class="cart-title">Welcome to Add to Cart</h1>

  <table class="cart-table">
    <thead>
      <tr>
        <th>Product Name</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Action</th>
        <th>Total Price</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="item" items="${sneakerCartList}">
        <tr>
            <td>
                <div class="product-info">
                    <img src="${pageContext.request.contextPath}/resources/images/sneakers/${item.imageUrl}" alt="Product Image" />
                    <div class="product-details">
                        <p class="product-name">${item.sneakerName}</p>
                        <p class="product-desc">${item.description}</p>
                    </div>
                </div>
            </td>
            <td class="unit-price" data-price="${item.price}">Rs. ${item.price}</td>
            <td>
                <div class="quantity-wrapper">
                    <button class="quantity-btn" onclick="decreaseQty(this)">-</button>
                    <span class="quantity-number">${item.cartQuantity}</span>
                    <button class="quantity-btn" onclick="increaseQty(this)">+</button>
                </div>
            </td>
            <td>
			  <form method="post" action="${pageContext.request.contextPath}/cart" onsubmit="return confirmDelete(this)">
			    <input type="hidden" name="action" value="remove" />
			    <input type="hidden" name="cartID" value="${item.cartID}" />
			    <button type="submit" class="delete-btn">
			      <img src="https://img.icons8.com/?size=100&id=85081&format=png&color=000000" alt="Delete Icon" class="delete-icon" />
			    </button>
			  </form>
			</td>
            <td class="item-total">Rs. ${item.price * item.cartQuantity}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <div class="cart-total-container" style="text-align:right; margin: 20px 50px;">
    <h3>Total: <span id="overall-cart-total">Rs. 0</span></h3>
  </div>

  <div class="checkout-btn-container">
    <button class="checkout-btn" onclick="checkout()">CheckOut</button>
  </div>
</div>

<div id="messagePopup" class="message-popup">
  <div class="message-content">
    <span id="messageText"></span>
    <button onclick="closeMessage()">OK</button>
  </div>
</div>

<jsp:include page="footer.jsp" />

<script>
  // Function to get numeric price value from data attribute
  function getPrice(row) {
    return parseFloat(row.querySelector('.unit-price').dataset.price);
  }

  // Function to get current quantity
  function getQuantity(row) {
    return parseInt(row.querySelector('.quantity-number').textContent);
  }

  // Function to calculate row total
  function calculateRowTotal(row) {
    const price = getPrice(row);
    const quantity = getQuantity(row);
    return price * quantity;
  }

  // Function to update row total display
  function updateRowTotal(row) {
    const total = calculateRowTotal(row);
    row.querySelector('.item-total').textContent = 'Rs. ' + total.toFixed(2);
    return total;
  }

  // Function to update overall total
  function updateOverallTotal() {
    let totalSum = 0;
    document.querySelectorAll('tbody tr').forEach(row => {
      totalSum += updateRowTotal(row);
    });
    document.getElementById('overall-cart-total').textContent = 'Rs. ' + totalSum.toFixed(2);
  }

  // Quantity adjustment functions
  function increaseQty(btn) {
    const quantitySpan = btn.parentElement.querySelector('.quantity-number');
    let current = getQuantity(btn.closest('tr'));
    quantitySpan.textContent = current + 1;
    updateOverallTotal();
  }

  function decreaseQty(btn) {
    const quantitySpan = btn.parentElement.querySelector('.quantity-number');
    let current = getQuantity(btn.closest('tr'));
    if (current > 1) {
      quantitySpan.textContent = current - 1;
      updateOverallTotal();
    }
  }

  function deleteRow(btn) {
    const row = btn.closest('tr');
    row.remove();
    updateOverallTotal();
  }

  // Checkout function
  function checkout() {
    const rows = document.querySelectorAll('tbody tr');
    const messagePopup = document.getElementById('messagePopup');
    const messageText = document.getElementById('messageText');
    
    if (rows.length === 0) {
      messageText.textContent = "No items in the cart";
    } else {
      messageText.textContent = "Successfully checked out";
    }
    
    messagePopup.style.display = 'flex';
  }
  
  function confirmDelete(form) {
	  if (confirm("Are you sure you want to remove this item from your cart?")) {
	    // Remove from UI immediately while waiting for server response
	    const row = form.closest('tr');
	    row.style.opacity = '0.5'; // Visual feedback that deletion is in progress
	    row.querySelector('.delete-btn').disabled = true;
	    
	    // Let the form submit normally - server will handle the DB deletion
	    return true;
	  }
	  return false;
	}

  function closeMessage() {
    document.getElementById('messagePopup').style.display = 'none';
  }

  // Initialize totals when page loads
  document.addEventListener('DOMContentLoaded', updateOverallTotal);
</script>

</body>
</html>