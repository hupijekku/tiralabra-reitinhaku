/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import algoritmit.JPS;
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
public class JPSTest {
    
    public JPSTest() {
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
    public void konstruktoriAsettaaArvot() {
        Kartta labyrintti = new Kartta(new File("./kartat/maze512-2-0.map"));
        JPS jps = new JPS(labyrintti.luoTaulukko(), false);
        assertEquals(true, jps instanceof JPS);
    }
    
    @Test
    public void etsiReittiPalauttaaOikein() {
        Kartta labyrintti = new Kartta(new File("./kartat/maze512-2-0.map"));
        char[][] taulukko = labyrintti.luoTaulukko();
        JPS jps = new JPS(taulukko, false);
        Solmu s1 = new Solmu(10, 10);
        Solmu s2 = new Solmu(510, 510);
        Solmu s3 = new Solmu(500, 500);
        assertEquals(-1, jps.etsiReitti(s1, s2), 0.0001);
        jps = new JPS(taulukko, false);
        assertEquals(2847, jps.etsiReitti(s1, s3), 0.0001);
    }
    
    @Test
    public void kulunutAikaPalauttaaNanoSekuntteja() {
        Kartta labyrintti = new Kartta(new File("./kartat/maze512-2-0.map"));
        char[][] taulukko = labyrintti.luoTaulukko();
        JPS jps = new JPS(taulukko, false);
        Solmu s1 = new Solmu(10, 10);
        Solmu s3 = new Solmu(500, 500);
        double a = jps.etsiReitti(s1, s3);
        long aika = jps.kulunutAika();
        assertEquals(true, aika > 100000);
    }
    
    @Test
    public void haeReittiPalauttaaTaulukon() {
        Kartta labyrintti = new Kartta(new File("./kartat/maze512-2-0.map"));
        char[][] taulukko = labyrintti.luoTaulukko();
        JPS jps = new JPS(taulukko, false);
        Solmu s1 = new Solmu(10, 10);
        Solmu s3 = new Solmu(500, 500);
        double a = jps.etsiReitti(s1, s3);
        assertEquals(2, jps.haeReitti()[500][500]);
    }
}
