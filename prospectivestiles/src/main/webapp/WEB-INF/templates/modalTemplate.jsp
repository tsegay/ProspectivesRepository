<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title><tiles:insertAttribute name="title"></tiles:insertAttribute></title>


<%-- <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/css/images/favicon-spring.ico" /> --%>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.js"></script> --%>
<!-- Latest compiled and minified CSS -->
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css" /> --%>

<tiles:insertAttribute name="includes"></tiles:insertAttribute>

</head>
<body>

		<div class="header">
			<tiles:insertAttribute name="header"></tiles:insertAttribute>
		</div>
		<div class="sidebar">
			<tiles:insertAttribute name="sidebar"></tiles:insertAttribute>
		</div>
		<div class="bodycontent">
			<tiles:insertAttribute name="bodycontent"></tiles:insertAttribute>
		</div>
		<div class="footer">
			<tiles:insertAttribute name="footer"></tiles:insertAttribute>
		</div>

</body>
</html>