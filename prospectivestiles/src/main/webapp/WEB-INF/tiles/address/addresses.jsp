<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<sec:authorize access="hasRole('ROLE_ADMIN')">
	<div class="well well-sm row">
		<div class="col-sm-3">
			<img
				src="${pageContext.request.contextPath}/resources/images/placeholderImage_140x140.jpg"
				alt="Your Pic" class="img-rounded profileImg">
		</div>
		<dl class="dl-horizontal col-sm-9">
			<dt>Full name:</dt>
			<dd>
				<c:out value="${userEntity.firstName}"></c:out>
				<c:out value="${userEntity.lastName}"></c:out>
			</dd>
			<dt>Username</dt>
			<dd>
				<c:out value="${userEntity.username}" />
			</dd>
		</dl>
	</div>
</sec:authorize>

<h1>Addresses page</h1>


<c:if test="${param.deleted == true}">
	<div class="info alert">Address deleted.</div>
</c:if>


<c:choose>
	<c:when test="${empty addresses}">
		<p>No Address.</p>
	</c:when>
	<c:otherwise>
		<c:forEach var="address" items="${addresses}">
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<%-- <c:url var="addressUrl"	value="/accounts/${address.userEntity.id}/address/${address.id}" /> --%>
				<c:url var="editAddressUrl"
					value="/accounts/${address.userEntity.id}/address/${address.id}/edit" />
				<c:url var="deleteAddressUrl"
					value="/accounts/${address.userEntity.id}/address/${address.id}/delete" />
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_USER')">
				<%-- <c:url var="addressUrl"	value="/myAccount/address/${address.id}" /> --%>
				<c:url var="editAddressUrl"
					value="/myAccount/address/${address.id}/edit" />
				<c:url var="deleteAddressUrl"
					value="/myAccount/address/${address.id}/delete" />
			</sec:authorize>

			<address>
				<%-- <c:out value="${address.id}" /> --%>
				<span class="addressType"> <strong><c:out
							value="${address.addressType}" /></strong>
				</span>
				<hr />
				<c:out value="${address.address1}" />
				<c:if test="${address.address2 != null}">
			  	, <c:out value="${address.address2}" />
					<br>
				</c:if>
				<c:out value="${address.city}" /> <c:out value="${address.state}" />, <c:out value="${address.zipcode}" />
				<br>
				<c:out value="${address.country}" />
				<br> <abbr title="Phone">P:</abbr> (123) 456-7890<br> <a
					href="mailto:#">first.last@example.com</a><br>
				<br> <a href="${editAddressUrl}" class="btn btn-primary btn-lg">Edit</a>
				<br>
				<br>
				<form id="deleteForm" action="${deleteAddressUrl}" method="post">
					<div>
						<input type="submit" value="DELETE" />
					</div>
				</form>
			</address>
		</c:forEach>

	</c:otherwise>
</c:choose>


<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="newAddressUrl"
		value="/accounts/${userEntity.id}/address/new" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="newAddressUrl" value="/myAccount/address/new" />
</sec:authorize>

<h3>
	<a href="${newAddressUrl}">Add New Address</a>
</h3>

<!-- jQuery for Bootstrap's JavaScript plugins -->

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/script/jquery-1.7.1.min.js"></script>

<!-- Bootstrap JS -->
<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/script/jquery.js"></script>
<!-- Validate plugin -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/script/jquery.validate.min.js"></script>


<!-- 
		When I click on EDIT button the Modal is popluated with db datas for the current address. 
		When I clikc on Cancle and click on edit for another address, the selected value is populated to the modal.
		After 3 - 4 clicks the modal no more reloads.
		Using this js to fix that issue
	 -->
<script>
		$(document).ready(function(){
			
			/* Call the dropdowns via JavaScript */
		    $('.dropdown-toggle').dropdown();
		    
		    
		    
		    /* 
		    When user closes a modal and open it again. The modal should not retain values from previous attempt.
		    
		    the reload() function takes an optional parameter 
		    that can be set to true to reload from the server rather than the cache. 
		    by default parameter is false, so the page reloads from the browser's cache. 
		    !!!!! I DON'T WANT TO RELOAD THE PAGE. THE PURPOSE OF USING MODAL IS TO STOP RELOADING PAGES.
		     */
		    $('body').on('hidden.bs.modal', '.modal', function () {
		    	/* alert("Modal window has been completely closed."); */
			    $(this).removeData('bs.modal');
			    /* $('.bodycontent').removeData('bs.modal'); */
			    /* $('.bodycontent').find('#myModal').html(""); */
			    /* $(this).empty(); */
		    	/* location.reload(); */
			});
		    
		});
	</script>
