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
                   	
                        	<h2>Links by date</h2> 
                            	 <ul class="usefullinks">
                            	 
		                           	<c:forEach items="${links}" var="link" varStatus="i">
										<li>
											<div class="fleft mr">${link.ldate}</div>
											<div class="fleft mr"><p><a href="${link.address}">${link.name == '' ? link.address : link.name}</a>&#160;[${link.clicks}]</p></div>
											<div class="fright mr"><security:authorize access="hasRole('ROLE_ADMIN')"><a href="delete.html?id=${link.id}">Delete</a></security:authorize></div>
											<div class="url" style="clear: both;">${link.address}</div>
											<div class="description">${link.description}</div>
										</li>
									</c:forEach>
                            	  
        <li><h3>Lorem ipsum dolor sit amet consectetur adipiscing elit.</h3> 
          <p class="post">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tellus ipsum, sollicitudin ornare molestie nec, pretium id quam. Aliquam erat volutpat.Nullam dui ipsum, pharetra eu bibendum ut, porta et turpis. Vivamus hendrerit velit eget urna placerat pretium. Aenean a dolor mi. Vestibulum iaculis vehicula tellus, a varius massa adipiscing id. Fusce eleifend neque eu mi sagittis quis viverra nisl dapibus.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tellus ipsum, sollicitudin ornare molestie nec, pretium id quam.</p> 
           <p><a href="more.html" class="readMore">Read More</a></p> 
        </li> 
        <li><h3>Lorem ipsum dolor sit amet consectetur adipiscing elit.</h3> 
          <p class="post">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tellus ipsum, sollicitudin ornare molestie nec, pretium id quam. Aliquam erat volutpat.Nullam dui ipsum, pharetra eu bibendum ut, porta et turpis. Vivamus hendrerit velit eget urna placerat pretium. Aenean a dolor mi. Vestibulum iaculis vehicula tellus, a varius massa adipiscing id. Fusce eleifend neque eu mi sagittis quis viverra nisl dapibus.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tellus ipsum, sollicitudin ornare molestie nec, pretium id quam.</p> 
           <p><a href="more.html" class="readMore">Read More</a></p> 
        </li> 
        <li><h3>Lorem ipsum dolor sit amet consectetur adipiscing elit.</h3> 
          <p class="post">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tellus ipsum, sollicitudin ornare molestie nec, pretium id quam. Aliquam erat volutpat.Nullam dui ipsum, pharetra eu bibendum ut, porta et turpis. Vivamus hendrerit velit eget urna placerat pretium. Aenean a dolor mi. Vestibulum iaculis vehicula tellus, a varius massa adipiscing id. Fusce eleifend neque eu mi sagittis quis viverra nisl dapibus.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tellus ipsum, sollicitudin ornare molestie nec, pretium id quam.</p> 
           <p><a href="more.html" class="readMore">Read More</a></p> 
        </li> 
    </ul> 
                      </div> 
                	</div> 
                    
                    <div id="bodyRight"> 
						<form method="post" action="search.html">
							<input id="text" class="search" type="text" name="text"/>
							<input type="button" class="button" onclick="submit()" />
						</form>
						
                        <div class="latestNews"> 
                        <h2>Top 10</h2> 
                            <ul class="news">
								<c:forEach items="${toplinks}" var="link" varStatus="i">
									<li>
										<a href="${link.address}"><span>${link.ldate}</span>${link.name == '' ? link.address : link.name}&#160;[${link.clicks}]</a>
									</li>
								</c:forEach>
                                <li class="nobg"><a href="more.html" class="viewMore">View More</a></li> 
                            </ul> 
                      </div><!--end of latest news--> 
                        
                        <div class="testimonial" style="display: none;"> 
                        	<h2>site Content</h2> 
                        	<blockquote>Posuere cubilia Curaeis sagfa ettis risusat risus feu hregiat sed feugat nuctum. Etiam euismod dabus lorem, dui </blockquote>           					
                            <br /> 
                           	<blockquote>Sosuere cubilia Curaeis sagfa ettis risusat risus feu hregiat sed feugat nuctum tiam euismod dabus lorem</blockquote> 
                        </div><!--end of testimonials--> 
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
                            <li><a href="services.html">Services</a></li> 
                            <li><a href="portfolio.html">Portfolio</a></li> 
                            <li><a href="usefullinks.html">Useful Links</a></li> 
                            <li><a href="contact.html">Contact Us</a></li> 
                        </ul> 
                    </div> 
                    <!--end of pages div--> 
                    
                    <div class="services"> 
                    	<h4>Services</h4> 
                        <ul> 
                        	<li><a href="more.html">Services One</a></li> 
                            <li><a href="more.html">Services Two</a></li> 
                            <li><a href="more.html">Services Three</a></li> 
                            <li><a href="more.html">Services Four</a></li> 
                            <li><a href="more.html">Services Five</a></li> 
                            <li><a href="more.html">Services Six</a></li> 
                            <li><a href="more.html">Services Seven</a></li> 
                        </ul> 
                    </div> 
                    <!--end of services--> 
                    
                    <div class="various"> 
                    	<h4>Services</h4> 
                        <ul> 
                        	<li><a href="more.html">Register</a></li> 
                            <li><a href="more.html">Log in</a></li> 
                            <li><a href="tos.html">Terms of Service</a></li> 
                            <li><a href="contact.html">Contact</a></li> 
                            <li><a href="sitemap.html">Site Map</a></li> 
                        </ul> 
                    </div> 
                    <!--end of various--> 
                    
                    <div class="additional"> 
                    	<h4>Additional</h4> 
                        <ul> 
                        	<li><a href="more.html">Lorem ipsum</a></li> 
                            <li><a href="more.html">Vivamus sit mrus</a></li> 
                            <li><a href="more.html">Vestim eeros elit</a></li> 
                            <li><a href="more.html">Sit amet congue</a></li> 
                            <li><a href="more.html">Suspe ndieoncus</a></li> 
                        </ul> 
                    </div> 
                    <!--end of additional info--> 
                    
                    <div class="socialbookmarking"> 
                    	<h4>Keep in touch!</h4> 
                        <p>We will be glad to assist you if you have any problem with out products.</p> 
                        
                        <div class="bookmarkIcons"> 
                   	    <img src="images/facebook_icon.gif" width="32" height="32" alt=" " /> 
                        <img src="images/linken-icon.gif" width="32" height="32" alt=" " /> 
                        <img src="images/in_icon.gif" width="32" height="32" alt=" " /> 
                        <img src="images/rss_icon.gif" width="32" height="32" alt=" " /> 
                        <img src="images/twitter_icon.gif" width="32" height="32" alt=" " /> 
                        </div> 
                        <p class="rights">&copy; Your Copyright Info Here. Designed by: <strong><a href="http://www.templateworld.com" title="Template World" target="_blank">Template World</a></strong></p> 
                    </div> 
                    <!--end of additional info--> 
 
                </div> 
            </div><!--end of footer wrapper--> 
    </div> 
</body>
</html>

