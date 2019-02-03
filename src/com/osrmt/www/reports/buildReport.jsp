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

<table border="0" width="50%">
<tbody>
<tr>
<td align="left" width="30%">
    <table border="0">
    <c:forEach items="${reportList}" var="report" >
    <c:url value="/BuildReport.do" var="viewurl">
        <c:param name="reportid" value="${report.reportid}"/>
        <c:param name="step" value="${report.step}"/>
    </c:url>
    <tr>
        <td valign="middle">
            <c:out value="${report.name}"/>
        </td>
        <td align="left" style="font-size: x-small">
            <a href="<c:out value="${viewurl}"/>&amp;type=pdf">pdf</a></br>
            <a href="<c:out value="${viewurl}"/>&amp;type=html">html</a>
        </td>
    </tr>   
    </c:forEach>
    </table>
</td>
<td align="center" width="70%">
    <c:if test='${step=="2"}'>
        <c:if test='${mail=="true"}'>
            <%@ include file="sendFile.jsp" %>
        </c:if>
    </c:if>
</td>
</tr>
</tbody>
</table>

</body>
</html>
