<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>




<h1>Spring Multiple File Upload example</h1>
    <p>Following files are uploaded successfully.</p>
    ${fileUploaded}
    <%-- <ol>
        <c:forEach items="${files}" var="file">
            <li>${file}</li>
        </c:forEach>
    </ol> --%>
