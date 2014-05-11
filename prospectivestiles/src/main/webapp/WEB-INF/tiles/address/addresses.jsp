<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<!-- Use this for ROLE_USER to get current user -->
<%-- <sec:authentication var="myAccount" property="principal" /> --%>

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<%-- <c:url var="myAccount" value='/myAccount'/>
	<c:url var="account" value="../accounts/${user.id}" /> --%>
	<c:url var="myAccount" value="/accounts/${userEntity.id}" />
	<c:url var="educationUrl" value="/accounts/${userEntity.id}/educations" />
	<c:url var="newHighSchoolUrl"
		value="/accounts/${userEntity.id}/highSchool/new" />
	<c:url var="newInstituteUrl"
		value="/accounts/${userEntity.id}/institute/new" />
	<c:url var="addressUrl" value="/accounts/${userEntity.id}/addresses" />
	<c:url var="emergencyContactsUrl"
		value="/accounts/${userEntity.id}/emergencyContacts" />
	<c:url var="applyingForUrl"
		value="/accounts/${userEntity.id}/applyingFor" />
	<c:url var="standardTestsUrl"
		value="/accounts/${userEntity.id}/standardTests" />
	<c:url var="employersUrl" value="/accounts/${userEntity.id}/employers" />
	<c:url var="checklistUrl" value="/accounts/${userEntity.id}/checklists" />
	<c:url var="evaluationUrl"
		value="/accounts/${userEntity.id}/evaluations" />
	<c:url var="reportsUrl" value="/accounts/${userEntity.id}/reports" />
	<c:url var="missingDocumentsUrl"
		value="/accounts/${userEntity.id}/reports/missingDocuments" />
	<c:url var="evaluationReportUrl" value="/accounts/${userEntity.id}/reports/evaluationReport" />
	<c:url var="messagesUrl" value="/accounts/${userEntity.id}/messages" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="myAccount" value='/myAccount' />
	<c:url var="educationUrl" value="/myAccount/educations" />
	<c:url var="newHighSchoolUrl" value="/myAccount/highSchool/new" />
	<c:url var="newInstituteUrl" value="/myAccount/institute/new" />
	<!-- change address to addresses -->
	<c:url var="addressUrl" value="/myAccount/addresses" />
	<c:url var="emergencyContactsUrl" value="/myAccount/emergencyContacts" />
	<c:url var="applyingForUrl" value="/myAccount/applyingFor" />
	<c:url var="standardTestsUrl" value="/myAccount/standardTests" />
	<c:url var="employersUrl" value="/myAccount/employers" />
	<c:url var="messagesUrl" value="/myAccount/messages" />
</sec:authorize>


<ul class="nav nav-tabs">
	<li class="dropdown active"><a href="#" class="dropdown-toggle"
		data-toggle="dropdown" data-toggle="dropdown">Profile <span
			class="caret"></span></a>
		<ul class="dropdown-menu">
			<li><a href="${myAccount}">Personal Info</a></li>
			<li><a href="${addressUrl}">Addresses</a></li>
			<li><a href="${emergencyContactsUrl}">EmergencyContacts</a></li>
			<li class="divider"></li>
			<li><a href="${employersUrl}">Employment</a></li>
			<li class="divider"></li>
			<li><a href="#">One more separated link</a></li>
		</ul></li>
	<li><a href="${educationUrl}">Educational bgd</a></li>
	<li><a href="${applyingForUrl}">ApplyingFor</a></li>
	<li><a href="${standardTestsUrl}">StandardTest</a></li>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<li><a href="${checklistUrl}">Checklist</a></li>
		<li><a href="${evaluationUrl}">Evaluation</a></li>
		<li class="dropdown"><a href="#" class="dropdown-toggle"
			data-toggle="dropdown" data-toggle="dropdown">Reports<span
				class="caret"></span></a>
			<ul class="dropdown-menu">
				<li><a href="${reportsUrl}">Reports</a></li>
				<li><a href="${missingDocumentsUrl}">MissingDocuments</a></li>
				<li><a href="${evaluationReportUrl}">evaluationReport</a></li>
				<li class="divider"></li>
				<li><a href="#">Link</a></li>
				<li class="divider"></li>
				<li><a href="#">Link</a></li>
			</ul></li>
	</sec:authorize>
	<li><a href="${messagesUrl}">Messages</a></li>
</ul>

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



<!-- address Modal -->



<!-- edit address Modal -->



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
