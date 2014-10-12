<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<c:url var="editAccountUrl" value="/accounts/${userEntity.id}/edit" />
	<c:url var="createAssociatedUser" value="/accounts/${userEntity.id}/associatedUser/new" />
	<c:url var="editAssociatedUser" value="/accounts/${userEntity.id}/associatedUser/edit" />
	<c:url var="applyingForUrl" value="/accounts/${userEntity.id}/applyingFor" />
	<c:url var="updateAccountState" value="/accounts/${userEntity.id}/updateAccountState" />
	<c:url var="updateAccountState2Enrolled" value="/accounts/${userEntity.id}/updateAccountState2Enrolled" />
	<c:url var="newAddressUrl" value="/accounts/${userEntity.id}/address/new" />
</sec:authorize>

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
			<dt></dt>
			<dd class="text-right">
				<c:choose>
					<c:when test="${(userEntity.accountState == 'enrolled')}">
						<h3 class="red">ENROLLED</h3>
					</c:when>
				</c:choose>
			</dd>
		</dl>
	</div>
</sec:authorize>

<!-- * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |  -->
<!-- * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |  -->

<h3 class="page-header">Applicant Personal Information</h3>

<div class="row">
	<dl class="dl-horizontal col-md-6">
		<dt><spring:message code="account.label.applicantId" /></dt>
		<dd>
			<c:out value="${userEntity.applicantId}" />
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
		
		<dt><spring:message code="account.label.dob" /></dt>
		<dd>
			<fmt:formatDate var="dobString" value="${userEntity.dob}" pattern="MM-dd-yyyy" />
			<c:out value="${dobString}" />
		</dd>
	</dl>
	<dl class="dl-horizontal col-md-6">
		<dt><spring:message code="account.label.gender" /></dt>
		<dd>
			<c:out value="${userEntity.gender}" />
		</dd>
		
		<%-- <dt><spring:message code="account.label." />transferee</dt>
		<dd>
			<c:out value="${userEntity.transferee}" />
		</dd> --%>
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
			<c:out value="${userEntity.citizenship.name}" />
		</dd>
		
		<dt><spring:message code="account.label.ethnicity" /></dt>
		<dd>
			<c:out value="${userEntity.ethnicity}" />
		</dd>
		
		<dt><spring:message code="account.label.internationalLabel" /></dt>
		<dd>
			<c:out value="${userEntity.international}" />
		</dd>
		
		<dt><spring:message code="account.label.transfereeLabel" /></dt>
		<dd>
			<c:out value="${userEntity.transferee}" />
		</dd>
		
		<dt><spring:message code="account.label.sevisNumber" /></dt>
		<dd>
			<c:out value="${userEntity.sevisNumber}" />
		</dd>
		
		<dt><spring:message code="account.label.enabled" /></dt>
		<dd>
			<c:out value="${userEntity.enabled}" />
		</dd>
		
		<dt><spring:message code="account.label.accountState" /></dt>
		<dd>
			<c:out value="${userEntity.accountState}" />
		</dd>
		
		<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
		<dt><spring:message code="account.label.roles" /></dt>
		<dd>
		<c:out value="${userEntity.role.name}" />
			<%-- <c:forEach var="role" items="${userEntity.roles}">
				<c:out value="${role.name}" />
				<br />
			</c:forEach> --%>
		</dd>
		</sec:authorize>
		
		<c:choose>
	
			<c:when test="${(userEntity.accountState == 'complete') or (userEntity.accountState == 'admitted') or (userEntity.accountState == 'denied')}">
			
				<dt></dt>
				<dd>
					<!-- Button trigger modal -->
					<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
					<a data-toggle="modal" data-remote="${updateAccountState}" data-target="#updateAccountStateModal" 
						class="btn btn-warning btn-sm">Allow applicant to make changes</a><br><br>
					</sec:authorize>
				</dd>
			</c:when>
		</c:choose>
		
		<c:choose>
	
			<c:when test="${(userEntity.accountState == 'admitted')}">
			
				<dt></dt>
				<dd>
					<!-- Button trigger modal -->
					<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
					<a data-toggle="modal" data-remote="${updateAccountState2Enrolled}" data-target="#updateAccountState2EnrolledModal" 
						class="btn btn-warning btn-sm">Push to Registration Office</a><br><br>
					</sec:authorize>
				</dd>
			</c:when>
		</c:choose>
		
		
			
			
		<!-- delete address Modal -->
		<div class="modal fade" id="updateAccountStateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class = "modal-content">
		    
			</div>
		  </div>
		</div>
		<div class="modal fade" id="updateAccountState2EnrolledModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class = "modal-content">
		    
			</div>
		  </div>
		</div>
		

