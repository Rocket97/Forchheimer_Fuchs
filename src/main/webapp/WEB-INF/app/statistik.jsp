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
        <link rel="stylesheet" href="<c:url value="/css/form.css"/>" />

        <script>
            function zeigeSchaubild(event) {
                // Formular nicht abschicken, nur Grafik nachladen
                event.preventDefault();

                // URL des <img>-Elements setzen
                let von = document.getElementById("input-von").value;
                let bis = document.getElementById("input-bis").value;

                let benutzername = "${admin ? 'admin' : benutzername}";

                if (benutzername === "") {
                    benutzername = document.getElementById("input-benutzername").value;
                }

                if (benutzername !== "" && von !== "" && bis !== "") {
                    let imgPie = document.getElementById("chartPie");
                    imgPie.src = "chart?benutzername=" + encodeURI(benutzername)
                            + "&von=" + encodeURI(von)
                            + "&bis=" + encodeURI(bis)
                            + "&type=pie"
                            + "&width=800"
                            + "&height=600";

                    let imgBar = document.getElementById("chartBar");
                    imgBar.src = "chart?benutzername=" + encodeURI(benutzername)
                            + "&von=" + encodeURI(von)
                            + "&bis=" + encodeURI(bis)
                            + "&type=bar"
                            + "&width=800"
                            + "&height=600";
                    console.log(imgBar.src);

                    // <img>-Element sichtbar schalten
                    imgPie.classList.remove("invisible");
                    imgBar.classList.remove("invisible");
                }
            }
        </script>
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/home/"/>" class="icon-home">Home</a>
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
                        <input id="input-von" type="datetime-local" name="von" value="${statistic_form.values["statistic_vonDatum"][0]}">
                    </div>

                    <label for="bis">
                        Bis:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input id="input-bis" type="datetime-local" name="bis" value="${statistic_form.values["statistic_bisDatum"][0]}">
                    </div>

                    <c:if test="${admin}">
                        <%-- Suchfelder --%>    
                        <label for="search_mitgliedsnummer">
                            Mitgliedsnummer:
                            <span class="required">*</span>
                        </label>
                        <div class="side-by-side">
                            <input type="number" name="search_mitlgiedsnummer" value="${statistic_form.values["statistic_mitgliedsnummer"][0]}">
                        </div>

                        <label for="search_nachname">
                            Nachname:
                            <span class="required">*</span>
                        </label>
                        <div class="side-by-side">
                            <input type="text" name="search_nachname" value="${statistic_form.values["statistic_nachname"][0]}">
                        </div>

                        <label for="search_vorname">
                            Vorname:
                            <span class="required">*</span>
                        </label>
                        <div class="side-by-side">
                            <input type="text" name="search_vorname" value="${statistic_form.values["statistic_vorname"][0]}">
                        </div>

                        <label for="search_username">
                            Benutzername:
                            <span class="required">*</span>
                        </label>
                        <div class="side-by-side">
                            <input id="input-benutzername" type="text" name="search_username" value="${statistic_form.values["statistic_username"][0]}">
                        </div> 

                        <%-- Button zur Suche der Helfer --%>
                        <div class="side-by-side">
                            <button class="icon-search" type="submit">
                                Helfer Suchen
                            </button>
                        </div>
                    </c:if>

                    <%-- Button zur Anzeige des Schaubilds --%>
                    <div class="side-by-side">
                        <button class="icon-search" onclick="zeigeSchaubild(event)">
                            Schaubild anzeigen
                        </button>
                    </div>

                    <%-- Panel oder ApplicationFrame (Frame) --%>
                    <div>
                        <img id="chartPie" class="invisible">
                    </div>
                    <div>
                        <img id="chartBar" class="invisible">
                    </div>
                </div>


                <%-- Fehlermeldungen --%>
                <c:if test="${!empty statistic_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${statistic_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base>
