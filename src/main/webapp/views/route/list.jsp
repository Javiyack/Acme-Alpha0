<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<security:authorize access="isAuthenticated()">
    <security:authentication property="principal" var="logedActor"/>
    <security:authentication property="principal.authorities[0]"
                             var="permiso"/>
    <jstl:set var="rol" value="${fn:toLowerCase(permiso)}"/>
</security:authorize>
<jstl:if test="${pageSize == null}">
    <jstl:set value="20" var="pageSize"/>
</jstl:if>
<jstl:if test="${actorForm != null}">
    <jstl:set value="actor/display.do" var="requestUri"/>
</jstl:if>
<div class="seccion w3-light-grey">
    <legend>
        <spring:message code="label.routes"/>
    </legend>

    <jstl:if test="${!routes.isEmpty()}">
        <form:form action="${requestUri}" method="GET">
        <spring:message code="pagination.size"/>
        <input hidden="true" name="word" value="${word}">
        <input hidden="true" name="actorId" value="${route.id}">
        <input type="number" name="pageSize" min="1" max="100"
               value="${pageSize}">
        <input type="submit" value=">">
    </form:form>
    </jstl:if>
    <div style="overflow-x:auto;">
        <display:table pagesize="${pageSize}"
                       class="flat-table flat-table-1 w3-light-grey" name="routes"
                       requestURI="${requestUri}" id="row">
            <jstl:set var="owns"                value="${logedActor.id == row.user.userAccount.id}"/>
            <jstl:set value="route/display.do?routeId=${row.id}" var="rowUrl"/>
            <jstl:if test="${owns}">
                <jstl:set value="route/user/edit.do?routeId=${row.id}" var="rowUrl"/>
            </jstl:if>
            <acme:column property="${row.name}" title="label.name" sortable="true" rowUrl="${rowUrl}"/>
            <acme:column property="${row.description}" title="label.description" sortable="true" rowUrl="${rowUrl}"/>
            <acme:column property="${row.length}" title="label.length" sortable="true" rowUrl="${rowUrl}"/>
            <jstl:if test="${owns}">
                <acme:column property=" " title="label.none" sortable="true"
                             icon="fa fa-edit w3-xlarge" rowUrl="${rowUrl}"/>
            </jstl:if>
            <jstl:if test="${!owns}">
                <acme:column property=" " title="label.none" sortable="true"
                             icon="fa fa-eye w3-xlarge" rowUrl="${rowUrl}"/>
            </jstl:if>

        </display:table>
    </div>
    <p>
        <i class="w3-bar-item fa fa fa-edit w3-xxxlarge w3-text-dark-grey w3-hover-text-light-blue iButton"
           onclick="relativeRedir('route/user/create.do');"></i>
    </p>


</div>