<%-- 		<dt>Password:</dt>
		<dd>
			<a href="${updatePassword}">Change Password</a>
		</dd> --%>
	</dl>
</div>
<div class="row">
	<dl class="dl-horizontal col-md-12">
		<dt>How did you hear about ACCT?</dt>
		<dd>
			<c:out value="${userEntity.heardAboutAcctThru}" />
		</dd>
  </dl>
</div>


<c:choose>
	<c:when test="${(userEntity.accountState != 'enrolled')}">
		<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
			<a href="${editAccountUrl}" class="btn btn-primary btn-sm">Update Personal Information</a>
		</sec:authorize>
	</c:when>
</c:choose>




<hr style="border:2px solid #A4A4A4;">

<!-- * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |  -->
<!-- * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |  -->

<h3>Term and Program of Study</h3>
<br><br>

<!-- if student has selected the term and program of study, show his selection and hide the add button
if student hasn't selected show the add button 
user can't delete term or program of study but they can change it.
I am using the same method to create and edit the term and program of study to 
insert the term and program of study to the userEntity using jdbc
-->

<c:choose>
	<c:when test="${empty userEntity.term}">
		<p class="red">No Term and Program of Study selected.</p>
		
		<!-- Button trigger modal -->
		<p>Select term and program of study you are applying for:</p> <br />
		
		<c:choose>
			<c:when test="${(userEntity.accountState != 'enrolled')}">
				<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
					<button class="btn btn-primary btn-md" data-toggle="modal" data-target="#addTermModal">
					  Select
					</button>
				</sec:authorize>
			</c:when>
		</c:choose>
		

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
			
			<dt></dt>

		</dl>
		<br>
		
		<div class="row">
			<div class="col-md-2">
				<c:choose>
					<c:when test="${(userEntity.accountState != 'enrolled')}">
						<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
							<button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addTermModal">
							  Edit
							</button>
						</sec:authorize>
					</c:when>
				</c:choose>
			</div>
		</div>
		
		
	</c:otherwise>
</c:choose>
	

<!-- Term and ProgramOfStudy Modal -->
<div class="modal fade" id="addTermModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class = "modal-content">
	        <form:form action="${applyingForUrl}" modelAttribute="userEntity" role="form" class = "form-horizontal">
	            <div class = "modal-header">
	                <h4>Select Term and Program of Study you are applying for</h4>
	            </div>
	            <div class = "modal-body">
	            
					
					<div class="form-group row">
						<!-- <label for="term" class="col-sm-3 control-label">term</label> -->
						<label for="term" class="col-sm-2 control-label">
							<spring:message code="applyingFor.label.term" />
							<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
						</label>
					    <div class="col-sm-5">
					      <form:select path="term.id">
							   <%-- <form:option value="NONE" label="--- Select ---"/> --%>
							   <%-- <form:options items="${terms}" /> --%>
							   <c:forEach var="term" items="${terms}">
							   		<form:option value="${term.id}" label="${term.name}"/>
							   </c:forEach>
							</form:select>
					    </div>
					    <div class="col-sm-3">
					    	<form:errors class="errormsg" path="term" htmlEscape="false" />
					    </div>
					</div>
					<div class="form-group row">
						<label for="programOfStudies" class="col-sm-2 control-label">
							<spring:message code="applyingFor.label.programOfStudies" />
							<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
						</label>
					    <div class="col-sm-5">
					      <form:select path="programOfStudy.id">
							   <c:forEach var="programOfStudy" items="${programOfStudies}">
							   		<form:option value="${programOfStudy.id}" label="${programOfStudy.name}"/>
							   </c:forEach>
							</form:select>
					    </div>
					    <div class="col-sm-3">
					    	<form:errors class="errormsg" path="term" htmlEscape="false" />
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

<hr style="border:2px solid #A4A4A4;">
<!-- * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |  -->
<!-- * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |  -->


<h3>Administrative Use</h3>

