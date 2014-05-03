<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



<h1>editChecklist.jsp</h1>
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
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal">Cancel</a> <input
			class="btn btn-primary" type="submit" value="Submit"></input>
	</div>
</form:form>
