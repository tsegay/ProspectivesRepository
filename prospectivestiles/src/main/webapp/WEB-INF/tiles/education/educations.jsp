<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Use this for ROLE_USER to get current user -->
<%-- <sec:authentication var="myAccount" property="principal" /> --%>

<!-- Should delete this user, NOT using it -->
<%-- <c:set var="user" value="${highSchool.userEntity}" /> --%>

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<%-- <c:url var="myAccount" value='/myAccount'/>
	<c:url var="account" value="../accounts/${user.id}" /> --%>
	<c:url var="myAccount" value="/accounts/${userEntity.id}" />
	<c:url var="educationUrl" value="/accounts/${userEntity.id}/educations" />
	<c:url var="newHighSchoolUrl" value="/accounts/${userEntity.id}/highSchool/new" />
	<c:url var="newInstituteUrl" value="/accounts/${userEntity.id}/institute/new" />
	<c:url var="addressUrl" value="/accounts/${userEntity.id}/addresses" />
	<%-- <c:url var="newAddressUrl" value="/accounts/${userEntity.id}/address/new" /> --%>
	<c:url var="emergencyContactsUrl" value="/accounts/${userEntity.id}/emergencyContacts" />
	<c:url var="applyingForUrl" value="/accounts/${userEntity.id}/applyingFor" />
	<c:url var="standardTestsUrl" value="/accounts/${userEntity.id}/standardTests" />
	<c:url var="employersUrl" value="/accounts/${userEntity.id}/employers" />
	<c:url var="checklistUrl" value="/accounts/${userEntity.id}/checklists" />
	<c:url var="evaluationUrl" value="/accounts/${userEntity.id}/evaluations" />
	<c:url var="reportsUrl" value="/accounts/${userEntity.id}/reports" />
	<c:url var="missingDocumentsUrl" value="/accounts/${userEntity.id}/missingDocuments" />
	<c:url var="messagesUrl" value="/accounts/${userEntity.id}/messages" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="myAccount" value='/myAccount'/>
	<c:url var="educationUrl" value="/myAccount/educations" />
	<c:url var="newHighSchoolUrl" value="/myAccount/highSchool/new" />
	<c:url var="newInstituteUrl" value="/myAccount/institute/new" />
	<!-- change address to addresses -->
	<c:url var="addressUrl" value="/myAccount/addresses" />
	<%-- <c:url var="newAddressUrl" value="/myAccount/address/new" /> --%>
	<c:url var="emergencyContactsUrl" value="/myAccount/emergencyContacts" />
	<c:url var="applyingForUrl" value="/myAccount/applyingFor" />
	<c:url var="standardTestsUrl" value="/myAccount/standardTests" />
	<c:url var="employersUrl" value="/myAccount/employers" />
	<c:url var="messagesUrl" value="/myAccount/messages" />
</sec:authorize>


<ul class="nav nav-tabs">
	<li class="dropdown">
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
	<li class="active">
		<a href="${educationUrl}">Educational bg</a>	
	</li>
	<li>
		<a href="${applyingForUrl}">ApplyingFor</a>
	</li>
	<li>
		<a href="${standardTestsUrl}">StandardTest</a>
	</li><sec:authorize access="hasRole('ROLE_ADMIN')">
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
		    <li><a href="#">Link</a></li>
		    <li class="divider"></li>
		    <li><a href="#">Link</a></li>
		    <li class="divider"></li>
		    <li><a href="#">Link</a></li>
		  </ul>
		</li>
	</sec:authorize>
	<li>
		<a href="${messagesUrl}">Messages</a>
	</li>
	
</ul>

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

<h1>HighSchools page</h1>



<sec:authorize access="hasRole('ROLE_ADMIN')">
	<h3>
		<a href="${newHighSchoolUrl}">Add New HighSchool</a>
	</h3>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<h3>
		<a href="${newHighSchoolUrl}">Add New HighSchool</a>
	</h3>
</sec:authorize>


<c:if test="${param.deleted == true}">
	<div class="info alert">HighSchool/Institute deleted.</div>
