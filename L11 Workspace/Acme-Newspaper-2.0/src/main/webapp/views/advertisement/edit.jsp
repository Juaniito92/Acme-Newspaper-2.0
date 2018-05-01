
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="advertisement/agent/edit.do" modelAttribute="advertisement">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="agent" />

	<form:label path="bannerURL">
		<spring:message code="advertisement.bannerURL" />:
	</form:label>
	<form:input path="bannerURL" />
	<form:errors cssClass="error" path="bannerURL" />
	<br/>
	
	
	<form:label path="infoPageLink">
		<spring:message code="advertisement.infoPageLink" />:
	</form:label>
	<form:input path="infoPageLink" />
	<form:errors cssClass="error" path="infoPageLink" />
	<br/>

	<%-- <form:label path="creditCard">
		<spring:message code="advertisement.creditCard"/>:
	</form:label>
	<form:select path="creditCard">
		<form:option value="0">----</form:option>
		<form:options
			items="${creditCards}"
			itemLabel="number"
			itemValue="id"
		/>
	</form:select>
	<form:errors cssClass="error" path="creditCard"/>
	Si la lista de tarjetas esta vacia, debemos crear una nueva
	<jstl:if test="${empty creditCards}">
		<a href="creditCard/manager/create.do"><spring:message code="advertisement.create"/></a>
	</jstl:if> --%>
	<fieldset>
	<legend><spring:message code="advertisement.introduceCreditCard"/></legend><br/>
	<form:hidden path="creditCard.id" />
	<form:hidden path="creditCard.version" />
	
	<form:label path="creditCard.holderName">
		<spring:message code="creditCard.holderName" />:
	</form:label>
	<form:input path="creditCard.holderName" />
	<form:errors cssClass="error" path="creditCard.holderName" />
	<br/>
	
	<form:label path="creditCard.brandName">
		<spring:message code="creditCard.brandName" />:
	</form:label>
	<form:input path="creditCard.brandName" />
	<form:errors cssClass="error" path="creditCard.brandName" />
	<br/>

	<form:label path="creditCard.number">
		<spring:message code="creditCard.number"/>:
	</form:label>
	<form:input path="creditCard.number" />
	<form:errors cssClass="error" path="creditCard.number"/>
	<br/>

	<form:label path="creditCard.expirationMonth">
		<spring:message code="creditCard.expirationMonth" />:
	</form:label>
	<form:input path="creditCard.expirationMonth" />
	<form:errors cssClass="error" path="creditCard.expirationMonth" />
	<br/>

	<form:label path="creditCard.expirationYear">
		<spring:message code="creditCard.expirationYear" />:
	</form:label>
	<form:input path="creditCard.expirationYear" />
	<form:errors cssClass="error" path="creditCard.expirationYear" />
	<br/>

	<form:label path="creditCard.CVV">
		<spring:message code="creditCard.CVV" />:
	</form:label>
	<form:input path="creditCard.CVV" />
	<form:errors cssClass="error" path="creditCard.CVV" />
	<br/>
	</fieldset>

	<br/>
	
	<form:label path="trip">
		<spring:message code="advertisement.trip"/>:
	</form:label>
	<form:select path="trip">
		<form:option value="0">----</form:option>
		<form:options
			items="${trips}"
			itemLabel="title"
			itemValue="id"
		/>
	</form:select>
	<form:errors cssClass="error" path="trip"/>
	
	<br/>
	
	<input type="submit" name="save"
		value="<spring:message code="advertisement.save" />" />&nbsp; 

	<%-- SOLO SE PUEDE ELIMINAR SI ESTAMOS EDITANDO, NO SI ESTAMOS CREANDO --%>
	<jstl:if test="${advertisement.id != 0}">
	<input type="submit" name="delete"
		value="<spring:message code="advertisement.delete" />"
		onclick="return confirm('<spring:message code="advertisement.confirm.delete" />')" />&nbsp; 
	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="advertisement.cancel" />"
		onclick="javascript: relativeRedir('advertisement/agent/list.do');" />

</form:form>
