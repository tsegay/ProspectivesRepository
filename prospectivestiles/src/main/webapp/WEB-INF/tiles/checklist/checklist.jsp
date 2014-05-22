<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!-- Use this for ROLE_USER to get current user -->
<%-- <sec:authentication var="myAccount" property="principal" /> --%>


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
	<%-- <c:url var="newAddressUrl" value="/myAccount/address/new" /> --%>
	<c:url var="emergencyContactsUrl" value="/myAccount/emergencyContacts" />
	<c:url var="applyingForUrl" value="/myAccount/applyingFor" />
	<c:url var="standardTestsUrl" value="/myAccount/standardTests" />
	<c:url var="uploadedFilesUrl" value="/myAccount/files" />
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
		<li class="active">
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

<h1>Checklist page</h1>


<c:if test="${param.deleted == true}">
	<div class="info alert">Checklist deleted.</div>
</c:if>

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<!-- checklistUrl name is already used. look up -->
	<%-- <c:url var="checklistUrl"	value="/accounts/${userEntity.id}/checklist/${userEntity.checklist.id}" /> --%>
	<c:url var="editChecklistUrl" value="/accounts/${userEntity.id}/checklist/${userEntity.checklist.id}" />
	<c:url var="deleteChecklistUrl" value="/accounts/${userEntity.id}/checklist/${userEntity.checklist.id}/delete" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<%-- <c:url var="checklistUrl"	value="/myAccount/checklist/${userEntity.checklist.id}" /> --%>
	<c:url var="editChecklistUrl" value="/myAccount/checklist/${userEntity.checklist.id}" />
	<c:url var="deleteChecklistUrl" value="/myAccount/checklist/${userEntity.checklist.id}/delete" />
</sec:authorize>
		
<c:choose>
	<c:when test="${empty checklists}">
		<p>No Checklist.</p>
		<!-- Button trigger modal -->
		<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#addChecklistModal">
		  Create Checklist
		</button>
	</c:when>
	<c:otherwise>
				
		<dl class="dl-horizontal">
		  <dt>Id</dt>
		  <dd>
		  	<c:out value="${userEntity.checklist.id}"></c:out>
		  </dd>
		  
		  <dt>f1Visa</dt>
		  <%-- <dd><c:out value="${userEntity.checklist.f1Visa}"></c:out></dd> --%>
		  <dd>
		  	<c:if test="${userEntity.checklist.f1Visa == true}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.f1Visa == false}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt>bankStmt</dt>
		  <%-- <dd><c:out value="${userEntity.checklist.bankStmt}"></c:out></dd> --%>
		  <dd>
		  	<c:if test="${userEntity.checklist.bankStmt == true}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.bankStmt == false}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt>i20</dt>
		  <%-- <dd><c:out value="${userEntity.checklist.i20}"></c:out></dd> --%>
		  <dd>
		  	<c:if test="${userEntity.checklist.i20 == true}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.i20 == false}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt>passport</dt>
		  <dd>
		  	<c:if test="${userEntity.checklist.passport == true}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.passport == false}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt>financialAffidavit</dt>
		  <dd>
		  	<c:if test="${userEntity.checklist.financialAffidavit == true}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.financialAffidavit == false}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt>applicationFee</dt>
		  <dd>
		  	<c:if test="${userEntity.checklist.applicationFee == true}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.applicationFee == false}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt>transcript</dt>
		  <dd>
		  	<c:if test="${userEntity.checklist.transcript == true}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.transcript == false}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt>diplome</dt>
		  <dd>
		  	<c:if test="${userEntity.checklist.diplome == true}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.diplome == false}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt>notes</dt>
		  <dd><c:out value="${userEntity.checklist.notes}"></c:out></dd>
		  
		</dl>
		
		<br />
		<br />
		
		<!-- Checklist can't be deleted by any user.  -->
		<a data-toggle="modal" href="${editChecklistUrl}" data-target="#editChecklistModal" 
			class="btn btn-primary btn-lg">Edit</a>
		<br />
		<br />	
		<form id="deleteForm" action="${deleteChecklistUrl}" method="post">
			<div><input type="submit" value="DELETE" /></div>
		</form>
		
	</c:otherwise>
</c:choose>

<br />

