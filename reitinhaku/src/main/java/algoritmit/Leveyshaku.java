/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmit;

import tietorakenteet.Jono;
import tietorakenteet.Solmu;

/**
 *
 * @author eemes
 */
public class Leveyshaku {
    
    Jono jono;
    int lukumäärä;
    char[][] taulukko;
    boolean[][] käyty;
    int[][] reitti;
    int pituus = 0;
    long aikaAlussa;
    long aikaLopussa;
    
    public Leveyshaku(char[][] taulukko) {
        this.taulukko = taulukko;
        this.käyty = new boolean[taulukko.length][taulukko[0].length];
        this.reitti = new int[taulukko.length][taulukko[0].length];
        this.jono = new Jono();
    }
    
    /**
     * Hakee leveyshakua käyttäen reitin Solmusta alku Solmuun loppu.
     * @param alku Lähtösolmu
     * @param loppu Maalisolmu
     * @return 1 mikäli reitti löytyy, -1 mikäli reittiä ei ole olemassa.
     */
    public double etsiReitti(Solmu alku, Solmu loppu) {
        this.aikaAlussa = System.nanoTime();
        this.lukumäärä = 0;
        this.jono.lisää(alku);
        
        // Käydään jonoa läpi, kunnes päästään maalisolmuun, tai solmut loppuvat
        while(!jono.onTyhjä()) {
            Solmu nykyinen = jono.otaEnsimmäinen();
            this.käyty[nykyinen.haeY()][nykyinen.haeX()] = true;
            this.reitti[nykyinen.haeY()][nykyinen.haeX()] = 1;
            
            // Reitti löydettiin, merkataan se ja lopetetaan läpikäynti
            if (nykyinen.equals(loppu)) {
                merkkaaReitti(nykyinen);
                this.aikaLopussa = System.nanoTime();
                return pituus;
            }
            
            // BFS toimii vain painottomissa verkoissa, eli ei kulmikkain siirtymistä
            // Vieraillaan siis kaikissa viereisissä solmuissa
            käsittele(nykyinen.haeX() + 1, nykyinen.haeY(), nykyinen);
            käsittele(nykyinen.haeX() - 1, nykyinen.haeY(), nykyinen);
            käsittele(nykyinen.haeX(), nykyinen.haeY() + 1, nykyinen);
            käsittele(nykyinen.haeX(), nykyinen.haeY() - 1, nykyinen);
            
        }
        return -1;
    }
    
    /**
     * Käy rekursiivisesti läpi reitin Solmut ja merkkaa ne taulukkoon
     * @param loppu 
     */
    private void merkkaaReitti(Solmu loppu) {
        pituus++;
        this.reitti[loppu.haeY()][loppu.haeX()] = 2;
        Solmu vanhempi = loppu.haeVanhempi();
        if(vanhempi != null) {
            merkkaaReitti(vanhempi);
        }
    }
    
    public long kulunutAika() {
        return this.aikaLopussa - this.aikaAlussa;
    }
    
    /**
     * Tarkastaa onko annettu kohta kelvollinen ja lisää sen jonoon.
     * @param x x-koordinaatti
     * @param y y-koordinaatti
     * @param nykyinen Solmu josta tutkittavaan kohtaaan siirryttiin.
     */
    private void käsittele(int x, int y, Solmu nykyinen) {
        if(x >= 0 && x < this.taulukko[0].length 
                && y >= 0 && y < this.taulukko.length 
                && !this.käyty[y][x]
                && this.taulukko[y][x] == '.') {
            this.jono.lisää(new Solmu(x, y, 0, nykyinen));
            this.käyty[y][x] = true;
        }
    }
    
    /**
     * Palauttaa löydetyn reitin.
     * Taulukossa on arvo 1, mikäli solussa on käyty ja 2 mikäli solu kuuluu reittiin.
     * @return Löydetty reitti taulukossa.
     */
    public int[][] haeReitti() {
        return this.reitti;
    }
}
