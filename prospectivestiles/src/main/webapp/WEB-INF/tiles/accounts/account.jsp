<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%-- <sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="myAccount" value="/accounts/${userEntity.id}" />
	<c:url var="educationUrl" value="/accounts/${userEntity.id}/educations" />
	<c:url var="newHighSchoolUrl" value="/accounts/${userEntity.id}/highSchool/new" />
	<c:url var="newInstituteUrl" value="/accounts/${userEntity.id}/institute/new" />
	<c:url var="addressUrl" value="/accounts/${userEntity.id}/addresses" />
	<c:url var="emergencyContactsUrl" value="/accounts/${userEntity.id}/emergencyContacts" />
	<c:url var="applyingForUrl" value="/accounts/${userEntity.id}/applyingFor" />
	<c:url var="standardTestsUrl" value="/accounts/${userEntity.id}/standardTests" />
	<c:url var="uploadedFilesUrl" value="/accounts/${userEntity.id}/files" />
	<c:url var="employersUrl" value="/accounts/${userEntity.id}/employers" />
	<c:url var="checklistUrl" value="/accounts/${userEntity.id}/checklists" />
	<c:url var="evaluationUrl" value="/accounts/${userEntity.id}/evaluations" />
	<c:url var="reportsUrl" value="/accounts/${userEntity.id}/reports" />
	<c:url var="missingDocumentsUrl" value="/accounts/${userEntity.id}/reports/missingDocuments" />
	<c:url var="evaluationReportUrl" value="/accounts/${userEntity.id}/reports/evaluationReport" />
	<c:url var="messagesUrl" value="/accounts/${userEntity.id}/messages" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="myAccount" value='/myAccount'/>
	<c:url var="educationUrl" value="/myAccount/educations" />
	<c:url var="newHighSchoolUrl" value="/myAccount/highSchool/new" />
	<c:url var="newInstituteUrl" value="/myAccount/institute/new" />
	<!-- change address to addresses -->
	<c:url var="addressUrl" value="/myAccount/addresses" />
	<c:url var="emergencyContactsUrl" value="/myAccount/emergencyContacts" />
	<c:url var="applyingForUrl" value="/myAccount/applyingFor" />
	<c:url var="standardTestsUrl" value="/myAccount/standardTests" />
	<c:url var="uploadedFilesUrl" value="/myAccount/files" />
	<c:url var="employersUrl" value="/myAccount/employers" />
</sec:authorize>


<ul class="nav nav-tabs">
	<li class="dropdown active">
	  <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-toggle="dropdown">Profile <span class="caret"></span></a>
	  <ul class="dropdown-menu">
	    <li><a href="${myAccount}">Personal Info</a></li>
	    <li><a href="${addressUrl}">Addresses</a></li>
	    <li><a href="${emergencyContactsUrl}">EmergencyContacts</a></li>
	    <li class="divider"></li>
	    <li><a href="${employersUrl}">Employment</a></li>
	    <li class="divider"></li>
	    <li><a href="#">One more separated link</a></li>
	  </ul>
	</li>
	<li>
		<a href="${educationUrl}">Educational bgd</a>
	</li>
	<li>
		<a href="${applyingForUrl}">ApplyingFor</a>
	</li>
	<li>
		<a href="${standardTestsUrl}">StandardTest</a>
	</li>
	<li>
		<a href="${uploadedFilesUrl}">Documents</a>
	</li>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<li>
			<a href="${checklistUrl}">Checklist</a>
		</li>
		<li>
			<a href="${evaluationUrl}">Evaluation</a>
		</li>
		<li class="dropdown">
		  <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-toggle="dropdown">Reports<span class="caret"></span></a>
		  <ul class="dropdown-menu">
		    <li><a href="${reportsUrl}">Reports</a></li>
		    <li><a href="${missingDocumentsUrl}">MissingDocuments</a></li>
		    <li><a href="${evaluationReportUrl}">evaluationReport</a></li>
		    <li class="divider"></li>
		    <li><a href="#">Link</a></li>
		    <li class="divider"></li>
		    <li><a href="#">Link</a></li>
		  </ul>
		</li>
		<li>
			<a href="${messagesUrl}">Messages</a>
		</li>
	</sec:authorize>
	
</ul> --%>

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


<dl class="dl-horizontal">
	<dt>id</dt>
	<dd>
		<c:out value="${userEntity.id}" />
	</dd>
	
	<dt>firstName</dt>
	<dd>
		<c:out value="${userEntity.firstName}" />
	</dd>

	<dt>middleName</dt>
	<dd>
		<c:out value="${userEntity.middleName}" />
	</dd>

	<dt>lastName</dt>
	<dd>
		<c:out value="${userEntity.lastName}" />
	</dd>

	<dt>Username</dt>
	<dd>
		<c:out value="${userEntity.username}" />
	</dd>

	<dt>E-mail</dt>
	<dd>
		<a href="mailto:${userEntity.email}">${userEntity.email}</a>
	</dd>
	
	<dt>marketingOk</dt>
	<dd>
		<c:out value="${userEntity.marketingOk}" />
	</dd>
	
	<dt>acceptTerms</dt>
	<dd>
		<c:out value="${userEntity.acceptTerms}" />
	</dd>
	
	<dt>dateCreated</dt>
	<dd>
		<fmt:formatDate var="dateCreatedString" value="${userEntity.dateCreated}" pattern="yyyy-MM-dd HH:mm:ss" />
		<c:out value="${dateCreatedString}" />
	</dd>
	
	<dt>dob</dt>
	<dd>
		<fmt:formatDate var="dobString" value="${userEntity.dob}" pattern="yyyy-MM-dd" />
		<c:out value="${dobString}" />
	</dd>
	
	<dt>gender</dt>
	<dd>
		<c:out value="${userEntity.gender}" />
	</dd>
	
	<dt>transferee</dt>
	<dd>
		<c:out value="${userEntity.transferee}" />
	</dd>
	<dt>homePhone</dt>
	<dd>
		<c:out value="${userEntity.homePhone}" />
	</dd>
	<dt>cellPhone</dt>
	<dd>
		<c:out value="${userEntity.cellPhone}" />
	</dd>
	<dt>ssn</dt>
	<dd>
		<c:out value="${userEntity.ssn}" />
	</dd>
	
	<dt>citizenship</dt>
	<dd>
		<c:out value="${userEntity.citizenship}" />
	</dd>
	
	<dt>ethnicity</dt>
	<dd>
		<c:out value="${userEntity.ethnicity}" />
	</dd>
	
	<dt>Account enabled</dt>
	<dd>
		<c:out value="${userEntity.enabled}" />
	</dd>

	<dt>Roles</dt>
	<dd>
		<c:forEach var="role" items="${userEntity.roles}">
			<c:out value="${role.name}" />
			<br />
		</c:forEach>
	</dd>
	
</dl>

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="accountUrl"	value="/accounts/${userEntity.id}" />
	<c:url var="editAccountUrl" value="/accounts/${userEntity.id}/edit" />
	<c:url var="deleteAccountUrl" value="/accounts/${userEntity.id}/delete" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	
</sec:authorize>

<h3>
	<a href="${editAccountUrl}" class="btn btn-primary btn-lg">Edit/Update</a>
</h3>

<%-- <a data-toggle="modal" data-remote="${editAccountUrl}"
	data-target="#editAccountModal" class="btn btn-primary btn-lg">Edit/Update</a> --%>

	<!-- editAccountModal  -->
<!-- <div class="modal fade" id="editAccountModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class = "modal-content">
	  </div>
  </div>
</div> -->


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