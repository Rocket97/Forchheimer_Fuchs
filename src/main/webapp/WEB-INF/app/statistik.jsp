<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
            Statistik ansehen
    </jsp:attribute>

   <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/home/"/>" class="icon-home">Home</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">
            
                <div class="column">
                    <label for="von">
                        Von:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="datetime-local" name="von">
                    </div>
                    
                    <label for="bis">
                        Bis:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="datetime-local" name="bis">
                    </div>
                    
                    <c:if test="${admin}">
                        <%-- Suchfelder --%>    
              
                        <label for="search_mitgliedsnummer">
                            Mitgliedsnummer:
                            <span class="required">*</span>
                        </label>
                        <div class="side-by-side">
                            <input type="number" name="search_mitlgiedsnummer" value="${signup_form.values["search_mitgliedsnummer"][0]}">
                        </div>
                    
                        <label for="search_nachname">
                            Nachname:
                            <span class="required">*</span>
                        </label>
                        <div class="side-by-side">
                            <input type="text" name="search_nachname" value="${signup_form.values["search_nachname"][0]}">
                        </div>
                    
                        <label for="search_vorname">
                            Vorname:
                            <span class="required">*</span>
                        </label>
                        <div class="side-by-side">
                            <input type="text" name="search_vorname" value="${signup_form.values["search_vorname"][0]}">
                        </div>
                   
                        <label for="search_username">
                            Nutzername:
                            <span class="required">*</span>
                        </label>
                        <div class="side-by-side">
                            <input type="text" name="search_username" value="${signup_form.values["search_username"][0]}">
                        </div> 
                    </c:if>
                    
                    
                    <%-- Button zur Suche der Helfer --%>
                    <div class="side-by-side">
                        <button class="icon-search" type="submit">
                            Suchen
                        </button>
                    </div>
                    
                    <%-- Panel oder ApplicationFrame (Frame) --%>
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
