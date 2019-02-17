<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF8">
<title>Artifact Tree</title>
<link rel="stylesheet" type="text/css" href="osrmt.css" />
<script src="osrmt.js"></script>
</head>
<%@ include file="/custom/header.htm"%>
<body><html:errors/>
<%@ include file="/custom/menu.html"%>        
<table class="navTable" cellpadding="1" cellspacing="0" width="100%">
  <tbody>
    <tr>
      <td width="4">&nbsp;</td>
      <td>
<span>
<c:set var="headloop" value="0"/>
<c:forEach items="${parentlist}" var="artifact" >
	<!--  View Link -->
	<c:url value="/ArtifactTree.do" var="viewurl">
		<c:param name="productrefid" value="${artifact.productRefId}"/>
		<c:param name="artifactid" value="${artifact.artifactId}"/>
		<c:param name="artifactrefid" value="${artifact.artifactRefId}"/>
		<c:param name="crud" value="read"/>
	</c:url>
	<c:if test='${headloop > 0}'>
	.
	</c:if>
	<c:set var="headloop" value="1"/>
	<a href="<c:out value="${viewurl}"/>"><c:out value="${artifact.artifactName}"/></a>
</c:forEach>				      
</span>
      </td>
      <td align="right">
      <!--  <nobr>
      <form name="searchform" action="/actions/search"
 method="get"><input value="comp.lang.java.gui"
 name="group" type="hidden"><input
 class="searchinput" type="textfield"><input
 class="bt" value="Search" name="searchtree"
 type="submit"></form>
      </nobr> --></td>
      <td></td>
    </tr>
  </tbody>
</table>
<table class="lsh" cellpadding="1" cellspacing="0"
 width="100%">
  <tbody>
    <tr class="fontsize2">
      <td>&nbsp;
      </td>
      <td align="right">
<!--  
<c:if test='${selectedartifact.artifactId > 0}'>
	<c:url value="/IssueDetail.do" var="feedbackurl">
		<c:param name="artifactid" value="${selectedartifact.artifactId}"/>
		<c:param name="artifactrefid" value="${selectedartifact.artifactRefId}"/>
		<c:param name="productrefid" value="${selectedartifact.productRefId}"/>
		<c:param name="issuetyperefid" value="${com.osrmt.modellibrary.reference.group.IssueTypeGroup.FEEDBACK}"/>
		<c:param name="crud" value="create"/>
	</c:url>
	<div id="DivFeedback" style="display:block;"><a href="#" onclick="DivHideDisplay('DivFeedback','DivFeedbackOptions'); return false;">Feedback</a></div> 
	<div id="DivFeedbackOptions" style="display:none;">
		<a target="feedbackFrame" href="<c:out value="${feedbackurl}"/>">new feedback</a>&nbsp; | &nbsp;
		<a target="feedbackFrame" href="<c:out value="${feedbackurl}"/>">view feedback</a>&nbsp; | &nbsp;
		<a target="feedbackFrame" href="<c:out value="${feedbackurl}"/>">feedback history</a>
	</div>
</c:if>
-->      
      </td>
      <td width="4">&nbsp;</td>
    </tr>
  </tbody>
</table>
<c:forEach items="${artifactlist}" var="artifact" >
	<!--  View Link -->
	<c:url value="/ArtifactTree.do" var="viewurl">
		<c:param name="productrefid" value="${artifact.productRefId}"/>
		<c:param name="artifactid" value="${artifact.artifactId}"/>
		<c:param name="artifactrefid" value="${artifact.artifactRefId}"/>
		<c:param name="crud" value="read"/>
	</c:url>
	<a href="<c:out value="${viewurl}"/>"><c:out value="${artifact.artifactName}"/></a>
<br/>
</c:forEach>

