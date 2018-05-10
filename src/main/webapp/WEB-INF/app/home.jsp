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
        <link rel="stylesheet" href="<c:url value="/css/form.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">
            
                <div class="row">
                    <div class="column">
                    <%-- Buttons zum Weiterleiten auf die verschiedenen Seiten als normaler Nutzer--%>
                        <button name="action" value="own_profil_edit">
                            Profil bearbeiten
                        </button>
                        <button name="action" value="effort_save">
                            Stunden eintragen
                        </button>
                        <button name="action" value="statistic_generate">
                            Statistik ansehen
                        </button>
                    </div>
                    
                    <%-- Buttons zum Weiterleiten auf die verschiedenen Seiten als Admin --%>
                    <div class="column">
                        <c:if test="${admin}">
                            <button name="action" value="signup">
                                Mitglied anlegen
                            </button>
                            <button name="action" value="user_edit">
                                Mitglied bearbeiten
                            </button>
                            <button name="action" value="protocol_save">
                                Protokolle eintragen
                            </button>
                            <button name="action" value="extra_effort_save">
                                Zusätzliche Stunden eintragen
                            </button>
                            <button name="action" value="extra_effort_edit">
                                Zusätzliche Stunden bearbeiten
                            </button>
                        </c:if>
                    </div>
                </div>
            </form>
        </div>
    </jsp:attribute>
</template:base>
