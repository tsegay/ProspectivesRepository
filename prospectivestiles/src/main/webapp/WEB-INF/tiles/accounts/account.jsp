<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<div class="well well-sm row">
		<div class="col-sm-3">
	  		<img src="${pageContext.request.contextPath}/resources/images/placeholderImage_140x140.jpg" alt="Your Pic" class="img-rounded profileImg">
	    </div>
		<dl class="dl-horizontal col-sm-9">
			<dt>Full name:</dt>
			<dd>
				<c:out value="${userEntity.firstName}"></c:out> <c:out value="${userEntity.lastName}"></c:out>
			</dd>
			<dt>Username</dt>
			<dd>
				<c:out value="${userEntity.username}" />
			</dd>
		</dl>
	</div>
</sec:authorize>

<h1 class="page-header">Account Page</h1>

<div class="row">
	<dl class="dl-horizontal col-md-6">
		<dt><spring:message code="account.label.id" /></dt>
		<dd>
			<c:out value="${userEntity.id}" />
		</dd>
		
		<dt><spring:message code="account.label.firstName" /></dt>
		<dd>
			<c:out value="${userEntity.firstName}" />
		</dd>
	
		<dt><spring:message code="account.label.middleName" /></dt>
		<dd>
			<c:out value="${userEntity.middleName}" />
		</dd>
	
		<dt><spring:message code="account.label.lastName" /></dt>
		<dd>
			<c:out value="${userEntity.lastName}" />
		</dd>
	
		<dt><spring:message code="account.label.username" /></dt>
		<dd>
			<c:out value="${userEntity.username}" />
		</dd>
	
		<dt><spring:message code="account.label.email" /></dt>
		<dd>
			<a href="mailto:${userEntity.email}">${userEntity.email}</a>
		</dd>
		
		<dt><spring:message code="account.label.marketingOk" /></dt>
		<dd>
			<c:out value="${userEntity.marketingOk}" />
		</dd>
		
		<dt><spring:message code="account.label.acceptTerms" /></dt>
		<dd>
			<c:out value="${userEntity.acceptTerms}" />
		</dd>
		
		<dt><spring:message code="account.label.dateCreated" /></dt>
		<dd>
			<fmt:formatDate var="dateCreatedString" value="${userEntity.dateCreated}" pattern="yyyy-MM-dd HH:mm:ss" />
			<c:out value="${dateCreatedString}" />
		</dd>
		
		<dt><spring:message code="account.label.dob" /></dt>
		<dd>
			<fmt:formatDate var="dobString" value="${userEntity.dob}" pattern="yyyy-MM-dd" />
			<c:out value="${dobString}" />
		</dd>
	</dl>
	<dl class="dl-horizontal col-md-6">
		<dt><spring:message code="account.label.gender" /></dt>
		<dd>
			<c:out value="${userEntity.gender}" />
		</dd>
		
		<%-- <dt><spring:message code="account.label." />transferee</dt>
		<dd>
			<c:out value="${userEntity.transferee}" />
		</dd> --%>
		<dt><spring:message code="account.label.homePhone" /></dt>
		<dd>
			<c:out value="${userEntity.homePhone}" />
		</dd>
		<dt><spring:message code="account.label.cellPhone" /></dt>
		<dd>
			<c:out value="${userEntity.cellPhone}" />
		</dd>
		<dt><spring:message code="account.label.ssn" /></dt>
		<dd>
			<c:out value="${userEntity.ssn}" />
		</dd>
		
		<dt><spring:message code="account.label.citizenship" /></dt>
		<dd>
			<c:out value="${userEntity.citizenship}" />
		</dd>
		
		<dt><spring:message code="account.label.ethnicity" /></dt>
		<dd>
			<c:out value="${userEntity.ethnicity}" />
		</dd>
		
		<dt><spring:message code="account.label.international" /></dt>
		<dd>
			<c:out value="${userEntity.international}" />
		</dd>
		
		<dt><spring:message code="account.label.transferee" /></dt>
		<dd>
			<c:out value="${userEntity.transferee}" />
		</dd>
		
		<dt><spring:message code="account.label.enabled" /></dt>
		<dd>
			<c:out value="${userEntity.enabled}" />
		</dd>
	
		<dt><spring:message code="account.label.roles" /></dt>
		<dd>
			<c:forEach var="role" items="${userEntity.roles}">
				<c:out value="${role.name}" />
				<br />
			</c:forEach>
		</dd>
		
	</dl>
</div>
<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="editAccountUrl" value="/accounts/${userEntity.id}/edit" />
	<%-- <c:url var="accountUrl"	value="/accounts/${userEntity.id}" /> --%>
	<%-- <c:url var="deleteAccountUrl" value="/accounts/${userEntity.id}/delete" /> --%>
	
	<c:url var="createAssociatedUser" value="/accounts/${userEntity.id}/associatedUser/new" />
	<c:url var="editAssociatedUser" value="/accounts/${userEntity.id}/associatedUser/edit" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	
