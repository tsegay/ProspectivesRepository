<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
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

<h1>Checklist</h1>


<c:if test="${param.deleted == true}">
	<div class="info alert">Evaluation deleted.</div>
</c:if>

<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<c:url var="newEvaluationUrl" value="/accounts/${userEntity.id}/evaluation/new" />
	<c:url var="editEvaluationUrl" value="/accounts/${userEntity.id}/evaluation/${userEntity.evaluation.id}/edit" />
	<c:url var="grantAdmisionUrl" value="/accounts/${userEntity.id}/evaluation/${userEntity.evaluation.id}/grantAdmision" />
	<c:url var="denyAdmisionUrl" value="/accounts/${userEntity.id}/evaluation/${userEntity.evaluation.id}/denyAdmision" />
	<c:url var="getEvaluationReport" value="/admin/report/${userEntity.id}/evaluation" />
</sec:authorize>
		
<c:choose>
	<c:when test="${empty evaluations}">
		<p>No Evaluation.</p>
		<!-- Button trigger modal -->
		<!-- <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#addEvaluationModal">
		  Create Evaluation
		</button> -->
		<h3>
			<a href="${newEvaluationUrl}">Create Checklist</a>
		</h3>
	</c:when>
					
	<c:otherwise>
				
		<div class="row">
		<dl class="dl-horizontal col-md-6">
		  <dt>Id</dt>
		  <dd><c:out value="${userEntity.evaluation.id}"></c:out></dd>
		  
		  <dt><spring:message code="evaluationForm.label.f1Visa" /></dt>
		  <dd>
		  	<c:if test="${userEntity.evaluation.f1Visa == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.f1Visa == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.f1Visa == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="evaluationForm.label.bankStmt" /></dt>
		  <dd>
		  	<c:if test="${userEntity.evaluation.bankStmt == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.bankStmt == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.bankStmt == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="evaluationForm.label.i20" /></dt>
		  <dd>
		  	<c:if test="${userEntity.evaluation.i20 == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.i20 == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.i20 == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="evaluationForm.label.passport" /></dt>
		  <dd>
		  	<c:if test="${userEntity.evaluation.passport == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.passport == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.passport == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="evaluationForm.label.financialAffidavit" /></dt>
		  <dd>
		  	<c:if test="${userEntity.evaluation.financialAffidavit == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.financialAffidavit == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.financialAffidavit == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="evaluationForm.label.applicationFee" /></dt>
		  <dd>
		  	<c:if test="${userEntity.evaluation.applicationFee == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.applicationFee == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.applicationFee == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="evaluationForm.label.applicationForm" /></dt>
		  <dd>
		  	<c:if test="${userEntity.evaluation.applicationForm == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.applicationForm == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.applicationForm == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="evaluationForm.label.enrollmentAgreement" /></dt>
		  <dd>
		  	<c:if test="${userEntity.evaluation.enrollmentAgreement == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.enrollmentAgreement == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.enrollmentAgreement == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="evaluationForm.label.grievancePolicy" /></dt>
		  <dd>
		  	<c:if test="${userEntity.evaluation.grievancePolicy == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.grievancePolicy == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.grievancePolicy == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="evaluationForm.label.recommendationLetter" /></dt>
		  <dd>
		  	<c:if test="${userEntity.evaluation.recommendationLetter == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.recommendationLetter == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.recommendationLetter == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="evaluationForm.label.transcript" /></dt>
		  <dd>
		  	<c:if test="${userEntity.evaluation.transcript == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.transcript == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.transcript == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="evaluationForm.label.diplome" /></dt>
		  <dd>
		  	<c:if test="${userEntity.evaluation.diplome == \"incomplete\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.diplome == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.diplome == \"complete\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Complete
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt><spring:message code="evaluationForm.label.sourceOfMoney" /></dt>
		  <dd><c:out value="${userEntity.evaluation.sourceOfMoney}"></c:out></dd>
		  
		  <dt><spring:message code="evaluationForm.label.amountOfMoney" /></dt>
		  <dd><c:out value="${userEntity.evaluation.amountOfMoney}"></c:out></dd>
		  
		</dl>
		
		<!-- <div class="col-md-6">
			<a href="#" class="btn btn-primary btn-md" target="_blank">Print Checkllist</a>
			<br/><br/>
			<a href="#" class="btn btn-primary btn-md" target="_blank">Print Missing Documents</a>
			<br/><br/>
			<a href="#" class="btn btn-primary btn-md" target="_blank">Email Student Missing Documents</a>
			<br/><br/>
			
		</div> -->
			
		</div>
		<!-- * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |  -->
		
		<hr style="border:2px solid #A4A4A4;">
		
		<h1>Applicant Evaluation</h1>
		
		<div class="row">
		
		<!-- <dl class="dl-horizontal col-md-6"> -->
		<dl class="dl-horizontal">
		  
		  <dt><spring:message code="evaluationForm.label.admnOfficerReport" /></dt>
		  <dd><c:out value="${userEntity.evaluation.admnOfficerReport}"></c:out></dd>
		  
		  <dt><spring:message code="evaluationForm.label.admissionOfficer" /></dt>
		  <dd><c:out value="${admissionOfficerName}"></c:out></dd>
		  
		  <dt><spring:message code="evaluationForm.label.dateCreated" /></dt>
		  <dd>
		  	<fmt:formatDate var="dateCreatedString" value="${userEntity.evaluation.dateCreated}" pattern="MM-dd-yyyy" />
			<c:out value="${dateCreatedString}" />
		  	<%-- <c:out value="${userEntity.evaluation.dateCreated}"></c:out> --%>
		  </dd>
		  
		  <dt><spring:message code="evaluationForm.label.createdBy" /></dt>
		  <dd><c:out value="${userEntity.evaluation.createdBy}"></c:out></dd>
		  
		  <dt><spring:message code="evaluationForm.label.dateLastModified" /></dt>
		  <dd>
		  	<fmt:formatDate var="dateLastModifiedString" value="${userEntity.evaluation.dateLastModified}" pattern="MM-dd-yyyy" />
			<c:out value="${dateLastModifiedString}" />
		  </dd>
		  
		  <dt><spring:message code="evaluationForm.label.lastModifiedBy" /></dt>
		  <dd><c:out value="${userEntity.evaluation.lastModifiedBy}"></c:out></dd>
		  
		  <dt><spring:message code="evaluationForm.label.notes" /></dt>
		  <dd><c:out value="${userEntity.evaluation.notes}"></c:out></dd>
		  
		  
		</dl>
		<%-- <div class="col-md-6">
			<a href="${getEvaluationReport}" class="btn btn-primary btn-md" target="_blank">Print Evaluation</a>
			
		</div> --%>
			
		</div>
		
		<br />
		<br />
		
		<!-- Evaluation can't be deleted by any user.  -->
		<a href="${editEvaluationUrl}" class="btn btn-primary btn-sm">Edit Checkist & Evaluation</a>
		<br />
		<br />	
		
		<!-- * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |  -->
		
		<hr style="border:2px solid #A4A4A4;">
		
		<%-- <c:if test="${userEntity.accountState == \"admitted\"}">
	  		Prospective student is admitted
	  	</c:if>
		<c:if test="${userEntity.accountState == \"denied\"}">
	  		Prospective student admission is denied. Restart application.
	  	</c:if> --%>
			
		<c:choose>
			<c:when test="${userEntity.accountState == 'admitted'}">
				Prospective student application for admission is GRANTED.
			</c:when>
			<c:when test="${userEntity.accountState == 'denied'}">
				Prospective student application for admission is DENIED.
			</c:when>
			<c:otherwise>
				<form id="grantAdmisionForm" action="${grantAdmisionUrl}" method="post">
					<div>
						<input type="submit" class="btn btn-success btn-sm" value="Grant Admision">
					</div>
				</form>
				<br/><br/>
				<form id="grantAdmisionForm" action="${denyAdmisionUrl}" method="post">
					<div>
						<input type="submit" class="btn btn-danger btn-sm" value="Deny Admision">
					</div>
				</form>
			</c:otherwise>
			<%-- <c:when test="${userEntity.accountState != 'admitted'}">
				<form id="grantAdmisionForm" action="${grantAdmisionUrl}" method="post">
					<div>
						<input type="submit" class="btn btn-success btn-sm" value="Grant Admision">
					</div>
				</form>
				<br/><br/>
				<form id="grantAdmisionForm" action="${denyAdmisionUrl}" method="post">
					<div>
						<input type="submit" class="btn btn-danger btn-sm" value="Deny Admision">
					</div>
				</form>
			</c:when>
			<c:otherwise>Prospective student is admitted</c:otherwise> --%>
		</c:choose>
		
	</c:otherwise>
</c:choose>

<br />


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