<c:choose>
	<c:when test="${empty associatedUsers}">
		<c:choose>
			<c:when test="${(userEntity.accountState != 'enrolled')}">
				<a href="${createAssociatedUser}" class="btn btn-primary btn-sm">Add Admission Officer, Agent, Referrer</a>
			</c:when>
		</c:choose>
	</c:when>
	<c:otherwise>
		
		<div class="row">
			<dl class="dl-horizontal col-md-6">
				<dt><spring:message code="account.label.admissionCounselor" /></dt>
				<dd>
					<c:out value="${associatedUsers.admissionOfficer.fullName}"></c:out>
				</dd>
			
				<dt><spring:message code="account.label.agent" /></dt>
				<dd>
					<c:out value="${associatedUsers.agent}"></c:out>
				</dd>
				
				<dt><spring:message code="account.label.referrer" /></dt>
				<dd>
					<c:out value="${associatedUsers.referrer}"></c:out>
				</dd>
				
				<dt></dt>
			</dl>
		</div>
		
		<div class="row">
			<div class="col-md-2">
				<c:choose>
					<c:when test="${(userEntity.accountState != 'enrolled')}">
						<a href="${editAssociatedUser}" class="btn btn-primary btn-sm">Edit Admission Officer, Agent, Referrer</a>
					</c:when>
				</c:choose>
			</div>
		</div>
		
	</c:otherwise>
</c:choose>

<hr style="border:2px solid #A4A4A4;">

<!-- * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |  -->
<!-- * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |  -->

<h3>Addresses</h3>
<hr />

<!-- 
$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
I have to put the addresses in two clolumsn in the page.
I will have to iterate over all the addresses when displaying, 
then put the odds on the left column and even on right column. 
$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
 -->


<c:choose>
	<c:when test="${empty addresses}">
		<p class="red">No Address.</p>
	</c:when>
	<c:otherwise>
		<c:forEach var="address" items="${addresses}">
			<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
				<c:url var="editAddressUrl" value="/accounts/${address.userEntity.id}/address/${address.id}/edit" />
				<c:url var="deleteAddressUrl" value="/accounts/${address.userEntity.id}/address/${address.id}/delete" />
			</sec:authorize>

			<address class="col-md-6">
				<%-- address.id: <c:out value="${address.id}" /> --%>
				<span class="addressType"> <em><c:out
							value="${address.addressType}" /></em>
				</span>
				
				<hr style="border:2px solid #A4A4A4;">
				
				<c:out value="${address.address1}" />
				<c:if test="${address.address2 != null}">
			  	, <c:out value="${address.address2}" />
					<br>
				</c:if>
				<c:out value="${address.city}" />, <c:out value="${address.state}" /> <c:out value="${address.zipcode}" />
				<br>
				<c:out value="${address.country.name}" />
				<!-- <br> <abbr title="Phone">P:</abbr> (123) 456-7890<br> <a
					href="mailto:#">first.last@example.com</a><br> -->
				<br><br>
					
				<div class="row">
					<div class="col-md-3">
						<c:choose>
							<c:when test="${(userEntity.accountState != 'enrolled')}">
								<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
									<a href="${editAddressUrl}" class="btn btn-primary btn-sm">Edit</a>
								</sec:authorize>
							</c:when>
						</c:choose>
					</div>
					<div class="col-md-3">
						<!-- Button trigger modal -->
						<c:choose>
							<c:when test="${(userEntity.accountState != 'enrolled')}">
								<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
								<a data-toggle="modal" data-remote="${deleteAddressUrl}" data-target="#deleteModal" 
									class="btn btn-danger btn-sm">Delete</a><br><br>
								</sec:authorize>
							</c:when>
						</c:choose>
							
							
						<!-- delete address Modal -->
						<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						  <div class="modal-dialog">
						    <div class = "modal-content">
						    
							</div>
						  </div>
						</div>
						
						<%-- <form id="deleteForm" action="${deleteAddressUrl}" method="post">
							<div>
								<input class="btn btn-danger btn-sm" type="submit" value="DELETE" />
							</div>
						</form> --%>
						
						

					</div>
				</div>
			</address>
		</c:forEach>

	</c:otherwise>
</c:choose>

<!-- I am displaying the addresses in 2 columns,
I need this to push down the h3 below from mixing with the addresses -->
<div class="row"></div>

<c:choose>
	<c:when test="${(userEntity.accountState != 'enrolled')}">
		<h5>
			<a href="${newAddressUrl}">Add New Address</a>
		</h5>
	</c:when>
</c:choose>

<!-- * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |  -->
<!-- * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |  -->



<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.js"></script>
	
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