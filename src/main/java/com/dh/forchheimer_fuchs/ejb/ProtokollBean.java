/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.ejb;

import com.dh.forchheimer_fuchs.jpa.Protokoll;
import com.dh.forchheimer_fuchs.jpa.ProtokollKategorie;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.Entity;

/**
 *
 * @author Valerie
 */
@Stateless
@RolesAllowed("ff_nutzer")
public class ProtokollBean extends EntityBean<Protokoll, Long>{

    public ProtokollBean() {
        super(Protokoll.class);
    }
    
    
    public List<Entity> sucheNachTitel(String titel) {
         String select = "SELECT x FROM Protokoll x WHERE x.titel LIKE :titel";
         return em.createQuery(select).setParameter("titel", titel).getResultList();
    }
    
    public List<Entity> sucheNachKategorie(ProtokollKategorie kategorie) {
        String select = "SELECT x FROM Protokoll x WHERE x.kategorie = :kategorie";
        return em.createQuery(select).setParameter("kategorie", kategorie).getResultList();        
    }
}
