<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!-- Use this for ROLE_USER to get current user -->
<%-- <sec:authentication var="myAccount" property="principal" /> --%>

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
	<c:url var="addressUrl" value="/myAccount/addresses" />
	<c:url var="emergencyContactsUrl" value="/myAccount/emergencyContacts" />
	<c:url var="applyingForUrl" value="/myAccount/applyingFor" />
	<c:url var="standardTestsUrl" value="/myAccount/standardTests" />
	<c:url var="uploadedFilesUrl" value="/myAccount/files" />
	<c:url var="employersUrl" value="/myAccount/employers" />
	<c:url var="messagesUrl" value="/myAccount/messages" />
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
		<a href="${educationUrl}">Educational bg</a>
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
	</sec:authorize>
	<li>
		<a href="${messagesUrl}">Messages</a>
	</li>
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

<h1>Employer page</h1>


<c:if test="${param.deleted == true}">
	<div class="info alert">Employer deleted.</div>
</c:if>

<c:choose>
	<c:when test="${empty employers}">
		<p>No Employer.</p>
	</c:when>
	<c:otherwise>

		<div class="table-responsive">
			<table class="table table-hover table-striped">
				<tr>
					<th>Id</th>
					<th>employed</th>
					<th>employerName</th>
					<th>companyName</th>
					<th>employedSince</th>
					<th>position</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
	
				<c:forEach var="employer" items="${employers}">
				
				 <sec:authorize access="hasRole('ROLE_ADMIN')">
					<c:url var="employerUrl"	value="/accounts/${employer.userEntity.id}/employer/${employer.id}" />
					<c:url var="editEmployerUrl" value="/accounts/${employer.userEntity.id}/employer/${employer.id}/edit" />
					<c:url var="deleteEmployerUrl" value="/accounts/${employer.userEntity.id}/employer/${employer.id}/delete" />
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
					<c:url var="employerUrl"	value="/myAccount/employer/${employer.id}" />
					<c:url var="editEmployerUrl" value="/myAccount/employer/${employer.id}/edit" />
					<c:url var="deleteEmployerUrl" value="/myAccount/employer/${employer.id}/delete" />
				</sec:authorize>
				
				<tr>
					<td><c:out value="${employer.id}"></c:out>
						<%-- <a href="${employerUrl}"><c:out value="${employer.id}"></c:out></a> --%>
					</td>
					<td><c:out value="${employer.employed}"></c:out></td>
					<td><c:out value="${employer.employerName}"></c:out></td>
					<td><c:out value="${employer.companyName}"></c:out></td>
					<td><c:out value="${employer.employedSince}"></c:out></td>
					<td><c:out value="${employer.position}"></c:out></td>
					<td>
						<a href="${editEmployerUrl}" class="btn btn-primary btn-lg">Edit</a>
					</td>
					<td>
						<form id="deleteForm" action="${deleteEmployerUrl}" method="post">
							<div><input type="submit" value="DELETE" /></div>
						</form>
					</td>
				</tr>
								
				</c:forEach>
	
			</table>
		</div>
	</c:otherwise>
</c:choose>

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="newEmployerUrl" value="/accounts/${userEntity.id}/employer/new" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="newEmployerUrl" value="/myAccount/employer/new" />
</sec:authorize>
			
<h3>
	<a href="${newEmployerUrl}">Add New Employment</a>
</h3>

<!-- Button trigger modal -->
<!-- <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#addEmployerModal">
  Add Employer
</button> -->

<!-- employer Modal -->
<%-- <div class="modal fade" id="addEmployerModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class = "modal-content">
	        <form:form action="${employersUrl}" modelAttribute="employer" role="form" class = "form-horizontal">
	            <div class = "modal-header">
	                <h4>Add Employer</h4>
	                employer: 
	                <c:if test="${employer.id > 0}">
	                	<c:out value="${employer.id}" />
	                </c:if>
	                <br />
	                employer.userEntity.id: 
	                <c:if test="${employer.id > 0}">
	                	<c:out value="${employer.userEntity.id}" />
	                </c:if>
	            </div>
	            <div class = "modal-body">
	            	
	            	<div class="form-group checkbox row">
					    <div class="col-sm-2">
					    	<form:checkbox class="form-control" id="employed" path="employed" />
					    </div>
						<label for="employed" class="col-sm-5 control-label">employed</label>
					    <div class="col-sm-5">
					      	<form:errors path="employed" htmlEscape="false" />
					    </div>
					</div>
	                <div class="form-group row">
						<label for="employerName" class="col-sm-2 control-label">employerName</label>
					    <div class="col-sm-5">
					      <form:input path="employerName" class="form-control" placeholder = "Your employerName" />
					    </div>
					    <div class="col-sm-5">
					    	<form:errors path="employerName" htmlEscape="false" />
					    </div>
					</div>
	                <div class="form-group row">
						<label for="companyName" class="col-sm-2 control-label">companyName</label>
					    <div class="col-sm-5">
					      <form:input path="companyName" class="form-control" placeholder = "Your companyName" />
					    </div>
					    <div class="col-sm-5">
					    	<form:errors path="companyName" htmlEscape="false" />
					    </div>
					</div>
					
					<fmt:formatDate value="${employer.employedSince}" var="employedSinceString" pattern="dd/MM/yyyy" />
	                <div class="form-group row">
						<label for="employedSince" class="col-sm-2 control-label">employedSince</label>
					    <div class="col-sm-5">
					      <form:input path="employedSince" value="${employedSinceString}" class="form-control" id="employedSince" placeholder="Your employedSince" />
						</div>
					    <div class="col-sm-5">
					    	<form:errors path="employedSince" htmlEscape="false" />
					    </div>
					</div>
					
	                <div class="form-group row">
						<label for="position" class="col-sm-2 control-label">position</label>
					    <div class="col-sm-5">
					      <form:input path="position" class="form-control" placeholder = "Your position" />
					    </div>
					    <div class="col-sm-5">
					    	<form:errors path="position" htmlEscape="false" />
					    </div>
					</div>
					
			        
	            </div>
	            <div class = "modal-footer">
	        		<a class = "btn btn-default" data-dismiss = "modal">Cancel</a>    
	        		<input class="btn btn-primary" type="submit" value="Submit"></input>
	            </div>
	        </form:form>
	  </div>
  </div>
</div> --%>


<!-- edit employer Modal -->
<!-- <div class="modal fade" id="editEmployerModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	