<%-- 
    Document   : enter_extra_efforts
    Created on : 22.03.2018, 14:35:34
    Author     : rocket
--%>

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
                <div class="column">
                    <%-- Buttons zum Weiterleiten auf die verschiedenen Seiten als Admin --%>
                    <label for="effort_category">Kategorie:</label>
                    <div class="side-by-side">
                    <select name="effort_category">
                        <option value="">Keine Kategorie</option>

                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}" ${effort_form.values["effort_category"][0] == category.id ? 'selected' : ''}>
                                <c:out value="${category.name}" />
                            </option>
                        </c:forEach>
                    </select>
                    </div>
                    
                    <label for="geleistete_stunden_datum">
                        Datum:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="date" name="geleistete_stunden_datum" value="${signup_form.values["geleistete_stunden_datum"][0]}">
                    </div>
                    
                    <label for="geleistete_stunden_abfahrtszeit">
                        Abfahrtszeit:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="date" name="geleistete_stunden_abfahrtszeit" value="${signup_form.values["geleistete_stunden_abfahrtszeit"][0]}">
                    </div>
                    
                    <label for="geleistete_stunden_beginn">
                        Beginn:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="date" name="geleistete_stunden_beginn" value="${signup_form.values["geleistete_stunden_beginn"][0]}">
                    </div>
                    
                    <label for="geleistete_stunden_ende">
                        Ende:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="date" name="geleistete_stunden_ende" value="${signup_form.values["geleistete_stunden_ende"][0]}">
                    </div>
                    
                    <label for="geleistete_stunden_ankunftszeit">
                        Ankunftszeit:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="date" name="geleistete_stunden_ankunftszeit" value="${signup_form.values["geleistete_stunden_ankunftszeit"][0]}">
                    </div>
                    
                    <label for="geleistete_stunden_ankunftszeit">
                        Helfer:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="helfer" value="${signup_form.values["helfer"][0]}">
                    </div>
                    
                    <label for="geleistete_stunden_ankunftszeit">
                        Titel:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="titel" value="${signup_form.values["titel"][0]}">
                    </div>
                    
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
                            <th>Abfahrtszeit</th>
                            <th>Beginn</th>
                            <th>Ende</th>
                            <th>Ankunftszeit</th>
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
                                <c:out value="${utils.formatDate(effort.startTime)}"/>
                            </td>
                            <td>
                                <c:out value="${utils.formatDate(effort.beginTime)}"/>
                            </td>
                            <td>
                                <c:out value="${utils.formatDate(effort.endTime)}"/>
                            </td>
                            <td>
                                <c:out value="${utils.formatDate(effort.returnTime)}"/>
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
