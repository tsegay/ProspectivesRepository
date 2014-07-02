<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%-- <c:set var="user" value="${evaluation.userEntity}" /> --%>

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="evaluationsUrl" value="/accounts/${userEntity.id}/evaluations" />
	<c:url var="editEvaluationUrl" value="/accounts/${userEntity.id}/evaluation/${evaluation.id}" />
	<c:url var="deleteEvaluationUrl" value="/accounts/${userEntity.id}/evaluation/${evaluation.id}/delete" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="evaluationsUrl" value="/myAccount/evaluations" />
	<c:url var="editEvaluationUrl"
		value="/myAccount/evaluation/${evaluation.id}" />
	<c:url var="deleteEvaluationUrl"
		value="/myAccount/evaluation/${evaluation.id}/delete" />
</sec:authorize>

<h1>Edit Evaluation</h1>

<form:form action="${editEvaluationUrl}" modelAttribute="evaluation"
	role="form" class="form-horizontal">


	<div class="form-group row">
		<!-- <label for="f1Visa" class="col-sm-2 control-label">f1Visa</label> -->
		<label for="f1Visa" class="col-sm-2 control-label">
			<spring:message code="evaluationForm.label.f1Visa" />
		</label>
		<div class="col-sm-5">
			<form:select path="f1Visa" class="form-control">
				<%-- <form:option value="NONE" label="--- Select ---" /> --%>
				<form:option value="notreviewed" label="Not Reviewed" />
				<form:option value="valid" label="Valid" />
				<form:option value="notrequired" label="Not Required" />
				<form:option value="invalid" label="Invalid" />
				<form:option value="incomplete" label="Incomplete" />
			</form:select>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="f1Visa" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="bankStmt" class="col-sm-2 control-label">
			<spring:message code="evaluationForm.label.bankStmt" />
		</label>
		<div class="col-sm-5">
			<form:select path="bankStmt" class="form-control">
				<%-- <form:option value="NONE" label="--- Select ---" /> --%>
				<form:option value="notreviewed" label="Not Reviewed" />
				<form:option value="valid" label="Valid" />
				<form:option value="notrequired" label="Not Required" />
				<form:option value="invalid" label="Invalid" />
				<form:option value="incomplete" label="Incomplete" />
			</form:select>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="bankStmt" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="i20" class="col-sm-2 control-label">
			<spring:message code="evaluationForm.label.i20" />
		</label>
		<div class="col-sm-5">
			<form:select path="i20" class="form-control">
				<%-- <form:option value="NONE" label="--- Select ---" /> --%>
				<form:option value="notreviewed" label="Not Reviewed" />
				<form:option value="valid" label="Valid" />
				<form:option value="notrequired" label="Not Required" />
				<form:option value="invalid" label="Invalid" />
				<form:option value="incomplete" label="Incomplete" />
			</form:select>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="i20" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="passport" class="col-sm-2 control-label">
			<spring:message code="evaluationForm.label.passport" />
		</label>
		<div class="col-sm-5">
			<form:select path="passport" class="form-control">
				<form:option value="notreviewed" label="Not Reviewed" />
				<form:option value="valid" label="Valid" />
				<form:option value="notrequired" label="Not Required" />
				<form:option value="invalid" label="Invalid" />
				<form:option value="incomplete" label="Incomplete" />
			</form:select>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="passport" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="financialAffidavit" class="col-sm-2 control-label">
			<spring:message code="evaluationForm.label.financialAffidavit" />
		</label>
		<div class="col-sm-5">
			<form:select path="financialAffidavit" class="form-control">
				<form:option value="notreviewed" label="Not Reviewed" />
				<form:option value="valid" label="Valid" />
				<form:option value="notrequired" label="Not Required" />
				<form:option value="invalid" label="Invalid" />
				<form:option value="incomplete" label="Incomplete" />
			</form:select>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="financialAffidavit" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="applicationFee" class="col-sm-2 control-label">
			<spring:message code="evaluationForm.label.applicationFee" />
		</label>
		<div class="col-sm-5">
			<form:select path="applicationFee" class="form-control">
				<form:option value="notreviewed" label="Not Reviewed" />
				<form:option value="valid" label="Valid" />
				<form:option value="notrequired" label="Not Required" />
				<form:option value="invalid" label="Invalid" />
				<form:option value="incomplete" label="Incomplete" />
			</form:select>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="applicationFee" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="transcript" class="col-sm-2 control-label">
			<spring:message code="evaluationForm.label.transcript" />
		</label>
		<div class="col-sm-5">
			<form:select path="transcript" class="form-control">
				<form:option value="notreviewed" label="Not Reviewed" />
				<form:option value="valid" label="Valid" />
				<form:option value="notrequired" label="Not Required" />
				<form:option value="invalid" label="Invalid" />
				<form:option value="incomplete" label="Incomplete" />
			</form:select>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="transcript" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="diplome" class="col-sm-2 control-label">
			<spring:message code="evaluationForm.label.diplome" />
		</label>
		<div class="col-sm-5">
			<form:select path="diplome" class="form-control">
				<form:option value="notreviewed" label="Not Reviewed" />
				<form:option value="valid" label="Valid" />
				<form:option value="notrequired" label="Not Required" />
				<form:option value="invalid" label="Invalid" />
				<form:option value="incomplete" label="Incomplete" />
			</form:select>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="diplome" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="studentQualification" class="col-sm-2 control-label">
			<spring:message code="evaluationForm.label.studentQualification" />
		</label>
		<div class="col-sm-5">
			<form:textarea path="studentQualification" class="form-control"
				rows="5" cols="30" />
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="studentQualification" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="admnOfficerReport" class="col-sm-2 control-label">
			<spring:message code="evaluationForm.label.admnOfficerReport" />
		</label>
		<div class="col-sm-5">
			<form:textarea path="admnOfficerReport" class="form-control" rows="5"
				cols="30" />
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="admnOfficerReport" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="notes" class="col-sm-2 control-label">
			<spring:message code="evaluationForm.label.notes" />
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
				class="btn btn-default" href="${evaluationsUrl}">Cancel</a>
		</div>
	</div>

</form:form>
