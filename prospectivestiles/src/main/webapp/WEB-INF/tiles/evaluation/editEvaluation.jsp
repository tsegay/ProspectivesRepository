<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



<h1>editEvaluation.jsp</h1>
<form:form action="${editEvaluationUrl}"
	modelAttribute="evaluation" role="form" class="form-horizontal">
	<div class="modal-header">
		<h4>Edit evaluation</h4>
		evaluation:
		<c:if test="${evaluation.id > 0}">
			<c:out value="${evaluation.id}" />
		</c:if>
		<br /> evaluation.userEntity.id:
		<c:if test="${evaluation.id > 0}">
			<c:out value="${evaluation.userEntity.id}" />
		</c:if>
	</div>
	<div class="modal-body">
			<div class="form-group row">
				<label for="f1Visa" class="col-sm-2 control-label">f1Visa</label>
			    <div class="col-sm-5">
					<form:select path="f1Visa" class="form-control">
						  <%-- <form:option value="NONE" label="--- Select ---" /> --%>
						   <form:option value="notreviewed" label="Not Reviewed" />
						  <form:option value="valid" label="Valid" />
						  <form:option value="invalid" label="Invalid" />
						  <form:option value="incomplete" label="Incomplete" />
				    </form:select>
			    </div>
			    <div class="col-sm-5">
			    	<form:errors path="f1Visa" htmlEscape="false" />
			    </div>
			</div>
           	
           	<div class="form-group row">
				<label for="bankStmt" class="col-sm-2 control-label">bankStmt</label>
			    <div class="col-sm-5">
					<form:select path="bankStmt" class="form-control">
						  <%-- <form:option value="NONE" label="--- Select ---" /> --%>
						   <form:option value="notreviewed" label="Not Reviewed" />
						  <form:option value="valid" label="Valid" />
						  <form:option value="invalid" label="Invalid" />
						  <form:option value="incomplete" label="Incomplete" />
				    </form:select>
			    </div>
			    <div class="col-sm-5">
			    	<form:errors path="bankStmt" htmlEscape="false" />
			    </div>
			</div>
           	
           	<div class="form-group row">
				<label for="i20" class="col-sm-2 control-label">i20</label>
			    <div class="col-sm-5">
					<form:select path="i20" class="form-control">
						  <%-- <form:option value="NONE" label="--- Select ---" /> --%>
						   <form:option value="notreviewed" label="Not Reviewed" />
						  <form:option value="valid" label="Valid" />
						  <form:option value="invalid" label="Invalid" />
						  <form:option value="incomplete" label="Incomplete" />
				    </form:select>
			    </div>
			    <div class="col-sm-5">
			    	<form:errors path="i20" htmlEscape="false" />
			    </div>
			</div>		
			
			<div class="form-group row">
				<label for="passport" class="col-sm-2 control-label">passport</label>
			    <div class="col-sm-5">
					<form:select path="passport" class="form-control">
						   <form:option value="notreviewed" label="Not Reviewed" />
						  <form:option value="valid" label="Valid" />
						  <form:option value="invalid" label="Invalid" />
						  <form:option value="incomplete" label="Incomplete" />
				    </form:select>
			    </div>
			    <div class="col-sm-5">
			    	<form:errors path="passport" htmlEscape="false" />
			    </div>
			</div>
           	
           	<div class="form-group row">
				<label for="financialAffidavit" class="col-sm-2 control-label">financialAffidavit</label>
			    <div class="col-sm-5">
					<form:select path="financialAffidavit" class="form-control">
						   <form:option value="notreviewed" label="Not Reviewed" />
						  <form:option value="valid" label="Valid" />
						  <form:option value="invalid" label="Invalid" />
						  <form:option value="incomplete" label="Incomplete" />
				    </form:select>
			    </div>
			    <div class="col-sm-5">
			    	<form:errors path="financialAffidavit" htmlEscape="false" />
			    </div>
			</div>
           	
           	<div class="form-group row">
				<label for="applicationFee" class="col-sm-2 control-label">applicationFee</label>
			    <div class="col-sm-5">
					<form:select path="applicationFee" class="form-control">
						   <form:option value="notreviewed" label="Not Reviewed" />
						  <form:option value="valid" label="Valid" />
						  <form:option value="invalid" label="Invalid" />
						  <form:option value="incomplete" label="Incomplete" />
				    </form:select>
			    </div>
			    <div class="col-sm-5">
			    	<form:errors path="applicationFee" htmlEscape="false" />
			    </div>
			</div>
           	
           	<div class="form-group row">
				<label for="transcript" class="col-sm-2 control-label">transcript</label>
			    <div class="col-sm-5">
					<form:select path="transcript" class="form-control">
						   <form:option value="notreviewed" label="Not Reviewed" />
						  <form:option value="valid" label="Valid" />
						  <form:option value="invalid" label="Invalid" />
						  <form:option value="incomplete" label="Incomplete" />
				    </form:select>
			    </div>
			    <div class="col-sm-5">
			    	<form:errors path="transcript" htmlEscape="false" />
			    </div>
			</div>
           	
           	<div class="form-group row">
				<label for="diplome" class="col-sm-2 control-label">diplome</label>
			    <div class="col-sm-5">
					<form:select path="diplome" class="form-control">
						   <form:option value="notreviewed" label="Not Reviewed" />
						  <form:option value="valid" label="Valid" />
						  <form:option value="invalid" label="Invalid" />
						  <form:option value="incomplete" label="Incomplete" />
				    </form:select>
			    </div>
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
