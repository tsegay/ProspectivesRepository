<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<c:url var="newHighSchoolUrl" value="/accounts/${userEntity.id}/highSchool/new" />
	<c:url var="newInstituteUrl" value="/accounts/${userEntity.id}/institute/new" />
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING')">
	<c:url var="newHighSchoolUrl" value="/myAccount/highSchool/new" />
	<c:url var="newInstituteUrl" value="/myAccount/institute/new" />
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
		</dl>
	</div>
</sec:authorize>

<h3>Standard Test</h3>

<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<c:url var="newStandardTestUrl" value="/accounts/${userEntity.id}/standardTest/new" />
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING')">
	<c:url var="newStandardTestUrl" value="/myAccount/standardTest/new" />
</sec:authorize>
		
<p>If you took any standard test like TOEFL or IELTS, please fill in the form. </p>

<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">	
	<h5>
		<a href="${newStandardTestUrl}">Add New Standard Test</a>
	</h5>
</sec:authorize>

<c:choose>
	<c:when test="${empty standardTests}">
		<p class="red">No StandardTest.</p>
	</c:when>
	<c:otherwise>

		<div class="table-responsive">
			<table class="table table-hover table-striped">
				<tr>
					<!-- <th>Id</th> -->
					<th>name</th>
					<th>score</th>
					<th>validTill</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
	
				<c:forEach var="standardTest" items="${standardTests}">
				
				 <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
					<c:url var="editStandardTestUrl" value="/accounts/${standardTest.userEntity.id}/standardTest/${standardTest.id}/edit" />
					<c:url var="deleteStandardTestUrl" value="/accounts/${standardTest.userEntity.id}/standardTest/${standardTest.id}/delete" />
				</sec:authorize>
				<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING')">
					<c:url var="editStandardTestUrl" value="/myAccount/standardTest/${standardTest.id}/edit" />
					<c:url var="deleteStandardTestUrl" value="/myAccount/standardTest/${standardTest.id}/delete" />
				</sec:authorize>
				
				<tr>
					<%-- <td><c:out value="${standardTest.id}"></c:out>
					</td> --%>
					<td><c:out value="${standardTest.name}"></c:out></td>
					<td><c:out value="${standardTest.score}"></c:out></td>
					<td>
					<fmt:formatDate var="validTillString" value="${standardTest.validTill}" pattern="MM-dd-yyyy" />
					<c:out value="${validTillString}" />
					</td>
					<td>
						<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
							<a href="${editStandardTestUrl}" class="btn btn-primary btn-sm">Edit</a>
						</sec:authorize>
					</td>
					<td>
						<!-- Button trigger modal -->
						<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
							<a data-toggle="modal" data-remote="${deleteStandardTestUrl}" data-target="#deleteStandardTestModal" 
								class="btn btn-danger btn-sm">Delete</a><br><br>
						</sec:authorize>
						
						<!-- delete address Modal -->
						<div class="modal fade" id="deleteStandardTestModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						  <div class="modal-dialog">
						    <div class = "modal-content">
						    
							</div>
						  </div>
						</div>
						
					</td>
				</tr>
								
				</c:forEach>
	
			</table>
		</div>
	</c:otherwise>
</c:choose>


<!-- * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |  -->


<hr style="border:2px solid #A4A4A4;">
<h3>High School</h3>

<p>
Applicants applying for a Certificate, a Bachelor or an Associates Degree are required to provide information about their High School.
</p>

<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<h5>
		<a href="${newHighSchoolUrl}">Add New High School</a>
	</h5>
</sec:authorize>
	

<c:if test="${param.deleted == true}">
	<div class="info alert">High School/Institute deleted.</div>
</c:if>

