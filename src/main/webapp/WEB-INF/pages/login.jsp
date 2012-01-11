<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<div class="form-block-header">Login</div>
<security:authorize ifAnyGranted="ROLE_ADMIN, ROLE_USER">
	<div class="highlighted">
    User logged successfuly. You have to <a href="<c:url value="/index.html"/>">Reload</a>.
    </div>
</security:authorize>
<security:authorize ifNotGranted="ROLE_ADMIN, ROLE_USER">
	<form id="jf" name="jf" action="<%= request.getContextPath() %>/j_spring_security_check" method="post" class="highlighted">
		<div style="float: left">
			<label>Login</label><br/>
			<div><input type="text" id="username" name="j_username" value="" size="5" class="enter" /></div>
		</div>
		<div style="float: left">
			<label>&#160;Password</label><br/>
			<div>&#160;<input type="password" name="j_password" value="" size="6" class="enter" /></div>
		</div>
		<div style="float: left">
			<div>&#160;</div>
			<div style="vertical-align: bottom; padding-top: 3px;">&#160;<a href="#" onclick="jQuery('#jf').submit();">Login</a><!-- <input type="button" class="button" onclick="submit()" /> --><input type="submit" style="position: absolute; left: -9999px; width: 1px; height: 1px;"/>
				<c:if test="${!empty param.loginfail}"><span class="errors">&#160;Login error!</span></c:if>
			</div>
		</div>
		<div style="clear: both;"></div>
		<div><input type='checkbox' name='_spring_security_remember_me'/>&#160;Remember me</div>
	</form>

	<script>
	jQuery('form[id=jf]').submit(function(){
	    jQuery.ajax({
	    	type: 'post',
	        url: this.action, 
	        data: jQuery(this).serialize(), 
	        success: function(res){
	            jQuery('#login').html(res);
	        }
	    });
	    return false; // prevent default action
	}); 
	</script>
</security:authorize>