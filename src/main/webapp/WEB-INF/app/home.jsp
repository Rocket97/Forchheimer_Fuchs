<%-- 
    Document   : home.jsp
    Created on : 16.03.2018, 15:03:31
    Author     : Christian Konstantin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Herzlich Willkommen!
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                <div class="column">
                    <%-- Buttons zum Weiterleiten auf die verschiedenen Seiten als Admin --%>
                    <c:if test="${user.admin}">
                        <button>
                            Mitglied anlegen
                        </button>
                    </c:if>
                    <c:if test="${user.admin}">
                        <button>
                            Mitglied bearbeiten
                        </button>
                    </c:if>
                    <c:if test="${user.admin}">
                        <button>
                            Protokolle eintragen
                        </button>
                    </c:if>
                    <c:if test="${user.admin}">
                        <button>
                            Jahresstatistik erstellen
                        </button>
                    </c:if>
                    <c:if test="${user.admin}">
                        <button>
                            Helferstatistik ansehen
                        </button>
                    </c:if>
                    <c:if test="${user.admin}">
                        <button>
                            Zusätzliche Stunden eintragen
                        </button>
                    </c:if>
                    
                    <%-- Buttons zum Weiterleiten auf die verschiedenen Seiten als normaler Nutzer--%>
                    <c:if test="${!user.admin}">
                        <button>
                            Stunden eintragen
                        </button>
                    </c:if>
                    <c:if test="${!user.admin}">
                        <button>
                            Statistik ansehen
                        </button>
                    </c:if>
                    <c:if test="${!user.admin}">
                        <button>
                            Profil bearbeiten
                        </button>
                    </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base>
