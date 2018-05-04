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

<form:form action="notification/admin/edit.do" modelAttribute="notification">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sender" />
	

	

	<%-- <form:label path="body">
		<spring:message code="ms.body" />:
	</form:label> --%>

	<form:textarea path="body" />
	<form:errors cssClass="error" path="body"/>
	<br />

	<input type="submit" name="save"
		value="<spring:message code="ms.send"/>" />&nbsp;
	<%-- <input type="button" name="cancel"
		value="<spring:message code="message.cancel"/>"
		onclick="javascript:relativeRedir('')" /> --%>



</form:form>