/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import tietorakenteet.Binäärikeko;
import tietorakenteet.Solmu;

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
    private int monessakoKäyty;
    public Binäärikeko keko;
    // TODO: Fibonacci-keko?
    
    public AStar(char[][] taulukko, boolean kulmikkain) {
        this.kulmikkain = kulmikkain;
        this.taulukko = taulukko;
        keko = new Binäärikeko();
        this.monessakoKäyty = 0;
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
    
    // Etäisyys loppusolmuun
    // TODO: Math-kirjaston käyttö vissiin kielletty?
    private double laskeEtäisyys(int x, int y, Solmu maali) {
        return Math.sqrt(Math.pow(maali.haeX() - x, 2) + Math.pow(maali.haeY() - y, 2));
    }
    
    /**
     * Etsii reitin parametreina annetusta solmusta toiseen.
     * @param alku Lähtösolmu
     * @param loppu Maalisolmu
     * @return Palauttaa reitin pituuden, -1 jos reittiä ei löydy
     */
    public double etsiReitti(Solmu alku, Solmu loppu) {
        alku.asetaEtäisyysAlkuun(0);
        this.etäisyydet[alku.haeY()][alku.haeX()] = 0;
        keko.lisää(alku);
        
        while(!keko.onTyhjä()) {
            Solmu nykyinen = keko.poistaPäällimmäinen();
            this.reitti[nykyinen.haeY()][nykyinen.haeX()] = 1;
            this.tilat[nykyinen.haeY()][nykyinen.haeX()] = true;
            this.monessakoKäyty++;
            
            // Reitti löydettiin, merkkaa se ja palauta pituus
            if(nykyinen.equals(loppu)) {
                merkkaaReitti(nykyinen);
                return this.etäisyydet[nykyinen.haeY()][nykyinen.haeX()];
            }
            // Tarkista viereiset solmut ja lisää kekoon.
            haeSeuraavat(nykyinen, loppu);
        }
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
            etäisyys = Math.sqrt(2);
        }
        
        // Onko tutkittava solmu kartan sisällä, ja saako siihen liikkua?
        if(x >= 0 && x < this.taulukko[0].length 
                && y >= 0 && y < this.taulukko.length 
                && !this.tilat[y][x]
                && this.taulukko[y][x] == '.') {
            
            // Liikutaanko lähemmäs kohti maalia?
            if(nykyinen.haeEtäisyysAlkuun() + etäisyys < this.etäisyydet[y][x]) {
                double yhteensä = nykyinen.haeEtäisyysAlkuun() + etäisyys;
                this.etäisyydet[y][x] = yhteensä;
                Solmu seuraava = new Solmu(x, y, yhteensä, nykyinen);
                seuraava.asetaEtäisyysMaaliin(laskeEtäisyys(x, y, loppu));
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
    
    /**
     * Palauttaa haun aikana käytyjen solmujen lukumäärän
     * @return käydyt solmut
     */
    public int haeKäyty() {
        return this.monessakoKäyty;
    }
}
