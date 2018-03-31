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
                <div class="column">
                    <label for="jahr">
                        Jahr:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="jahr">
                    </div>
                    
                    <c:if test="${user.admin}">
                        <label for="helfer">
                            Helfer:
                        </label>
                        <div class="side-by-side">
                            <input type="text" name="helfer">
                        </div> 
                    </c:if>
                    
                    
                    <%-- Button zur Suche der Helfer --%>
                    <div class="side-by-side">
                        <button class="icon-search" type="submit">
                            Suchen
                        </button>
                    </div>
                    
                    <%-- <img ...> für das JFreeChart --%>
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