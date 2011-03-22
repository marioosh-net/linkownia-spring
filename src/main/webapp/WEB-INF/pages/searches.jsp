<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<c:forEach items="${searches}" var="search" varStatus="i">
	<div class="search-item"><a href="index.html?q=${search.phrase}">${search.phrase}</a>&#160;<span class="search-counter">(${search.counter})</span></div>
</c:forEach>
<div style="clear: both;"></div>