</sec:authorize>

<a href="${editAccountUrl}" class="btn btn-primary btn-sm">Update Personal Information</a>

<hr>

<h2>Administrative Use</h2>

<c:choose>
	<c:when test="${empty associatedUsers}">
			<a href="${createAssociatedUser}" class="btn btn-primary btn-sm">Register associated users</a>
	</c:when>
	<c:otherwise>
		
		<div class="row">
			<dl class="dl-horizontal col-md-6">
				<dt><spring:message code="account.label.admissionCounselor" /></dt>
				<dd>
					<c:out value="${associatedUsers.admissionOfficer.fullName}"></c:out>
				</dd>
			
				<dt><spring:message code="account.label.agent" /></dt>
				<dd>
					<c:out value="${associatedUsers.agent}"></c:out>
				</dd>
				
				<dt><spring:message code="account.label.referrer" /></dt>
				<dd>
					<c:out value="${associatedUsers.referrer}"></c:out>
				</dd>
				
				<dt></dt>
			</dl>
		</div>
		
		<div class="row">
			<div class="col-md-2">
					<a href="${editAssociatedUser}" class="btn btn-primary btn-sm">Edit associated users</a>
			</div>
		</div>
		
		
	</c:otherwise>
</c:choose>


<h1>Addresses</h1>
<hr />

<!-- 
$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
I have to put the addresses in two clolumsn in the page.
I will have to iterate over all the addresses when displaying, 
then put the odds on the left column and even on right column. 
$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
 -->


<c:choose>
	<c:when test="${empty addresses}">
		<p>No Address.</p>
	</c:when>
	<c:otherwise>
		<c:forEach var="address" items="${addresses}">
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<c:url var="editAddressUrl"
					value="/accounts/${address.userEntity.id}/address/${address.id}/edit" />
				<c:url var="deleteAddressUrl"
					value="/accounts/${address.userEntity.id}/address/${address.id}/delete" />
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_USER')">
				<c:url var="editAddressUrl"
					value="/myAccount/address/${address.id}/edit" />
				<c:url var="deleteAddressUrl"
					value="/myAccount/address/${address.id}/delete" />
			</sec:authorize>

			<address class="col-md-6">
				<%-- address.id: <c:out value="${address.id}" /> --%>
				<span class="addressType"> <em><c:out
							value="${address.addressType}" /></em>
				</span>
				<hr />
				<c:out value="${address.address1}" />
				<c:if test="${address.address2 != null}">
			  	, <c:out value="${address.address2}" />
					<br>
				</c:if>
				<c:out value="${address.city}" />, <c:out value="${address.state}" /> <c:out value="${address.zipcode}" />
				<br>
				<c:out value="${address.country}" />
				<!-- <br> <abbr title="Phone">P:</abbr> (123) 456-7890<br> <a
					href="mailto:#">first.last@example.com</a><br> -->
				<br><br>
					
				<div class="row">
					<div class="col-md-3">
						<a href="${editAddressUrl}" class="btn btn-primary btn-sm">Edit</a>
					</div>
					<div class="col-md-3">
						<!-- Button trigger modal -->
						<a data-toggle="modal" data-remote="${deleteAddressUrl}" data-target="#deleteModal" 
							class="btn btn-danger btn-sm">Delete</a><br><br>
							
							
						<!-- delete address Modal -->
						<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						  <div class="modal-dialog">
						    <div class = "modal-content">
						    
							</div>
						  </div>
						</div>
						
						<%-- <form id="deleteForm" action="${deleteAddressUrl}" method="post">
							<div>
								<input class="btn btn-danger btn-sm" type="submit" value="DELETE" />
							</div>
						</form> --%>
						
						

					</div>
				</div>
			</address>
		</c:forEach>

	</c:otherwise>
</c:choose>

<!-- I am displaying the addresses in 2 columns,
I need this to push down the h3 below from mixing with the addresses -->
<div class="row"></div>

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





<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.js"></script>
	
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.js"></script>
	<!-- 
		When I click on EDIT button the Modal is popluated with db datas for the current address. 
		When I clikc on Cancle and click on edit for another address, the selected value is populated to the modal.
		After 3 - 4 clicks the modal no more reloads.
		Using this js to fix that issue
	 -->
	<script>
		$(function (){
			$('body').on('hidden.bs.modal', '.modal', function () {
			    $(this).removeData('bs.modal');
			});
		});
	</script>
	<!-- Call the dropdowns via JavaScript  -->
	<script>
		$(document).ready(function () {
	        $('.dropdown-toggle').dropdown();
	    });
	</script>