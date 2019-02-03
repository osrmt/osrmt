<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>


<html>
<head>
    <title>Reports</title>
    <link rel="stylesheet" type="text/css" href="osrmt.css" />
    <script src="osrmt.js"></script>
</head>
<body><html:errors/>
<h1 align="center" >Mail Sended Succesfully to all of recipients</h1>
<c:out value="${filePath}"/>

</body>
</html>

