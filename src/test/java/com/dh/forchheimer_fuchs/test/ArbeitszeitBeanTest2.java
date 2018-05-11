/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.forchheimer_fuchs.test;

import com.dh.forchheimer_fuchs.ejb.ArbeitszeitBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dennis
 */
public class ArbeitszeitBeanTest2 {

    ArbeitszeitBean arbeitszeitBean;
    
    @Before
    public void setUp() {
        arbeitszeitBean = new ArbeitszeitBean();
    }
    
    @Test
    public void testBerechneZeitspanne() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateBeginn = ("31-08-2018 10:20:00");
        Date from = sdf.parse(dateBeginn);
        
        
        String dateEnde = ("31-08-2018 11:10:00");
        Date to = sdf.parse(dateEnde);
        
        int result = arbeitszeitBean.berechneZeitspanne(from, to);
        assertEquals(result, 50);
    }
    
    @Test
    public void testBerechneZeitspanne2() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateBeginn = ("31-08-2018 10:20:00");
        Date from = sdf.parse(dateBeginn);
        
        
        String dateEnde = ("31-08-2018 11:50:00");
        Date to = sdf.parse(dateEnde);
        
        int result = arbeitszeitBean.berechneZeitspanne(from, to);
        assertEquals(result, 90);
    }
    
    @Test
    public void testBerechneZeitspanne3() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateBeginn = ("30-08-2018 10:20:00");
        Date from = sdf.parse(dateBeginn);
        
        
        String dateEnde = ("31-08-2018 11:10:00");
        Date to = sdf.parse(dateEnde);
        
        int result = arbeitszeitBean.berechneZeitspanne(from, to);
        assertEquals(result, 1490);
    }
}
