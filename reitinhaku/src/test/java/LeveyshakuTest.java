/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import algoritmit.Leveyshaku;
import dao.Kartta;
import java.io.File;
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
public class LeveyshakuTest {
    
    public LeveyshakuTest() {
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
    public void konstruktoriToimii() {
        Kartta labyrintti = new Kartta(new File("./kartat/maze512-2-0.map"));
        char[][] taulukko = labyrintti.luoTaulukko();
        Leveyshaku h = new Leveyshaku(taulukko);
        Solmu s1 = new Solmu(1, 1);
        Solmu s2 = new Solmu(500, 500);
        assertEquals(true, h instanceof Leveyshaku);
    }
    
    @Test
    public void etsiReittiLoytaaReitin() {
        Kartta labyrintti = new Kartta(new File("./kartat/maze512-2-0.map"));
        char[][] taulukko = labyrintti.luoTaulukko();
        Leveyshaku h = new Leveyshaku(taulukko);
        Solmu s1 = new Solmu(1, 1);
        Solmu s2 = new Solmu(500, 500);
        Solmu s3 = new Solmu(510, 510);
        assertEquals(true, h.etsiReitti(s1, s2) > 0);
        assertEquals(true, h.etsiReitti(s1, s3) < 0);
    }
    
    @Test
    public void kulunutAikaPalauttaaNanoSekuntteja() {
        Kartta labyrintti = new Kartta(new File("./kartat/maze512-2-0.map"));
        char[][] taulukko = labyrintti.luoTaulukko();
        Leveyshaku h = new Leveyshaku(taulukko);
        Solmu s1 = new Solmu(10, 10);
        Solmu s3 = new Solmu(500, 500);
        double a = h.etsiReitti(s1, s3);
        long aika = h.kulunutAika();
        assertEquals(true, aika > 100000);
    }
    
    @Test
    public void haeReittiPalauttaaTaulukon() {
        Kartta labyrintti = new Kartta(new File("./kartat/maze512-2-0.map"));
        char[][] taulukko = labyrintti.luoTaulukko();
        Leveyshaku h = new Leveyshaku(taulukko);
        Solmu s1 = new Solmu(10, 10);
        Solmu s3 = new Solmu(500, 500);
        double a = h.etsiReitti(s1, s3);
        assertEquals(2, h.haeReitti()[500][500]);
    }
}
