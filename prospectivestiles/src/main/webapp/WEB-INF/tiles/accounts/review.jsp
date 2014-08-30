<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<c:url var="getApplicationForm" value="/admin/report/${userEntity.id}/applicationForm" />
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING')">
	<c:url var="certifyInfoProvidedUrl"	value="/myAccount/certifyInfoProvided" />
	<c:url var="getApplicationForm" value="/myAccount/report/applicationForm" />
</sec:authorize>

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


<h1 class="page-header">Review and Accept</h1>

<!-- 
$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
 -->

<h3>Personal Information</h3>

<div class="row">
	<dl class="dl-horizontal col-md-6">
	
		<dt><spring:message code="account.label.id" /></dt>
		<dd>
			<c:out value="${userEntity.id}" />
		</dd>
		
		<dt><spring:message code="account.label.firstName" /></dt>
		<dd>
			<c:out value="${userEntity.firstName}" />
		</dd>
	
		<dt><spring:message code="account.label.middleName" /></dt>
		<dd>
			<c:out value="${userEntity.middleName}" />
		</dd>
	
		<dt><spring:message code="account.label.lastName" /></dt>
		<dd>
			<c:out value="${userEntity.lastName}" />
		</dd>
	
		<dt><spring:message code="account.label.username" /></dt>
		<dd>
			<c:out value="${userEntity.username}" />
		</dd>
	
		<dt><spring:message code="account.label.email" /></dt>
		<dd>
			<a href="mailto:${userEntity.email}">${userEntity.email}</a>
		</dd>
		
		<dt><spring:message code="account.label.marketingOk" /></dt>
		<dd>
			<c:out value="${userEntity.marketingOk}" />
		</dd>
		
		<dt><spring:message code="account.label.acceptTerms" /></dt>
		<dd>
			<c:out value="${userEntity.acceptTerms}" />
		</dd>
		
		<dt><spring:message code="account.label.dateCreated" /></dt>
		<dd>
			<fmt:formatDate var="dateCreatedString" value="${userEntity.dateCreated}" pattern="MM-dd-yyyy" />
			<c:out value="${dateCreatedString}" />
		</dd>
	</dl>
	<dl class="dl-horizontal col-md-6">
	
		<dt><spring:message code="account.label.dob" /></dt>
		<dd>
			<fmt:formatDate var="dobString" value="${userEntity.dob}" pattern="MM-dd-yyyy" />
			<c:out value="${dobString}" />
		</dd>
		
		<dt><spring:message code="account.label.gender" /></dt>
		<dd>
			<c:out value="${userEntity.gender}" />
		</dd>
		
		<dt><spring:message code="account.label.homePhone" /></dt>
		<dd>
			<c:out value="${userEntity.homePhone}" />
		</dd>
		<dt><spring:message code="account.label.cellPhone" /></dt>
		<dd>
			<c:out value="${userEntity.cellPhone}" />
		</dd>
		<dt><spring:message code="account.label.ssn" /></dt>
		<dd>
			<c:out value="${userEntity.ssn}" />
		</dd>
		
		<dt><spring:message code="account.label.citizenship" /></dt>
		<dd>
			<c:out value="${userEntity.citizenship}" />
		</dd>
		
		<dt><spring:message code="account.label.ethnicity" /></dt>
		<dd>
			<c:out value="${userEntity.ethnicity}" />
		</dd>
		
		<dt><spring:message code="account.label.sevisNumber" /></dt>
		<dd>
			<c:out value="${userEntity.sevisNumber}" />
		</dd>
		
	</dl>
</div>

<h3>Addresses</h3>
<c:choose>
	<c:when test="${empty addresses}">
		<p>No Address.</p>
	</c:when>
	<c:otherwise>
		<c:forEach var="address" items="${addresses}">
			<address class="col-md-6">
				<span class="addressType"> <em><c:out
							value="${address.addressType}" /></em>
				</span>
				<hr />
				<c:out value="${address.address1}" />
				<c:if test="${address.address2 != null}">
			  	, <c:out value="${address.address2}" />
					<br>
				</c:if>
				<c:out value="${address.city}" />, <c:out value="${address.state}" /> <c:out value="${address.zipcode}" />
				<br>
				<c:out value="${address.country}" />
				<br><br>
			</address>
		</c:forEach>

	</c:otherwise>
</c:choose>
<!-- I am displaying the addresses in 2 columns,
I need this to push down the content below from mixing with the addresses -->
<div class="row"></div>


<h3>Applying for</h3>

<c:choose>
	<c:when test="${empty userEntity.term}">
		<p>No Term and Program of Study selected.</p>
	</c:when>
	<c:otherwise>
		<dl class="dl-horizontal">
			<dt><spring:message code="applyingFor.label.term" /></dt>
			<dd>
				<c:out value="${userEntity.term.name}"></c:out>
			</dd>
		
			<dt><spring:message code="applyingFor.label.programOfStudy" /></dt>
			<dd>
				<c:out value="${userEntity.programOfStudy.name}"></c:out>
			</dd>
		</dl>
	</c:otherwise>
