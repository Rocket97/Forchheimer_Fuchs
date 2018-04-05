<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Mitglied bearbeiten
    </jsp:attribute>

   <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/user_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/home/"/>" class="icon-home">Home</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form method="GET" class="stacked">
                
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">
            
                <div class="column">

                    <%-- Suchfelder --%>    
              
                    <label for="search_mitgliedsnummer">
                        Mitgliedsnummer:
                    </label>
                    <div class="side-by-side">
                        <input type="number" name="search_mitgliedsnummer" value="${param.search_mitgliedsnr}}">
                    </div>
                    
                    <label for="search_nachname">
                        Nachname:
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="search_nachname" value="${param.search_nachname}">
                    </div>
                    
                    <label for="search_vorname">
                        Vorname:
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="search_vorname" value="${param.search_vorname}">
                    </div>
                   
                    <label for="search_username">
                        Nutzername:
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="search_username" value="${param.search_username}">
                    </div>

                    
           
                    <%-- Button zur Suche --%>
                    <div class="side-by-side">
                        <button class="icon-search" type="submit">
                            Suchen
                        </button>
                    </div>
                </div>
                
                <c:choose>
                    <c:when test="${empty users}">
                        <p>
                            Es wurden keine Benutzer gefunden. 🐈
                        </p>
                    </c:when>
                    <c:otherwise>
                       <jsp:useBean id="utils" class="com.dh.forchheimer_fuchs.web.WebUtils"/> <%-- eventuell funktioniert das nicht, ich bin mir nicht genau sicher, was es macht --%>

                        <table>
                            <thead>
                                <tr>
                                    <th>Benutzername</th>
                                    <th>Vorname</th>
                                    <th>Nachname</th>
                                    <th>Mitgliedsnummer</th>
                                </tr>
                            </thead>
                            <c:forEach items="${users}" var="user">
                                <tr>
                                    <td>
                                        <a href="<c:url value="/user/${user.benutzername}/"/>">
                                            <c:out value="${user.benutzername}"/>
                                        </a>
                                    </td>
                                    <td>
                                        <c:out value="${user.vorname}"/>
                                    </td>
                                    <td>
                                        <c:out value="${user.nachname}"/>
                                    </td>
                                    <td>
                                        <c:out value="${user.mitgliedsnr}"/>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>    
                
                <%-- Fehlermeldungen --%>
                <c:if test="${!empty user_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${user_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base>