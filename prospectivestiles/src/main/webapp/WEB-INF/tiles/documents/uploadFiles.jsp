<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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


<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<c:url var="saveFileUrl" value="/accounts/${userEntity.id}/saveFile" />
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_STUDENT_INPROCESS')">
	<c:url var="saveFileUrl" value="/myAccount/saveFile" />
</sec:authorize>
			


<h1>Documents</h1>
<h3>Please upload all your required documents. </h3>
<p>If you are INTERNATIONAL STUDENT, upload High School/ Bachelors/ Masters Degree transcript/Diploma/Certificate, TOEFL or IELTS Test Result, International Passport ID page, Financial Affidavit of Support Form, Current Bank Statement. For detail information read <a href="http://www.acct2day.org/admissions/international-students/" target="_blank">http://www.acct2day.org/admissions/international-students/</a></p>
<p>If you are INTERNATIONAL STUDENT TRANSFERRING from another institute, upload F-1 Student Visa, I-94 form, initial I-20 Form, current I-20, Official Transcript from your current Univeristy.  For detail information read <a href="http://www.acct2day.org/admissions/international-students/" target="_blank">http://www.acct2day.org/admissions/international-students/</a></p>

<p>For DOMESTIC STUDENT, upload Undergraduate Transcript, Undergraduate Certificate, A copy of Identification document (driver license, ID card, copy of green card for residents). For detail information read <a href="http://www.acct2day.org/admissions/domestic-students/" target="_blank">http://www.acct2day.org/admissions/domestic-students/</a></p>
<p>If you are DOMESTIC STUDENT TRANFERRING from another institute in addition to the documents mentioned above upload  Graduate Transcript, Applicants whose first language is not English must provide proof of language proficiency. For detail information read <a href="http://www.acct2day.org/admissions/domestic-students/" target="_blank">http://www.acct2day.org/admissions/domestic-students/</a></p>


<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<c:choose>
	<c:when test="${(userEntity.accountState != 'enrolled')}">

		<form:form method="post" action="${saveFileUrl}"
		        modelAttribute="uploadedFile" enctype="multipart/form-data">
		 
		    <h3>Please select files to upload.</h3>
		    <br>
		 
		    <div class="input-group col-md-8">
		   		<span class="input-group-addon">Description</span>
			  	<input type="text" name="description" class="form-control" placeholder="Enter File Description">
			</div>
		 
		    <br/>
			<input name="file" type="file" /><br/>
		    <input type="submit" value="Upload" class="btn btn-primary btn-sm" />
		    <span class="error">${fileErrMsg}</span>
		    
			<!-- There is the accept attribute for the input tag. 
			However, it is not reliable in any way. Browsers most likely treat it as a "suggestion" -->
			<!-- <input name="file" type="file" accept="image/gif, image/jpeg" /><br/> -->
		    
		    
		</form:form>
	</c:when>
</c:choose>
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_STUDENT_INPROCESS')">
	<c:choose>
	<c:when test="${(userEntity.accountState == 'pending') or (userEntity.accountState == 'inprocess')}">

		<form:form method="post" action="${saveFileUrl}"
		        modelAttribute="uploadedFile" enctype="multipart/form-data">
		 
		    <h3>Please select files to upload.</h3>
		    <br>
		 
		    <div class="input-group col-md-8">
		   		<span class="input-group-addon">Description</span>
			  	<input type="text" name="description" class="form-control" placeholder="Enter File Description">
			</div>
		 
		    <br/>
			<input name="file" type="file" /><br/>
		    <input type="submit" value="Upload" class="btn btn-primary btn-sm" />
		    <span class="error">${fileErrMsg}</span>
		    
			<!-- There is the accept attribute for the input tag. 
			However, it is not reliable in any way. Browsers most likely treat it as a "suggestion" -->
			<!-- <input name="file" type="file" accept="image/gif, image/jpeg" /><br/> -->
		    
		    
		</form:form>
	</c:when>
</c:choose>
</sec:authorize>


<br>

<c:choose>
	<c:when test="${empty files}">
		<p class="red">No uploaded files.</p>
	</c:when>
	<c:otherwise>
		<h3>You have uploaded these files:</h3>
		<br/>
		
		<ol>
		    <c:forEach items="${files}" var="file">
		    
		    
		    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
				<c:url var="donwloadFileUrl" value="/accounts/${userEntity.id}/files/${file.id}" />
		    	<c:url var="deleteFileUrl" value="/accounts/${userEntity.id}/files/${file.id}/delete" />
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_STUDENT_INPROCESS')">
				<c:url var="donwloadFileUrl" value="/myAccount/files/${file.id}" />
		    	<c:url var="deleteFileUrl" value="/myAccount/files/${file.id}/delete" />
			</sec:authorize>
		    
		         <li class="row">
		         	<p class="col-md-7 neg-12-margin-top">${file.description} (FileName: ${file.fileName})</p>
		         	
		         	<a href="${donwloadFileUrl}" class="btn btn-primary btn-sm col-md-2 glyphicon glyphicon-download-alt neg-12-margin-top"> Download</a>
		         	<span class="col-md-1"></span>
		         	
		         	<!-- Button trigger modal -->
		         	<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_STUDENT_INPROCESS', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
						<a data-toggle="modal" data-remote="${deleteFileUrl}" data-target="#deleteFileModal" 
							class="btn btn-danger btn-sm col-md-1 neg-12-margin-top">Delete</a><br><br>
					</sec:authorize>
						
					<!-- delete address Modal -->
					<div class="modal fade" id="deleteFileModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					  <div class="modal-dialog">
					    <div class = "modal-content">
					    
						</div>
					  </div>
					</div>
								
		         </li>
		         
		    </c:forEach>
		</ol>
	</c:otherwise>
</c:choose>


<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.js"></script>
	

	<!-- Call the dropdowns via JavaScript  -->
	<script>
		$(document).ready(function () {
	        $('.dropdown-toggle').dropdown();
	    });
	</script>