<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<div class="form-block-header">Edit</div>
<c:if test="${saved}">
	<div class="highlighted">
		Link saved. <a href="#" onclick="jQuery('#edit').hide('fast');">Close</a>.
	</div>
</c:if>
<c:if test="${not saved}">
<form:form commandName="link" method="post" action="save.html" id="editform" cssClass="highlighted">
    <form:hidden id="id" path="link.id"/>
    <form:hidden path="link.id"/>
    <form:hidden path="link.ldate"/>
    <form:hidden path="link.pub"/>
    <form:hidden path="link.userId"/>
    <form:hidden path="link.dateMod"/>
    <form:hidden path="link.clicks"/>
    
	<label>Address</label>&#160;<form:errors cssStyle="${someSaveErrors ? '' : 'display: none;'}" path="link.address" cssClass="errors" /><br/>
	<form:input path="link.address" cssClass="enter in" /><br/>
	<label>Name</label><br/>
	<form:input path="link.name" cssClass="enter in" /><br/>
	<label>Description</label><br/>
	<form:textarea path="link.description" cssStyle="height: 70px; width: 625px;"/><br/>
	<label>Tags</label><br/>
	<form:input path="tags"/><br/>
	<div class="buttons">
		<a href="#" onclick="jQuery('#editform').submit();">Save link</a>&#160;&#160;&#160;&#160;<a href="#" onclick="jQuery('#edit').hide('fast');">Cancel</a>
	</div>
	<input type="submit" style="position: absolute; left: -9999px; width: 1px; height: 1px;"/>
</form:form>

<script>
	jQuery('form[id=editform]').submit(function(){
		/*
	    jQuery('#register').load(this.action, jQuery(this).serialize());
	    */
	    jQuery.ajax({
	    	type: 'post',
	        url: this.action, 
	        data: jQuery(this).serialize(), 
	        success: function(res){
	            jQuery('#edit').html(res);
	        }
	    });
	    return false; // prevent default action
	}); 
</script>
</c:if>