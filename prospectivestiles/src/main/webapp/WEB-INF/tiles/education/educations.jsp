<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="newHighSchoolUrl" value="/accounts/${userEntity.id}/highSchool/new" />
	<c:url var="newInstituteUrl" value="/accounts/${userEntity.id}/institute/new" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="newHighSchoolUrl" value="/myAccount/highSchool/new" />
	<c:url var="newInstituteUrl" value="/myAccount/institute/new" />
</sec:authorize>


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

<h1>HighSchool</h1>

<h3>
	<a href="${newHighSchoolUrl}">Add New High School</a>
</h3>
	

<c:if test="${param.deleted == true}">
	<div class="info alert">High School/Institute deleted.</div>
</c:if>

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
				
				 <sec:authorize access="hasRole('ROLE_ADMIN')">
					<c:url var="highSchoolUrl"	value="/accounts/${highSchool.userEntity.id}/highSchool/${highSchool.id}" />
					<c:url var="editHighSchoolUrl" value="/accounts/${highSchool.userEntity.id}/highSchool/${highSchool.id}/edit" />
					<c:url var="deleteHighSchoolUrl" value="/accounts/${highSchool.userEntity.id}/highSchool/${highSchool.id}/delete" />
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
					<c:url var="highSchoolUrl"	value="/myAccount/highSchool/${highSchool.id}" />
					<c:url var="editHighSchoolUrl" value="/myAccount/highSchool/${highSchool.id}/edit" />
					<c:url var="deleteHighSchoolUrl" value="/myAccount/highSchool/${highSchool.id}/delete" />
				</sec:authorize>
				
				<tr>
					<td><c:out value="${highSchool.id}"></c:out></td>
					<%-- <td>
						<a href="${highSchoolUrl}"><c:out value="${highSchool.name}"></c:out></a>
					</td> --%>
					<td><c:out value="${highSchool.name}"></c:out></td>
					<td><c:out value="${highSchool.state}"></c:out></td>
					<td><c:out value="${highSchool.country}"></c:out></td>
					<td><c:out value="${highSchool.diplome}"></c:out></td>
					<td><c:out value="${highSchool.gED}"></c:out></td>
					<td>
						<a href="${editHighSchoolUrl}">Edit</a>
					</td>
					<td>
						<!-- Button trigger modal -->
						<a data-toggle="modal" data-remote="${deleteHighSchoolUrl}" data-target="#deleteHighSchoolModal" 
							class="btn btn-danger btn-sm">Delete</a><br><br>
							
						<!-- delete address Modal -->
						<div class="modal fade" id="deleteHighSchoolModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						  <div class="modal-dialog">
						    <div class = "modal-content">
						    
							</div>
						  </div>
						</div>
						
						<%-- <form id="deleteForm" action="${deleteHighSchoolUrl}" method="post">
							<div><input type="submit" value="DELETE" /></div>
						</form> --%>
					</td>
				</tr>
								
				</c:forEach>
	
			</table>
		</div>
	</c:otherwise>
</c:choose>



<h1>Institute</h1>
<h3>
	<a href="${newInstituteUrl}">Add New Institute</a>
</h3>
	


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
				
				 <sec:authorize access="hasRole('ROLE_ADMIN')">
					<c:url var="instituteUrl"	value="/accounts/${institute.userEntity.id}/institute/${institute.id}" />
					<c:url var="editInstituteUrl" value="/accounts/${institute.userEntity.id}/institute/${institute.id}/edit" />
					<c:url var="deleteInstituteUrl" value="/accounts/${institute.userEntity.id}/institute/${institute.id}/delete" />
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
					<c:url var="instituteUrl"	value="/myAccount/institute/${institute.id}" />
					<c:url var="editInstituteUrl" value="/myAccount/institute/${institute.id}/edit" />
					<c:url var="deleteInstituteUrl" value="/myAccount/institute/${institute.id}/delete" />
				</sec:authorize>
				
				<tr>
						<td><c:out value="${institute.id}"></c:out></td>
						<%-- <td>
							<a href="${instituteUrl}"><c:out value="${institute.name}"></c:out></a>
						</td> --%>
						<td><c:out value="${institute.name}"></c:out></td>
						<td><c:out value="${institute.programOfStudy}"></c:out></td>
						<td><c:out value="${institute.levelOfStudy}"></c:out></td>
						<td><c:out value="${institute.state}"></c:out></td>
						<td><c:out value="${institute.country}"></c:out></td>
						<td>
							<a href="${editInstituteUrl}">Edit</a>
						</td>
						<td>
							<!-- Button trigger modal -->
							<a data-toggle="modal" data-remote="${deleteInstituteUrl}" data-target="#deleteInstituteModal" 
								class="btn btn-danger btn-sm">Delete</a><br><br>
								
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