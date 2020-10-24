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
import tietorakenteet.Jono;
import tietorakenteet.Solmu;

/**
 *
 * @author eemes
 */
public class JonoTest {
    
    public JonoTest() {
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
    public void konstruktoriLuoJonon() {
        Jono jono = new Jono();
        assertEquals(true, jono instanceof Jono);
    }
    
    @Test
    public void otaEnsimmainenPalauttaaEnsimmaisen() {
        Jono jono = new Jono();
        Solmu s1 = new Solmu(1, 2, 3, null);
        Solmu s2 = new Solmu(1, 2, 6, null);
        jono.lisaa(s1);
        jono.lisaa(s2);
        assertEquals(true, jono.otaEnsimmainen().equals(s1));
    }
    
    @Test
    public void onTyhjaPalauttaaOikeanArvon() {
        Jono jono = new Jono();
        assertEquals(true, jono.onTyhja());
        Solmu s1 = new Solmu(1, 2, 3, null);
        Solmu s2 = new Solmu(1, 2, 6, null);
        assertEquals(true, jono.onTyhja());
        jono.lisaa(s1);
        jono.lisaa(s2);
        assertEquals(false, jono.onTyhja());
    }
}
