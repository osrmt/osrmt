<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<html>
<head>
<title>Header</title>
<link rel="stylesheet" type="text/css" href="osrmt.css" />
</head>
<%@ include file="/custom/header.htm"%>
<body>

<html:errors/>

	Not logged in due to invalid username or password<br><br>
    <c:url value="/Logon.do" var="tryagainurl">
	</c:url>   
	<a href="<c:out value="${tryagainurl}"/>"><c:out value="Retry login"/></a>
</body>
</html>