<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message var="registerLabel" code="newUserRegistration.label.register" />

<h1>Registration form</h1>

<!-- ########################################## -->

<form:form action="registrationform" modelAttribute="userEntity" role="form">

	<p><spring:message code="newUserRegistration.message.allFieldsRequired" /></p>
	
	<div class="form-group row">
		<label for="username" class="col-sm-2 control-label"><spring:message code="newUserRegistration.label.username" /></label>
	    <div class="col-sm-5">
	      <form:input path="username" class="form-control" placeholder = "Your Username" />
	    </div>
	    <div class="col-sm-5">
	    	<form:errors path="username" htmlEscape="false" />
	      	<%-- <form:errors path="username">
				<div class="errorMessage"><form:errors path="username" htmlEscape="false" /></div>
			</form:errors> --%>
	    </div>
	</div>
	
	<div class="form-group row">
		<label for="password" class="col-sm-2 control-label"><spring:message code="newUserRegistration.label.password" /></label>
	    <div class="col-sm-5">
	      <form:password path="password" class="form-control" placeholder = "Your password" />
	    </div>
	    <div class="col-sm-5">
	    	<form:errors path="password" cssClass="error" element="div" />
	    </div>
	</div>
	
	<div class="form-group row">
		<label for="confirmPassword" class="col-sm-2 control-label"><spring:message code="newUserRegistration.label.confirmPassword" /></label>
	    <div class="col-sm-5">
	      <form:password path="confirmPassword" class="form-control" placeholder = "Confirm your password" />
	    </div>
	    <%-- <div class="col-sm-5">
	    	<form:errors path="confirmPassword" cssClass="error" element="div" />
	    </div> --%>
	</div>
	
	<div class="form-group row">
		<label for="firstName" class="col-sm-2 control-label"><spring:message code="newUserRegistration.label.firstName" /></label>
	    <div class="col-sm-5">
	      <form:input path="firstName" class="form-control" placeholder = "Your First Name" />
	    </div>
	    <div class="col-sm-5">
	    	<form:errors path="firstName" htmlEscape="false" />
	    </div>
	</div>
	
	<div class="form-group row">
		<label for="lastName" class="col-sm-2 control-label"><spring:message code="newUserRegistration.label.lastName" /></label>
	    <div class="col-sm-5">
	      <form:input path="lastName" class="form-control" placeholder = "Your Last Name" />
	    </div>
	    <div class="col-sm-5">
	      	<form:errors path="lastName" htmlEscape="false" />
	    </div>
	</div>
	
	<div class="form-group row">
		<label for="email" class="col-sm-2 control-label"><spring:message code="newUserRegistration.label.email" /></label>
	    <div class="col-sm-5">
	      <form:input path="email" class="form-control" placeholder = "Your Email" />
	    </div>
	    <div class="col-sm-5">
	      	<form:errors path="email" htmlEscape="false" />
	    </div>
	</div>
	
	<div class="form-group checkbox row">
	    <div class="col-sm-2">
	    	<form:checkbox class="form-control" id="marketingOk" path="marketingOk" />
	    </div>
		<label for="marketingOk" class="col-sm-5 control-label"><spring:message code="newUserRegistration.label.marketingOk" /></label>
	    <div class="col-sm-5">
	      	<%-- <form:errors path="marketingOk" htmlEscape="false" /> --%>
	    </div>
	</div>
	
	<div class="form-group checkbox row">
	    <div class="col-sm-2">
	    	<form:checkbox class="form-control" id="acceptTerms" path="acceptTerms" />
	    </div>
		<label for="acceptTerms" class="col-sm-5 control-label"><spring:message code="newUserRegistration.label.acceptTerms" /></label>
	    <div class="col-sm-5">
	      	<form:errors path="acceptTerms" htmlEscape="false" />
	    </div>
	</div>
	
	
       <div class = "form-group row">
	       	<label for="privacyPolicy" class="col-sm-2 control-label">&nbsp;</label>
	       	<div class = "col-sm-10" id="privacyPolicy">
	       		<spring:message code="newUserRegistration.label.privacyPolicy" />
	       	</div>
       </div>
       
       <div class = "form-group row">
	       	<label class="col-sm-2 control-label">&nbsp;</label>
	       	<div class = "col-sm-10">
	       		<input class="btn btn-default" type="submit" value="${registerLabel}"></input>
	       	</div>
       </div>
       
       
</form:form>


<!-- ################### PREVIOUS FORM ####################### -->

		
<%-- <form:form cssClass="main" action="registrationform" modelAttribute="userEntity">
	
	<p><spring:message code="newUserRegistration.message.allFieldsRequired" /></p>
	
	<div class="panel grid">
			
			<div class="fieldLabel yui-u first"><spring:message code="newUserRegistration.label.username" /></div>
			<div class="yui-u">
				<form:input path="username" cssClass="short" cssErrorClass="short error" />
				<form:errors path="username">
					<div class="errorMessage"><form:errors path="username" htmlEscape="false" /></div>
				</form:errors>
			</div>
			
			<div class="fieldLabel yui-u first"><spring:message code="newUserRegistration.label.password" /></div>
			<div class="yui-u">
				<form:password path="password" showPassword="false" cssClass="short" cssErrorClass="short error" />
				<form:errors path="password">
					<div class="errorMessage"><form:errors path="password" htmlEscape="false" /></div>
				</form:errors>
			</div>
			
			<div class="fieldLabel yui-u first"><spring:message code="newUserRegistration.label.firstName" /></div>
			<div class="yui-u">
				<form:input path="firstName" cssClass="short" cssErrorClass="short error" />
				<form:errors path="firstName">
					<div class="errorMessage"><form:errors path="firstName" htmlEscape="false" /></div>
				</form:errors>
			</div>
			
			<div class="fieldLabel yui-u first"><spring:message code="newUserRegistration.label.lastName" /></div>
			<div class="yui-u">
				<form:input path="lastName" cssClass="short" cssErrorClass="short error" />
				<form:errors path="lastName">
					<div class="errorMessage"><form:errors path="lastName" htmlEscape="false" /></div>
				</form:errors>
			</div>
			
			<div class="fieldLabel yui-u first"><spring:message code="newUserRegistration.label.email" /></div>
			<div class="yui-u">
				<form:input path="email" cssClass="medium" cssErrorClass="medium error" />
				<form:errors path="email">
					<div class="errorMessage"><form:errors path="email" htmlEscape="false" /></div>
				</form:errors>
			</div>
			
			<div class="yui-u first"></div>
			<div class="yui-u">
				<form:checkbox id="marketingOk" path="marketingOk" />
				<label for="marketingOk"><spring:message code="newUserRegistration.label.marketingOk" /></label>
			</div>
			
			<div class="yui-u first"></div>
			<div class="yui-u">
				<div>
					<form:checkbox id="acceptTerms" path="acceptTerms" cssErrorClass="error" />
					<label for="acceptTerms"><spring:message code="newUserRegistration.label.acceptTerms" /></label>
				</div>
				<form:errors path="acceptTerms">
					<div class="errorMessage"><form:errors path="acceptTerms" htmlEscape="false" /></div>
				</form:errors>
			</div>
			
			<div class="yui-u first"></div>
			<div class="yui-u">
				<spring:message code="newUserRegistration.label.privacyPolicy" />
			</div>
			
			<div class="yui-u first"></div>
			<div class="yui-u"><input type="submit" value="${registerLabel}"></input></div>
	</div>
</form:form> --%>
