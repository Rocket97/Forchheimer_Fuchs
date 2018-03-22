<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Protokolle eintragen
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
                    
                    <label for="protocol_titel">
                        Titel:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="protocoll_titel" value="${task_form.values["protocol_titel"][0]}">
                    </div>
                    
                    <label for="gefahren_am_datum">
                        Datum:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="date" name="gefahren_am_datum" value="${signup_form.values["gefahren_am_datum"][0]}">
                    </div>
                    
                    <label for="protocol_ort">
                        Ort:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="protocol_ort" value="${task_form.values["protocol_ort"][0]}">
                    </div>
                   <!--Patient***************************************************************************************************************-->
                    
                    <label for="protocol_patient">
                        Patient:
                    </label>
                   
                    <label for="protocol_patient_name">
                        Name:
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="protocol_patient_name" value="${task_form.values["protocol_patient_name"][0]}">
                    </div>
                    
                    <label for="protocol_patient_vorname">
                        Vorname:
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="protocol_patient_vorname" value="${task_form.values["protocol_patient_vorname"][0]}">
                    </div>
                    
                    <label for="protocol_patient_geschlecht">
                        Geschlecht:
                    </label>
                    <div class="side-by-side">
                        <input type="checkbox" name="protocol_patient_geschlecht_weiblich" value="${signup_form.values["protocol_patient_geschlecht_weiblich"][0]}">weiblich<br />
                        <input type="checkbox" name="protocol_patient_geschlecht_maennlich" value="${signup_form.values["protocol_patient_geschlecht_maennlich"][0]}">männlich<br />
                    </div>
                        
                    <label for="protocol_patient_geburtsdatum">
                        Geburtsdatum:
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="protocol_patient_geburtsdatum" value="${task_form.values["protocol_patient_geburtsdatum"][0]}">
                    </div>
                    
                    <label for="protocol_patient_strasse">
                        Straße:
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="protocol_patient_strasse" value="${task_form.values["protocol_patient_strasse"][0]}">
                    </div>
                    
                    <label for="protocol_patient_postleitzahl">
                        Postleitzahl:
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="protocol_patient_postleitzahl" value="${task_form.values["protocol_patient_postleitzahl"][0]}">
                    </div>
                    
                    <label for="protocol_patient_wohnort">
                        Wohnort:
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="protocol_patient_wohnort" value="${task_form.values["protocol_patient_wohnort"][0]}">
                    </div>
                    
                    <label for="protocol_behandlung">
                        Behandlung:
                    </label>
                    
                    <label for="protocol_behandlungszeit_beginn">
                        Beginn:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="date" name="protocol_behandlungszeit_beginn" value="${signup_form.values["protocol_behandlungszeit_beginn"][0]}">
                    </div>
                    
                    <label for="protocol_behandlungszeit_ende">
                        Ende:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="date" name="protocol_behandlungszeit_ende" value="${signup_form.values["protocol_behandlungszeit_ende"][0]}">
                    </div>
                    
                    <label for="protocol_behandlungsbeschreibung">
                        Beschreibung:
                    </label>
                    <div class="side-by-side">
                        <textarea name="protocol_behandlungsbeschreibung"><c:out value="${task_form.values['protocol_behandlungsbeschreibung'][0]}"/></textarea>
                    </div>
                    
                    <%-- Helfer hinzufügen --%> <!--hilfeeeeeeeee********************************************-->
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
                    
                   
                    
                    <%-- Button zum Speichern und weitere Patienten anlegen --%>
                    <div class="side-by-side">
                        <button class="icon-pencil" type="submit">
                            Speichern
                        </button>
                    </div>
                    
                    <div class="side-by-side">
                        <button class="icon-user-plus" type="submit">
                            weiteren Patient hinzufügen
                        </button>
                    </div>
            </form>
        </div>
    </jsp:attribute>
</template:base>

