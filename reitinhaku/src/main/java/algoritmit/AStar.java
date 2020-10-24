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
    private double[][] etäisyydet;
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
        this.etäisyydet = new double[taulukko.length][taulukko[0].length];
        
        // Alustetaan etäisyydet
        for (int i = 0; i < taulukko[0].length; i++) {
            for (int j = 0; j < taulukko.length; j++) {
                etäisyydet[j][i] = 999999999;
            }
        }
    }
    
    
    /**
     * Etsii reitin parametreina annetusta solmusta toiseen.
     * @param alku Lähtösolmu
     * @param loppu Maalisolmu
     * @return Palauttaa reitin pituuden, -1 jos reittiä ei löydy
     */
    public double etsiReitti(Solmu alku, Solmu loppu) {
        this.aikaAlussa = System.nanoTime();
        alku.asetaEtäisyysAlkuun(0);
        this.etäisyydet[alku.haeY()][alku.haeX()] = 0;
        keko.lisää(alku);
        
        while(!keko.onTyhjä()) {
            Solmu nykyinen = keko.poistaPäällimmäinen();
            this.reitti[nykyinen.haeY()][nykyinen.haeX()] = 1;
            this.tilat[nykyinen.haeY()][nykyinen.haeX()] = true;
            
            // Reitti löydettiin, merkkaa se ja palauta pituus
            if(nykyinen.equals(loppu)) {
                merkkaaReitti(nykyinen);
                this.aikaLopussa = System.nanoTime();
                return this.etäisyydet[nykyinen.haeY()][nykyinen.haeX()];
            }
            // Tarkista viereiset solmut ja lisää kekoon.
            haeSeuraavat(nykyinen, loppu);
        }
        this.aikaLopussa = System.nanoTime();
        return -1;
    }
    
    /**
     * Kulkee reitin rekursiivisesti takaisin päin ja merkkaa sen taulukkoon
     * @param solmu
     */
    private void merkkaaReitti(Solmu solmu) {
        this.reitti[solmu.haeY()][solmu.haeX()] = 2;
        Solmu vanhempi = solmu.haeVanhempi();
        if(vanhempi != null) {
            merkkaaReitti(vanhempi);
        }
    }
    
    public long kulunutAika() {
        return this.aikaLopussa - this.aikaAlussa;
    }
    
    // Siirry kaikkiin viereisiin solmuihin
    private void haeSeuraavat(Solmu nykyinen, Solmu loppu) {
        
        käsittele(nykyinen.haeX() + 1, nykyinen.haeY(), nykyinen, loppu);
        käsittele(nykyinen.haeX() - 1, nykyinen.haeY(), nykyinen, loppu);
        käsittele(nykyinen.haeX(), nykyinen.haeY() + 1, nykyinen, loppu);
        käsittele(nykyinen.haeX(), nykyinen.haeY() - 1, nykyinen, loppu);
        
        // Mikäli kulmikkain liikkuminen on sallittu, käsitellään myös ne.
        if(this.kulmikkain) {
            käsittele(nykyinen.haeX() + 1, nykyinen.haeY() + 1, nykyinen, loppu);
            käsittele(nykyinen.haeX() + 1, nykyinen.haeY() - 1, nykyinen, loppu);
            käsittele(nykyinen.haeX() - 1, nykyinen.haeY() + 1, nykyinen, loppu);
            käsittele(nykyinen.haeX() - 1, nykyinen.haeY() - 1, nykyinen, loppu);
        }
    }
    
    /**
     * Tarkistaa tutkittavan solun kelvollisuuden, onko se lähempänä maalia, ja 
     * @param x
     * @param y
     * @param nykyinen
     * @param loppu 
     */
    private void käsittele(int x, int y, Solmu nykyinen, Solmu loppu) {
        double etäisyys = 1;
        
        // Onko kulmikkain liikkuminen sallittu?
        if (nykyinen.haeX() != x && nykyinen.haeY() != y && this.kulmikkain) {
            etäisyys = Laskin.SQRT2;
        }
        
        // Onko tutkittava solmu kartan sisällä, ja saako siihen liikkua?
        if(x >= 0 && x < this.taulukko[0].length 
                && y >= 0 && y < this.taulukko.length 
                && !this.tilat[y][x]
                && this.taulukko[y][x] == '.') {
            
            // Onko lyhyempää reittiä vielä löytynyt?
            if(nykyinen.haeEtäisyysAlkuun() + etäisyys < this.etäisyydet[y][x]) {
                double yhteensä = nykyinen.haeEtäisyysAlkuun() + etäisyys;
                this.etäisyydet[y][x] = yhteensä;
                Solmu seuraava = new Solmu(x, y, yhteensä, nykyinen);
                if (this.manhattan) {
                    seuraava.asetaEtäisyysMaaliin(Laskin.manhattanEtaisyys(x, y, loppu.haeX(), loppu.haeY()));
                } else {
                    seuraava.asetaEtäisyysMaaliin(Laskin.euklidinenEtaisyys(x, y, loppu.haeX(), loppu.haeY()));
                }
                keko.lisää(seuraava);
            }
        }
    }
    
    /**
     * Palauttaa löydetyn reitin
     * Taulukossa 1 merkitsee solua jossa on käyty haun aikana.
     * 2 merkitsee solua joka kuuluu reittiin.
     * @return taulukko jossa merkittynä käydyt solmut ja reitti
     */
    public int[][] haeReitti() {
        return this.reitti;
    }
    
}
