<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Artifact View</title>
        <link rel="stylesheet" type="text/css" href="osrmt.css" />
    </head>
    <%@ include file="/custom/header.htm"%>
    <body>
        <font size="small">Logged in: <c:out value="${user.userLogin}"/>
            <br/>-----------------------------------------------------------------------<br/><br/>
            
        </font>	
        <html:errors/>
        <c:if test='${crud == "create"}'>
            <html:form action="/SaveArtifact" method="post"> 
                <input name="parentartifactid" type="hidden" value="<c:out value="${parentartifactid}"/>" />		
                <input name="artifactrefid" type="hidden" value="<c:out value="${artifactrefid}"/>" />		
                <input name="crud" type="hidden" value="create" />		
                <html:submit property="Submit" value="Save"/>
                <c:out value="${controls}" escapeXml="false"/>
                <html:submit property="Submit" value="Save"/>
            </html:form>
        </c:if>
        <c:if test='${crud == "update"}'>
            <html:form action="/SaveArtifact"> 
                <input name="crud" type="hidden" value="update" />		
                <input name="artifactid" type="hidden" value="<c:out value="${artifactid}"/>" />		
                <html:submit property="Submit" value="Save"/>
                <c:out value="${controls}" escapeXml="false"/>
                <html:submit property="Submit" value="Save"/>
            </html:form>
        </c:if>
        
        <c:if test='${crud == "attachment"}'>
            <html:form action="/AddAttachment" enctype="multipart/form-data">
                
                <input name="crud" type="hidden" value="attachment" />		
                <input name="artifactid" type="hidden" value="<c:out value="${artifactid}"/>" />
                <input name="artifactrefid" type="hidden" value="<c:out value="${artifactrefid}"/>" />
                <input name="parentartifactid" type="hidden" value="<c:out value="${parentartifactid}"/>" />
                
                
                <table border="0">
                    
                    <tbody>
                        <tr>
                            <td width="50%"><bean:message key="Description" /></td>
                            <td width="50%"><html:text property="description" title="Description" size="50"/></td>
                        </tr>
                        <tr>
                            <td  width="50%"><bean:message key="file.select"/></td>
                            <td  width="50%"><html:file property="attachmentF" size="50"/></td>
                        </tr>
                        <tr>
                            <td  width="50%">&nbsp;</td>
                            <td  width="50%"><html:submit property="Submit" value="Upload File"/></td>
                        </tr>
                    </tbody>
                </table>
                
                
            
                <!--Dir:<br/>
                <input type="text" size="50" name="dir" title="Dir"/><br/>
                Attachment File: <br/>
                <input type="text" size="50" name="attachmentFile" title="File"/-->
                <!--input type="text" size="50" name="testname"/-->
                
            </html:form>
        </c:if>
        
        <c:if test='${crud == "urllink"}'>
            <html:form action="/InsertLink" >
                
                <input name="crud" type="hidden" value="urllink" />		
                <input name="artifactid" type="hidden" value="<c:out value="${artifactid}"/>" />
                <input name="artifactrefid" type="hidden" value="<c:out value="${artifactrefid}"/>" />
                <input name="parentartifactid" type="hidden" value="<c:out value="${parentartifactid}"/>" />
                
                 <table border="0">
                    
                    <tbody>
                        <tr>
                            <td width="30%"><bean:message key="Description"/></td>
                            <td width="70%"><html:textarea property="description" title="Description" cols="50" rows="10"/></td>
                        </tr>
                        <tr>
                            <td width="30%"><bean:message key="link.source"/></td>
                            <td width="70%"><input type="text" size="50" name="urllink" title="Link"/></td>
                        </tr>
                        <tr>
                            <td width="30%">&nbsp;</td>
                            <td width="70%"><html:submit property="Submit" value="Insert Link"/></td>
                        </tr>
                    </tbody>
                </table>
                
                
                
                
            </html:form>
        </c:if>
        
        <c:if test='${crud == "read"}'>
            <c:out value="${controls}" escapeXml="false"/>
        </c:if>
        <c:if test='${crud == "delete"}'>
            <html:form action="/SaveArtifact"> 
                <input name="crud" type="hidden" value="delete" />		
                <input name="artifactid" type="hidden" value="<c:out value="${artifactid}"/>" />		
                <html:submit property="Submit" value="Delete"/>
                <c:out value="${controls}" escapeXml="false"/>
                <html:submit property="Submit" value="Delete"/>
            </html:form>
        </c:if>
        <!--<font size="small">Logged in: <c:out value="${user.userLogin}"/></font>-->	
    </body>
</html>