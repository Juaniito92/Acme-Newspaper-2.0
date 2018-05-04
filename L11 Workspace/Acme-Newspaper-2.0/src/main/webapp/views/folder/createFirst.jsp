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

<form:form action="folder/editFirst.do" modelAttribute="folder">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="predefined" />
	<form:hidden path="messages" />
	<form:hidden path="actor" />

	<form:label path="name">
		<spring:message code="folder.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	<br />
	<input type="submit" name="saveFirst"
		value="<spring:message code="folder.save"/>" />&nbsp;
	<input type="button" name="back"
		value="<spring:message code="folder.back"/>"
		onclick="javascript:relativeRedir('folder/list.do')" />
</form:form>