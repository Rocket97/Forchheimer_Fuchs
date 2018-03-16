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
     * Suche nach Aufgaben anhand ihrer Bezeichnung, Kategorie und Status.
     * 
     * Anders als in der Vorlesung behandelt, wird die SELECT-Anfrage hier
     * mit der CriteriaBuilder-API vollkommen dynamisch erzeugt.
     * 
     * @param suche
     * @param suchdatum
     * @return Liste mit den gefundenen Events
     */
    public List<Event> suche(String suche, Date suchdatum) {
        
        // Hilfsobjekt zum Bauen des Query
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        
        // SELECT e FROM Event e
        CriteriaQuery<Event> query = cb.createQuery(Event.class);
        Root<Event> from = query.from(Event.class);
        query.select(from);

        // ORDER BY datum
        query.orderBy(cb.asc(from.get("datum")));
        
        // WHERE e.event_titel LIKE :suche
        if (suche != null && !suche.trim().isEmpty()) {
            query.where(cb.like(from.<String>get("etitel"), suche));
        }
        
        // WHERE e.datum = :datum
        if (suchdatum != null) {
            query.where(cb.equal(from.get("DATUM"), suchdatum));
        }
        
        return em.createQuery(query).getResultList();
    }
}
