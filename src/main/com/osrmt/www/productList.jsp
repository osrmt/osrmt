<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<html>
<head>
<title>Artifact Tree Frame</title>
<link rel="stylesheet" type="text/css" href="osrmt.css" />
</head>

<%@ include file="/custom/header.htm"%>
<body>
<html:errors/>

Select a product:
<table>
<logic:iterate id="product" name="productList" property="products">
  <tr>
    <td align="left">
    <c:url value="/ArtifactTree.do" var="url">
		<c:param name="productrefid" value="${product.refId}"/>
	</c:url>   
	<a href="<c:out value="${url}"/>"><c:out value="${product.display}"/></a>   
    </td>
  </tr>
</logic:iterate>
</table>
</body>
</html>