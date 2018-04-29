<%-- list.jsp de Application --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h3>
	<jstl:choose>
		<jstl:when test="${requestURI == 'volume/list.do'  }">
			<spring:message code="volume.allVolumes" />
		</jstl:when>
	</jstl:choose>
</h3>

<display:table name="volumes" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
	<security:authorize access="hasRole('USER')">
	<jstl:if test="${row.user.userAccount.id eq loggedactor.id}">
		<a href="volume/user/edit.do?volumeId=${row.id}"><spring:message
					code="volume.edit" /></a>
	</jstl:if>
	</security:authorize>

	<display:column>
		<jstl:if test="${not empty row.newspapers}">
			<a href="newspaper/list.do?volumeId=${row.id}"><spring:message
					code="volume.newspapers" /></a>
		</jstl:if>
	</display:column>

	<spring:message var="titleHeader" code="volume.title" />
	<display:column property="title" title="${titleHeader}" />

	<spring:message var="descriptionHeader" code="voluem.description" />
	<display:column property="description" title="${descriptionHeader}" />

	<spring:message var="yearHeader" code="volume.year" />
	<display:column property="year" title="${yearHeader}" sortable="true" />

	<jstl:if test="${requestURI == 'volume/list.do'}">
		<spring:message var="userHeader" code="volume.user" />
		<display:column title="${userHeader}" sortable="true">
			<a href="user/display.do?userId=${row.user.id}"><jstl:out
					value="${row.user.name} ${row.user.surname}" /></a>
		</display:column>
	</jstl:if>

</display:table>

<security:authorize access="hasRole('USER')">
	<a href="voluem/user/create.do"><spring:message
			code="volume.create" /></a>
	<br />
</security:authorize>

<spring:message var="backValue" code="volume.back" />
<input type="button" name="back" value="${backValue}"
	onclick="javascript: relativeRedir('welcome/index.do');" />