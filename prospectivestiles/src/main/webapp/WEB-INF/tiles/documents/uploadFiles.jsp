<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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


<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="saveFileUrl" value="/accounts/${userEntity.id}/saveFile" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="saveFileUrl" value="/myAccount/saveFile" />
</sec:authorize>
			


<h1>Documents</h1>

<form:form method="post" action="${saveFileUrl}"
        modelAttribute="uploadedFile" enctype="multipart/form-data">
 
    <h3>Please select files to upload.</h3>
 
<!--  	Description: <input name="description" type="text" />
    <input name="file" type="file" />

    <br/><br/>
    <input type="submit" value="Upload" /> -->
    
    <div class="input-group col-md-8">
   		<span class="input-group-addon">Description</span>
	  	<input type="text" name="description" class="form-control" placeholder="Enter File Description">
	</div>
 
    <br/>
	<input name="file" type="file" /><br/>
    <input type="submit" value="Upload" class="btn btn-primary btn-sm" />
    
</form:form>


<h3>You have uploaded these files:</h3>

<ol>
    <c:forEach items="${files}" var="file">
    
    
    <sec:authorize access="hasRole('ROLE_ADMIN')">
		<c:url var="donwloadFileUrl" value="/accounts/${userEntity.id}/files/${file.id}" />
    	<c:url var="deleteFileUrl" value="/accounts/${userEntity.id}/files/${file.id}/delete" />
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_USER')">
		<c:url var="donwloadFileUrl" value="/myAccount/files/${file.id}" />
    	<c:url var="deleteFileUrl" value="/myAccount/files/${file.id}/delete" />
	</sec:authorize>
    
         <li class="row">
         	<p class="col-md-8">${file.description} (FileName: ${file.fileName})</p>
         	
         	<a href="${donwloadFileUrl}" class="btn btn-primary btn-sm col-md-2 glyphicon glyphicon-download-alt"> Download</a>
         	
         	<form id="deleteForm" action="${deleteFileUrl}" method="post" class="col-md-2">
				<div><input type="submit" value="DELETE" class="btn btn-danger btn-xs" /></div>
			</form>
         	
         	
         </li>
         
    </c:forEach>
</ol>





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