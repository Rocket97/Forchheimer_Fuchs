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
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                <div class="column">
                    <%-- Buttons zum Weiterleiten auf die verschiedenen Seiten als Admin --%>
                    <label for="task_category">Kategorie:</label>
                    <div class="side-by-side">
                    <select name="task_category">
                        <option value="">Keine Kategorie</option>

                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}" ${task_form.values["task_category"][0] == category.id ? 'selected' : ''}>
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
                    
                    <%-- Button zum Speichern --%>
                    <div class="side-by-side">
                        <button class="icon-pencil" type="submit">
                            Speichern
                        </button>
                    </div>
            </form>
        </div>
    </jsp:attribute>
</template:base>

