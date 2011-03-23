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
	function deleteLink(id) {
		jQuery.get('delete.html?id='+id, function(data){
			if(data == '0') {
				jQuery('div.link-item_'+id).remove();				
				toplinks();
			}
			jQuery('#debug-content').html(data);
		}, 'text');
	}
	function openLink(id, url) {
		jQuery.get('open.html?id='+id, function(data) {
			jQuery('div.count_'+id).html(data);
			toplinks();
		}, 'text');
		/*window.open(url , "open", "height=400,width=600");*/
		window.open(url);
	}
	function edit(id) {
		jQuery.post('edit.html', {'id': id}, function(data) {
			alert(data);
		});
	}
	function reload(id) {
		/*jQuery('#debug-content').load('reload.html', {'id': id});*/
		jQuery.post('reload.html', {'id': id}, function(data) {
			jQuery('#debug-content').html(data);
		}, 'text');
	}	
	function toplinks() {
		jQuery('#toplinks').load('toplinks.html');
	}
	function searches() {
		jQuery('#searches').load('searches.html');
	}	
	jQuery(document).ready(function(){
		toplinks();
		searches();
	});
	</script>
</head>

<body id="TotalBodyId" onload="jQuery('#qtext').focus();"> 
 
 	<c:if test="${header['host'] == 'localhost:8081'}">
	 	<div id="debug">
	 		<div style="font-weight: bold; display: none;">DEBUG:</div>
	 		<div id="debug-content">
	 		</div>
	 	</div>
 	</c:if>
 	
	<div id="wraper"> 
    	<div id="headerblank"> 
			<div id="header"> 
				<h1><a href="index.html"><img src="images/logo.png" alt="" /></a></h1>
				<div id="search"> 
					<form method="post" action="search.html">
						<input id="qtext" type="text" name="q" value="${q}"/>
						<input type="button" class="button" onclick="submit()" />
					</form>
 				</div>
			</div>
        </div>
        
        <div id="menu-full">
       		<div id="menu">
       			<div class="menu-item first-item">
					<a href="javascript:;" onclick="hidepanels('new', function() {jQuery('#address').focus();});"><img width="51" height="20" src="images/add.png"/></a>
				</div>
				<div class="menu-item">
					<a href="javascript:;" onclick="alert('not active'); return false; hidepanels('login', function() {jQuery('#username').focus();});"><img width="51" height="20" src="images/loginbutton.png"/></a>
				</div>
				<!--
				<div class="menu-item">
					<a href="javascript:;" onclick="jQuery('#new').toggle('fast', function() {jQuery('#address').focus();});">New link</a>
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
								<div style="vertical-align: bottom; padding-top: 3px;">&#160;<a href="javascript:;" onclick="jQuery('#jf').submit();">Login</a><!-- <input type="button" class="button" onclick="submit()" /> --><input type="submit" style="position: absolute; left: -9999px; width: 1px; height: 1px;"/></div>
							</div>
							<div style="clear: both;"></div>
						</form>
					</div>
						
					<!-- new link form -->			
					<div class="panel" id="new" style="${someErrors ? '' : 'display: none;'}">
						<!-- <form method="post" action="add.html"> -->
						<form:form commandName="link" method="post" action="add.html" id="newform">
							<label>Address</label>&#160;<form:errors path="address" cssClass="errors" /><br/>
							<input type="text" id="address" name="address" style="width: 404px;"/><br/>
							<label>Name</label><br/>
							<input type="text" name="name" style="width: 404px;"/><br/>
							<label>Description</label><br/>
							<textarea name="description" style="width: 404px;"></textarea><br/>
							<a href="javascript:;" onclick="jQuery('#newform').submit();">Add link</a>&#160;&#160;&#160;&#160;<a href="javascript:;" onclick="jQuery('#new').hide('fast');">Cancel</a>
							<input type="submit" style="position: absolute; left: -9999px; width: 1px; height: 1px;"/>
						</form:form>
					</div>
					
               		<!-- links -->
                   	<div class="block-header" >Links by date <span class="main-count">(${count})</span>
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
                      	<c:forEach items="${links}" var="link" varStatus="i">
                      		<div class="link-item link-item_${link.id}">
                      			<div class="clicks count_${link.id}">${link.clicks}</div>
                      			<div class="link-data">
                      				<div>
									<div class="link-item-title">
									<a href="javascript:;" onclick="openLink(${link.id},'${link.address}');">${link.name != '' ? link.name : link.address}</a>
									</div>                   				
									<a href="index.html?q=${link.hostName}" class="link-item-source">${link.hostName}</a>
									<a href="${link.address}" class="link-item-teaser">— ${link.description}<span class="timestamp">${link.ldate }</span></a>										
                      				</div>
                      				<div class="admin-funcs">
                      					<span class="func-item">
	                      					<a class="del" href="javascript:;" onclick="deleteLink(${link.id})"><img src="images/del.png"/><span class="button-text">delete</span></a>
	                      				</span>
	                      				<span class="func-item">	
	                      					<a class="edit" href="javascript:;" onclick="edit(${link.id})"><img src="images/ed.png"/><span class="button-text">edit</span></a>
                      					</span>
	                      				<span class="func-item">	
	                      					<a class="reload" href="javascript:;" onclick="reload(${link.id})"><img src="images/reload.png"/><span class="button-text">reload</span></a>
                      					</span>                      					
                      				</div>                      				
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
                   
                   <div id="bodyRight">
                   
					<!-- searches -->
					<div class="block-header" >Top searches</div>
					<div id="searches">
						<div style="padding-left: 10px; padding-top: 10px;"><img src="images/ajax.gif"/>&#160;Loading...</div>
					</div>
                   
                   	<!-- top links -->
					<div class="block-header" >Top clicks</div>
					<span id="toplinks">
						<div style="padding-left: 10px; padding-top: 10px;"><img src="images/ajax.gif"/>&#160;Loading...</div>
					</span>                        

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

