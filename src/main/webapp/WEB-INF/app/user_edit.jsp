<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        <c:if test="${user.admin}">
            Mitglied bearbeiten
        </c:if>
    </jsp:attribute>       
    
    <jsp:attribute name="title">
        <c:if test="${!user.admin}">
            Profil bearbeiten
        </c:if>
    </jsp:attribute> 
   
   <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_list.css"/>" />
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
                    <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">

                    <%-- Eingabefelder --%>    
                    
                    <!--: Admin: Allle Eingaben müssen sichtbar sein - nur Passwort nicht!-->
                    
                    <c:if test="${user.admin}">
                        <label for="signup_mitgliedsnummer">
                            Mitgliedsnummer:
                            <span class="required">*</span>
                        </label>
                        <div class="side-by-side">
                            <input type="number" name="signup_mitlgiedsnummer" value="${signup_form.values["signup_mitgliedsnummer"][0]}">
                        </div>
                    </c:if>
                    
                    <label for="signup_nachname">
                        Nachname:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_nachname" value="${signup_form.values["signup_nachname"][0]}">
                    </div>
                    
                    <label for="signup_vorname">
                        Vorname:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_vorname" value="${signup_form.values["signup_vorname"][0]}">
                    </div>
                   
                    <label for="signup_geburtsdatum">
                        Geburtsdatum:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="date" name="signup_geburtsdatum" value="${signup_form.values["signup_geburtsdatum"][0]}">
                    </div>
                    
                    <label for="signup_telefonnummer">
                        Telefonnummer:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="tel" name="signup_telefonnummer" value="${signup_form.values["signup_telefonnummer"][0]}">
                    </div>
                    
                    <label for="signup_email">
                        E-Mail:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="email" name="signup_email" value="${signup_form.values["signup_email"][0]}">
                    </div>
                    
                    <label for="signup_strasse">
                        Strasse:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_strasse" value="${signup_form.values["signup_strasse"][0]}">
                    </div>
                    
                    <label for="signup_hausnummer">
                        Hausnummer:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_hausnummer" value="${signup_form.values["signup_hausnummer"][0]}">
                    </div>
                    
                    <label for="signup_plz">
                        PLZ:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="number" name="signup_plz" value="${signup_form.values["signup_plz"][0]}">
                    </div>
                    
                    <label for="signup_ort">
                        Ort:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_ort" value="${signup_form.values["signup_ort"][0]}">
                    </div>
                    
                    <c:if test="${user.admin}">
                        <label for="signup_abteilung">
                           Abteilung:
                            <span class="required">*</span>
                        </label>
                        <div class="side-by-side">
                            <input type="checkbox" name="signup_abteilung_jugend" value="${signup_form.values["signup_abteilung_jugend"][0]}">Jugend<br />
                            <input type="checkbox" name="signup_abteilung_bereitschaft" value="${signup_form.values["signup_abteilung_bereitschaft"][0]}">Bereitschaft<br />
                        </div>
                    </c:if>
                    
                    
                    <label for="signup_username">
                        Nutzername:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_username" value="${signup_form.values["signup_username"][0]}">
                    </div>

                    <c:if test="${!user.admin}">
                        <label for="signup_password">
                            Passwort:
                            <span class="required">*</span>
                        </label>
                        <div class="side-by-side">
                            <input type="password" name="signup_password" value="${signup_form.values["signup_password"][0]}">
                        </div>
                    </c:if>
                    
                    <c:if test="${user.admin}">
                        <label for="signup_admin">
                            Admin
                        </label>
                        <div class="side-by-side">
                            <input type="checkbox" name="signup_admin" value="${signup_form.values["signup_admin"][0]}">
                        </div>
                    </c:if>
                    
           
                    <%-- Button zum Speichern --%>
                    <div class="side-by-side">
                        <button class="icon-pencil" type="submit">
                            Speichern
                        </button>
                    </div>
                </div>

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