/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.ejb;

import com.dh.forchheimer_fuchs.jpa.Protokoll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

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
    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
