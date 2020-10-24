/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tietorakenteet.Solmu;

/**
 *
 * @author eemes
 */
public class SolmuTest {
    
    public SolmuTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void konstruktoriAsettaaArvot() {
         Solmu solmu = new Solmu(1, 2);
         assertEquals(1, solmu.haeX());
         assertEquals(2, solmu.haeY());
     }
     
     @Test
     public void etaisyysAlkuunPalauttaaOikeanArvon() {
         Solmu solmu = new Solmu(1, 3, 10, null);
         assertEquals(10, solmu.haeEtaisyysAlkuun(), 0.001);
     }
     
     @Test
     public void etaisyysMaaliinUudellaSolmullaNolla() {
         Solmu solmu = new Solmu(1, 2, 3, null);
         assertEquals(0, solmu.haeEtaisyysMaaliin(), 0.001);
     }
     
     @Test
     public void haeVanhempiPalauttaaOikeanSolmun() {
         Solmu vanhempi = new Solmu(1, 2, 3, null);
         Solmu solmu = new Solmu(4, 5, 6, vanhempi);
         assertEquals(vanhempi, solmu.haeVanhempi());
     }
     
     @Test
     public void asetaEtaisyysAlkuunMuuttaaArvoa() {
         Solmu solmu = new Solmu(1, 2, 3, null);
         solmu.asetaEtaisyysAlkuun(5);
         assertEquals(5, solmu.haeEtaisyysAlkuun(), 0.001);
     }
     
     @Test
     public void asetaMaaliinLoppuunMuuttaaArvoa() {
         Solmu solmu = new Solmu(1, 2, 3, null);
         solmu.asetaEtaisyysMaaliin(5);
         assertEquals(5, solmu.haeEtaisyysMaaliin(), 0.001);
     }
     
     @Test
     public void asetaXVaihtaaKoordinaatin() {
         Solmu solmu = new Solmu(1, 2, 3, null);
         solmu.asetaX(4);
         assertEquals(4, solmu.haeX());
     }
     
     @Test
     public void asetaYVaihtaaKoordinaatin() {
         Solmu solmu = new Solmu(1, 2, 3, null);
         solmu.asetaY(5);
         assertEquals(5, solmu.haeY());
     }
     
     @Test
     public void laajempiKonstruktoriAsettaaArvot() {
         Solmu vanhempi = new Solmu(1, 2, 3, null);
         Solmu solmu = new Solmu(4, 5, 6, vanhempi);
         
         assertEquals(1, vanhempi.haeX());
         assertEquals(5, solmu.haeY());
         assertEquals(vanhempi, solmu.haeVanhempi());
     }
     
     @Test
     public void equalsKomparaattoriTunnistaaSamatSolmut() {
         Solmu solmu1 = new Solmu(1, 2, 3, null);
         Solmu solmu2 = new Solmu(1, 2, 3, null);
         Solmu solmu3 = new Solmu(2, 3, 3, null);
         
         assertEquals(true, solmu1.equals(solmu2));
         assertEquals(false, solmu2.equals(solmu3));
     }
     
     @Test
     public void compareToVertaaSolmujaOikein() {
         Solmu solmu1 = new Solmu(1, 2, 3, null);
         Solmu solmu2 = new Solmu(1, 2, 3, null);
         Solmu solmu3 = new Solmu(2, 3, 4, null);
         
         assertEquals(0, solmu1.compareTo(solmu2));
         assertEquals(-1, solmu2.compareTo(solmu3));
         assertEquals(1, solmu3.compareTo(solmu2));
     }
     
     @Test
     public void equalsFalseJosEiOleSolmu() {
         Solmu solmu1 = new Solmu(1, 2, 3, null);
         Object o = new Object();
         assertEquals(false, solmu1.equals(o));
     }
     
     @Test
     public void compareToNollaJosNull() {
         Solmu solmu1 = new Solmu(1, 2, 3, null);
         assertEquals(0, solmu1.compareTo(null));
     }
}
