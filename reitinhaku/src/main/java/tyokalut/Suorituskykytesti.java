/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tyokalut;

import dao.Kartta;
import java.io.File;
import tietorakenteet.Solmu;
import tietorakenteet.Binaarikeko;
import tyokalut.Laskin;
import algoritmit.AStar;
import algoritmit.JPS;
import algoritmit.Leveyshaku;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author eemes
 */
public class Suorituskykytesti {
    
    private static long[][] ajat = new long[3][3];
    
    public static void suoritaTestit(boolean manhattan) {
        System.out.println("Suoritetaan testeja labyrintilla");
        labyrintti(manhattan);
        System.out.println("Suoritetaan testeja New Yorkilla");
        newyork(manhattan);
        System.out.println("Suoritetaan testeja satunnaisella");
        random(manhattan);
       
        System.out.println("Labyrintti (AStar): " + ajat[0][0] + " (ka. " + ajat[0][0] / 100 + ")");
        System.out.println("Labyrintti (JPS): " + ajat[0][1] + " (ka. " + ajat[0][1] / 100 + ")");
        System.out.println("Labyrintti (Leveyshaku): " + ajat[0][2] + " (ka. " + ajat[0][2] / 100 + ")");
        
        System.out.println("New York (AStar): " + ajat[1][0] + " (ka. " + ajat[1][0] / 100 + ")");
        System.out.println("New York (JPS): " + ajat[1][1] + " (ka. " + ajat[1][1] / 100 + ")");
        System.out.println("New York (Leveyshaku): " + ajat[1][2] + " (ka. " + ajat[1][2] / 100 + ")");
        
        System.out.println("Satunnainen (AStar): " + ajat[2][0] + " (ka. " + ajat[2][0] / 100 + ")");
        System.out.println("Satunnainen (JPS): " + ajat[2][1] + " (ka. " + ajat[2][1] / 100 + ")");
        System.out.println("Satunnainen (Leveyshaku): " + ajat[2][2] + " (ka. " + ajat[2][2] / 100 + ")");
        
        try {
            PrintWriter kirjoitin = new PrintWriter("suorituskykytestit.txt", "UTF-8");
            kirjoitin.println("Labyrintti (AStar): " + ajat[0][0] + " (ka. " + ajat[0][0] / 100 + ")");
            kirjoitin.println("Labyrintti (JPS): " + ajat[0][1] + " (ka. " + ajat[0][1] / 100 + ")");
            kirjoitin.println("Labyrintti (Leveyshaku): " + ajat[0][2] + " (ka. " + ajat[0][2] / 100 + ")");
        
            kirjoitin.println("New York (AStar): " + ajat[1][0] + " (ka. " + ajat[1][0] / 100 + ")");
            kirjoitin.println("New York (JPS): " + ajat[1][1] + " (ka. " + ajat[1][1] / 100 + ")");
            kirjoitin.println("New York (Leveyshaku): " + ajat[1][2] + " (ka. " + ajat[1][2] / 100 + ")");
        
            kirjoitin.println("Satunnainen (AStar): " + ajat[2][0] + " (ka. " + ajat[2][0] / 100 + ")");
            kirjoitin.println("Satunnainen (JPS): " + ajat[2][1] + " (ka. " + ajat[2][1] / 100 + ")");
            kirjoitin.println("Satunnainen (Leveyshaku): " + ajat[2][2] + " (ka. " + ajat[2][2] / 100 + ")");
            kirjoitin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    
    private static void labyrintti(boolean manhattan) {
        Random rnd = new Random(1337);
        Kartta labyrintti = new Kartta(new File("./kartat/maze512-2-0.map"));
        
        char[][] labTaulukko = labyrintti.luoTaulukko();
        
        int c = 0;
        
        while (c < 100) {
            int x1 = rnd.nextInt(510) + 1;
            int y1 = rnd.nextInt(510) + 1;
            int x2 = rnd.nextInt(510) + 1;
            int y2 = rnd.nextInt(510) + 1;
            
            Solmu alku = new Solmu(x1, y1);
            Solmu loppu = new Solmu(x2, y2);
            
            AStar hakuAStar = new AStar(labTaulukko, true, manhattan);
            double loytyi = hakuAStar.etsiReitti(alku, loppu);
            int[][] reitti = hakuAStar.haeReitti();
            long aika = hakuAStar.kulunutAika();
            
            if (loytyi > 0) {
                ajat[0][0] += (int) aika;
                c++;
            }
        }
        
        c = 0;
        
        while (c < 100) {
            int x1 = rnd.nextInt(510) + 1;
            int y1 = rnd.nextInt(510) + 1;
            int x2 = rnd.nextInt(510) + 1;
            int y2 = rnd.nextInt(510) + 1;
            
            Solmu alku = new Solmu(x1, y1);
            Solmu loppu = new Solmu(x2, y2);
            
            JPS hakuJPS = new JPS(labTaulukko, manhattan);
            double loytyi = hakuJPS.etsiReitti(alku, loppu);
            int[][] reitti = hakuJPS.haeReitti();
            long aika = hakuJPS.kulunutAika();
            
            if (loytyi > 0) {
                ajat[0][1] += (int) aika;
                c++;
            }
        }
        
        c = 0;
        
        while (c < 100) {
            int x1 = rnd.nextInt(510) + 1;
            int y1 = rnd.nextInt(510) + 1;
            int x2 = rnd.nextInt(510) + 1;
            int y2 = rnd.nextInt(510) + 1;
            
            Solmu alku = new Solmu(x1, y1);
            Solmu loppu = new Solmu(x2, y2);
            
            Leveyshaku hakuBFS = new Leveyshaku(labTaulukko);
            double loytyi = hakuBFS.etsiReitti(alku, loppu);
            int[][] reitti = hakuBFS.haeReitti();
            long aika = hakuBFS.kulunutAika();
            
            if (loytyi > 0) {
                ajat[0][2] += (int) aika;
                c++;
            }
        }
    }
    
    private static void newyork(boolean manhattan) {
        Random rnd = new Random(1337);
        Kartta ny = new Kartta(new File("./kartat/NewYork_0_512.map"));
        
        char[][] nyTaulukko = ny.luoTaulukko();
        
        int c = 0;
        
        while (c < 100) {
            int x1 = rnd.nextInt(510) + 1;
            int y1 = rnd.nextInt(510) + 1;
            int x2 = rnd.nextInt(510) + 1;
            int y2 = rnd.nextInt(510) + 1;
            
            Solmu alku = new Solmu(x1, y1);
            Solmu loppu = new Solmu(x2, y2);
            
            AStar hakuAStar = new AStar(nyTaulukko, true, manhattan);
            double loytyi = hakuAStar.etsiReitti(alku, loppu);
            int[][] reitti = hakuAStar.haeReitti();
            long aika = hakuAStar.kulunutAika();
            
            if (loytyi > 0) {
                ajat[1][0] += (int) aika;
                c++;
            }
        }
        
        c = 0;
        
        while (c < 100) {
            int x1 = rnd.nextInt(510) + 1;
            int y1 = rnd.nextInt(510) + 1;
            int x2 = rnd.nextInt(510) + 1;
            int y2 = rnd.nextInt(510) + 1;
            
            Solmu alku = new Solmu(x1, y1);
            Solmu loppu = new Solmu(x2, y2);
            
            JPS hakuJPS = new JPS(nyTaulukko, manhattan);
            double loytyi = hakuJPS.etsiReitti(alku, loppu);
            int[][] reitti = hakuJPS.haeReitti();
            long aika = hakuJPS.kulunutAika();
            
            if (loytyi > 0) {
                ajat[1][1] += (int) aika;
                c++;
            }
        }
        
        c = 0;
        
        while (c < 100) {
            int x1 = rnd.nextInt(510) + 1;
            int y1 = rnd.nextInt(510) + 1;
            int x2 = rnd.nextInt(510) + 1;
            int y2 = rnd.nextInt(510) + 1;
            
            Solmu alku = new Solmu(x1, y1);
            Solmu loppu = new Solmu(x2, y2);
            
            Leveyshaku hakuBFS = new Leveyshaku(nyTaulukko);
            double loytyi = hakuBFS.etsiReitti(alku, loppu);
            int[][] reitti = hakuBFS.haeReitti();
            long aika = hakuBFS.kulunutAika();
            
            if (loytyi > 0) {
                ajat[1][2] += (int) aika;
                c++;
            }
        }
    }
    
    private static void random(boolean manhattan) {
        Random rnd = new Random(1337);
        Kartta random = new Kartta(new File("./kartat/random512-15-3.map"));
        
        char[][] randomTaulukko = random.luoTaulukko();
        
        int c = 0;
        
        while (c < 100) {
            int x1 = rnd.nextInt(510) + 1;
            int y1 = rnd.nextInt(510) + 1;
            int x2 = rnd.nextInt(510) + 1;
            int y2 = rnd.nextInt(510) + 1;
            
            Solmu alku = new Solmu(x1, y1);
            Solmu loppu = new Solmu(x2, y2);
            
            AStar hakuAStar = new AStar(randomTaulukko, true, manhattan);
            double loytyi = hakuAStar.etsiReitti(alku, loppu);
            int[][] reitti = hakuAStar.haeReitti();
            long aika = hakuAStar.kulunutAika();
            
            if (loytyi > 0) {
                ajat[2][0] += (int) aika;
                c++;
            }
        }
        
        c = 0;
        
        while (c < 100) {
            int x1 = rnd.nextInt(510) + 1;
            int y1 = rnd.nextInt(510) + 1;
            int x2 = rnd.nextInt(510) + 1;
            int y2 = rnd.nextInt(510) + 1;
            
            Solmu alku = new Solmu(x1, y1);
            Solmu loppu = new Solmu(x2, y2);
            
            JPS hakuJPS = new JPS(randomTaulukko, manhattan);
            double loytyi = hakuJPS.etsiReitti(alku, loppu);
            int[][] reitti = hakuJPS.haeReitti();
            long aika = hakuJPS.kulunutAika();
            
            if (loytyi > 0) {
                ajat[2][1] += (int) aika;
                c++;
            }
        }
        
        c = 0;
        
        while (c < 100) {
            int x1 = rnd.nextInt(510) + 1;
            int y1 = rnd.nextInt(510) + 1;
            int x2 = rnd.nextInt(510) + 1;
            int y2 = rnd.nextInt(510) + 1;
            
            Solmu alku = new Solmu(x1, y1);
            Solmu loppu = new Solmu(x2, y2);
            
            Leveyshaku hakuBFS = new Leveyshaku(randomTaulukko);
            double loytyi = hakuBFS.etsiReitti(alku, loppu);
            int[][] reitti = hakuBFS.haeReitti();
            long aika = hakuBFS.kulunutAika();
            
            if (loytyi > 0) {
                ajat[2][2] += (int) aika;
                c++;
            }
        }
    }
}
