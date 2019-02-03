<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>

<html:html locale="true">
<head>
<title><bean:message key="change.title"/></title>
<link rel="stylesheet" type="text/css" href="osrmt.css" />
<html:base/>
</head>
<%@ include file="/custom/header.htm"%>
<body>

<bean:message key="change.message"/>
<html:link action="/Logon">
  <bean:message key="change.try"/>
</html:link>

</body>
</html:html>