<c:choose>
	<c:when test="${empty highSchools}">
		<p class="red">No High School.</p>
	</c:when>
	<c:otherwise>

		<div class="table-responsive">
			<table class="table table-hover table-striped">
				<tr>
					<!-- <th>Id</th> -->
					<th>Name</th>
					<th>State</th>
					<th>Country</th>
					<th>Diplome</th>
					<th>gED</th>
					<th>Edit</th>
					<th>Delete</th>
					<!-- diplome
					diplomeAwardedDate
					gEDAwardedDate
					zip
					city
					attendedFrom
					attendedTo -->
				</tr>
	
				<c:forEach var="highSchool" items="${highSchools}">
				
				 <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
					<c:url var="editHighSchoolUrl" value="/accounts/${highSchool.userEntity.id}/highSchool/${highSchool.id}/edit" />
					<c:url var="deleteHighSchoolUrl" value="/accounts/${highSchool.userEntity.id}/highSchool/${highSchool.id}/delete" />
				</sec:authorize>
				<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING')">
					<c:url var="editHighSchoolUrl" value="/myAccount/highSchool/${highSchool.id}/edit" />
					<c:url var="deleteHighSchoolUrl" value="/myAccount/highSchool/${highSchool.id}/delete" />
				</sec:authorize>
				
				<tr>
					<%-- <td><c:out value="${highSchool.id}"></c:out></td> --%>
					<td><c:out value="${highSchool.name}"></c:out></td>
					<td><c:out value="${highSchool.state}"></c:out></td>
					<td><c:out value="${highSchool.country}"></c:out></td>
					<td><c:out value="${highSchool.diplome}"></c:out></td>
					<td><c:out value="${highSchool.gED}"></c:out></td>
					<td>
						<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
							<a href="${editHighSchoolUrl}" class="btn btn-primary btn-sm">Edit</a>
						</sec:authorize>
					</td>
					<td>
						<!-- Button trigger modal -->
						<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
							<a data-toggle="modal" data-remote="${deleteHighSchoolUrl}" data-target="#deleteHighSchoolModal" 
								class="btn btn-danger btn-sm">Delete</a><br><br>
						</sec:authorize>
							
						<!-- delete address Modal -->
						<div class="modal fade" id="deleteHighSchoolModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						  <div class="modal-dialog">
						    <div class = "modal-content">
						    
							</div>
						  </div>
						</div>
						
					</td>
				</tr>
								
				</c:forEach>
	
			</table>
		</div>
	</c:otherwise>
</c:choose>

<!-- * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * | * |  -->

<hr style="border:2px solid #A4A4A4;">

<h3>Institute</h3>

<p>
Applicants applying for a Masters or Post Masters Degree are required to provide information about Institutes attended in the past.
</p>

<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<h5>
		<a href="${newInstituteUrl}">Add New Institute</a>
	</h5>
</sec:authorize>


<c:choose>
	<c:when test="${empty institutes}">
		<p class="red">No Institute.</p>
	</c:when>
	<c:otherwise>

		<div class="table-responsive">
			<table class="table table-hover table-striped">
				<tr>
					<!-- <th>Id</th> -->
					<th>Name</th>
					<th>programOfStudy</th>
					<th>levelOfStudy</th>
					<th>State</th>
					<th>Country</th>
					<th>Edit</th>
					<th>Delete</th>
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
				
				 <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
					<%-- <c:url var="instituteUrl"	value="/accounts/${institute.userEntity.id}/institute/${institute.id}" /> --%>
					<c:url var="editInstituteUrl" value="/accounts/${institute.userEntity.id}/institute/${institute.id}/edit" />
					<c:url var="deleteInstituteUrl" value="/accounts/${institute.userEntity.id}/institute/${institute.id}/delete" />
				</sec:authorize>
				<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING')">
					<%-- <c:url var="instituteUrl"	value="/myAccount/institute/${institute.id}" /> --%>
					<c:url var="editInstituteUrl" value="/myAccount/institute/${institute.id}/edit" />
					<c:url var="deleteInstituteUrl" value="/myAccount/institute/${institute.id}/delete" />
				</sec:authorize>
				
				<tr>
						<%-- <td><c:out value="${institute.id}"></c:out></td> --%>
						<%-- <td>
							<a href="${instituteUrl}"><c:out value="${institute.name}"></c:out></a>
						</td> --%>
						<td><c:out value="${institute.name}"></c:out></td>
						<td><c:out value="${institute.programOfStudy}"></c:out></td>
						<td><c:out value="${institute.levelOfStudy}"></c:out></td>
						<td><c:out value="${institute.state}"></c:out></td>
						<td><c:out value="${institute.country}"></c:out></td>
						<td>
							<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
								<a href="${editInstituteUrl}" class="btn btn-primary btn-sm">Edit</a>
							</sec:authorize>
						</td>
						<td>
							<!-- Button trigger modal -->
							<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
								<a data-toggle="modal" data-remote="${deleteInstituteUrl}" data-target="#deleteInstituteModal" 
									class="btn btn-danger btn-sm">Delete</a><br><br>
							</sec:authorize>
								
							<!-- delete address Modal -->
							<div class="modal fade" id="deleteInstituteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
							  <div class="modal-dialog">
							    <div class = "modal-content">
							    
								</div>
							  </div>
							</div>
							
							<%-- <form id="deleteForm" action="${deleteInstituteUrl}" method="post">
								<div><input type="submit" value="DELETE" /></div>
							</form> --%>
						</td>
					</tr>
				
				</c:forEach>
	
			</table>
		</div>
	</c:otherwise>
</c:choose>

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