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
        <link rel="stylesheet" href="<c:url value="/css/task_edit.css"/>" />
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
                    <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">

                    <%-- Eingabefelder --%>    
                    
                    <!--: Admin: Allle Eingaben müssen sichtbar sein - nur Passwort nicht!-->
                    
                    <c:if test="${user.admin}">
                        <label for="user_mitgliedsnummer">
                            Mitgliedsnummer:
                            <span class="required">*</span>
                        </label>
                        <div class="side-by-side">
                            <input type="number" name="user_mitlgiedsnummer" value="${signup_form.values["user_mitgliedsnummer"][0]}">
                        </div>
                    </c:if>
                    
                    <label for="user_nachname">
                        Nachname:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="user_nachname" value="${signup_form.values["user_nachname"][0]}">
                    </div>
                    
                    <label for="user_vorname">
                        Vorname:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="user_vorname" value="${signup_form.values["user_vorname"][0]}">
                    </div>
                   
                    <label for="user_telefonnummer">
                        Telefonnummer:
                    </label>
                    <div class="side-by-side">
                        <input type="tel" name="user_telefonnummer" value="${signup_form.values["user_telefonnummer"][0]}">
                    </div>
                    
                    <label for="user_email">
                        E-Mail:
                    </label>
                    <div class="side-by-side">
                        <input type="email" name="user_email" value="${signup_form.values["user_email"][0]}">
                    </div>
                    
                    <label for="user_strasse">
                        Strasse:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="user_strasse" value="${signup_form.values["user_strasse"][0]}">
                    </div>
                    
                    <label for="user_hausnummer">
                        Hausnummer:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="user_hausnummer" value="${signup_form.values["user_hausnummer"][0]}">
                    </div>
                    
                    <label for="user_plz">
                        PLZ:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="number" name="user_plz" value="${signup_form.values["user_plz"][0]}">
                    </div>
                    
                    <label for="user_ort">
                        Ort:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="user_ort" value="${signup_form.values["user_ort"][0]}">
                    </div>
                    
                    <c:if test="${user.admin}">
                        <label for="user_abteilung">
                           Abteilung:
                            <span class="required">*</span>
                        </label>
                        <div class="side-by-side">
                            <input type="checkbox" name="user_abteilung_jugend" value="${signup_form.values["user_abteilung_jugend"][0]}">Jugend<br />
                            <input type="checkbox" name="user_abteilung_bereitschaft" value="${signup_form.values["user_abteilung_bereitschaft"][0]}">Bereitschaft<br />
                        </div>
                    </c:if>
                    
                    
                    <label for="user_username">
                        Nutzername:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="user_username" value="${signup_form.values["user_username"][0]}">
                    </div>
                    <c:if test="${!user.admin}">
                        <label for="user_passwordAlt">
                            Altes Passwort:
                            <span class="required">*</span>
                        </label>
                        <div class="side-by-side">
                            <input type="password" name="user_passwordAlt" value="${signup_form.values["user_passwordAlt"][0]}">
                        </div>
                    </c:if>
                    <c:if test="${!user.admin}">
                        <label for="user_password1">
                            Neues Passwort:
                            <span class="required">*</span>
                        </label>
                        <div class="side-by-side">
                            <input type="password" name="user_password1" value="${signup_form.values["user_password1"][0]}">
                        </div>
                    </c:if>
                    <c:if test="${!user.admin}">
                        <label for="user_password">
                            Passwort (Wdh.):
                            <span class="required">*</span>
                        </label>
                        <div class="side-by-side">
                            <input type="password" name="user_password" value="${signup_form.values["user_password"][0]}">
                        </div>
                    </c:if>
                    
                    <c:if test="${user.admin}">
                        <label for="user_admin">
                            Admin
                        </label>
                        <div class="side-by-side">
                            <input type="checkbox" name="user_admin" value="${signup_form.values["user_admin"][0]}">
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
