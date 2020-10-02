/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import dao.Kartta;
import java.util.PriorityQueue;
import tietorakenteet.Solmu;

/**
 * AStar reitinhakualgoritmi
 * Käyttää toistaiseksi vielä Javan PriorityQueue-rakennetta.
 * @author eemes
 */
public class AStar {
    
    


    private char[][] taulukko;
    private int[][] reitti;
    private boolean[][] tilat;
    private double[][] etäisyydet;
    public boolean kulmikkain;
    private int monessakoKäyty;
    // TODO: Korvaa omalla toteutuksella
    public PriorityQueue<Solmu> keko;
    
    // Muuttujien asettaminen
    public AStar(char[][] taulukko, boolean kulmikkain) {
        this.kulmikkain = kulmikkain;
        this.taulukko = taulukko;
        keko = new PriorityQueue();
        this.monessakoKäyty = 0;
        this.tilat = new boolean[taulukko.length][taulukko[0].length];
        this.reitti = new int[taulukko.length][taulukko[0].length];
        this.etäisyydet = new double[taulukko.length][taulukko[0].length];
        
        for (int i = 0; i < taulukko[0].length; i++) {
            for (int j = 0; j < taulukko.length; j++) {
                etäisyydet[j][i] = 999999999;
                tilat[j][i] = false;
            }
        }
    }
    
    // Etäisyys loppusolmuun
    public double laskeEtäisyys(int x, int y, Solmu maali) {
        return Math.sqrt(Math.pow(maali.haeX() - x, 2) + Math.pow(maali.haeY() - y, 2));
    }
    
    // Käy solmut läpi
    public double etsiReitti(Solmu alku, Solmu loppu) {
        alku.asetaEtäisyysAlkuun(0);
        this.etäisyydet[alku.haeY()][alku.haeX()] = 0;
        keko.add(alku);
        
        while(!keko.isEmpty()) {
            Solmu nykyinen = keko.poll();
            this.reitti[nykyinen.haeY()][nykyinen.haeX()] = 1;
            this.tilat[nykyinen.haeY()][nykyinen.haeX()] = true;
            this.monessakoKäyty++;
            
            if(nykyinen.equals(loppu)) {
                merkkaaReitti(nykyinen);
                return this.etäisyydet[nykyinen.haeY()][nykyinen.haeX()];
            }
            haeSeuraavat(nykyinen, loppu);
        }
        return -1;
    }
    
    // Reitin löydyttyä, kulje se takaisinpäin merkaten käydyt solmut
    public void merkkaaReitti(Solmu solmu) {
        this.reitti[solmu.haeY()][solmu.haeX()] = 2;
        Solmu vanhempi = solmu.haeVanhempi();
        if(vanhempi != null) {
            merkkaaReitti(vanhempi);
        }
    }
    
    // Siirry kaikkiin viereisiin solmuihin
    public void haeSeuraavat(Solmu nykyinen, Solmu loppu) {
        
        käsittele(nykyinen.haeX() + 1, nykyinen.haeY(), nykyinen, loppu);
        käsittele(nykyinen.haeX() - 1, nykyinen.haeY(), nykyinen, loppu);
        käsittele(nykyinen.haeX(), nykyinen.haeY() + 1, nykyinen, loppu);
        käsittele(nykyinen.haeX(), nykyinen.haeY() - 1, nykyinen, loppu);
        
        if(this.kulmikkain) {
            käsittele(nykyinen.haeX() + 1, nykyinen.haeY() + 1, nykyinen, loppu);
            käsittele(nykyinen.haeX() + 1, nykyinen.haeY() - 1, nykyinen, loppu);
            käsittele(nykyinen.haeX() - 1, nykyinen.haeY() + 1, nykyinen, loppu);
            käsittele(nykyinen.haeX() - 1, nykyinen.haeY() - 1, nykyinen, loppu);
        }
    }
    
    // Ollaanko lähempänä maalia?
    public void käsittele(int x, int y, Solmu nykyinen, Solmu loppu) {
        double etäisyys = 1;
        if (nykyinen.haeX() != x && nykyinen.haeY() != y && this.kulmikkain) {
            etäisyys = Math.sqrt(2);
        }
        if(x >= 0 && x < this.taulukko[0].length 
                && y >= 0 && y < this.taulukko.length 
                && !this.tilat[y][x]
                && this.taulukko[y][x] == '.') {
            
            if(nykyinen.haeEtäisyysAlkuun() + etäisyys < this.etäisyydet[y][x]) {
                double yhteensä = nykyinen.haeEtäisyysAlkuun() + etäisyys;
                this.etäisyydet[y][x] = yhteensä;
                Solmu seuraava = new Solmu(x, y, yhteensä, nykyinen);
                seuraava.asetaEtäisyysMaaliin(laskeEtäisyys(x, y, loppu));
                keko.add(seuraava);
            }
        }
    }
    
    public int[][] haeReitti() {
        return this.reitti;
    }
    
    public int haeKäyty() {
        return this.monessakoKäyty;
    }
}
