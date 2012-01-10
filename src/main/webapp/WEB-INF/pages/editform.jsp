<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<form:form commandName="link" method="post" action="save.html" id="editform">
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
	<form:textarea path="link.description" cssStyle="height: 70px; width: 648px;"/><br/>
	<label>Tags</label><br/>
	<form:input path="tags"/><br/>
	<a href="#" onclick="jQuery('#editform').submit();">Save link</a>&#160;&#160;&#160;&#160;<a href="#" onclick="jQuery('#edit').hide('fast');">Cancel</a>
	<input type="submit" style="position: absolute; left: -9999px; width: 1px; height: 1px;"/>
</form:form>