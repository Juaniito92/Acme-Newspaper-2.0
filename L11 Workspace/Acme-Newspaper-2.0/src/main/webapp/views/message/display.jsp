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

<ul style="list-style-type: disc">

	<li><b><spring:message code="ms.priority"></spring:message>:</b> <jstl:out
			value="${m.priority}" /></li>

	<li><b><spring:message code="ms.sender"></spring:message>:</b> <jstl:out
			value="${m.sender.userAccount.username}" /></li>

	<li><b><spring:message code="ms.recipient"></spring:message>:</b>
		<jstl:out value="${m.recipient.userAccount.username}" /></li>

	<li><b><spring:message code="ms.subject"></spring:message>:</b> <jstl:out
			value="${m.subject}" /></li>

	<li><b><spring:message code="ms.body"></spring:message>:</b> <jstl:out
			value="${m.body}" /></li>
</ul>



<input type="button" name="back"
	value="<spring:message code="ms.back" />"
	onclick="javascript: relativeRedir('folder/display.do?folderId=${folder.id}')" />

<input type="button" name="move"
	value="<spring:message code="ms.move" />"
	onclick="javascript: relativeRedir('message/move.do?messageId=<jstl:out value="${m.getId()}"/>');" />


<input type="button" name="delete"
	value="<spring:message code="ms.delete" />"
	onclick="javascript: relativeRedir('message/delete.do?messageId=<jstl:out value="${m.getId()}"/>');" />

