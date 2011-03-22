<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<c:forEach items="${toplinks}" var="link" varStatus="i">
	<div class="link-item">
		<div class="clicks count_${link.id}">
			${link.clicks}
		</div>
		<div class="link-data" style="width: 200px;">
			<div>
				<div class="link-item-title">
					<a href="#" onclick="openLink(${link.id},'${link.address}');">${link.name != '' ? link.name : link.address}</a>
				</div>
				<a href="index.html?q=${link.hostName}" class="link-item-source">${link.hostName}</a>
				<span class="timestamp">${link.ldate }</span>
			</div>
		</div>
		<div style="clear: both;">
		</div>
	</div>
</c:forEach>