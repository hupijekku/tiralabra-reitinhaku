/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dao.Kartta;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author eemes
 */
public class KarttaTest {
    
    public KarttaTest() {
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
    public void karttaLoytaaTiedoston() {
        Kartta kartta = new Kartta(new File("./kartat/arena2.map"));
        assertNotNull(kartta.haeLukija());
    }
    
    @Test
    public void nullJosTiedostoaEiOle() {
        Kartta kartta = new Kartta(new File("./kartat/eiole.map"));
        assertNull(kartta.haeLukija());
    }
    
    @Test
    public void luoTaulukonKartasta() {
        Kartta kartta = new Kartta(new File("./kartat/arena2.map"));
        char[][] taulukko = kartta.luoTaulukko();
        assertEquals('@', taulukko[0][0]);
    }
}
