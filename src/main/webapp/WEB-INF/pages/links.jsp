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
	<div id="wrapper">
	<div id="topfull">
		<div class="centered">
			<a class="logo" href="."><img src="images/logo.png"></img></a>
			<div id="search">
				<form method="post" action="search.html">
					<input id="text" type="text" name="text" style="width: 150px;"/>
					<input type="submit" value="Search"/>
				</form>
			</div>
			<div style="clear: both;"></div>			
		</div>
	</div>
	<div id="container">
		
		<div id="topc">
	
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

		<div id="menu"><a href="#" onclick="jQuery('#new').toggle('fast');"><img width="51" height="20" src="images/add.png"/></a></div>
		<div id="new" style="${someErrors ? '' : 'display: none;'}">
			<!-- <form method="post" action="add.html"> -->
			<form:form commandName="link" action="add.html">
				<label>Address</label>&#160;<form:errors path="address" cssClass="errors" /><br/>
				<input type="text" name="address" style="width: 404px;"/><br/>
				<label>Name</label><br/>
				<input type="text" name="name" style="width: 404px;"/><br/>
				<label>Description</label><br/>
				<textarea name="description" style="width: 404px;"></textarea><br/>
				<input type="submit" value="Add link"/>
			</form:form>
		</div>
		</div>
		
<div class="inner">
        	<div class="innersub">
            	<div class="innerLeft">
               	  <div class="leftTop">
                    <h3>Links</h3>
                    
					<ul class="innerdata">
						<c:forEach items="${links}" var="link" varStatus="i">
							<li>
								<div class="fleft mr">${link.ldate}</div>
								<div class="fleft mr"><p><a href="${link.address}">${link.name == '' ? link.address : link.name}</a></p></div>
								<div class="fright mr"><security:authorize access="hasRole('ROLE_ADMIN')"><a href="delete.html?id=${link.id}">Delete</a></security:authorize></div>
								<div class="url" style="clear: both;">${link.address}</div>
								<div class="description">${link.description}</div>
							</li>
						</c:forEach>
					</ul>
                    
                    <img src="images/leftBtm.png" align="bottom" alt="">
                  </div>
  				</div>
  				                  
                <div class="innerRight">
                	<div class="rightTop">
                       <h3>Top 10</h3>
                       <ul>
                       </ul>
                       <img src="images/rightBtm.png" alt="" align="bottom">                       
                    </div>
                    
                </div>
                
            </div>
            <img src="images/inner_btm.png" alt="" align="bottom">
        </div>		
				
	</div>		
	</div>
</body>
</html>

