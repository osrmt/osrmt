<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>


<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Attachments</title>
        <link rel="stylesheet" type="text/css" href="osrmt.css" />
    </head>
    <body>
        
        <h1>Attachments</h1>
        
        <bean:message key="mainMenu.title" /><br/>
        <bean:message key="menu.line"/><br/>
        <c:set var="myvalue" value="${param.crud}"/>
        <%--
        param cout  <c:out value="${param.crud}"/><br>
         param ${param.crud} <br>
         
        atr <c:out value="${artifactid}"/><br>
        atr <c:out value="${artifactrefid}"/><br>
        atr <c:out value="${parentartifactid}"/><br>
        <br>-----------------------------------------------------------------------------------------------------------<br/>
        
        
        
        atr bean <bean:write name="artifactid"/><br/>
        atr bean <bean:write name="artifactrefid"/><br/>
        atr bean <bean:write name="parentartifactid"/><br/>
        
        <br>-----------------------------------------------------------------------------------------------------------<br/>
        
        --%>
        
        <c:url value="/ArtifactTree.do" var="editurl">
            <c:param name="crud" value="read"/>
            <c:param name="artifactid" value="${artifactid}"/>
            
            <c:param name="artifactrefid" value="${artifactrefid}"/>
            <c:param name="parentartifactid" value="${parentartifactid}"/>
            <c:param name="productrefid" value="${productrefid}"/>
        </c:url>
        <a target="detailFrame" href="<c:out value="${editurl}"/>">Go to Artifact Tree </a>
        
        
        <br/>
        <bean:message key="menu.line"/>
        <br/>
        
        <c:if test='${param.crud == "attachment"}'>
            <html:form action="/AddAttachment" enctype="multipart/form-data">
                
                <input name="crud" type="hidden" value="attachment" />		
                <input name="artifactid" type="hidden" value="<c:out value="${artifactid}"/>" />
                <input name="artifactrefid" type="hidden" value="<c:out value="${artifactrefid}"/>" />
                <input name="parentartifactid" type="hidden" value="<c:out value="${parentartifactid}"/>" />
                
                
                <table border="0">
                    
                    <tbody>
                        <tr>
                            <td width="30%"><bean:message key="Description" /></td>
                            <td width="70%"><html:text property="description" title="Description" size="50"/></td>
                        </tr>
                        <tr>
                            <td  width="30%"><bean:message key="file.select"/></td>
                            <td  width="70%"><html:file property="attachmentF" size="50"/></td>
                        </tr>
                        <tr>
                            <td  width="30%">&nbsp;</td>
                            <td  width="70%"><html:submit property="Submit" value="Upload File"/></td>
                        </tr>
                    </tbody>
                </table>                
            </html:form>
        </c:if>
        
        <c:if test='${param.crud == "urllink"}'>
            <html:form action="/InsertLink" >
                
                <input name="crud" type="hidden" value="urllink" />		
                <input name="artifactid" type="hidden" value="<c:out value="${artifactid}"/>" />
                <input name="artifactrefid" type="hidden" value="<c:out value="${artifactrefid}"/>" />
                <input name="parentartifactid" type="hidden" value="<c:out value="${parentartifactid}"/>" />
                
                <table border="0" width="100%" >
                    
                    <tbody>
                        <tr>
                            <td width="30%"><bean:message key="Description"/></td>
                            <td width="70%"><html:textarea property="description" title="Description" cols="37" rows="10"/></td>
                        </tr>
                        <tr>
                            <td width="30%"><bean:message key="file.name"/></td>
                            <td width="70%"><html:text property="filename" title="File name" size="50"/></td>
                        </tr>
                        
                        
                        <tr>
                            <td width="30%"><bean:message key="link.source"/></td>
                            <td width="70%"><html:text property="urllink" size="50" title="Link"/></td>
                        </tr>
                        <tr>
                            <td width="30%">&nbsp;</td>
                            <td width="70%"><html:submit property="Submit" value="Insert Link"/></td>
                        </tr>
                    </tbody>
                                
            </html:form>
        </c:if>
        
        <br/>
        <bean:message key="menu.line"/><br/>        
        
        
        <table border="1" width="100%">
            <thead>
                <caption>
                    <bean:message key="all.attachments"/>
                </caption>
                <tr>
                    <th width="50%"><bean:message key="Description"/></th>
                    <th width="25%"><bean:message key="file.name"/></th>
                    <th width="25%"><bean:message key="link.source"/></th>
                </tr>
            </thead>
            <tbody>
                <logic:iterate id="record" name="recordslist">
                    <tr>
                        <td width="25%"><bean:write name="record" property="description" />&nbsp;</td>
                        <td width="25%">
    <c:url value="/OpenAttachment.do" var="viewurl">
        <c:param name="recordfileid" value="${record.recordFileId}"/>
    </c:url>
                        <a href="<c:out value="${viewurl}"/>"><c:out value="${record.fileName}"/></a>
                        </td>
                    </tr>
                </logic:iterate>
            </tbody>
        </table>
        
        
        <html:errors/>
        <%--
    This example uses JSTL, uncomment the taglib directive above.
    To test, display the page like this: index.jsp?sayHello=true&name=Murphy
    --%>
        <%--
    <c:if test="${param.sayHello}">
        <!-- Let's welcome the user ${param.name} -->
        Hello ${param.name}!
    </c:if>
    --%>
    
    </body>
</html>
