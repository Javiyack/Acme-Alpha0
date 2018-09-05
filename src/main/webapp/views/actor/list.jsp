<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<%@page import="org.apache.commons.lang.time.DateUtils"%>
<%@page import="org.hibernate.engine.spi.RowSelection"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<spring:message code="msg.delete.confirmation" var="deleteConfirmation" />
<div class="seccion  w3-flat-green-sea">
	<ul class="w3-ul">

		<jstl:forEach items="${actors}" var="row">
			<jstl:set var="url" value="actor/activate.do?actorId=${row.id}" />

			<li class="w3-display-container">
				<div class="w3-row">
					<div class="w3-col m2">
						<a href="actor/display.do?actorId=${row.id}"
							class="w3-button w3-transparent"> <jstl:out
								value="${row.userAccount.username}" />
						</a>
					</div>
					<div class="w3-col m6">
						<a href="actor/display.do?actorId=${row.id}"
							class="w3-button w3-transparent"><jstl:out
								value="${row.surname}" />, <jstl:out
								value="${row.name}" />
						</a>
					</div>
					<div class="w3-col m3">
						<spring:message code="date.pattern" var="datePattern" />
							<a href="actor/display.do?actorId=${row.id}"
							class="w3-button w3-transparent">
							<fmt:formatDate pattern="${datePattern}" value="${row.registrationMoment}" />
							 
						</a>

					</div>
					<div class="w3-col m1">
						<jstl:if test="${!row.userAccount.active}">
							<a href="${url}" class="w3-transparent"> <i
								class="fa fa-toggle-off w3-xlarge w3-text-grey"></i></a>
						</jstl:if>
						<jstl:if test="${row.userAccount.active}">
							<a href="${url}" class="w3-transparent"> <i
								class="fa fa-toggle-on w3-xlarge"></i></a>
						</jstl:if>
					</div>
					<div class="w3-col"></div>
				</div>






			</li>
		</jstl:forEach>
	</ul>
</div>
<br />
<acme:button url="/"
	text="label.back" css="formButton toLeft w3-padding" />

