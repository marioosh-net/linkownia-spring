<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/main2.css" media="screen">
</head>

<body id="TotalBodyId" onload="jQuery('#text').focus();"> 
 
	<div id="wraper"> 
    	<div id="headerblank"> 
       	  <div id="header"> 
           	<ul style="display: none;"> 
                	<li><a href="index.html">Home</a></li> 
                    <li><a href="about.html">About</a></li> 
                    <li><a href="blog.html">Blog</a></li> 
                    <li><a href="services.html">Services</a></li> 
                    <li><a href="portfolio.html">Portfolio</a></li> 
                    <li><a href="usefullinks.html" class="active">Useful&nbsp;Links</a></li> 
                    <li><a href="contact.html">Contact</a></li> 
            </ul> 
           	  <h1><a href="index.html"><img src="images/logo.png" alt="" /></a></h1><h3 style="display: none;">...</h3> 
           	
            </div> 
        </div>
        <div id="menu-full">
        		<div id="menu">
						<a href="#" onclick="jQuery('#new').toggle('fast');"><img width="51" height="20" src="images/add.png"/></a>        		
        		</div> 
        
        </div> 
        	<div id="bodycontentblank">
            	<div id="bodycontent"> 
               	  <div id="bodyleftcontent"> 
                   	<div id="leftPan"> 
                   	
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
                   	
                       	<div class="link-items-header" >Links by date</div>
                       	<c:forEach items="${links}" var="link" varStatus="i">
                       		<div class="link-item">
                       			<div class="clicks">${link.clicks}</div>
                       			<div class="link-data">
                       				<div>
										<div class="link-item-title">
										<a href="${link.address}" target="_blank">${link.name != '' ? link.name : link.address}</a>
										</div>                   				
										<a href="/search?q=site:${link.hostName}" class="link-item-source">${link.hostName}</a>
										<a href="${link.address}" class="link-item-teaser">— ${link.description}<span class="timestamp">${link.ldate }</span></a>										
                       				</div>
                       			</div>
                       			<div style="clear: both;"></div>
                       		</div>
						</c:forEach>
							     
                      </div> 
                	</div> 
                    
                    <div id="bodyRight">
                    	<div class="link-items-header" >Search</div> 
						<form method="post" action="search.html">
							<input id="text" type="text" name="text"/>
							<input type="button" class="button" onclick="submit()" />
						</form>
						
						<div class="link-items-header" >Top clicks</div>                        
						<c:forEach items="${toplinks}" var="link" varStatus="i">
                       		<div class="link-item">
                       			<div class="clicks">${link.clicks}</div>
                       			<div class="link-data" style="width: 200px;">
                       				<div>
										<div class="link-item-title">
										<a href="${link.address}" target="_blank">${link.name != '' ? link.name : link.address}</a>
										</div>                   				
										<a href="/search?q=site:${link.hostName}" class="link-item-source">${link.hostName}</a>
										<span class="timestamp">${link.ldate }</span>										
                       				</div>
                       			</div>
                       			<div style="clear: both;"></div>
                       		</div>
						</c:forEach>
 
                    </div> 
                    <!--end of right column --> 
                </div> 
            </div> 
 
            <div id="footer-wrap"> 
                <div class="footer"> 
                	<div class="pages"> 
	                    <h4>Pages</h4> 
                        <ul> 
                        	<li><a href="index.html">Home</a></li> 
                            <li><a href="about.html">About Us</a></li> 
                            <li><a href="blog.html">Blog</a></li> 
                            <li><a href="contact.html">Contact Us</a></li> 
                        </ul> 
                    </div> 
                    <!--end of pages div--> 
                    
                    
                    <div class="socialbookmarking"> 
                        <p class="rights">&copy; 2011. Designed by: <strong><a href="http://marioosh.net" target="_blank">marioosh.net</a></strong></p> 
                    </div> 
                    <!--end of additional info--> 
 
                </div> 
            </div><!--end of footer wrapper--> 
    </div> 
    
</body>
</html>

