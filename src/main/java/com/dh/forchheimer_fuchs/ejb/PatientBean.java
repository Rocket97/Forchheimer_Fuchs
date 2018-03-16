/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.ejb;

import com.dh.forchheimer_fuchs.jpa.Patient;
import javax.ejb.Stateless;

/**
 *
 * @author Valerie
 */
@Stateless
public class PatientBean extends EntityBean<Patient, Long>{
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public PatientBean() {
        super(Patient.class);
    }
    
}
