/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmit;

import tietorakenteet.Binaarikeko;
import tietorakenteet.Solmu;
import tyokalut.Laskin;

/**
 * AStar reitinhakualgoritmi
 * @author eemes
 */
public class AStar {

    private char[][] taulukko;
    private int[][] reitti;
    private boolean[][] tilat;
    private double[][] etaisyydet;
    public boolean kulmikkain;
    private boolean manhattan;
    public Binaarikeko keko;
    // TODO: Fibonacci-keko?
    
    long aikaAlussa;
    long aikaLopussa;
    
    public AStar(char[][] taulukko, boolean kulmikkain, boolean manhattan) {
        this.kulmikkain = kulmikkain;
        this.manhattan = manhattan;
        this.taulukko = taulukko;
        keko = new Binaarikeko();
        this.tilat = new boolean[taulukko.length][taulukko[0].length];
        this.reitti = new int[taulukko.length][taulukko[0].length];
        this.etaisyydet = new double[taulukko.length][taulukko[0].length];
        
        // Alustetaan etaisyydet
        for (int i = 0; i < taulukko[0].length; i++) {
            for (int j = 0; j < taulukko.length; j++) {
                etaisyydet[j][i] = 999999999;
            }
        }
    }
    
    
    /**
     * Etsii reitin parametreina annetusta solmusta toiseen.
     * @param alku Lahtosolmu
     * @param loppu Maalisolmu
     * @return Palauttaa reitin pituuden, -1 jos reittia ei loydy
     */
    public double etsiReitti(Solmu alku, Solmu loppu) {
        this.aikaAlussa = System.nanoTime();
        alku.asetaEtaisyysAlkuun(0);
        this.etaisyydet[alku.haeY()][alku.haeX()] = 0;
        keko.lisaa(alku);
        
        while (!keko.onTyhja()) {
            Solmu nykyinen = keko.poistaPaallimmainen();
            this.reitti[nykyinen.haeY()][nykyinen.haeX()] = 1;
            this.tilat[nykyinen.haeY()][nykyinen.haeX()] = true;
            
            // Reitti loydettiin, merkkaa se ja palauta pituus
            if (nykyinen.equals(loppu)) {
                merkkaaReitti(nykyinen);
                this.aikaLopussa = System.nanoTime();
                return this.etaisyydet[nykyinen.haeY()][nykyinen.haeX()];
            }
            // Tarkista viereiset solmut ja lisaa kekoon.
            haeSeuraavat(nykyinen, loppu);
        }
        this.aikaLopussa = System.nanoTime();
        return -1;
    }
    
    /**
     * Kulkee reitin rekursiivisesti takaisin pain ja merkkaa sen taulukkoon
     * @param solmu
     */
    private void merkkaaReitti(Solmu solmu) {
        this.reitti[solmu.haeY()][solmu.haeX()] = 2;
        Solmu vanhempi = solmu.haeVanhempi();
        if (vanhempi != null) {
            merkkaaReitti(vanhempi);
        }
    }
    
    public long kulunutAika() {
        return this.aikaLopussa - this.aikaAlussa;
    }
    
    // Siirry kaikkiin viereisiin solmuihin
    private void haeSeuraavat(Solmu nykyinen, Solmu loppu) {
        
        kasittele(nykyinen.haeX() + 1, nykyinen.haeY(), nykyinen, loppu);
        kasittele(nykyinen.haeX() - 1, nykyinen.haeY(), nykyinen, loppu);
        kasittele(nykyinen.haeX(), nykyinen.haeY() + 1, nykyinen, loppu);
        kasittele(nykyinen.haeX(), nykyinen.haeY() - 1, nykyinen, loppu);
        
        // Mikali kulmikkain liikkuminen on sallittu, kasitellaan myos ne.
        if (this.kulmikkain) {
            kasittele(nykyinen.haeX() + 1, nykyinen.haeY() + 1, nykyinen, loppu);
            kasittele(nykyinen.haeX() + 1, nykyinen.haeY() - 1, nykyinen, loppu);
            kasittele(nykyinen.haeX() - 1, nykyinen.haeY() + 1, nykyinen, loppu);
            kasittele(nykyinen.haeX() - 1, nykyinen.haeY() - 1, nykyinen, loppu);
        }
    }
    
    /**
     * Tarkistaa tutkittavan solun kelvollisuuden, onko se lahempana maalia, ja 
     * @param x
     * @param y
     * @param nykyinen
     * @param loppu 
     */
    private void kasittele(int x, int y, Solmu nykyinen, Solmu loppu) {
        double etaisyys = 1;
        
        // Onko kulmikkain liikkuminen sallittu?
        if (nykyinen.haeX() != x && nykyinen.haeY() != y && this.kulmikkain) {
            etaisyys = Laskin.SQRT2;
        }
        
        // Onko tutkittava solmu kartan sisalla, ja saako siihen liikkua?
        if (x >= 0 && x < this.taulukko[0].length 
                && y >= 0 && y < this.taulukko.length 
                && !this.tilat[y][x]
                && this.taulukko[y][x] == '.') {
            
            // Onko lyhyempaa reittia viela loytynyt?
            if (nykyinen.haeEtaisyysAlkuun() + etaisyys < this.etaisyydet[y][x]) {
                double yhteensa = nykyinen.haeEtaisyysAlkuun() + etaisyys;
                this.etaisyydet[y][x] = yhteensa;
                Solmu seuraava = new Solmu(x, y, yhteensa, nykyinen);
                if (this.manhattan) {
                    seuraava.asetaEtaisyysMaaliin(Laskin.manhattanEtaisyys(x, y, loppu.haeX(), loppu.haeY()));
                } else {
                    seuraava.asetaEtaisyysMaaliin(Laskin.euklidinenEtaisyys(x, y, loppu.haeX(), loppu.haeY()));
                }
                keko.lisaa(seuraava);
            }
        }
    }
    
    /**
     * Palauttaa loydetyn reitin
     * Taulukossa 1 merkitsee solua jossa on kayty haun aikana.
     * 2 merkitsee solua joka kuuluu reittiin.
     * @return taulukko jossa merkittyna kaydyt solmut ja reitti
     */
    public int[][] haeReitti() {
        return this.reitti;
    }
    
}
