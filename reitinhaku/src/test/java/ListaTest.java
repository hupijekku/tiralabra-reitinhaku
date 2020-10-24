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
import tietorakenteet.Lista;
import tietorakenteet.Solmu;

/**
 *
 * @author eemes
 */
public class ListaTest {
    
    public ListaTest() {
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
    // @Test
    // public void hello() {}
    
    @Test
    public void listaKasvaaTarvittaessa() {
        Lista lista = new Lista();
        assertEquals(0, lista.haeMaara());
        for (int i = 0; i < 100; i++) {
            lista.lisaa(new Solmu(1, i, 5, null));
        }
        assertEquals(100, lista.haeMaara());
    }
    
    @Test
    public void haeIndeksiPalauttaaNullJosVirheellinenParametri() {
        Lista lista = new Lista();
        for (int i = 0; i < 100; i++) {
            lista.lisaa(new Solmu(1, i, 5, null));
        }
        assertEquals(null, lista.haeIndeksi(105));
    }
    
    @Test
    public void poistaIndeksiEiPoistaJosVirheellinenParametri() {
        Lista lista = new Lista();
        for (int i = 0; i < 100; i++) {
            lista.lisaa(new Solmu(1, i, 5, null));
        }
        lista.poistaIndeksi(105);
        assertEquals(100, lista.haeMaara());
    }
}
