<%-- 
    Document   : home
    Created on : 16.03.2018, 15:03:31
    Author     : Christian Konstantin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Herzlich Willkommen!</title>
    </head>
    <body>
        <button>Mitglied anlegen</button>
        <button>Mitglieder bearbeiten</button>
        <button>Protololle einfügen</button>
        <button>Jahresstatistik erstellen</button>
        <button>Helferstatistik ansehen</button>
        <button>Zusätzliche Stunden eintragen</button>
    </body>
</html>


<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Mitglied anlegen
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/logout/"/>">Einloggen</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                <div class="column">
                    <%-- Button zum Speichern --%>
                    <div class="side-by-side">
                        <button class="icon-pencil" type="submit">
                            Mitglied anlegen
                        </button>
                    </div>
            </form>
        </div>
    </jsp:attribute>
</template:base>
