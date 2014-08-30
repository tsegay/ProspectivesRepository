<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- <sec:authentication var="userEntity" property="principal" /> --%>

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


<h1 class="page-header">MyAccount Page</h1>

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
			<fmt:formatDate var="dateCreatedString" value="${userEntity.dateCreated}" pattern="MM-dd-yyyy" />
			<c:out value="${dateCreatedString}" />
		</dd>
	</dl>
	<dl class="dl-horizontal col-md-6">
	
		<dt><spring:message code="account.label.dob" /></dt>
		<dd>
			<fmt:formatDate var="dobString" value="${userEntity.dob}" pattern="MM-dd-yyyy" />
			<c:out value="${dobString}" />
		</dd>
		
		<dt><spring:message code="account.label.gender" /></dt>
		<dd>
			<c:out value="${userEntity.gender}" />
		</dd>
		
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
		
		<dt><spring:message code="account.label.sevisNumber" /></dt>
		<dd>
			<c:out value="${userEntity.sevisNumber}" />
		</dd>
		
	</dl>
</div>
<div class="row">
	<dl class="dl-horizontal col-md-12">
		<dt>How did you hear about ACCT?</dt>
		<dd>
			<c:out value="${userEntity.heardAboutAcctThru}" />
		</dd>
  </dl>
</div>

<sec:authorize access="hasRole('ROLE_ADMIN')">
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<c:url var="userEntityUrl"	value="/myAccount" />
	<c:url var="editAccountUrl" value="/myAccount/edit" />
	<c:url var="deleteUserEntityUrl" value="/myAccount/delete" />
</sec:authorize>


<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<h3>
		<a href="${editAccountUrl}" class="btn btn-primary btn-sm">Update Personal Information</a>
	</h3>
</sec:authorize>

<h1>Addresses</h1>
<hr />

<!-- 
$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
I have to put the addresses in two columns in the page.
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
			<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
			<%-- <sec:authorize access="hasRole('ROLE_ADMIN')"> --%>
				<c:url var="editAddressUrl" value="/accounts/${address.userEntity.id}/address/${address.id}/edit" />
				<c:url var="deleteAddressUrl" value="/accounts/${address.userEntity.id}/address/${address.id}/delete" />
			</sec:authorize>
			
			<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING')">
				<c:url var="editAddressUrl" value="/myAccount/address/${address.id}/edit" />
				<c:url var="deleteAddressUrl" value="/myAccount/address/${address.id}/delete" />
			</sec:authorize>

			<address class="col-md-6">
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
							<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">	
							<a href="${editAddressUrl}" class="btn btn-primary btn-sm">Edit</a>
							</sec:authorize>
						</div>
						<div class="col-md-3">
						
							<!-- Button trigger modal -->
							<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">	
							<a data-toggle="modal" data-remote="${deleteAddressUrl}" data-target="#deleteModal" 
								class="btn btn-danger btn-sm">Delete</a><br><br>
							</sec:authorize>
								
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

<%-- <sec:authorize access="hasRole('ROLE_ADMIN')"> --%>
<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<c:url var="newAddressUrl" value="/accounts/${userEntity.id}/address/new" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_STUDENT_PENDING')">
	<c:url var="newAddressUrl" value="/myAccount/address/new" />
</sec:authorize>

<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<h3>
		<a href="${newAddressUrl}">Add New Address</a>
	</h3>
</sec:authorize>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
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