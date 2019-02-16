<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>


<html>
<head>
    <title>Artifact Trace</title>
    <link rel="stylesheet" type="text/css" href="osrmt.css" />
    <script src="osrmt.js"></script>
</head>
<%@ include file="/custom/header.htm"%>
<body><html:errors/>
<html:form action="/ArtifactTrace">
<table border="0">
<tbody>
<tr><td><bean:message key="trace.from"/>&nbsp;</td>
<td><html:select property="from">
        <c:forEach items="${artifactlist}" var="artifact">
            <c:if test='${from2 == artifact.artifactRefId}'>
                <option value="<c:out value="${artifact.artifactRefId}"/>" selected > <c:out value="${artifact.artifactName}" /></option>
            </c:if>
            <c:if test='${from2 != artifact.artifactRefId}'>
                <option value="<c:out value="${artifact.artifactRefId}"/>"><c:out value="${artifact.artifactName}"/></option>
            </c:if>
        </c:forEach>  
</html:select></td>
<td>
    <input type="radio" name="traceType" value="1"/><bean:message key="trace.type.1"/></br>   
    <input type="radio" name="traceType" value="2" checked/><bean:message key="trace.type.2"/></br> 
    <input type="radio" name="traceType" value="3"/><bean:message key="trace.type.3"/></br>
</td>
</tr>
<tr><td><bean:message key="trace.to"/>&nbsp;</td>
    <td><html:select property="to">
            <c:forEach items="${artifactlist}" var="artifact">
                <c:if test='${to2 == artifact.artifactRefId}'>
                <option value="<c:out value="${artifact.artifactRefId}"/>" selected > <c:out value="${artifact.artifactName}" /></option>
            </c:if>
            <c:if test='${to2 != artifact.artifactRefId}'>
                <option value="<c:out value="${artifact.artifactRefId}"/>"><c:out value="${artifact.artifactName}"/></option>
            </c:if>
            
                
            </c:forEach>  
    </html:select></td>
    <html:hidden property="action" value="update"/>
    <input type="hidden" name="productrefid" value="<c:out value="${productrefid}"/>"/>
    <td><html:submit value="Apply"/></td>
</tr>
</tbody>
</table>
</html:form>

<c:if test='${action=="update"}'>    
    <c:out value="${traceTable}" escapeXml="false"/>
</c:if>

</body>
</html>
