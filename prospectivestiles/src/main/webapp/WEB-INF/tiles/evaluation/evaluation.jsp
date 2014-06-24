<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


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

<h1>Evaluation</h1>


<c:if test="${param.deleted == true}">
	<div class="info alert">Evaluation deleted.</div>
</c:if>

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<!-- evaluationUrl name is already used. look up -->
	<%-- <c:url var="evaluationUrl"	value="/accounts/${userEntity.id}/evaluation/${userEntity.evaluation.id}" /> --%>
	<c:url var="newEvaluationUrl" value="/accounts/${userEntity.id}/evaluation/new" />
	<c:url var="editEvaluationUrl" value="/accounts/${userEntity.id}/evaluation/${userEntity.evaluation.id}/edit" />
	<%-- <c:url var="deleteEvaluationUrl" value="/accounts/${userEntity.id}/evaluation/${userEntity.evaluation.id}/delete" /> --%>
	<c:url var="grantAdmisionUrl" value="/accounts/${userEntity.id}/evaluation/${userEntity.evaluation.id}/grantAdmision" />
</sec:authorize>
<!-- Delete this student doesn't have the evaluations page -->
<%-- <sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="evaluationUrl"	value="/myAccount/evaluation/${userEntity.evaluation.id}" />
	<c:url var="newEvaluationUrl" value="/myAccount/evaluation/new" />
	<c:url var="editEvaluationUrl" value="/myAccount/evaluation/${userEntity.evaluation.id}/edit" />
	<c:url var="deleteEvaluationUrl" value="/myAccount/evaluation/${userEntity.evaluation.id}/delete" />
</sec:authorize> --%>
		
<c:choose>
	<c:when test="${empty evaluations}">
		<p>No Evaluation.</p>
		<!-- Button trigger modal -->
		<!-- <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#addEvaluationModal">
		  Create Evaluation
		</button> -->
		<h3>
			<a href="${newEvaluationUrl}">Create Evaluation</a>
		</h3>
	</c:when>
	<c:otherwise>
				
		<dl class="dl-horizontal">
		  <dt>Id</dt>
		  <dd><c:out value="${userEntity.evaluation.id}"></c:out></dd>
		  
		  <dt>f1Visa</dt>
		  <dd>
		  	<c:if test="${userEntity.evaluation.f1Visa == \"notreviewed\"}">
		  		<span class="gray"> 
		  			Not Reviewed
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.f1Visa == \"incomplete\"}">
		  		<span class="orange"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.f1Visa == \"invalid\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Invalid
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.f1Visa == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.f1Visa == \"valid\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Valid
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt>bankStmt</dt>
		  <%-- <dd><c:out value="${userEntity.evaluation.bankStmt}"></c:out></dd> --%>
		  <dd>
		  	<c:if test="${userEntity.evaluation.bankStmt == \"notreviewed\"}">
		  		<span class="gray"> 
		  			Not Reviewed
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.bankStmt == \"incomplete\"}">
		  		<span class="orange"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.bankStmt == \"invalid\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Invalid
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.bankStmt == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.bankStmt == \"valid\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Valid
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt>i20</dt>
		  <%-- <dd><c:out value="${userEntity.evaluation.i20}"></c:out></dd> --%>
		  <dd>
		  	<c:if test="${userEntity.evaluation.i20 == \"notreviewed\"}">
		  		<span class="gray"> 
		  			Not Reviewed
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.i20 == \"incomplete\"}">
		  		<span class="orange"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.i20 == \"invalid\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Invalid
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.i20 == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.i20 == \"valid\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Valid
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt>passport</dt>
		  <dd>
		  	<c:if test="${userEntity.evaluation.passport == \"notreviewed\"}">
		  		<span class="gray"> 
		  			Not Reviewed
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.passport == \"incomplete\"}">
		  		<span class="orange"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.passport == \"invalid\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Invalid
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.passport == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.passport == \"valid\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Valid
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt>financialAffidavit</dt>
		  <dd>
		  	<c:if test="${userEntity.evaluation.financialAffidavit == \"notreviewed\"}">
		  		<span class="gray"> 
		  			Not Reviewed
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.financialAffidavit == \"incomplete\"}">
		  		<span class="orange"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.financialAffidavit == \"invalid\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Invalid
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.financialAffidavit == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.financialAffidavit == \"valid\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Valid
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt>applicationFee</dt>
		  <dd>
		  	<c:if test="${userEntity.evaluation.applicationFee == \"notreviewed\"}">
		  		<span class="gray"> 
		  			Not Reviewed
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.applicationFee == \"incomplete\"}">
		  		<span class="orange"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.applicationFee == \"invalid\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Invalid
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.applicationFee == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.applicationFee == \"valid\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Valid
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt>transcript</dt>
		  <dd>
		  	<c:if test="${userEntity.evaluation.transcript == \"notreviewed\"}">
		  		<span class="gray"> 
		  			Not Reviewed
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.transcript == \"incomplete\"}">
		  		<span class="orange"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.transcript == \"invalid\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Invalid
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.transcript == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.transcript == \"valid\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Valid
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt>diplome</dt>
		  <dd>
		  	<c:if test="${userEntity.evaluation.diplome == \"notreviewed\"}">
		  		<span class="gray"> 
		  			Not Reviewed
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.diplome == \"incomplete\"}">
		  		<span class="orange"> 
		  			Incomplete
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.diplome == \"invalid\"}">
		  		<span class="red glyphicon glyphicon-remove"> 
		  			Invalid
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.diplome == \"notrequired\"}">
		  		<span class="green"> 
		  			Not Required
		  		</span>
		  	</c:if>
		  	<c:if test="${userEntity.evaluation.diplome == \"valid\"}">
		  		<span class="green glyphicon glyphicon-ok"> 
		  			Valid
		  		</span>
		  	</c:if>
		  </dd>
		  
		  <dt>studentQualification</dt>
		  <dd><c:out value="${userEntity.evaluation.studentQualification}"></c:out></dd>
		  
		  <dt>admnOfficerReport</dt>
		  <dd><c:out value="${userEntity.evaluation.admnOfficerReport}"></c:out></dd>
		  
		  <dt>admissionOfficer</dt>
		  <dd><c:out value="${userEntity.evaluation.admissionOfficer}"></c:out></dd>
		  
		  <dt>dateCreated</dt>
		  <dd><c:out value="${userEntity.evaluation.dateCreated}"></c:out></dd>
		  
		  <dt>dateLastModified</dt>
		  <dd><c:out value="${userEntity.evaluation.dateLastModified}"></c:out></dd>
		  
		  <dt>Remarks/notes</dt>
		  <dd><c:out value="${userEntity.evaluation.notes}"></c:out></dd>
		  
		  
		</dl>
		
		<br />
		<br />
		
		<!-- Evaluation can't be deleted by any user.  -->
		<%-- <a data-toggle="modal" href="${editEvaluationUrl}" data-target="#editEvaluationModal" 
			class="btn btn-primary btn-lg">Edit</a> --%>
		<a href="${editEvaluationUrl}" class="btn btn-primary btn-lg">Edit</a>
		<br />
		<br />	
		<%-- <form id="deleteForm" action="${deleteEvaluationUrl}" method="post">
			<div><input type="submit" value="DELETE" /></div>
		</form> --%>
		
		<br />	
		<c:choose>
			<c:when test="${userEntity.evaluation.status != 'admitted'}">
				<form id="grantAdmisionForm" action="${grantAdmisionUrl}" method="post">
					<div><input type="submit" value="grantAdmision" /></div>
				</form>
			</c:when>
			<c:otherwise>Prospective student already admitted</c:otherwise>
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