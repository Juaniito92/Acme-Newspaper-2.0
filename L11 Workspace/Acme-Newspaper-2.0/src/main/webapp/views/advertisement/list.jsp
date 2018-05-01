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

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="advertisements" requestURI="advertisement/agent/list.do"
	id="row">

	<!-- Action links -->
	<!-- Attributes -->

	<%-- <display:column>
		<a href="advertisement/agent/edit.do?advertisementId=${row.id}"><spring:message
				code="advertisement.edit" /></a>
	</display:column> --%>

	<%-- <spring:message code="advertisement.bannerURL" var="bannerURL" />
	<display:column property="bannerURL" title="${bannerURL}"
		sortable="false" /> --%>

	<spring:message code="advertisement.infoPageLink" var="infoPageLink" />
	<display:column property="infoPageLink" title="${infoPageLink}"
		sortable="false" />

	<spring:message code="advertisement.creditCard" var="creditCard" />
	<display:column property="creditCard.brandName" title="${creditCard}"
		sortable="false" />
		
		<spring:message code="advertisement.trip" var="trip" />
	<display:column property="trip.title" title="${trip}"
		sortable="false" />
		
	<display:column>
			<a href="advertisement/agent/display.do?advertisementId=<jstl:out value="${row.getId()}"/>"><spring:message code="trip.display" /></a><br/>
	</display:column>
</display:table>

<a href="advertisement/agent/create.do"><spring:message
		code="advertisement.create" /></a>


