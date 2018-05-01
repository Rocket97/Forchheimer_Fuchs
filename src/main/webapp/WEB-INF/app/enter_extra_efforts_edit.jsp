<%-- 
    Document   : enter_extra_efforts_edit
    Created on : 01.05.2018, 15:43:29
    Author     : Valerie
--%>

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
                    <label for="special_efforts_titel">
                        Titel:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="special_efforts_titel">
                    </div>

                    <label for="special_efforts_zeit_beginn">
                        Beginn (Datum und Uhrzeit im Format 'dd.MM.yyyy hh:mm'):
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="datetime-local" name="special_efforts_zeit_beginn">
                    </div>

                    <label for="special_efforts_zeit_ende">
                        Ende (Datum und Uhrzeit im Format 'dd.MM.yyyy hh:mm'):
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="datetime-local" name="special_efforts_zeit_ende">
                    </div>
                    <label for="user_abteilung">
                        Abteilung:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="checkbox" id="user_abteilung_jugend" value="Jugend">Jugend<br />
                        <input type="checkbox" id="user_abteilung_bereitschaft" value="Bereitschaft">Bereitschaft<br />
                    </div>
                    <%-- Button zum Speichern --%>
                    <div class="side-by-side">
                        <button class="icon-pencil" name="action" value="saveHelferInEvent">
                            Helfer zuordnen
                        </button>
                    </div>
                </div>
                <c:choose>
                    <c:when test="${empty helfer}">
                        <p>
                            Es sind noch keine Helfer hinterlegt. Bitte Helfer hinterlegen!
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

                            <button name="action" value="delete" class="icon-trash">
                                Markierte Helfer löschen
                            </button>
                        </div>
                    </c:otherwise>
                </c:choose>


                <%-- Button zum Speichern --%>
                <div class="side-by-side">
                    <button class="icon-pencil" name="action" value="saveEvent">
                        Alles speichern
                    </button>
                </div>

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty extra_effort_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${extra_effort_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base>