<!-- checklist Modal -->
<div class="modal fade" id="addChecklistModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class = "modal-content">
	        <form:form action="${checklistUrl}" modelAttribute="checklist" role="form" class = "form-horizontal">
	            <div class = "modal-header">
	                <h4>Add Checklist</h4>
	                checklist: 
	                <c:if test="${checklist.id > 0}">
	                	<c:out value="${checklist.id}" />
	                </c:if>
	                <br />
	                checklist.userEntity.id: 
	                <c:if test="${checklist.id > 0}">
	                	<c:out value="${checklist.userEntity.id}" />
	                </c:if>
	            </div>
	            <div class = "modal-body">
	            	
	            	<div class="form-group checkbox row">
					    <div class="col-sm-2">
					    	<form:checkbox class="form-control" id="f1Visa" path="f1Visa" />
					    </div>
						<label for="f1Visa" class="col-sm-5 control-label">f1Visa</label>
					    <div class="col-sm-5">
					      	<form:errors path="f1Visa" htmlEscape="false" />
					    </div>
					</div>
	            	
	            	<div class="form-group checkbox row">
					    <div class="col-sm-2">
					    	<form:checkbox class="form-control" id="bankStmt" path="bankStmt" />
					    </div>
						<label for="bankStmt" class="col-sm-5 control-label">bankStmt</label>
					    <div class="col-sm-5">
					      	<form:errors path="bankStmt" htmlEscape="false" />
					    </div>
					</div>
	            	
	            	<div class="form-group checkbox row">
					    <div class="col-sm-2">
					    	<form:checkbox class="form-control" id="i20" path="i20" />
					    </div>
						<label for="i20" class="col-sm-5 control-label">i20</label>
					    <div class="col-sm-5">
					      	<form:errors path="i20" htmlEscape="false" />
					    </div>
					</div>
	            	
	            	<div class="form-group checkbox row">
					    <div class="col-sm-2">
					    	<form:checkbox class="form-control" id="passport" path="passport" />
					    </div>
						<label for="passport" class="col-sm-5 control-label">passport</label>
					    <div class="col-sm-5">
					      	<form:errors path="passport" htmlEscape="false" />
					    </div>
					</div>
	            	
	            	<div class="form-group checkbox row">
					    <div class="col-sm-2">
					    	<form:checkbox class="form-control" id="financialAffidavit" path="financialAffidavit" />
					    </div>
						<label for="financialAffidavit" class="col-sm-5 control-label">financialAffidavit</label>
					    <div class="col-sm-5">
					      	<form:errors path="financialAffidavit" htmlEscape="false" />
					    </div>
					</div>
	            	
	            	<div class="form-group checkbox row">
					    <div class="col-sm-2">
					    	<form:checkbox class="form-control" id="applicationFee" path="applicationFee" />
					    </div>
						<label for="applicationFee" class="col-sm-5 control-label">applicationFee</label>
					    <div class="col-sm-5">
					      	<form:errors path="applicationFee" htmlEscape="false" />
					    </div>
					</div>
	            	
	            	<div class="form-group checkbox row">
					    <div class="col-sm-2">
					    	<form:checkbox class="form-control" id="transcript" path="transcript" />
					    </div>
						<label for="transcript" class="col-sm-5 control-label">transcript</label>
					    <div class="col-sm-5">
					      	<form:errors path="transcript" htmlEscape="false" />
					    </div>
					</div>
	            	
	            	<div class="form-group checkbox row">
					    <div class="col-sm-2">
					    	<form:checkbox class="form-control" id="diplome" path="diplome" />
					    </div>
						<label for="diplome" class="col-sm-5 control-label">diplome</label>
					    <div class="col-sm-5">
					      	<form:errors path="diplome" htmlEscape="false" />
					    </div>
					</div>
	            	
	            	<div class="form-group row">
						<label for="notes" class="col-sm-2 control-label">notes</label>
					    <div class="col-sm-5">
					      <form:textarea path="notes" class="form-control" rows="5" cols="30" />
					    </div>
					    <div class="col-sm-5">
					    	<form:errors path="notes" htmlEscape="false" />
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
</div>


<!-- edit checklist Modal -->
<div class="modal fade" id="editChecklistModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class = "modal-content">
	  </div>
  </div>
</div>



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