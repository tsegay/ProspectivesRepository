<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="checklistsUrl" value="/accounts/${checklist.userEntity.id}/checklists" />
	<c:url var="editChecklistUrl" value="/accounts/${checklist.userEntity.id}/checklist/${checklist.id}" />
</sec:authorize>
<%-- <sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="evaluationsUrl" value="/myAccount/evaluations" />
	<c:url var="editChecklistUrl" value="/myAccount/evaluation/${userEntity.id}" />
</sec:authorize> --%>

<h1>Edit Checklist</h1>

<form:form action="${editChecklistUrl}" modelAttribute="checklist"
	role="form" class="form-horizontal">


	<div class="form-group row">
		<!-- <label for="f1Visa" class="col-sm-2 control-label">f1Visa</label> -->
		<label for="f1Visa" class="col-sm-2 control-label">
			<spring:message code="checklist.label.f1Visa" />
		</label>
		<div class="col-sm-5">
			<form:select path="f1Visa" class="form-control">
				<%-- <form:option value="NONE" label="--- Select ---" /> --%>
				<form:option value="incomplete" label="Incomplete" />
				<form:option value="notrequired" label="Not Required" />
				<form:option value="complete" label="Complete" />
			</form:select>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="f1Visa" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<!-- <label for="bankStmt" class="col-sm-2 control-label">bankStmt</label> -->
		<label for="bankStmt" class="col-sm-2 control-label">
			<spring:message code="checklist.label.bankStmt" />
		</label>
		<div class="col-sm-5">
			<form:select path="bankStmt" class="form-control">
				<%-- <form:option value="NONE" label="--- Select ---" /> --%>
				<form:option value="incomplete" label="Incomplete" />
				<form:option value="notrequired" label="Not Required" />
				<form:option value="complete" label="Complete" />
			</form:select>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="bankStmt" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<!-- <label for="i20" class="col-sm-2 control-label">i20</label> -->
		<label for="i20" class="col-sm-2 control-label">
			<spring:message code="checklist.label.i20" />
		</label>
		<div class="col-sm-5">
			<form:select path="i20" class="form-control">
				<%-- <form:option value="NONE" label="--- Select ---" /> --%>
				<form:option value="incomplete" label="Incomplete" />
				<form:option value="notrequired" label="Not Required" />
				<form:option value="complete" label="Complete" />
			</form:select>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="i20" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<!-- <label for="passport" class="col-sm-2 control-label">passport</label> -->
		<label for="passport" class="col-sm-2 control-label">
			<spring:message code="checklist.label.passport" />
		</label>
		<div class="col-sm-5">
			<form:select path="passport" class="form-control">
				<form:option value="incomplete" label="Incomplete" />
				<form:option value="notrequired" label="Not Required" />
				<form:option value="complete" label="Complete" />
			</form:select>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="passport" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<!-- <label for="financialAffidavit" class="col-sm-2 control-label">financialAffidavit</label> -->
		<label for="financialAffidavit" class="col-sm-2 control-label">
			<spring:message code="checklist.label.financialAffidavit" />
		</label>
		<div class="col-sm-5">
			<form:select path="financialAffidavit" class="form-control">
				<form:option value="incomplete" label="Incomplete" />
				<form:option value="notrequired" label="Not Required" />
				<form:option value="complete" label="Complete" />
			</form:select>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="financialAffidavit" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<!-- <label for="applicationFee" class="col-sm-2 control-label">applicationFee</label> -->
		<label for="applicationFee" class="col-sm-2 control-label">
			<spring:message code="checklist.label.applicationFee" />
		</label>
		<div class="col-sm-5">
			<form:select path="applicationFee" class="form-control">
				<form:option value="incomplete" label="Incomplete" />
				<form:option value="notrequired" label="Not Required" />
				<form:option value="complete" label="Complete" />
			</form:select>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="applicationFee" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<!-- <label for="transcript" class="col-sm-2 control-label">transcript</label> -->
		<label for="transcript" class="col-sm-2 control-label">
			<spring:message code="checklist.label.transcript" />
		</label>
		<div class="col-sm-5">
			<form:select path="transcript" class="form-control">
				<form:option value="incomplete" label="Incomplete" />
				<form:option value="notrequired" label="Not Required" />
				<form:option value="complete" label="Complete" />
			</form:select>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="transcript" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<!-- <label for="diplome" class="col-sm-2 control-label">diplome</label> -->
		<label for="diplome" class="col-sm-2 control-label">
			<spring:message code="checklist.label.diplome" />
		</label>
		<div class="col-sm-5">
			<form:select path="diplome" class="form-control">
				<form:option value="incomplete" label="Incomplete" />
				<form:option value="notrequired" label="Not Required" />
				<form:option value="complete" label="Complete" />
			</form:select>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="diplome" htmlEscape="false" />
		</div>
	</div>


	<div class="form-group row">
		<!-- <label for="notes" class="col-sm-2 control-label">Remarks/notes</label> -->
		<label for="notes" class="col-sm-2 control-label">
			<spring:message code="checklist.label.notes" />
		</label>
		<div class="col-sm-5">
			<form:textarea path="notes" class="form-control" rows="5" cols="30" />
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="notes" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group">
		<label for="" class="col-sm-2 control-label">&nbsp;</label>
		<div class="col-sm-10">
			<input class="btn btn-primary" type="submit" value="Save"></input> <a
				class="btn btn-default" href="${checklistsUrl}">Cancel</a>
		</div>
	</div>

