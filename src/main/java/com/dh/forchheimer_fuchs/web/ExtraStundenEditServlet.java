/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.web;

import static com.dh.forchheimer_fuchs.jpa.Protokoll_.event;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Valerie
 */
@WebServlet(urlPatterns = {"/extra_effort/*"})
public class ExtraStundenEditServlet extends HttpServlet {
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Zu bearbeitende Arbeitszeit einlesen
        HttpSession session = request.getSession();
        
        // Events vorbelegen, wenn eines übergeben wurde
        if (session.getAttribute("extra_effort_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
//            request.setAttribute("extra_effort_form", this.createExtraEffortForm(event));
        }

        // Events neu anlegen
        
        
        
        session.removeAttribute("extra_effort_form");
    }
}    
