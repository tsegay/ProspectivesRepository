<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
	<c:url var="newChecklistUrl" value="/accounts/${userEntity.id}/checklist/new" />
	<c:url var="editChecklistUrl" value="/accounts/${userEntity.id}/checklist/${userEntity.checklist.id}/edit" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="newChecklistUrl" value="/myAccount/checklist/new" />
	<c:url var="editChecklistUrl" value="/myAccount/checklist/${userEntity.checklist.id}/edit" />
</sec:authorize>
		
<c:choose>
	<c:when test="${empty checklists}">
		<p>No Checklist.</p>
		<!-- Button trigger modal -->
		<!-- <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#addChecklistModal">
		  Create Checklist
		</button> -->
		<h3>
			<a href="${newChecklistUrl}">Create Checklist</a>
		</h3>
	</c:when>
	<c:otherwise>
				
		<dl class="dl-horizontal">
		  <dt>Id</dt>
		  <dd><c:out value="${userEntity.checklist.id}"></c:out></dd>
		  
		  <dt><spring:message code="checklist.label.f1Visa" /></dt>
		  <dd>
		  	<c:if test="${userEntity.checklist.f1Visa == \"notrequired\"}">
		  		<span class="gray"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.f1Visa == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.f1Visa == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="checklist.label.bankStmt" /></dt>
		  <dd>
		  	<c:if test="${userEntity.checklist.bankStmt == \"notrequired\"}">
		  		<span class="gray"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.bankStmt == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.bankStmt == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="checklist.label.i20" /></dt>
		  <dd>
		  	<c:if test="${userEntity.checklist.i20 == \"notrequired\"}">
		  		<span class="gray"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.i20 == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.i20 == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="checklist.label.passport" /></dt>
		  <dd>
		  	<c:if test="${userEntity.checklist.passport == \"notrequired\"}">
		  		<span class="gray"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.passport == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.passport == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="checklist.label.financialAffidavit" /></dt>
		  <dd>
		  	<c:if test="${userEntity.checklist.financialAffidavit == \"notrequired\"}">
		  		<span class="gray"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.financialAffidavit == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.financialAffidavit == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="checklist.label.applicationFee" /></dt>
		  <dd>
		  	<c:if test="${userEntity.checklist.applicationFee == \"notrequired\"}">
		  		<span class="gray"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.applicationFee == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.applicationFee == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  <!-- ################# -->
		  <dt><spring:message code="checklist.label.applicationForm" /></dt>
		  <dd>
		  	<c:if test="${userEntity.checklist.applicationForm == \"notrequired\"}">
		  		<span class="gray"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.applicationForm == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.applicationForm == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="checklist.label.enrollmentAgreement" /></dt>
		  <dd>
		  	<c:if test="${userEntity.checklist.enrollmentAgreement == \"notrequired\"}">
		  		<span class="gray"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.enrollmentAgreement == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.enrollmentAgreement == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="checklist.label.grievancePolicy" /></dt>
		  <dd>
		  	<c:if test="${userEntity.checklist.grievancePolicy == \"notrequired\"}">
		  		<span class="gray"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.grievancePolicy == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.grievancePolicy == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="checklist.label.recommendationLetter" /></dt>
		  <dd>
		  	<c:if test="${userEntity.checklist.recommendationLetter == \"notrequired\"}">
		  		<span class="gray"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.recommendationLetter == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.recommendationLetter == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <!-- ################# -->
		  
		  <dt><spring:message code="checklist.label.transcript" /></dt>
		  <dd>
		  	<c:if test="${userEntity.checklist.transcript == \"notrequired\"}">
		  		<span class="gray"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.transcript == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.transcript == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="checklist.label.diplome" /></dt>
		  <dd>
		  	<c:if test="${userEntity.checklist.diplome == \"notrequired\"}">
		  		<span class="gray"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.diplome == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.checklist.diplome == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="checklist.label.notes" /></dt>
		  <dd><c:out value="${userEntity.checklist.notes}"></c:out></dd>
		  
		  <dt><spring:message code="checklist.label.dateCreated" /></dt>
		  <dd>
		  	<fmt:formatDate var="dateCreatedString" value="${userEntity.checklist.dateCreated}" pattern="MM-dd-yyyy" />
			<c:out value="${dateCreatedString}" />
		  	<%-- <c:out value="${userEntity.checklist.dateCreated}"></c:out> --%>
		  </dd>
		  
		  <dt><spring:message code="checklist.label.createdBy" /></dt>
		  <dd><c:out value="${userEntity.checklist.createdBy}"></c:out></dd>
		  
		  <dt><spring:message code="checklist.label.dateLastModified" /></dt>
		  <dd>
		  	<fmt:formatDate var="dateLastModifiedString" value="${userEntity.checklist.dateLastModified}" pattern="MM-dd-yyyy" />
			<c:out value="${dateLastModifiedString}" />
		  	<%-- <c:out value="${userEntity.checklist.dateLastModified}"></c:out> --%>
		  </dd>
		  
		  <dt><spring:message code="checklist.label.lastModifiedBy" /></dt>
		  <dd><c:out value="${userEntity.checklist.lastModifiedBy}"></c:out></dd>
		  
		</dl>
		
		<br />
		<br />
		
		<!-- Checklist can't be deleted by any user.  -->
		<a href="${editChecklistUrl}" class="btn btn-primary btn-lg">Edit</a>
		<br />
		<%-- <a data-toggle="modal" href="${editChecklistUrl}" data-target="#editChecklistModal" 
			class="btn btn-primary btn-lg">Edit</a> --%>
		<br />
		<br />	
		<%-- <form id="deleteForm" action="${deleteChecklistUrl}" method="post">
			<div><input type="submit" value="DELETE" /></div>
		</form> --%>
		
	</c:otherwise>
</c:choose>

<br />

<!-- checklist Modal -->
<%-- <div class="modal fade" id="addChecklistModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
</div> --%>


<!-- edit checklist Modal -->
<!-- <div class="modal fade" id="editChecklistModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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