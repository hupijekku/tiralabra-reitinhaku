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
    int lukumaara;
    char[][] taulukko;
    boolean[][] kayty;
    int[][] reitti;
    int pituus = 0;
    long aikaAlussa;
    long aikaLopussa;
    
    public Leveyshaku(char[][] taulukko) {
        this.taulukko = taulukko;
        this.kayty = new boolean[taulukko.length][taulukko[0].length];
        this.reitti = new int[taulukko.length][taulukko[0].length];
        this.jono = new Jono();
    }
    
    /**
     * Hakee leveyshakua kayttaen reitin Solmusta alku Solmuun loppu.
     * @param alku Lahtösolmu
     * @param loppu Maalisolmu
     * @return 1 mikali reitti löytyy, -1 mikali reittia ei ole olemassa.
     */
    public double etsiReitti(Solmu alku, Solmu loppu) {
        this.aikaAlussa = System.nanoTime();
        this.lukumaara = 0;
        this.jono.lisaa(alku);
        
        // Kaydaan jonoa lapi, kunnes paastaan maalisolmuun, tai solmut loppuvat
        while (!jono.onTyhja()) {
            Solmu nykyinen = jono.otaEnsimmainen();
            this.kayty[nykyinen.haeY()][nykyinen.haeX()] = true;
            this.reitti[nykyinen.haeY()][nykyinen.haeX()] = 1;
            
            // Reitti löydettiin, merkataan se ja lopetetaan lapikaynti
            if (nykyinen.equals(loppu)) {
                merkkaaReitti(nykyinen);
                this.aikaLopussa = System.nanoTime();
                return pituus;
            }
            
            // BFS toimii vain painottomissa verkoissa, eli ei kulmikkain siirtymista
            // Vieraillaan siis kaikissa viereisissa solmuissa
            kasittele(nykyinen.haeX() + 1, nykyinen.haeY(), nykyinen);
            kasittele(nykyinen.haeX() - 1, nykyinen.haeY(), nykyinen);
            kasittele(nykyinen.haeX(), nykyinen.haeY() + 1, nykyinen);
            kasittele(nykyinen.haeX(), nykyinen.haeY() - 1, nykyinen);
            
        }
        return -1;
    }
    
    /**
     * Kay rekursiivisesti lapi reitin Solmut ja merkkaa ne taulukkoon
     * @param loppu 
     */
    private void merkkaaReitti(Solmu loppu) {
        pituus++;
        this.reitti[loppu.haeY()][loppu.haeX()] = 2;
        Solmu vanhempi = loppu.haeVanhempi();
        if (vanhempi != null) {
            merkkaaReitti(vanhempi);
        }
    }
    
    public long kulunutAika() {
        return this.aikaLopussa - this.aikaAlussa;
    }
    
    /**
     * Tarkastaa onko annettu kohta kelvollinen ja lisaa sen jonoon.
     * @param x x-koordinaatti
     * @param y y-koordinaatti
     * @param nykyinen Solmu josta tutkittavaan kohtaaan siirryttiin.
     */
    private void kasittele(int x, int y, Solmu nykyinen) {
        if (x >= 0 && x < this.taulukko[0].length 
                && y >= 0 && y < this.taulukko.length 
                && !this.kayty[y][x]
                && this.taulukko[y][x] == '.') {
            this.jono.lisaa(new Solmu(x, y, 0, nykyinen));
            this.kayty[y][x] = true;
        }
    }
    
    /**
     * Palauttaa löydetyn reitin.
     * Taulukossa on arvo 1, mikali solussa on kayty ja 2 mikali solu kuuluu reittiin.
     * @return Löydetty reitti taulukossa.
     */
    public int[][] haeReitti() {
        return this.reitti;
    }
}
