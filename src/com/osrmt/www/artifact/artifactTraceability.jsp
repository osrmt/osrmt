<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>


<html>
<head>
    <title>Artifact Traceability</title>
    <link rel="stylesheet" type="text/css" href="osrmt.css" />
    <script src="osrmt.js"></script>
</head>
<%@ include file="/custom/header.htm"%>
<body>
    <bean:message key="mainMenu.title" /><br/>
        <bean:message key="menu.line"/><br/>
            
            <html:errors/>
            
            <html:form action="/ArtifactTraceability" >
            
            <%--productrefid <c:out value="${productrefid}"/>--%>
            
            <table border="1">
            <thead>
                <tr>
                    <th width="40%"><bean:message key="trace.from"/>&nbsp;</th>
                    <th width="20%">&nbsp;</th>
                    <th width="40%"><bean:message key="trace.to"/>&nbsp;</th>
                </tr>
            </thead>
            <tbody>
            <tr>
                <td>
                    <input type="hidden" name="productrefid" value="<c:out value="${productrefid}"/>"/>
                    <%--c:out value="${frompool2}"/><br/--%>
                    <html:select property="frompool" style="width: 100%" onchange="document.forms['ArtifactTraceabilityActionForm'].submit();" >
                        <option value="0" ><c:out value=" -- Select Option -- "/></option> 
                        <c:forEach items="${artifactPoolsList}" var="artifactPoolFrom">
                            
                            
                            <c:if test='${frompool2 == artifactPoolFrom.artifactRefId}'>
                                <option value="<c:out value="${artifactPoolFrom.artifactRefId}"/>" selected > <c:out value="${artifactPoolFrom.artifactName}" /></option>
                            </c:if>
                            
                            <c:if test='${frompool2 != artifactPoolFrom.artifactRefId}'>
                                <option value="<c:out value="${artifactPoolFrom.artifactRefId}"/>"><c:out value="${artifactPoolFrom.artifactName}"/></option>
                            </c:if>
                            <%--
                                   <option value="<c:out value="${artifactPoolFrom.artifactRefId}"/>"><c:out value="${artifactPoolFrom.artifactName}"/></option> 
                                    --%>
                                                       
                        </c:forEach>  
                    </html:select>
                </td>
                <td>&nbsp;</td>
                <td><%--c:out value="${topool2}"/><br/--%>
                    <html:select property="topool" style="width: 100%" onchange="document.forms['ArtifactTraceabilityActionForm'].submit();">
                        <option value="0" ><c:out value=" -- Select Option -- "/></option> 
                        <c:forEach items="${artifactPoolsList}" var="artifactPoolTo">
                            
                            <c:if test='${topool2 == artifactPoolTo.artifactRefId}'>
                                <option value="<c:out value="${artifactPoolTo.artifactRefId}"/>" selected > <c:out value="${artifactPoolTo.artifactName}" /></option>
                            </c:if>
                            <c:if test='${topool2 != artifactPoolTo.artifactRefId}'>
                                <option value="<c:out value="${artifactPoolTo.artifactRefId}"/>"><c:out value="${artifactPoolTo.artifactName}"/></option>
                            </c:if>
                            <%--
                                    <option value="<c:out value="${artifactPoolTo.artifactRefId}"/>"><c:out value="${artifactPoolTo.artifactName}" /></option>
                                    --%>
                                    
                        </c:forEach>  
                    </html:select>
                </td>
            </tr>
            <tr>
                <td><%--c:out value="${fromartifact}"/><br/--%>
                    <html:select property="fromartifact" style="width: 100%" >
                        <option value="0" ><c:out value=" -- Select Option -- "/></option> 
                        <c:forEach items="${fromArtifactList}" var="artifact">
                            <option value="<c:out value="${artifact.artifactId}"/>"><c:out value="${artifact.artifactName}"/></option>
                        </c:forEach>  
                    </html:select>
                    
                </td>
                <td>&nbsp; </td>
                <td><%--c:out value="${toartifact}"/><br/--%>
                    <html:select property="toartifact" style="width: 100%" >
                        <option value="0" ><c:out value=" -- Select Option -- "/></option> 
                        <c:forEach items="${toArtifactList}" var="artifact">
                            <option value="<c:out value="${artifact.artifactId}"/>"><c:out value="${artifact.artifactName}"/></option>
                        </c:forEach>  
                    </html:select>
                </td>
            </tr>
            <tr>
            <td><%--c:out value="${fromartifact}"/--%>&nbsp;</td>
            <td>
        <input type="radio" name="action" value="Trace" checked/><bean:message key="trace.actio.trace"/></br>   
    <input type="radio" name="action" value="Untrace" /><bean:message key="trace.actio.untrace"/></br> 
    <html:submit value="Apply action"/>
    </td>
    <td><%--c:out value="${toartifact}"/--%>&nbsp;</td>
    </tr>
    </tbody>
    </table>
    <c:if test='${successTrace==1}'>
        <c:out value="Artifacts traced succesfully"/>
    </c:if>
    <c:if test='${successTrace==2}'>
        <c:out value="Artifacts untraced succesfully"/>
    </c:if>
    
    <c:if test='${successTrace==3}'>
        <c:out value="Artifacts NOT traced succesfully"/>
    </c:if>
    
    
    
    
    
    </html:form>
    
</body>
</html>
