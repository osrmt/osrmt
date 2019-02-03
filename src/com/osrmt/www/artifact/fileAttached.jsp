<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><bean:message key="attachment.title"/></title>
        <link rel="stylesheet" type="text/css" href="osrmt.css" />
    </head>
    <body>
        
        <h1><bean:message key="attachment.title"/></h1>
        
        <bean:message key="mainMenu.title" /><br/>
        <bean:message key="file.attached"/><br/>
        <bean:message key="file.name"/><bean:write name="fileName" /><br/>
        <bean:message key="menu.line"/><br/>
        
        
        <table border="1" >
            <thead>
                <caption>
                    <bean:message key="all.attachments"/>
                </caption>
                <tr>
                    <th width="33%"><bean:message key="Description"/></th>
                    <th width="33%"><bean:message key="file.name"/></th>
                    <th width="33%"><bean:message key="link.source"/></th>
                </tr>
            </thead>
            <tbody>
                <logic:iterate id="record" name="recordslist">
                    <tr>
                        <td width="33%"><bean:write name="record" property="description" />&nbsp;</td>
                        <td width="33%"><bean:write name="record" property="fileName" />&nbsp;</td>
                        <td width="33%">
                            <a href="<bean:write name="record" property="sourceFile" />" target="_blank">
                                <bean:write name="record" property="sourceFile" />&nbsp;
                            </a>
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
