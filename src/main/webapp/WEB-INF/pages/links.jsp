<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta name="description" content="Linkownia - przechowalnia linków" />
	<meta name="author" content="ramtatam.com">
	<meta name="Keywords" lang="pl" content="morele, linkownia, baza linków, odnośniki, adresy, www, marioosh">
	<meta name="Keywords" lang="en" content="apricots, linkownia, links database, references, addresses, www, marioosh">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.14.custom.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/main.css" media="screen">
	<link type="text/css" href="css/jquery-ui.css" rel="stylesheet" />	
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/main.js"></script>
	<title>Linkownia - przechowalnia linków</title>
</head>

<body id="body" onload="jQuery('#qtext').focus();"> 

    <%--
 	<c:if test="${header['host'] == 'localhost:8081' or header['host'] == 'localhost:8080'}">
	 	<div id="debug">
	 		<a class="debugbutton" href="#" onclick="jQuery('#debug-container').toggle();">DEBUG</a>
	 		<div id="debug-container">
		 		<c:forEach items="${param}" var="par">
		 			<div>${par }</div>
		 		</c:forEach>
		 		<div style="font-weight: bold; display: none;">DEBUG:</div>
		 		<div id="debug-content">
		 		</div>
	 		</div>
	 	</div>
 	</c:if>
    --%>

	<div id="wrapper">
		<div id="header">
			<div class="fixedwidth">
				<div class="left leftfixed">
				<a href="index.html"><img src="images/logo.png" alt="" /></a>
				</div>
				
				<div class="left rightfixed">
					<div id="search">
					<form method="post" action="search.html">
						<input id="qtext" type="text" name="q" value="${param['site'] != 1 ? q : ''}"/>
						<input type="button" class="button" onclick="submit()" />
					</form>
					</div>
 				</div>
 				
 				<div class="clear"></div>
			</div>		
		</div>
	
		<div id="main">
			<div id="menu">
    			<div class="fixedwidth">
				
					<div class="left">
		       			<%--<security:authorize ifAnyGranted="ROLE_ADMIN, ROLE_USER">--%>
		       			<div class="menu-item first-item">
							<a href="#" onclick="hidepanels('new', function() {jQuery('#address').focus();});">New link</a>
						</div>
						<%--</security:authorize>--%>
						
						<security:authorize ifNotGranted="ROLE_ADMIN, ROLE_USER">
						<div class="menu-item">
							<%--<a href="#" onclick="hidepanels('login', function() {jQuery('#username').focus();});"><img width="51" height="20" src="images/loginbutton.png"/></a>--%>
							<%--<a href="<c:url value="/login.html"/>"><img width="51" height="20" src="images/loginbutton.png"/></a>--%>
							<%--<a href="#" onclick="login();">Login</a>--%>
							<a href="<c:url value="/login.html"/>">Login</a>
						</div>
						<div class="menu-item">
                            <a href="#" onclick="register();">Register</a>
						</div>
						</security:authorize>
						<div class="clear"></div>
					</div>
					
					<div class="right">
						<security:authorize ifAnyGranted="ROLE_ADMIN, ROLE_USER">
						<div class="menu-item last-item">
							<span class="username"><security:authentication property="principal.username" /></span>
							<a href="<%= request.getContextPath() %>/logout.html" >Logout</a>
						</div>
						</security:authorize>
						<!--
						<div class="menu-item">
							<a href="#" onclick="jQuery('#new').toggle('fast', function() {jQuery('#address').focus();});">New link</a>
						</div>
						-->        		
					</div>
					
					<div class="clear"></div>
				</div>
			</div>
			
			<div class="fixedwidth">
				<div id="content">		
				
 
              	  <div class="left leftfixed"> 
                  	<div id="leftPan"> 
                  	
					<!-- login form -->
					<div class="panel" id="login" style="${loginInProgress ? '' : 'display: none;'}">
						<jsp:include page="login.jsp"/>
					</div>
						
					<!-- new link form -->
					<%--<security:authorize ifAnyGranted="ROLE_ADMIN, ROLE_USER">--%>			
					<div class="panel" id="new" style="${someErrors ? '' : 'display: none;'}">
						<!-- <form method="post" action="add.html"> -->
						<div class="form-block-header">New link</div>
						<form:form commandName="link" method="post" action="add.html" id="newform" cssClass="highlighted">
							<label>Address</label>&#160;<form:errors cssStyle="${someErrors ? '' : 'display: none;'}" path="link.address" cssClass="errors" /><br/>
							<input type="text" id="address" name="link.address" style="width: 404px;" value="${param['address']}" class="enter"/><br/>
							<label>Name</label><br/>
							<input type="text" name="link.name" style="width: 404px;" value="${param['name']}" class="enter"/><br/>
							<label>Description</label><br/>
							<textarea name="link.description" style="height: 70px; width: 625px;">${param['description']}</textarea><br/>
							<label>Tags</label><br/>
							<input id="tags_input" type="text" name="tags" style="width: 404px;" value="${param['tags']}" class="tags_input"/><br/>
							<div class="buttons">
								<a href="#" onclick="jQuery('#newform').submit();">Add link</a>&#160;&#160;&#160;&#160;<a href="#" onclick="jQuery('#new').hide('fast');">Cancel</a>
							</div>
							<input type="submit" style="position: absolute; left: -9999px; width: 1px; height: 1px;"/>
						</form:form>
					</div>
					<div class="panel" id="edit" style="${someSaveErrors ? '' : 'display: none;'}">
					</div>					
                    <div class="panel" id="register" style="${someRegisterErrors ? '' : 'display: none;'}">
                    </div>					
					<%--</security:authorize>--%>
					
               		<!-- links -->
                   	<div class="block-header" >Links by date <span class="main-count">(${count})</span> 
                   		<security:authorize ifAnyGranted="ROLE_ADMIN, ROLE_USER">
							<form action="settings.html" method="post" style="display: inline;">
							<select name="mode" onchange="submit();/*mode(jQuery(this).val());*/"><option ${user.mode == 'ALL' ? 'selected="selected"' : ''} value="0">All</option><option ${user.mode == 'PUBLIC' ? 'selected="selected"' : ''} value="1">Public</option><option ${user.mode == 'MY_OWN' ? 'selected="selected"' : ''} value="2">My own</option> </select>
							</form>
                   		</security:authorize>
                   		
                   		<span id="topnavi">
                   		<c:if test="${count > 1}" >
                   			<c:if test="${page != 1}" >
                   				<a href="index.html?q=${q}&amp;p=${page -1}">&#171; Newer</a>
                   			</c:if>
                   			<c:if test="${page == 1}" >
                   				<span class="inactive">&#171; Newer</span>
                   			</c:if>
                   			<c:if test="${page < pagesCount}">
                   				<a href="index.html?q=${q}&amp;p=${page +1}">Older &#187;</a>
                   			</c:if>
                   			<c:if test="${page >= pagesCount}">
                   				<span class="inactive">Older &#187;</span>
                   			</c:if>                   			
                   		</c:if>
                   		</span>
                   		<!-- <span style="float: right; font-size: 12px;">Open links in new window&#160;<input type="checkbox"/></span> --></div>
                      	<c:forEach items="${links}" var="li" varStatus="i">
                            <c:set var="link" value="${li[0]}"/>
                            <c:set var="tagsTab" value="${li[1]}"/>
                      		<div class="link-item link-item_${link.id}">
                      			<div class="clicks count_${link.id}">${link.clicks}<div><img class="ajax_${link.id}" style="display:none;" src="images/ajax.gif"/></div></div>
                      			<div class="link-data">
                      				<div>
									<div class="link-item-title">
									<a href="#" title="${link.addressWithHttp}" onclick="openLink(${link.id},'${link.addressWithHttp}'); return false;" class="name ${link.pub ? '' : 'private-link'}">${link.name != '' ? link.name : link.addressWithHttp}</a>
									</div>                   				
									<a href="index.html?site=1&amp;q=${link.hostName}" class="link-item-source">${link.hostName}</a>
									<span class="link-item-teaser">— <span class="descr">${link.description}</span><span class="timestamp">${link.dateModFormatted}</span></span>										
                      				</div>
                      				<%-- tags --%>
                                    <div class="tags">
                                        <c:forEach items="${tagsTab}" var="tag">
                                            <span class="tag">  
                                                <a href="index.html?site=1&amp;qt=${tag}"><span class="button-text">${tag}</span></a>
                                            </span>                                     
                                        </c:forEach> 
                                    </div>
	                      			
                      				<c:set var="linkOwner" value="${(user == null and link.userId == null) or(user != null and user.id == link.userId)}"/>
	                      			<c:set var="adminLogged" value="${false}"/>
	                      			<security:authorize ifAnyGranted="ROLE_ADMIN">
	                      				<c:set var="adminLogged" value="${true}"/>
	                      			</security:authorize>
	                      			<c:if test="${linkOwner or adminLogged}">
	                      				<div class="admin-funcs">
	                      					<span class="func-item">
		                      					<a class="del" href="#" onclick="deleteLink(${link.id})"><img src="images/del.png"/><span class="button-text">delete</span></a>
		                      				</span>
		                      				<span class="func-item">	
		                      					<a class="edit" href="#" onclick="edit(${link.id})"><img src="images/ed.png"/><span class="button-text">edit</span></a>
	                      					</span>
		                      				<span class="func-item">	
		                      					<a class="refresh" href="#" onclick="refresh(${link.id}); return false;"><img src="images/reload.png"/><span class="button-text">refresh</span></a>
	                      					</span>           
	                      					<security:authorize ifAnyGranted="ROLE_ADMIN, ROLE_USER">           					
	                      						<span class="func-item">
		                      						<a class="visibility" href="#" onclick="visibility(${link.id}); return false;"><img src="images/icon_padlock.gif"/><span class="button-text">${link.pub ? 'make private' : 'make public'}</span></a>
		                      					</span>
	                      					</security:authorize>
    	                  				</div>                      				
                      				</c:if>
                      			</div>
                      			<div style="clear: both;"></div>
                      		</div>
						</c:forEach>
                     </div>
                     <div id="navi">
                     	<c:forEach items="${pages}" var="p" varStatus="i">
                     		<c:if test="${i.index == 0}">Pages:&#160;</c:if>
                     		<span>
                     			<c:if test="${p[0] != page}"><a href="index.html?q=${q}&amp;p=${p[0]}">${p[0]}</a></c:if>
                     			<c:if test="${p[0] == page}">${p[0]}</c:if>
                     			&#160;
                     		</span>
                     	</c:forEach>
                     </div>
                      
               	</div> 
                   
                   <div class="right rightfixed">
                   
					<!-- searches -->
					<div class="block-header" >Top searches</div>
					<div id="searches">
						<div style="padding-left: 10px; padding-top: 10px;"><img src="images/ajax.gif"/>&#160;Loading...</div>
					</div>
                   
					<!-- tags -->
					<div class="block-header" >Tags</div>
					<div id="toptags">
						<div style="padding-left: 10px; padding-top: 10px;"><img src="images/ajax.gif"/>&#160;Loading...</div>
					</div>
					                   
                   	<!-- top links -->
					<div class="block-header" >Top clicks</div>
					<span id="toplinks">
						<div style="padding-left: 10px; padding-top: 10px;"><img src="images/ajax.gif"/>&#160;Loading...</div>
					</span>                        

                   </div> 
                   <!--end of right column --> 
				
					<div class="clear"></div>

				</div>
			</div>
		</div>
        <div id="push"></div>
	</div>
	
	<div id="footer">
		<div class="fixedwidth">
            <%--
			<div class="pages"> 
				<ul> 
					<li><a href="index.html">Linkownia</a></li> 
					<li><a href="http://ramtatam.com">Home</a></li> 
					<li><a href="http://ramtatam.com/blog/">Blog</a></li> 
				</ul> 
			</div> 
            --%>
            Powered by <a href="http://springsource.org"><img style="vertical-align: -7px;" src="<c:url value="/images/spring.png"/>"/></a> | <a href="http://ramtatam.com">ramtatam.com</a>
            <%--
			<div class="copyright"> 
				<p class="rights">&copy; 2011. Designed by: <strong><a href="http://ramtatam.com" target="_blank">ramtatam.com</a></strong></p> 
			</div>
            --%>
		</div>
	</div>
    
</body>
</html>

