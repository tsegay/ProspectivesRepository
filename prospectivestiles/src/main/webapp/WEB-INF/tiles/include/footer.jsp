<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div class="navbar navbar-default navbar-fixed-bottom">

	<div>
		<p class="navbar-text pull-left">Site Built By Daniel Anenia</p>
		
		<jsp:useBean id="date" class="java.util.Date" />
		<p class="navbar-text pull-left">&copy; <fmt:formatDate value="${date}" pattern="yyyy" /> ACCT</p>
		
		<a href="#"
			class="navbar-btn btn-danger btn pull-right">Subscribe</a>
	</div>

</div>