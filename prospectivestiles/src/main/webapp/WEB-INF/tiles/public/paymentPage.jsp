<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.js"></script>


<h1>Pay Application Fee</h1>
<br>
<br>


<p>
When you click on the "Pay" button below, you will be redirected to the "skybankgateway" webpage. 
This is a secure banking system used by ACCT to handle online payment transactions.
You will be charged $40.00 USD for processing your application for admission. 
You can also send a check or pay your application fee in person at the school.
</p>

<form action="https://secure.skybankgateway.com/cart/cart.php" method="POST" target="_blank">
<input type="submit" name="submit" value="Click here to pay" class="btn btn-primary btn-sm" />
<input type="hidden" name="key_id" value="4318396" />
<input type="hidden" name="action" value="process_fixed" />
<input type="hidden" name="amount" value="40.00" />
<input type="hidden" name="order_description" value="Application Fee" />
<input type="hidden" name="language" value="en" />
<input type="hidden" name="customer_receipt" value="true" />
<input type="hidden" name="hash" value="action|amount|order_description|7f2a37f9e699e962c2562941e23eebc7" />
</form>



