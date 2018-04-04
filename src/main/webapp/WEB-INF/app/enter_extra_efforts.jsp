<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Zusätzliche Stunden eintragen
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>
        
    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/home/"/>" class="icon-homehome">Home</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">
            
                <div class="column">
                    <%-- Buttons zum Weiterleiten auf die verschiedenen Seiten als Admin --%>
                    <label for="special_effort_category">Kategorie:</label>
                    <div class="side-by-side">
                    <select name="special_effort_category">
                        <option value="">Keine Kategorie</option>

                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}" ${effort_form.values["special_effort_category"][0] == category.id ? 'selected' : ''}>
                                <c:out value="${category.name}" />
                            </option>
                        </c:forEach>
                    </select>
                    </div>
                    
                    <label for="special_efforts_titel">
                        Titel:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="titel">
                    </div>
                    
                    <label for="special_efforts_zeit_beginn">
                        Beginn (Datum und Uhrzeit):
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="datetime-local" name="special_efforts_zeit_beginn">
                    </div>
                    
                    <label for="special_efforts_zeit_ende">
                        Ende (Datum und Uhrzeit):
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="datetime-local" name="special_efforts_zeit_ende">
                    </div>
                    
                    <c:choose>
                    <c:when test="${empty helfer}">
                        <p>
                            Es sind noch keine Helfer hinterlegt. Bitte helfer hinterlegen!
                        </p>
                    </c:when>
                    <c:otherwise>
                        <div>
                            <div class="margin">
                                <c:forEach items="${helfer}" var="helfer">
                                    <input type="checkbox" name="helfer" id="${'helfer-'.concat(helfer.id)}" value="${helfer.id}" />
                                    <label for="${'helfer-'.concat(helfer.id)}">
                                        <c:out value="${helfer.name}"/>
                                    </label>
                                    <br />
                                </c:forEach>
                            </div>

                            <button type="submit" name="action" value="delete" class="icon-trash">
                                Markierte Helfer löschen
                            </button>
                        </div>
                    </c:otherwise>
                </c:choose>
                    
                    
                    <%-- Button zum Speichern --%>
                    <div class="side-by-side">
                        <button class="icon-pencil" type="submit">
                            Speichern
                        </button>
                    </div>
                    
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
                            <th>Datum</th>
                            <th>Kategorie</th>
                            <th>Zeit</th>
                            <th>Helfer</th>
                        </tr>
                    </thead>
                    <c:forEach items="${efforts}" var="effort">
                        <tr>
                            <td>
                                <a href="<c:url value="/app/effort/${effort.id}/"/>">
                                    <c:out value="${effort.date}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${effort.category.name}"/>
                            </td>
                            <td>
                                <c:out value="${utils.formatDate(effort.time)}"/>
                            </td>
                            <td>
                                <c:out value="${effort.helfer}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
            </form>
        </div>
    </jsp:attribute>
</template:base>
