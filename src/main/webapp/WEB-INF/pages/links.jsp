<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/main.css" media="screen">
</head>
<body onload="jQuery('#text').focus();">
	<div id="container">
		
		<div id="header">
			<div style="float: left;"><a class="logo" href="."><img src="images/logo.png"></img></a></div>
			<div id="search">
				<form method="post" action="search.html">
					<input id="text" type="text" name="text" style="width: 150px;"/>
					<input type="submit" value="Search"/>
				</form>
			</div>
			<div style="clear: both;"></div>
		</div>
	
		<!-- login form -->
		<security:authorize access="!hasRole('ROLE_ADMIN')">
			<form id="jf" name="f" action="<%= request.getContextPath() %>/j_spring_security_check" method="post">
				<div style="float: left">
					<div class="smalllabel">login</div>
					<div><input type="text" name="j_username" value="" size="5"/></div>
				</div>
				<div style="float: left">
					<div class="smalllabel">&#160;password</div>
					<div>&#160;<input type="password" name="j_password" value="" size="6" /></div>
				</div>
				<div style="float: left">
					<div>&#160;</div>
					<div style="vertical-align: bottom; padding-top: 3px;">&#160;<input type="submit" value="Login"/></div>
				</div>
				<div style="clear: both;"></div>
			</form>
		</security:authorize>
		
		<div id="menu"><a href="#" onclick="jQuery('#new').toggle('fast');">New</a></div>
		
		<div id="new" style="${someErrors ? '' : 'display: none;'}">
			<form method="post" action="add.html">
				<label>Address</label><form:errors path="address" /><br/>
				<input type="text" name="address" style="width: 404px;"/><br/>
				<label>Name</label><br/>
				<input type="text" name="name" style="width: 404px;"/><br/>
				<label>Description</label><br/>
				<textarea name="description" style="width: 404px;"></textarea><br/>
				<input type="submit" value="Add link"/>
				
				<!-- dostep do bledow bez uzycia form:form i form:errors -->
				<spring:hasBindErrors name="link" >
					<c:forEach items="${errors.allErrors}" var="error">
						${error.code}
			        </c:forEach>
		        </spring:hasBindErrors>
			</form>
		</div>
		
		<div id="list">
			<c:forEach items="${links}" var="link" varStatus="i">
				<div class="link">
					<div class="fleft mr">${link.ldate}</div>
					<div class="fleft mr"><a href="${link.address}">${link.name == '' ? link.address : link.name}</a></div>
					<div class="fright mr"><security:authorize access="hasRole('ROLE_ADMIN')"><a href="delete.html?id=${link.id}">Delete</a></security:authorize></div>
					<div class="url" style="clear: both;">${link.address}</div>
					<div class="description">${link.description}</div>
				</div>
			</c:forEach>
		</div>
	</div>		
</body>
</html>