<br>
<br>
<c:if test='${selectedartifact.artifactId > 0}'>  
<div class="description">
<p><c:out value="${selectedartifact.description}"/></p>
</div><br><br>
            <bean:message key="menu.line"/>
            <br>
            
                            <!--  'Add Attachment' Link -->
                <%--c:url value="/ArtifactDetail.do" var="editurl">
                    <c:param name="artifactid" value="${selectedartifact.artifactId}"/>
                    <c:param name="crud" value="attachment"/>
                </c:url>
                <a target="detailFrame" href="<c:out value="${editurl}"/>">add attachment</a--%>
                
                <!--  'Insert Link' Link -->
                <!--uncomment to add functionality of adding attachments-->
                <%--c:url value="/ArtifactDetail.do" var="editurl">
                    <c:param name="artifactid" value="${selectedartifact.artifactId}"/>
                    <c:param name="crud" value="urllink"/>
                </c:url>
                <a target="detailFrame" href="<c:out value="${editurl}"/>">Insert Link</a--%>
                
                <!--  'Attachments' Link -->
                <c:url value="/ListAttachments.do" var="editurl">
                    <c:param name="crud" value="attachments"/>
                    <c:param name="artifactid" value="${selectedartifact.artifactId}"/>
                    <c:param name="artifactrefid" value="${selectedartifact.artifactRefId}"/>
                    <c:param name="parentartifactid" value="${selectedartifact.artifactId}"/>
                    <c:param name="productrefid" value="${selectedartifact.productRefId}"/>
                    
                </c:url>
                <a target="detailFrame" href="<c:out value="${editurl}"/>">Attachments</a>
                
                <!--  'Add Attachment' Link -->
                <!--uncomment to add functionality of adding attachments-->
                <c:url value="/ListAttachments.do" var="editurl">
                    <c:param name="crud" value="attachment"/>
                    <c:param name="artifactid" value="${selectedartifact.artifactId}"/>
                    <c:param name="artifactrefid" value="${selectedartifact.artifactRefId}"/>
                    <c:param name="parentartifactid" value="${selectedartifact.artifactId}"/>
                    <c:param name="productrefid" value="${selectedartifact.productRefId}"/>
                    
                </c:url>
                <a target="detailFrame" href="<c:out value="${editurl}"/>">Add attachment</a>
                
	<!--  Detail Link -->
	<c:url value="/ArtifactDetail.do" var="detailurl">
		<c:param name="artifactid" value="${selectedartifact.artifactId}"/>
		<c:param name="crud" value="read"/>
	</c:url>
	<a target="detailFrame" href="<c:out value="${detailurl}"/>">details</a>
	<c:if test='${artifactprivilege.delete}'>
	<!--  Edit Link -->
	<c:url value="/ArtifactDetail.do" var="editurl">
		<c:param name="artifactid" value="${selectedartifact.artifactId}"/>
		<c:param name="crud" value="update"/>
	</c:url>
	<a target="detailFrame" href="<c:out value="${editurl}"/>">edit</a>
	<!--  Delete Link -->
	<c:url value="/ArtifactDetail.do" var="deleteurl">
		<c:param name="artifactid" value="${selectedartifact.artifactId}"/>
		<c:param name="crud" value="delete"/>
	</c:url>
	<a target="detailFrame" href="<c:out value="${deleteurl}"/>">delete</a>
	<!--  Child Link -->
	<c:url value="/ArtifactDetail.do" var="childurl">
		<c:param name="parentartifactid" value="${selectedartifact.artifactId}"/>
		<c:param name="artifactrefid" value="${selectedartifact.artifactRefId}"/>
		<c:param name="crud" value="create"/>
	</c:url>
	<a target="detailFrame" href="<c:out value="${childurl}"/>">new</a>
	</c:if>
</c:if>
<c:if test='${selectedartifact.artifactId < 1}'>  
   <c:if test='${selectedartifact.artifactRefId > 0}'>  
	<c:url value="/ArtifactDetail.do" var="childurl">
		<c:param name="parentartifactid" value="${selectedartifact.artifactId}"/>
		<c:param name="artifactrefid" value="${selectedartifact.artifactRefId}"/>
		<c:param name="crud" value="create"/>
	</c:url>
	<a target="detailFrame" href="<c:out value="${childurl}"/>">new</a>
   </c:if>
</c:if>
</body>
</html>