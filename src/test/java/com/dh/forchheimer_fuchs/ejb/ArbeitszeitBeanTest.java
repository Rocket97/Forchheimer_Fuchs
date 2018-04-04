///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.dh.forchheimer_fuchs.ejb;
//
//import com.dh.forchheimer_fuchs.jpa.Arbeitszeit;
//import com.dh.forchheimer_fuchs.jpa.Benutzer;
//import com.dh.forchheimer_fuchs.jpa.StundenKategorie;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
//import java.util.List;
//import javax.ejb.EJB;
//import javax.ejb.embeddable.EJBContainer;
//import javax.naming.Context;
//import org.jfree.chart.JFreeChart;
//import org.jfree.ui.ApplicationFrame;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author Valerie
// */
//public class ArbeitszeitBeanTest {
//    
//    @EJB
//    ArbeitszeitBean arbeitszeitBean;
//    
//    private static Context  ctx;
//    private static EJBContainer ejbContainer;
//    
//    public ArbeitszeitBeanTest() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//        ejbContainer = EJBContainer.createEJBContainer();      
//        ctx = ejbContainer.getContext();
//    }
//    
////    public Boolean testeBerechneZeitspanne(){
////        System.out.println("verify");
////        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
////        ArbeitszeitBean instance = (ArbeitszeitBean)container.getContext().lookup("java:global/classes/ArbeitszeitBean");
////        int expResult = 0;
////        int result = instance.berechneZeitspanne(, );
////        assertEquals(expResult, result);
////        container.close();
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////        
////        
////        Calendar beginn = new GregorianCalendar(2018,1,28,13,24,00);
////        Calendar ende = new GregorianCalendar(2018,1,29,15,24,00);
////        Arbeitszeit a = new Arbeitszeit();
////
////        assertEquals(1560, a.berechneZeitspanne(beginn, ende));
////        
////    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//        ejbContainer.close();
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of findById method, of class ArbeitszeitBean.
//     * @throws java.lang.Exception
//     */
//    @Test
//    public void testFindById() throws Exception {
//        System.out.println("findById");
//        Long id = null;
//        ArbeitszeitBean instance = (ArbeitszeitBean)ctx.lookup("java:global/classes/ArbeitszeitBean");
//        Arbeitszeit expResult = null;
//        Arbeitszeit result = instance.findById(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findAll method, of class ArbeitszeitBean.
//     * @throws java.lang.Exception
//     */
//    @Test
//    public void testFindAll() throws Exception {
//        System.out.println("findAll");
//        ArbeitszeitBean instance = (ArbeitszeitBean)ctx.lookup("java:global/classes/ArbeitszeitBean");
//        List<Arbeitszeit> expResult = null;
//        List<Arbeitszeit> result = instance.findAll();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of saveNew method, of class ArbeitszeitBean.
//     * @throws java.lang.Exception
//     */
//    @Test
//    public void testSaveNew() throws Exception {
//        System.out.println("saveNew");
//        Arbeitszeit entity = null;
//        ArbeitszeitBean instance = (ArbeitszeitBean)ejbContainer.getContext().lookup("java:global/classes/ArbeitszeitBean");
//        Arbeitszeit expResult = null;
//        Arbeitszeit result = instance.saveNew(entity);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of update method, of class ArbeitszeitBean.
//     * @throws java.lang.Exception
//     */
//    @Test
//    public void testUpdate() throws Exception {
//        System.out.println("update");
//        Arbeitszeit entity = null;
//        ArbeitszeitBean instance = (ArbeitszeitBean)ctx.lookup("java:global/classes/ArbeitszeitBean");
//        Arbeitszeit expResult = null;
//        Arbeitszeit result = instance.update(entity);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of delete method, of class ArbeitszeitBean.
//     * @throws java.lang.Exception
//     */
//    @Test
//    public void testDelete() throws Exception {
//        System.out.println("delete");
//        Arbeitszeit entity = null;
//        ArbeitszeitBean instance = (ArbeitszeitBean)ctx.lookup("java:global/classes/ArbeitszeitBean");
//        instance.delete(entity);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of stundenAuswertenAlle method, of class ArbeitszeitBean.
//     * @throws java.lang.Exception
//     */
//    @Test
//    public void testStundenAuswertenAlle() throws Exception {
//        System.out.println("stundenAuswertenAlle");
//        List<Benutzer> helfer = null;
//        StundenKategorie kategorie = null;
//        ArbeitszeitBean instance = (ArbeitszeitBean)ctx.lookup("java:global/classes/ArbeitszeitBean");
//        ApplicationFrame expResult = null;
//        ApplicationFrame result = instance.stundenAuswertenAlle(helfer, kategorie);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of stundenAuswertenEinzeln method, of class ArbeitszeitBean.
//     * @throws java.lang.Exception
//     */
//    @Test
//    public void testStundenAuswertenEinzeln() throws Exception {
//        System.out.println("stundenAuswertenEinzeln");
//        Benutzer benutzer = null;
//        ArbeitszeitBean instance = (ArbeitszeitBean)ctx.lookup("java:global/classes/ArbeitszeitBean");
//        ApplicationFrame expResult = null;
//        ApplicationFrame result = instance.stundenAuswertenEinzeln(benutzer);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of tortendiagrammErstellen method, of class ArbeitszeitBean.
//     * @throws java.lang.Exception
//     */
//    @Test
//    public void testTortendiagrammErstellen() throws Exception {
//        System.out.println("tortendiagrammErstellen");
//        String titel = "";
//        int[] kategorie = null;
//        ArbeitszeitBean instance = (ArbeitszeitBean)ctx.lookup("java:global/classes/ArbeitszeitBean");
//        JFreeChart expResult = null;
//        JFreeChart result = instance.tortendiagrammErstellen(titel, kategorie);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of balkendiagrammErstellen method, of class ArbeitszeitBean.
//     * @throws java.lang.Exception
//     */
//    @Test
//    public void testBalkendiagrammErstellen() throws Exception {
//        System.out.println("balkendiagrammErstellen");
//        String titel = "";
//        int[] kategorie = null;
//        ArbeitszeitBean instance = (ArbeitszeitBean)ctx.lookup("java:global/classes/ArbeitszeitBean");
//        JFreeChart expResult = null;
//        JFreeChart result = instance.balkendiagrammErstellen(titel, kategorie);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setzeFrameFuerDiagramme method, of class ArbeitszeitBean.
//     * @throws java.lang.Exception
//     */
//    @Test
//    public void testSetzeFrameFuerDiagramme() throws Exception {
//        System.out.println("setzeFrameFuerDiagramme");
//        JFreeChart pieChart = null;
//        JFreeChart barChart = null;
//        ArbeitszeitBean instance = (ArbeitszeitBean)ctx.lookup("java:global/classes/ArbeitszeitBean");
//        ApplicationFrame expResult = null;
//        ApplicationFrame result = instance.setzeFrameFuerDiagramme(pieChart, barChart);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of berechneZeitspanne method, of class ArbeitszeitBean.
//     * @throws java.lang.Exception
//     */
//    @Test
//    public void testBerechneZeitspanne() throws Exception {
//        System.out.println("berechneZeitspanne");
//        Calendar from = new GregorianCalendar(2018,1,28,13,24,00);
//        Calendar to = new GregorianCalendar(2018,1,29,15,24,00);
//        ArbeitszeitBean instance = (ArbeitszeitBean)ctx.lookup("java:global/classes/ArbeitszeitBean");
//        int expResult = 1560;
//        int result = instance.berechneZeitspanne(from, to);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findAllSorted method, of class ArbeitszeitBean.
//     * @throws java.lang.Exception
//     */
//    @Test
//    public void testFindAllSorted() throws Exception {
//        System.out.println("findAllSorted");
//        Benutzer benutzer = null;
//        ArbeitszeitBean instance = (ArbeitszeitBean)ctx.lookup("java:global/classes/ArbeitszeitBean");
//        List<Arbeitszeit> expResult = null;
//        List<Arbeitszeit> result = instance.findAllSorted(benutzer);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    
//}
