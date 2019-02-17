<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<html>
<head>
<title>Issue View</title>
<link rel="stylesheet" type="text/css" href="osrmt.css" />
</head>
<%@ include file="/custom/header.htm"%>
<body>
<html:errors/>
<c:if test='${crud == "create"}'>
	<html:form action="/SaveIssue" method="post"> 
		<input name="issuerefid" type="hidden" value="<c:out value="${issuerefid}"/>" />		
		<input name="crud" type="hidden" value="create" />		
		<html:submit property="Submit" value="Save"/>
		<c:out value="${controls}" escapeXml="false"/>
		<html:submit property="Submit" value="Save"/>
	</html:form>
</c:if>
<c:if test='${crud == "update"}'>
	<html:form action="/SaveIssue"> 
		<input name="crud" type="hidden" value="update" />		
		<html:submit property="Submit" value="Save"/>
		<c:out value="${controls}" escapeXml="false"/>
		<html:submit property="Submit" value="Save"/>
	</html:form>
</c:if>
<c:if test='${crud == "read"}'>
		<c:out value="${controls}" escapeXml="false"/>
</c:if>
<c:if test='${crud == "delete"}'>
	<html:form action="/SaveIssue"> 
		<input name="crud" type="hidden" value="delete" />		
		<html:submit property="Submit" value="Delete"/>
		<c:out value="${controls}" escapeXml="false"/>
		<html:submit property="Submit" value="Delete"/>
	</html:form>
</c:if>
<!--  font size="small">Logged in: <c:out value="${user.userLogin}"/></font>	-->
</body>
</html>