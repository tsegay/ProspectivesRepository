<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<h1>All Accounts Page</h1>

<h3><a class="btn btn-info" href="<c:url value='/registrationform'/>">Register</a></h3>

<%-- <div class="table-responsive">
	<table class="table table-hover table-striped" data-filter="#filter" data-page-size="5"> 
		 <thead>
			<tr>
				<th data-toggle="true">Username</th>
				<th data-sort-initial="true">firstName</th>
				<th>lastName</th>
				<th data-hide="phone,tablet" data-name="Email address">Email</th>
				<th>acceptTerms</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="user" items="${users}">
			<tr>
				<td>
					<c:url var="myAccountUrl" value="/accounts/${user.id}" />
					<a href="${myAccountUrl}"><c:out value="${user.username}"></c:out></a>
				</td>
				<td><c:out value="${user.firstName}"></c:out></td>
				<td><c:out value="${user.lastName}"></c:out></td>
				<td><c:out value="${user.email}"></c:out></td>
				<td><c:out value="${user.acceptTerms}"></c:out></td>
			</tr>
		</c:forEach>
		</tbody>
		<tfoot>
			<ul class="pagination">
			  <li><a href="#">&laquo;</a></li>
			  <c:forEach var="i" begin="1" end="${totalPages}">
				   <c:choose> 
					  <c:when test="${i == page}" > 
					    <li class="active"><a href="<c:url value='/accounts/accounts/${i}'/>"><c:out value="${i}"/></a></li> 
					  </c:when> 
					  <c:otherwise> 
					    <li><a href="<c:url value='/accounts/accounts/${i}'/>"><c:out value="${i}"/></a></li>
					  </c:otherwise> 
					</c:choose> 
				</c:forEach>
			  <li><a href="#">&raquo;</a></li>
			</ul>
        </tfoot>
	</table>
                   		usersCount:<c:out value="${usersCount}" />
                   		pageSize:<c:out value="${pageSize}" />
                   		page:<c:out value="${page}" />
                   		totalPages:<c:out value="${totalPages}" />
</div> --%>



<!-- ############################################################################################# -->
<!-- 			UNDER CONSTRUCTION			 -->
<!-- Using JavaScript for Pagination -->
<!-- ############################################################################################# -->



<div class="table-responsive">
	<table class="table table-hover table-striped" data-filter="#filter" data-page-size="5"> 
		 <thead>
			<tr>
				<th data-toggle="true">Username</th>
				<th data-sort-initial="true">firstName</th>
				<th>lastName</th>
			</tr>
		</thead>
		<tbody id="tbody-content">
		

		</tbody>
		<tfoot>
			<ul id="pagination-ul" class="pagination">
			  
			  
			</ul>
        </tfoot>
	</table>
</div>
		

<!-- ############################################################################################# -->
<!-- 			jQuery Scripts			 -->
<!-- ############################################################################################# -->

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->

<!-- <script type="text/javascript" src="http://www.datejs.com/build/date.js"></script> -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/date.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>


<%-- <script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.js"></script> --%>



<sec:authorize access="hasRole('ROLE_ADMIN')">
	<%-- <c:url var="getAccountsUrl" value="/accounts/accounts/{page}/{pageSize}" /> --%>
	<c:url var="getAccountsUrl" value="/accounts/accounts/1/3" />
	<%-- <c:url var="getAccountsUrl" value="/accounts/accountspage?page=1&pageSize=3" /> --%>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">

</sec:authorize>

