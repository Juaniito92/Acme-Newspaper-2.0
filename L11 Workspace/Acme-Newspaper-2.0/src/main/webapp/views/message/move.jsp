<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%-- <script type="text/javascript">

value = $("#folderSelection").val();

</script>
<form:form action="message/edit.do" modelAttribute="m">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="sender" />
	<form:hidden path="recipient" />
	<form:hidden path="subject" />
	<form:hidden path="body" />
	<form:hidden path="priority" />

	<label> <spring:message code="folder.folders" />:
	</label>


	<select id="folderSelection" name="choosedFolder">
		<!-- <option value="0" label="----" /> -->
		<jstl:forEach items="${folders}" var="folder">
			<option value="${folder.id}" >
				<jstl:out value="${folder.name}" />
			</option>
		</jstl:forEach>
	</select>
	
	<input type="submit" name="move" value="<spring:message code="ms.move" />"/> --%>
	<%-- <input type="button" name="move"
		value="<spring:message code="ms.move" />"
		onclick="javascript: relativeRedir('message/saveMove.do?messageId=<jstl:out value="${m.getId()}"/>&folderId=<jstl:out value="${choosedFolder.id}"/>');" /> --%>


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="folders"  id="row" requestURI="message/move.do">
	
	<spring:message code="ms.folderName" var="folderName" />
	<display:column property="name" title="${folderName}"/>
	<display:column>
	<jstl:choose>
	<jstl:when test="${folder.name eq row.name}">
	<spring:message code="ms.alreadyIn" />
	</jstl:when>
	<jstl:otherwise>
	<a href="message/saveMove.do?folderId=${row.id}&messageId=${m.getId()}"><spring:message
						code="ms.move" /></a>
	</jstl:otherwise>
	</jstl:choose>
	</display:column>
</display:table>