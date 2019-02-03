<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="org.apache.struts.validator.DynaValidatorForm"%>


<%
Date now = new Date();
Calendar c = Calendar.getInstance();
java.text.DateFormat df = java.text.DateFormat
        .getDateInstance(java.text.DateFormat.FULL);
//System.out.println(df.format(now));
String date = df.format(now);
String subject = date + " Report from capabilities repository";
%>

<%--html:form action="mailto:kowalczykj@tt.com.pl" enctype="multipart/form-data"--%>
<html:form action="/SendMail" enctype="multipart/form-data">
    <%--input name="crud" type="hidden" value="attachment" />		
                <input name="artifactid" type="hidden" value="<c:out value="${artifactid}"/>" />
                <input name="artifactrefid" type="hidden" value="<c:out value="${artifactrefid}"/>" />
                <input name="parentartifactid" type="hidden" value="<c:out value="${parentartifactid}"/>" /--%>
    <%--
    <html:hidden property="auth" />
    <html:hidden property="ssl" />
    <html:hidden property="emailAddressFrom" />
    <html:hidden property="passwd" />
    <html:hidden property="host" />
    --%>
    <input type="hidden" name="filename2" value="<c:out value="${filename2}"/>"/>
    
    <%=date%>
    <table border="0">
        
        <tbody>
            <%--
				<tr>
				<td width="50%"><bean:message key="mail.address.from"/></td>
				<td width="50%"><html:text property="emailAddressFrom" title="E-Mail address" size="50"/></td>
				</tr>
			--%>
            <tr>
                <td width="50%"><bean:message key="mail.address.to" /></td>
                <td width="50%"><html:text property="emailAddressTo"
                                           title="E-Mail address" size="50" /></td>
            </tr>
            
            <tr>
                <td width="50%"><bean:message key="mail.subject" /></td>
                <td width="50%"><html:text property="subject" title="Subject"
                                           size="50" value="<%=subject%>" /></td>
            </tr>
            <tr>
                <td width="50%"><bean:message key="mail.body" /></td>
                <td width="50%"><html:textarea property="messageBody"
                                               title="Message Body" rows="10" cols="37" /></td>
            </tr>
            
            
            <tr>
                <td width="50%">&nbsp;</td>
                <td width="50%"><html:submit property="Submit"
                                             value="Send e-mail" /></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
        </tbody>
    </table>
    
</html:form>
