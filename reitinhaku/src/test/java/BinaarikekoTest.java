/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tietorakenteet.Binaarikeko;
import tietorakenteet.Solmu;

/**
 *
 * @author eemes
 */
public class BinaarikekoTest {
    
    public BinaarikekoTest() {
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
    public void konstruktoriLuoKeon() {
        Binaarikeko keko = new Binaarikeko();
        assertEquals(true, keko instanceof Binaarikeko);
    }
    
    @Test
    public void otaPaallimmainenPalauttaaOikean() {
        Binaarikeko keko = new Binaarikeko();
        Solmu s1 = new Solmu(1, 2, 3, null);
        Solmu s2 = new Solmu(1, 2, 5, null);
        Solmu s3 = new Solmu(1, 2, 1, null);
        keko.lisaa(s1);
        keko.lisaa(s2);
        keko.lisaa(s3);
        assertEquals(s3, keko.otaPaallimmainen());
        assertEquals(s3, keko.poistaPaallimmainen());
        assertEquals(s1, keko.poistaPaallimmainen());
    }
    
    @Test
    public void kekoToimiiOikeinSuurena() {
        Binaarikeko keko = new Binaarikeko();
        Random rnd = new Random(1337);
        for (int i = 0; i < 100; i++) {
            Solmu s = new Solmu(i, i, rnd.nextInt(10000), null);
            keko.lisaa(s);
        }
        assertEquals(28, keko.poistaPaallimmainen().haeEtaisyysAlkuun(), 0.0001);
    }
    
    @Test
    public void onTyhjaToimii() {
        Binaarikeko keko = new Binaarikeko();
        assertEquals(true, keko.onTyhja());
        keko.lisaa(new Solmu(1, 2, 3, null));
        assertEquals(false, keko.onTyhja());
    }
}
