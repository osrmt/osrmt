<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<html>
<head>
<title>Clear Reference Cache</title>
<link rel="stylesheet" type="text/css" href="osrmt.css" />
</head>

<%@ include file="/custom/header.htm"%>
<body>
<html:errors/>

Clear cache:
<c:url value="/ClearCache.do" var="url">
</c:url>   
<a href="<c:out value="${url}"/>"><c:out value="clear reference cache"/></a>   
</body>
</html>