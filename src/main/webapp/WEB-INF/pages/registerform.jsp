<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<div class="form-block-header">Register</div>
<c:if test="${not empty param['done']}">
	<div class="highlighted">
    User registered successfuly. You can <a href="#" onclick="login();">Login</a>.
    </div>
</c:if>
<c:if test="${empty param['done']}">
	<form:form commandName="user1" method="post" action="register.html" id="registerform" cssClass="highlighted">
	    <label>Login</label>&#160;<form:errors path="login" cssClass="errors" /><br/>
	    <form:input path="login" id="userlogin"/><br/>
	    <label>Password</label>&#160;<form:errors path="pass" cssClass="errors" /><br/>
	    <form:password path="pass"/><br/>
	    <div class="buttons">
			<a href="#" onclick="jQuery('#registerform').submit();">Register</a>&#160;&#160;&#160;&#160;<a href="#" onclick="jQuery('#register').hide('fast');">Cancel</a>
		</div>
		<input type="submit" style="position: absolute; left: -9999px; width: 1px; height: 1px;"/>
	</form:form>
	
	<script>
	jQuery('form[id=registerform]').submit(function(){
		/*
	    jQuery('#register').load(this.action, jQuery(this).serialize());
	    */
	    jQuery.ajax({
	    	type: 'post',
	        url: this.action, 
	        data: jQuery(this).serialize(), 
	        success: function(res){
	            jQuery('#register').html(res);
	        }
	    });
	    return false; // prevent default action
	}); 
	</script>
</c:if>