</form:form>

<%-- <h1>editChecklist.jsp</h1>
<form:form action="${editChecklistUrl}"
	modelAttribute="checklist" role="form" class="form-horizontal">
	<div class="modal-header">
		<h4>Edit checklist</h4>
		checklist:
		<c:if test="${checklist.id > 0}">
			<c:out value="${checklist.id}" />
		</c:if>
		<br /> checklist.userEntity.id:
		<c:if test="${checklist.id > 0}">
			<c:out value="${checklist.userEntity.id}" />
		</c:if>
	</div>
	<div class="modal-body">

		<div class="form-group checkbox row">
		    <div class="col-sm-2">
		    	<form:checkbox class="form-control" id="f1Visa" path="f1Visa" />
		    </div>
			<label for="f1Visa" class="col-sm-5 control-label">f1Visa</label>
		    <div class="col-sm-5">
		      	<form:errors class="errormsg" path="f1Visa" htmlEscape="false" />
		    </div>
		</div>
          	
       	<div class="form-group checkbox row">
		    <div class="col-sm-2">
		    	<form:checkbox class="form-control" id="bankStmt" path="bankStmt" />
		    </div>
			<label for="bankStmt" class="col-sm-5 control-label">bankStmt</label>
		    <div class="col-sm-5">
		      	<form:errors class="errormsg" path="bankStmt" htmlEscape="false" />
		    </div>
		</div>
          	
        <div class="form-group checkbox row">
		    <div class="col-sm-2">
		    	<form:checkbox class="form-control" id="i20" path="i20" />
		    </div>
			<label for="i20" class="col-sm-5 control-label">i20</label>
		    <div class="col-sm-5">
		      	<form:errors class="errormsg" path="i20" htmlEscape="false" />
		    </div>
		</div>
		
		<div class="form-group checkbox row">
		    <div class="col-sm-2">
		    	<form:checkbox class="form-control" id="passport" path="passport" />
		    </div>
			<label for="passport" class="col-sm-5 control-label">passport</label>
		    <div class="col-sm-5">
		      	<form:errors class="errormsg" path="passport" htmlEscape="false" />
		    </div>
		</div>
          	
      	<div class="form-group checkbox row">
		<div class="col-sm-2">
		    	<form:checkbox class="form-control" id="financialAffidavit" path="financialAffidavit" />
		    </div>
			<label for="financialAffidavit" class="col-sm-5 control-label">financialAffidavit</label>
		    <div class="col-sm-5">
		      	<form:errors class="errormsg" path="financialAffidavit" htmlEscape="false" />
		    </div>
		</div>
          	
       	<div class="form-group checkbox row">
		<div class="col-sm-2">
		    	<form:checkbox class="form-control" id="applicationFee" path="applicationFee" />
		    </div>
			<label for="applicationFee" class="col-sm-5 control-label">applicationFee</label>
		    <div class="col-sm-5">
		      	<form:errors class="errormsg" path="applicationFee" htmlEscape="false" />
		    </div>
		</div>
          	
        <div class="form-group checkbox row">
		    <div class="col-sm-2">
		    	<form:checkbox class="form-control" id="transcript" path="transcript" />
		    </div>
			<label for="transcript" class="col-sm-5 control-label">transcript</label>
		    <div class="col-sm-5">
		      	<form:errors class="errormsg" path="transcript" htmlEscape="false" />
		    </div>
		</div>
          	
        <div class="form-group checkbox row">
		    <div class="col-sm-2">
		    	<form:checkbox class="form-control" id="diplome" path="diplome" />
		    </div>
			<label for="diplome" class="col-sm-5 control-label">diplome</label>
		    <div class="col-sm-5">
		      	<form:errors class="errormsg" path="diplome" htmlEscape="false" />
		    </div>
		</div>
          	
        <div class="form-group row">
			<label for="notes" class="col-sm-2 control-label">notes</label>
		    <div class="col-sm-5">
		    	<form:textarea path="notes" class="form-control" rows="5" cols="30" />
		    </div>
		    <div class="col-sm-5">
		    	<form:errors class="errormsg" path="notes" htmlEscape="false" />
		    </div>
		</div>
					
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal">Cancel</a> <input
			class="btn btn-primary" type="submit" value="Submit"></input>
	</div>
</form:form> --%>