</c:if>

<c:choose>
	<c:when test="${empty highSchools}">
		<p>No HighSchool.</p>
	</c:when>
	<c:otherwise>

		<div class="table-responsive">
			<table class="table table-hover table-striped">
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>State</th>
					<th>Country</th>
					<th>Diplome</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
	
				<c:forEach var="highSchool" items="${highSchools}">
				
				 <sec:authorize access="hasRole('ROLE_ADMIN')">
					<c:url var="highSchoolUrl"	value="/accounts/${highSchool.userEntity.id}/highSchool/${highSchool.id}" />
					<c:url var="editHighSchoolUrl" value="/accounts/${highSchool.userEntity.id}/highSchool/${highSchool.id}/edit" />
					<c:url var="deleteHighSchoolUrl" value="/accounts/${highSchool.userEntity.id}/highSchool/${highSchool.id}/delete" />
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
					<c:url var="highSchoolUrl"	value="/myAccount/highSchool/${highSchool.id}" />
					<c:url var="editHighSchoolUrl" value="/myAccount/highSchool/${highSchool.id}/edit" />
					<c:url var="deleteHighSchoolUrl" value="/myAccount/highSchool/${highSchool.id}/delete" />
				</sec:authorize>
				
				<tr>
					<td><c:out value="${highSchool.id}"></c:out></td>
					<td>
						<a href="${highSchoolUrl}"><c:out value="${highSchool.name}"></c:out></a>
					</td>
					<td><c:out value="${highSchool.state}"></c:out></td>
					<td><c:out value="${highSchool.country}"></c:out></td>
					<td><c:out value="${highSchool.diplome}"></c:out></td>
					<td>
						<a href="${editHighSchoolUrl}">Edit</a>
					</td>
					<td>
						<form id="deleteForm" action="${deleteHighSchoolUrl}" method="post">
							<div><input type="submit" value="DELETE" /></div>
						</form>
					</td>
				</tr>
								
				</c:forEach>
	
			</table>
		</div>
	</c:otherwise>
</c:choose>



<h1>Institutes</h1>

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<h3>
		<a href="${newInstituteUrl}">Add New Institute</a>
	</h3>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<h3>
		<a href="${newInstituteUrl}">Add New Institute</a>
	</h3>
</sec:authorize>


<c:choose>
	<c:when test="${empty institutes}">
		<p>No Institute.</p>
	</c:when>
	<c:otherwise>

		<div class="table-responsive">
			<table class="table table-hover table-striped">
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>State</th>
					<th>Country</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
	
				<c:forEach var="institute" items="${institutes}">
				
				 <sec:authorize access="hasRole('ROLE_ADMIN')">
					<c:url var="instituteUrl"	value="/accounts/${institute.userEntity.id}/institute/${institute.id}" />
					<c:url var="editInstituteUrl" value="/accounts/${institute.userEntity.id}/institute/${institute.id}/edit" />
					<c:url var="deleteInstituteUrl" value="/accounts/${institute.userEntity.id}/institute/${institute.id}/delete" />
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
					<c:url var="instituteUrl"	value="/myAccount/institute/${institute.id}" />
					<c:url var="editInstituteUrl" value="/myAccount/institute/${institute.id}/edit" />
					<c:url var="deleteInstituteUrl" value="/myAccount/institute/${institute.id}/delete" />
				</sec:authorize>
				
				<tr>
						<td><c:out value="${institute.id}"></c:out></td>
						<td>
							<a href="${instituteUrl}"><c:out value="${institute.name}"></c:out></a>
						</td>
						<td><c:out value="${institute.state}"></c:out></td>
						<td><c:out value="${institute.country}"></c:out></td>
						<td>
							<a href="${editInstituteUrl}">Edit</a>
						</td>
						<td>
							<form id="deleteForm" action="${deleteInstituteUrl}" method="post">
								<div><input type="submit" value="DELETE" /></div>
							</form>
						</td>
					</tr>
				
				</c:forEach>
	
			</table>
		</div>
	</c:otherwise>
</c:choose>

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