<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<security:authorize access="isAuthenticated()">
    <jstl:set var="colom" value=", "/>
    <security:authentication property="principal.username" var="username"/>
    <security:authentication property="principal.authorities[0]"
                             var="permiso"/>
    <jstl:set var="rol" value="${fn:toLowerCase(permiso)}"/>
</security:authorize>
<!-- Menu and banner usually + "$") -->
<!-- Sidebar/menu -->
<nav
        class="w3-sidebar w3-collapse w3-flat-midnight-blue sombra"
        style="z-index: 3; width: 260px;" id="mySidebar">

    <br>
    <div class="w3-container w3-row">
        <div class="w3-col s4">
            <security:authorize access="isAuthenticated()">
                <a href="j_spring_security_logout" class=""><i
                        class="fa fa-sign-out w3-margin w3-xxlarge"></i></a>
            </security:authorize>
            <security:authorize access="isAnonymous()">
                <a href="security/login.do" class=""><i
                        class="fa fa-sign-in w3-margin w3-xxlarge"></i></a>
            </security:authorize>
        </div>
        <div class="w3-col s8 w3-bar">
            <span><spring:message code="welcome.greeting.msg"/>${colom}<strong>${username}</strong></span><br>
            <security:authorize access="isAnonymous()">
                <a href="actor/create.do" class=""><i
                        class="fa fa-user-plus w3-bar-item w3-large"></i></a>
            </security:authorize>
            <a href="folder/list.do"><i class="fa fa-envelope w3-bar-item w3-large"></i></a>
            <security:authorize access="isAuthenticated()">
                <a href="actor/edit.do"><i
                        class="fa fa-user w3-bar-item w3-large"></i></a>
            </security:authorize>
            <a href="preferences/actor/edit.do"><i class="fa fa-cog w3-bar-item w3-large"></i></a>
        </div>
    </div>
    <hr>
    <div class="w3-bar-block" style="padding-bottom: 60px">

        <a href="route/list.do"
           class="w3-bar-item w3-button w3-padding w3-xlarge"> <i
                class="fa fa-diamond fa-fw"></i>� <spring:message
                code="label.routes"/>
        </a>
        <security:authorize access="isAnonymous()">
            <a href="user/list.do" class="w3-bar-item w3-button w3-padding w3-xlarge"> <i
                class="fa fa-users fa-fw"></i>� <spring:message code="label.users"/> </a>
            <a href="user/list.do" class="w3-bar-item w3-button w3-padding w3-xlarge"> <i
                class="fa fa-users fa-fw"></i>� <spring:message code="label.users"/> </a>

        </security:authorize>

        <security:authorize access="hasRole('USER')">
        <a href="user/list.do" class="w3-bar-item w3-button w3-padding w3-xlarge"> <i
                    class="fa fa-users fa-fw"></i>� <spring:message code="label.users"/> </a>

            <a href="route/user/list.do"
               class="w3-bar-item w3-button w3-padding w3-xlarge">
                <i class="fa fa-blind fa-fw w3-xxlarge"></i><spring:message
                    code="label.my.routes"/>
            </a>
            <a href="chirp/user/list.do"
               class="w3-bar-item w3-button w3-padding w3-xlarge"> <i
                    class="fa fa-podcast fa-fw"></i>� <spring:message
                    code="label.chirp"/>
            </a>
            <a href="chirp/user/stream.do"
               class="w3-bar-item w3-button w3-padding w3-xlarge"> <i
                    class="fa fa-bell-o fa-fw"></i>� <spring:message
                    code="label.chirp"/> </a>
        </security:authorize>
        <security:authorize access="hasRole('ADMINISTRATOR')">
            <a href="user/administrator/list.do" class="w3-bar-item w3-button w3-padding w3-xlarge"> <i
                    class="fa fa-users fa-fw"></i>� <spring:message code="label.users"/> </a>
            <a href="configuration/administrator/edit.do" class="w3-bar-item w3-button w3-padding w3-xlarge"> <i
                    class="fa fa-cog fa-fw"></i>� Settings </a>
            <a href="tabooWord/administrator/list.do" class="w3-bar-item w3-button w3-padding w3-xlarge"> <i
                    class="fa fa-cog fa-fw"></i>� <spring:message
                    code="label.tabooWords"/> </a>
        </security:authorize>
        <security:authorize access="hasRole('INNKEEPER')">
            <a href="/inn/innkeeper/list.do" class="w3-bar-item w3-button w3-padding w3-xlarge">
                <i class="fa fa-home fa-fw"></i>� <spring:message code="label.inns"/> </a>
        </security:authorize>
        <br>
        <br>
    </div>

</nav>


<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large w3-animate-opacity"
     onclick="w3_close()" style="cursor: pointer" title="close side menu"
     id="myOverlay"></div>

<script>
    var mySidebar = document.getElementById("mySidebar");

    //Get the DIV with overlay effect
    var overlayBg = document.getElementById("myOverlay");

    //Toggle between showing and hiding the sidebar, and add overlay effect
    function w3_open() {
        if (mySidebar.style.display === 'block') {
            mySidebar.style.display = 'none';
            overlayBg.style.display = "none";
        } else {
            mySidebar.style.display = 'block';
            overlayBg.style.display = "block";
        }
    }

    //Close the sidebar with the close button
    function w3_close() {
        mySidebar.style.display = "none";
        overlayBg.style.display = "none";
    }

    var btnContainer = document.getElementById("myDIV");

    //Get all buttons with class="btn" inside the container
    var btns = btnContainer.getElementsByClassName("btn");

    //Loop through the buttons and add the active class to the current/clicked button
    for (var i = 0; i < btns.length; i++) {
        btns[i].addEventListener("click", function () {
            var current = document.getElementsByClassName("active");
            current[0].className = current[0].className.replace(" active", "");
            this.className += " active";
        });
    }
</script>