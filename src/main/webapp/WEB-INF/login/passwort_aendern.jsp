<%-- 
    Document   : passwort_aendern
    Created on : 15.03.2018, 11:26:57
    Author     : Valerie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Passwort ändern
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                <div class="column">
                    <%-- Eingabefelder --%>
                    <label for="passwortAlt">
                        Altes Passwort:
                        <span class="required">*</span>
                    </label>
                    <input type="password" name="passwortAlt">

                    <label for="passwortNeu">
                        Neues Passwort:
                        <span class="required">*</span>
                    </label>
                    <input type="password" name="passwortNeu">
                    
                    <label for="passwortNeu2">
                        Neues Passwort (Wdh.):
                        <span class="required">*</span>
                    </label>
                    <input type="password" name="passwortNeu2">

                    <%-- Button zum Abschicken --%>
                    <button class="icon-login" type="submit">
                        Einloggen
                    </button>
                </div>
            </form>
        </div>
    </jsp:attribute>
</template:base>
