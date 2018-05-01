/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.ejb;

import com.dh.forchheimer_fuchs.jpa.Event;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Valerie
 */
@Stateless
public class EventBean extends EntityBean<Event, Long>{
    
    public EventBean(){
        super(Event.class);
    }
    
    /**
     * Suche nach Events anhand ihres Titels, Beginndatums und Endedatums.
     * 
     * @param text
     * @param beginn
     * @param ende
     * @return Liste mit den gefundenen Events
     */
    public List<Event> search(String text, Date beginn, Date ende) {
        
        // Hilfsobjekt zum Bauen des Query
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        
        // SELECT e FROM Event e
        CriteriaQuery<Event> query = cb.createQuery(Event.class);
        Root<Event> from = query.from(Event.class);
        query.select(from);

        // ORDER BY datum
        query.orderBy(cb.asc(from.get("beginn")));
        
        // WHERE e.eTitel LIKE :text
        if (text != null && !text.trim().isEmpty()) {
            query.where(cb.like(from.<String>get("eTitel"), text));
        }
        
        // WHERE e.beginn = :beginn
        if (beginn != null) {
            query.where(cb.equal(from.get("BEGINN"), beginn));
        }
        
        // WHERE e.ende = :ende
        if (ende != null) {
            query.where(cb.equal(from.get("ENDE"), ende));
        }
        
        return em.createQuery(query).getResultList();
    }
    
     public List<Event> findAllSorted() {
        return em.createQuery("SELECT e FROM Event e ORDER BY e.eTitel").getResultList();
    }
}
