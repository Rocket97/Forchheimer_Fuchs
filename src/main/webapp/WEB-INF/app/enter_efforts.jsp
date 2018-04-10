<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Stunden eintragen
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/form.css"/>" />
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
                    <%-- Buttons zum Weiterleiten auf die verschiedenen Seiten als Admin --%>
                    <label for="effort_category">Kategorie:</label>
                    <div class="side-by-side">
                        <select name="effort_category">
                            <option value="">Keine Kategorie</option>

                            <c:forEach items="${categories}" var="category">
                                <option value="${category}" ${param.search_category == category ? 'selected' : ''}>
                                    <c:out value="${category.label}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>



                    <label for="efforts_zeit_beginn">
                        Beginn (Datum und Uhrzeit):
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="datetime-local" name="efforts_zeit_beginn">
                    </div>

                    <label for="efforts_zeit_ende">
                        Ende (Datum und Uhrzeit):
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="datetime-local" name="efforts_zeit_ende">
                    </div>

                    <%-- Button zum Speichern --%>
                    <div class="side-by-side">
                        <button class="icon-pencil" name="action" value="save">
                            Speichern
                        </button>
                    </div>
                    <%-- Fehlermeldungen --%>
                    <c:if test="${!empty effort_form.errors}">
                        <ul class="errors">
                            <c:forEach items="${effort_form.errors}" var="error">
                                <li>${error}</li>
                                </c:forEach>
                        </ul>
                    </c:if>
                    <%-- Gefundene einegtragene Aufwände --%>
                    <c:choose>
                        <c:when test="${empty efforts}">
                            <p>
                                Keine eingetragenen Stunden.
                            </p>
                        </c:when>
                        <c:otherwise>
                            <jsp:useBean id="utils" class="com.dh.forchheimer_fuchs.web.WebUtils"/>

                            <table>
                                <thead>
                                    <tr>
                                        <th></th>   <%-- nur als Platzhalter für die Lösch-Knöpfe--%>
                                        <th>Beginn</th>
                                        <th>Ende</th>
                                        <th>Kategorie</th>
                                        <th>Zeitspanne</th>
                                    </tr>
                                </thead>
                                <c:forEach items="${efforts}" var="effort">
                                    <tr>
                                        <td>
                                            <input type="checkbox" name="effort" id="${'category-'.concat(effort.zeitId)}" value="${effort.zeitId}" />
                                        </td>
                                        <td>
                                            <c:out value="${utils.formatTimestamp(effort.beginn)}"/>
                                        </td>
                                        <td>
                                            <c:out value="${utils.formatTimestamp(effort.ende)}"/>
                                        </td>
                                        <td>
                                            <c:out value="${effort.kategorie.label}"/>
                                        </td>
                                        <td>
                                            <c:out value="${effort.zeitspanne}"/> Minuten
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <%-- Button zum Löschen --%>
                            <div class="side-by-side">
                                <button class="icon-pencil" name="action" value="delete">
                                    Löschen
                                </button>
                            </div>
                        </c:otherwise>
                    </c:choose>
            </form>
        </div>
    </jsp:attribute>
</template:base>