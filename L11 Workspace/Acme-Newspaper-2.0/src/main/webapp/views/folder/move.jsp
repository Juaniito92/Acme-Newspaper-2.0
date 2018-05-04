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




<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="folders" id="row" requestURI="folder/move.do">
	<spring:message code="folder.folder" var="folderHeader" />
	<display:column property="name" title="${ folderHeader}" />
	<display:column>
		<jstl:choose>
			<jstl:when test="${folder.name eq row.name}">
				<spring:message code="folder.samefolder" />
			</jstl:when>
			<jstl:otherwise>
				<a href="folder/saveMove.do?targetfolderId=${row.id}&folderId=${folder.getId()}"><spring:message
						code="folder.move" /></a>
						
						
				
						
						
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
</display:table>