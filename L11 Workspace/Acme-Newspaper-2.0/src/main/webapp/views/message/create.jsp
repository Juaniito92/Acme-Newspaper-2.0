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

<form:form action="message/edit.do" modelAttribute="m">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="sender" />

 	<form:label path="priority">
		<spring:message code="ms.priority" />:
	</form:label>
	<form:select path="priority">
		<form:option value="0" label="----" />
		<form:option value="HIGH" label="HIGH" />
		<form:option value="NEUTRAL" label="NEUTRAL" />
		<form:option value="LOW" label="LOW" />
	</form:select>
	<form:errors cssClass="error" path="priority" />
	<br />
	<br />

	<form:label path="recipient">
		<spring:message code="ms.recipient" />:
	</form:label>
	<form:select id="actors" path="recipient">
		<form:option value="0" label="----" />
		<form:options items="${actors}" itemValue="id" itemLabel="userAccount.username" />
	</form:select>
	<form:errors cssClass="error" path="recipient" />
	<br />
	<br />

	<form:label path="subject">
		<spring:message code="ms.subject" />:
	</form:label>
	<form:input path="subject" />
	<form:errors cssClass="error" path="subject" />
	<br />
	<br /> 

	<form:textarea path="body" />
	<br />	

	<input type="submit" name="save"
		value="<spring:message code="ms.send"/>" />&nbsp;

</form:form>