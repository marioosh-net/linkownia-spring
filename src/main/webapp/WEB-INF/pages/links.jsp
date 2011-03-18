<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/main.css" media="screen">
</head>
<body style="margin: 8px; font-family: arial, tahoma; font-size: 13px;">
	<a href=".">This</a>
	
	<div style="border-bottom: 1px solid #000000;">
	<form method="post" action="search.html">
		<input type="text" name="text" style="width: 350px;"/>
		<input type="submit" value="Search"/>
	</form>
	
	<!-- form:form commandName="link" action="add"> -->
	<form method="post" action="add.html">
		<label style="margin-right: 160px;">address</label><label>name</label><br/>
		<input type="text" name="address" style="width: 200px;"/>
		<input type="text" name="name" style="width: 200px;"/><br/>
		<label>description</label><br/>
		<textarea name="description" style="width: 404px;"></textarea><br/>
		<input type="submit" value="Add link"/>
		
		<!-- dostep do bledow bez uzycia form:form i form:errors -->
		<spring:hasBindErrors name="link" >
			<c:forEach items="${errors.allErrors}" var="error">
				${error.code}
	        </c:forEach>
        </spring:hasBindErrors>
	</form>
	
	<div>
	<c:forEach items="${links}" var="link" varStatus="i">
		<div style="${i.index%2 == 0 ? 'background-color: #fbfbfb' : ''}">
			<!-- ${link.id} -->
			<div>
				<div class="fleft mr">${link.ldate}</div>
				<div class="fleft mr"><a href="${link.address}">${link.name == '' ? link.address : link.name}</a></div>
				<div class="fleft mr"><a href="delete.html?id=${link.id}">Delete</a></div>
				<div style="clear: both;">${link.address}</div>
			</div>
		</div>
	</c:forEach>
	</div>
</body>
</html>

