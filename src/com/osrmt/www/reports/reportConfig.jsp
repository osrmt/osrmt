<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>


<table border="0">
    <tr>
        <td align="center">
            <html:form action="/BuildReport">
                <table border="0">
                    <tr>
                        <td>File name: </td>
                        <td><input type="text" name="fileName" size="30" value="<c:out value="${fileName}"/>"/></td>
                    </tr>
                    <!--List of additional parameters-->
                    <c:forEach var="params" items="${BuildReportActionForm.map.params}">
                        <tr>
                            <td><c:out value="${params.name}"/></td>
                            <td><c:choose>
                                    <c:when test="${params.list==false}">
                                        <html:text name="params" indexed="true" property="value" value=""/>
                                    </c:when>
                                    <c:otherwise>
                                        <html:select name="params" property="value" indexed="true">
                                            <c:forEach items="${params.dropDownValues}" var="var">
                                                <option value="<c:out value="${var}"/>"> <c:out value="${var}" /></option>
                                            </c:forEach> 
                                        </html:select>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    <!--End of list-->
                    <tr>
                        <td colspan="2"><html:checkbox property="mail" value="true">Send e-mail with report as attachment</html:checkbox></td>
                    </tr>
                    <tr>
                        <td><input type="hidden" name="reportid" value="<c:out value="${reportid}"/>"/>
                            <input type="hidden" name="type" value="<c:out value="${type}"/>"/>
                            <html:hidden property="step" value="2"/>
                        </td>
                        <td><html:submit value="Generate Report"/></td>
                    </tr>
                </table>
            </html:form>
        </td>
    </tr>
</table>