</c:choose>


<h3>HighSchools</h3>
	
<c:choose>
	<c:when test="${empty highSchools}">
		<p>No High School.</p>
	</c:when>
	<c:otherwise>

		<div class="table-responsive">
			<table class="table table-hover table-striped">
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>State</th>
					<th>Country</th>
					<th>Diplome</th>
					<th>gED</th>
					<!-- diplome
					diplomeAwardedDate
					gEDAwardedDate
					zip
					city
					attendedFrom
					attendedTo -->
				</tr>
	
				<c:forEach var="highSchool" items="${highSchools}">
				
				<tr>
					<td><c:out value="${highSchool.id}"></c:out></td>
					<td><c:out value="${highSchool.name}"></c:out></td>
					<td><c:out value="${highSchool.state}"></c:out></td>
					<td><c:out value="${highSchool.country}"></c:out></td>
					<td><c:out value="${highSchool.diplome}"></c:out></td>
					<td><c:out value="${highSchool.gED}"></c:out></td>
				</tr>
								
				</c:forEach>
	
			</table>
		</div>
	</c:otherwise>
</c:choose>


<h3>Institute</h3>
	
<c:choose>
	<c:when test="${empty institutes}">
		<p>No Institute.</p>
	</c:when>
	<c:otherwise>

		<div class="table-responsive">
			<table class="table table-hover table-striped">
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>programOfStudy</th>
					<th>levelOfStudy</th>
					<th>State</th>
					<th>Country</th>
					<!-- diplome
					diplomeAwardedDate
					gEDAwardedDate
					zip
					city
					attendedFrom
					attendedTo
					graduationDate; -->
				</tr>
	
				<c:forEach var="institute" items="${institutes}">
				
				<tr>
						<td><c:out value="${institute.id}"></c:out></td>
						<td><c:out value="${institute.name}"></c:out></td>
						<td><c:out value="${institute.programOfStudy}"></c:out></td>
						<td><c:out value="${institute.levelOfStudy}"></c:out></td>
						<td><c:out value="${institute.state}"></c:out></td>
						<td><c:out value="${institute.country}"></c:out></td>
					</tr>
				
				</c:forEach>
	
			</table>
		</div>
	</c:otherwise>
</c:choose>


<h3>Standard Test</h3>

<c:choose>
	<c:when test="${empty standardTests}">
		<p>No StandardTest.</p>
	</c:when>
	<c:otherwise>

		<div class="table-responsive">
			<table class="table table-hover table-striped">
				<tr>
					<th>Id</th>
					<th>name</th>
					<th>score</th>
					<th>validTill</th>
				</tr>
	
				<c:forEach var="standardTest" items="${standardTests}">
				<tr>
					<td><c:out value="${standardTest.id}"></c:out>
					</td>
					<td><c:out value="${standardTest.name}"></c:out></td>
					<td><c:out value="${standardTest.score}"></c:out></td>
					<td>
					<fmt:formatDate var="validTillString" value="${standardTest.validTill}" pattern="MM-dd-yyyy" />
					<c:out value="${validTillString}" />
					</td>
				</tr>
								
				</c:forEach>
	
			</table>
		</div>
	</c:otherwise>
</c:choose>

<!-- 
$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
 -->

<br>
<hr>

<c:if test="${studentAgreement.id < 1}">

		<p>
		I certify the information provided in this application is true and accurate to the best of my knowledge. 
		</p>
		<p>
		By typing your full name and clicking on "Submit" in the box below you agree to the statement above.
		</p>
	<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING')">
		
		<form:form action="${certifyInfoProvidedUrl}" modelAttribute="studentAgreement" role="form" class="form-horizontal">
		
			<div class="form-group row">
				<label for="signature" class="col-sm-2 control-label">
					<spring:message code="studentAgreement.label.signature" />
					<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
				</label>
				<div class="col-sm-5">
					<form:input class="form-control" path="signature" />
				</div>
				<div class="col-sm-5">
					<form:errors class="errormsg" path="signature" htmlEscape="false" />
				</div>
			</div>
		
			<div class="form-group">
				<label for="" class="col-sm-2 control-label">&nbsp;</label>
				<div class="col-sm-10">
					<input class="btn btn-primary" type="submit" value="Sign"></input> 
				</div>
			</div>
		
		</form:form>
	</sec:authorize>
</c:if>
<c:if test="${studentAgreement.id > 0}">
	<p>I certify the information provided in this application is true and accurate to the best of my knowledge.</p>
	<p>Name(Signature): <c:out value="${studentAgreement.signature}" /></p>
	<p>Date: <c:out value="${studentAgreement.dateCreated}" /></p>
		
</c:if>


<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING')">
	<a href="${getApplicationForm}" class="btn btn-primary btn-lg" target="_blank">Download application form</a>
</sec:authorize>
<!-- 
$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
 -->
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