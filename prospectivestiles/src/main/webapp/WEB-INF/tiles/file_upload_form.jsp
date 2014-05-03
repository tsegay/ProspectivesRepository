<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<c:url var="saveUrl" value="/save" />

<h1>Spring File Upload example</h1>

<form:form method="post" action="${saveUrl}"
        modelAttribute="uploadedFile" enctype="multipart/form-data">
 
    <p>Select files to upload. Press Add button to add more file inputs.</p>
 
    <input name="file" type="file" />

    <br/><input type="submit" value="Upload" />
</form:form>