<script>	
	
	function goToPage(i){
		/* var pageNum = i;
		alert("i" + i);
		alert("pageNum" + pageNum);
		var pageVal = "/accounts/accounts/" + pageNum + "/3";
		alert("pageVal: " + pageVal);
		var pageUrl = "<c:url value="${pageVal}" />";
		alert("pageUrl: " + pageUrl);
		alert("${pageContext.request.contextPath}"); */
		/* $.getJSON("<c:url value="/accounts/accounts/'+i+'/3"/>", fetchAndDisplayAccounts); */
		/* $.getJSON('${pageUrl}', fetchAndDisplayAccounts); */
		/* $.getJSON('<c:url value="/accounts/accountspage/" />', { page: pageNum, pageSize: 3 }, fetchAndDisplayAccounts); */
		$.getJSON("${pageContext.request.contextPath}"+"/accounts/accounts/"+i+"/3", fetchAndDisplayAccounts);
		/* $('#pagination-ul > ul').children().removeClass('active');
    	$('#pageLi'+ i).addClass('active'); */
		
		
	}
	
	/* when user clicks on the username link rediredt to the student page */
	function goToAccount(id){
		var url = "${pageContext.request.contextPath}"+"/accounts/" + id;
		/* to simulate an HTTP redirect, use location.replace */
		window.location.replace(url);
	}
	
	function fetchAndDisplayAccounts(data){
		/* get student id from the model */
		/* var studentId = '${userEntityId}'; */
		
		$("#tbody-content").html("");
		
		/* fetch all the messages from the db and display it */
		for (var i = 0; i < data.users.length; i++) {
			
			var user = data.users[i];
			
			var userTr = document.createElement("tr");
			userTr.setAttribute("class", "user");
			
			
			var usernameTd = document.createElement("td");
			/* usernameTd.appendChild(document.createTextNode(user.username)); */
			
			
			var accountLink = document.createElement("a");
			accountLink.setAttribute("class", "accountLink");
			accountLink.setAttribute("href", "#");
			accountLink.setAttribute("onclick", "goToAccount(" + user.id + ")");
			accountLink.appendChild(document.createTextNode(user.username));
			
			usernameTd.appendChild(accountLink);
			
			
			var firstnameTd = document.createElement("td");
			firstnameTd.appendChild(document.createTextNode(user.firstName));
			var lastnameTd = document.createElement("td");
			lastnameTd.appendChild(document.createTextNode(user.lastName));
			
			userTr.appendChild(usernameTd);
			userTr.appendChild(firstnameTd);
			userTr.appendChild(lastnameTd);
			
			$("#tbody-content").append(userTr);
			
		}
		getPagination(data);
	}
	
	function getPagination(data){
		
		$("#pagination-ul").html("");
		
		for (var i = 1; i <= data.totalPages; i++) {
			
			var pageLi = document.createElement("li");
			
			if(i == data.page){
				pageLi.setAttribute("class", "active");
			} else {
				pageLi.setAttribute("class", "pageLi");
			}
			
			pageLi.setAttribute("id", "pageLi" + i);
			
			
			var pageLink = document.createElement("a");
			pageLink.setAttribute("class", "pageLink");
			pageLink.setAttribute("href", "#");
			pageLink.setAttribute("onclick", "goToPage(" + i + ")");
			pageLink.appendChild(document.createTextNode(i));

			pageLi.appendChild(pageLink);
			
			
			$("#pagination-ul").append(pageLi);
			
		}
	}
	
	function updatePage(){
		$.getJSON('${getAccountsUrl}', fetchAndDisplayAccounts);
	}
	
	function onLoad(){
		updatePage();
	}
	
	$(document).ready(onLoad);

</script>
<!-- <script>	
	
	function success(data){
		alert("Successfully sent email");
		alert(data.stId);
		fetchAndDisplayAccounts(data);
	}
	
	function error(data){
		alert("Error. Send message failed. within JS");
	}
	
	function goToPage(i){
		var pageNum = i;
		alert("i" + i);
		alert("pageNum" + pageNum);
		/* $.getJSON('<c:url value="/accounts/accounts/${pageNum}/3" />', fetchAndDisplayAccounts); */
		
		$.ajax({
			"type": 'GET',
			"url" : '<c:url value="accounts/accounts/getAccounts" />',
			"data": JSON.stringify({"pg": i, "pgSize": 3}),
			"success" : success,
			"error" : error,
			contentType : "application/json",
			dataType : "json"
		});
	}
	
	function fetchAndDisplayAccounts(data){
		
		$("#tbody-content").html("");
		
		for (var i = 0; i < data.users.length; i++) {
			
			var user = data.users[i];
			
			var userTr = document.createElement("tr");
			userTr.setAttribute("class", "user");
			
			
			var usernameTd = document.createElement("td");
			usernameTd.appendChild(document.createTextNode(user.username));
			var firstnameTd = document.createElement("td");
			firstnameTd.appendChild(document.createTextNode(user.firstName));
			var lastnameTd = document.createElement("td");
			lastnameTd.appendChild(document.createTextNode(user.lastName));
			
			userTr.appendChild(usernameTd);
			userTr.appendChild(firstnameTd);
			userTr.appendChild(lastnameTd);
			
			$("#tbody-content").append(userTr);
			
		}
		getPagination(data);
	}
	
	function getPagination(data){
		
		$("#pagination-ul").html("");
		
		for (var i = 1; i <= data.totalPages; i++) {
			
			var pageLi = document.createElement("li");
			
			var pageLink = document.createElement("a");
			pageLink.setAttribute("class", "pageLink");
			pageLink.setAttribute("href", "#");
			pageLink.setAttribute("onclick", "goToPage(" + i + ")");
			pageLink.appendChild(document.createTextNode(i));

			pageLi.appendChild(pageLink);
			
			
			$("#pagination-ul").append(pageLi);
			
		}
	}
	
	
	function updatePage(){
		$.getJSON('${getAccountsUrl}', fetchAndDisplayAccounts);
	}
	
	function onLoad(){
		updatePage();
	}
	
	$(document).ready(onLoad);

</script> -->
