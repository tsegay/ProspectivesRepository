<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<h3>Authorised Users Only!</h3>


<h3>testRestTemplate</h3>

<p><c:out value="${testRestTemplate.Name}"></c:out></p>
<p><c:out value="${testRestTemplate.About}"></c:out></p>
<p><c:out value="${testRestTemplate.Phone}"></c:out></p>
<p><c:out value="${testRestTemplate.Website}"></c:out></p>
