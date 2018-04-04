/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.web;

import com.dh.forchheimer_fuchs.ejb.BenutzerBean;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Valerie
 */
@WebServlet(urlPatterns = {"/app/home/"})
public class HomeServlet extends HttpServlet {
    
    @EJB
    BenutzerBean benutzerBean;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //request.setAttribute("user", benutzerBean.getCurrentUser().getAdmin());
        request.getRequestDispatcher("/WEB-INF/app/home.jsp").forward(request, response);
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "signup":  // erledigt
                response.sendRedirect(WebUtils.appUrl(request, "/signup/"));        //signup.jsp
                break;
            case "user_edit": 
                response.sendRedirect(WebUtils.appUrl(request, "/member/"));     //search_user_edit.jsp -> Suche des Mitglieds, um dann danach in user_edit.jsp die Daten des Mitglieds zu laden
                break;
            case "protokoll_save":
                response.sendRedirect(WebUtils.appUrl(request, "/protocol/")); // muss man da noch einen * hinter url packen?  -> enter_protocol.jsp
                break;
            case "statistik_von_allen_generate":
                response.sendRedirect(WebUtils.appUrl(request, "/statistic/"));     //statistik.jsp
                break;
            case "statistik_von_einzelnem_generate":
                response.sendRedirect(WebUtils.appUrl(request, "/statistic/"));     //statistik.jsp
                break;
            case "extra_effort_save":
                response.sendRedirect(WebUtils.appUrl(request, "/extra_efforts/")); //enter_extra_efforts.jsp
                break;
            case "effort_save":
                response.sendRedirect(WebUtils.appUrl(request, "/efforts/"));       //enter_efforts.jsp
                break;
            case "own_statistik_generate":
                response.sendRedirect(WebUtils.appUrl(request, "/statistic/"));     //statistik.jsp
                break;
            case "own_profil_edit":  //erledigt
                response.sendRedirect(WebUtils.appUrl(request, "/user/" + this.benutzerBean.getCurrentUser().getMitgliedsnr()));          //user_edit.jsp
                break;
            
            default:
                
        }
    }
}
