<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta name="description" content="Linkownia - przechowalnia linków" />
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/main2.css" media="screen">
	<script>
	function hidepanels(id, fun) {
		jQuery('.panel').each(function(index) {
			if(jQuery(this).attr('id') != id) {
				jQuery(this).hide('fast');
			}
		});
		jQuery('#'+id).toggle('fast', fun);
	}
	function openLink(id, url) {
		jQuery.get('open.html?id='+id, function(data) {
			jQuery('.count_'+id).html(data);
		});
		window.open(url);
	}
	jQuery(document).ready(function(){
		/* nope */
	});
	</script>
</head>

<body id="TotalBodyId" onload="jQuery('#text').focus();"> 
 
	<div id="wraper"> 
    	<div id="headerblank"> 
			<div id="header"> 
				<h1><a href="index.html"><img src="images/logo.png" alt="" /></a></h1>
				<div id="search"> 
					<form method="post" action="search.html">
						<input id="text" type="text" name="text"/>
						<input type="button" class="button" onclick="submit()" />
					</form>
 				</div>
			</div>
        </div>
        
        <div id="menu-full">
       		<div id="menu">
       			<div class="menu-item first-item">
					<a href="#" onclick="hidepanels('new', function() {jQuery('#address').focus();});"><img width="51" height="20" src="images/add.png"/></a>
				</div>
				<div class="menu-item">
					<a href="#" onclick="alert('not active'); return false; hidepanels('login', function() {jQuery('#username').focus();});"><img width="51" height="20" src="images/loginbutton.png"/></a>
				</div>
				<!--
				<div class="menu-item">
					<a href="#" onclick="jQuery('#new').toggle('fast', function() {jQuery('#address').focus();});">New link</a>
				</div>
				-->        		
       		</div> 
        </div>
         
       	<div id="bodycontentblank">
           	<div id="bodycontent"> 
              	  <div id="bodyleftcontent"> 
                  	<div id="leftPan"> 
                  	
					<!-- login form -->
					<div class="panel" id="login" style="display: none;">
						<form id="jf" name="f" action="<%= request.getContextPath() %>/j_spring_security_check" method="post">
							<div style="float: left">
								<div class="smalllabel">login</div>
								<div><input type="text" id="username" name="j_username" value="" size="5"/></div>
							</div>
							<div style="float: left">
								<div class="smalllabel">&#160;password</div>
								<div>&#160;<input type="password" name="j_password" value="" size="6" /></div>
							</div>
							<div style="float: left">
								<div>&#160;</div>
								<div style="vertical-align: bottom; padding-top: 3px;">&#160;<a href="#" onclick="jQuery('#jf').submit();">Login</a><!-- <input type="button" class="button" onclick="submit()" /> --><input type="submit" style="position: absolute; left: -9999px; width: 1px; height: 1px;"/></div>
							</div>
							<div style="clear: both;"></div>
						</form>
					</div>
					
					<!-- new link form -->			
					<div class="panel" id="new" style="${someErrors ? '' : 'display: none;'}">
						<!-- <form method="post" action="add.html"> -->
						<form:form commandName="link" action="add.html" id="newform">
							<label>Address</label>&#160;<form:errors path="address" cssClass="errors" /><br/>
							<input type="text" id="address" name="address" style="width: 404px;"/><br/>
							<label>Name</label><br/>
							<input type="text" name="name" style="width: 404px;"/><br/>
							<label>Description</label><br/>
							<textarea name="description" style="width: 404px;"></textarea><br/>
							<a href="#" onclick="jQuery('#newform').submit();">Add link</a>
							<input type="submit" style="position: absolute; left: -9999px; width: 1px; height: 1px;"/>
						</form:form>
					</div>
                  	
               		<!-- links -->
                   	<div class="link-items-header" >Links by date<!-- <span style="float: right; font-size: 12px;">Open links in new window&#160;<input type="checkbox"/></span> --></div>
                      	<c:forEach items="${links}" var="link" varStatus="i">
                      		<div class="link-item">
                      			<div class="clicks count_${link.id}">${link.clicks}</div>
                      			<div class="link-data">
                      				<div>
									<div class="link-item-title">
									<a href="#" onclick="openLink(${link.id},'${link.address}');">${link.name != '' ? link.name : link.address}</a>
									</div>                   				
									<a href="search.html?q=${link.hostName}" class="link-item-source">${link.hostName}</a>
									<a href="${link.address}" class="link-item-teaser">— ${link.description}<span class="timestamp">${link.ldate }</span></a>										
                      				</div>
                      			</div>
                      			<div style="clear: both;"></div>
                      		</div>
						</c:forEach>
                     </div>
                      
               	</div> 
                   
                   <div id="bodyRight">
                   
                   	<!-- top links -->
					<div class="link-items-header" >Top clicks</div>                        
					<c:forEach items="${toplinks}" var="link" varStatus="i">
                      		<div class="link-item">
                      			<div class="clicks count_${link.id}">${link.clicks}</div>
                      			<div class="link-data" style="width: 200px;">
                      				<div>
									<div class="link-item-title">
									<a href="#" onclick="openLink(${link.id},'${link.address}');">${link.name != '' ? link.name : link.address}</a>
									</div>                   				
									<a href="search.html?q=${link.hostName}" class="link-item-source">${link.hostName}</a>
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
                       	<li><a href="index.html">Linkownia</a></li> 
                           <li><a href="http://marioosh.net">Home</a></li> 
                           <li><a href="http://marioosh.net/blog/">Blog</a></li> 
                       </ul> 
                   </div> 
                   
                   <div class="copyright"> 
                       <p class="rights">&copy; 2011. Designed by: <strong><a href="http://marioosh.net" target="_blank">marioosh.net</a></strong></p> 
                   </div> 

               </div> 
           </div> 
    </div> 
    
</body>
</html>

