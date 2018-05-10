<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Zusätzliche Stunden
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/home/"/>" class="icon-homehome">Home</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">

                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">

                <div class="column">
                    <label for="special_effort_titel">
                        Titel:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="special_effort_titel">
                    </div>

                    <label for="special_effort_zeit_beginn">
                        Beginn (Datum und Uhrzeit im Format 'dd.MM.yyyy hh:mm'):
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="datetime-local" name="special_effort_zeit_beginn">
                    </div>

                    <label for="special_effort_zeit_ende">
                        Ende (Datum und Uhrzeit im Format 'dd.MM.yyyy hh:mm'):
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="datetime-local" name="special_effort_zeit_ende">
                    </div>
                </div>

                <%-- Button zum Speichern --%>
                <div class="side-by-side">
                    <button class="icon-search" type="submit">
                        Suchen
                    </button>
                </div>

                <%-- Gefundene einegtragene Aufwände --%>
                <c:choose>
                    <c:when test="${empty extra_efforts}">
                        <p>
                            Keine eingetragenen Events.
                        </p>
                    </c:when>
                    <c:otherwise>
                        <jsp:useBean id="utils" class="com.dh.forchheimer_fuchs.web.WebUtils"/>

                        <table>
                            <thead>
                                <tr>
                                    <th></th>   <%-- nur als Platzhalter für die Lösch-Knöpfe--%>
                                    <th>Titel</th>
                                    <th>Beginn</th>
                                    <th>Ende</th>
                                    <th>Abteilung</th>
                                    <th>Helfer</th>
                                </tr>
                            </thead>
                            <c:forEach items="${extra_efforts}" var="extra_effort">
                                <tr>
                                    <td>
                                        <input type="checkbox" name="effort" id="${'category-'.concat(extra_effort.eventId)}" value="${extra_effort.eventId}" />
                                    </td>
                                    <td>
                                        <a href="<c:url value="/extra_effort/${extra_effort.eventId}/"/>">
                                            <c:out value="${extra_effort.eTitel}"/>
                                        </a>
                                    </td>
                                    <td>
                                        <c:out value="${extra_effort.abteilung}"/>
                                    </td>
                                    <td>
                                        <c:out value="${utils.formatTimestamp(extra_effort.beginn)}"/>
                                    </td>
                                    <td>
                                        <c:forEach items="${extra_effort.helfer}" var="person">
                                            <ol>
                                                <li>
                                                    <c:out value="${person.vorname}"/> 
                                                    <c:out value="${person.nachname}"/>
                                                </li>
                                            </ol>
                                        </c:forEach>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>
                <div>
                    <button name="action" value="delete" class="icon-trash">
                        Löschen
                    </button>
                </div>
            </form>
        </div>
    </jsp:attribute>
</template:base>
