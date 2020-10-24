/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmit;

import tietorakenteet.Binaarikeko;
import tietorakenteet.Lista;
import tietorakenteet.Solmu;
import tyokalut.Laskin;

/**
 *
 * @author eemes
 */
public class JPS {
    
    private char[][] taulukko;
    private int[][] reitti;
    private boolean[][] tilat;
    private double[][] etaisyydet;
    public Binaarikeko keko;
    private Lista naapurit;
    boolean reittiLoytyi = false;
    boolean manhattan;
    long aikaAlussa;
    long aikaLopussa;
    
    public JPS(char[][] taulukko, boolean manhattan) {
        this.taulukko = taulukko;
        this.manhattan = manhattan;
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
    
    public double etsiReitti(Solmu alku, Solmu loppu) {
        this.aikaAlussa = System.nanoTime();
        this.keko.lisaa(alku);
        this.etaisyydet[alku.haeY()][alku.haeX()] = 0;
        while (!this.keko.onTyhja()) {
            Solmu nykyinen = this.keko.poistaPaallimmainen();
            this.reitti[nykyinen.haeY()][nykyinen.haeX()] = 2;
            this.tilat[nykyinen.haeY()][nykyinen.haeX()] = true;
            if (nykyinen.equals(loppu)) {
                // Reitti loytyi
                merkkaaReitti(nykyinen);
                this.aikaLopussa = System.nanoTime();
                return this.etaisyydet[nykyinen.haeY()][nykyinen.haeX()];
            }
            haeSeuraavat(nykyinen, loppu);

        }
        
        return -1;
    }
    
    
    public long kulunutAika() {
        return this.aikaLopussa - this.aikaAlussa;
    }
    
    private void merkkaaReitti(Solmu solmu) {
        Solmu vanhempi = solmu.haeVanhempi();
        if (vanhempi == null) {
            this.reitti[solmu.haeY()][solmu.haeX()] = 2;
            
        } else {
            
            int x = solmu.haeX();
            int y = solmu.haeY();
            int vx = vanhempi.haeX();
            int vy = vanhempi.haeY();
            
            int pienempiX, suurempiX, pienempiY, suurempiY;
            pienempiX = x < vx ? x : vx;
            suurempiX = x > vx ? x : vx;
            pienempiY = y < vy ? y : vy;
            suurempiY = y > vy ? y : vy;
            
            if (x == vx) {
                for (int i = pienempiY; i <= suurempiY; i++) {
                    this.reitti[i][x] = 2;
                }
            } else if (y == vy) {
                for (int i = pienempiX; i <= suurempiX; i++) {
                    this.reitti[y][i] = 2;
                }
            } else {
                int kerroin = y < vy ? -1 : 1;
                for (int i = pienempiX, j = 0; i <= suurempiX; i++, j++) {
                    this.reitti[vy + j * kerroin][i] = 2;
                }
            }
            
            merkkaaReitti(vanhempi);
        }
    }
    
    public int[][] haeReitti() {
        return this.reitti;
    }
    
    public void haeSeuraavat(Solmu nykyinen, Solmu loppu) {
        this.naapurit = karsiNaapurit(nykyinen);
        for (int i = 0; i < this.naapurit.haeMaara(); i++) {
            Solmu naapuri = this.naapurit.haeIndeksi(i);
            this.reitti[naapuri.haeY()][naapuri.haeX()] = 3;
            int[] hyppypiste = hyppaa(naapuri.haeX(), naapuri.haeY(), nykyinen.haeX(), nykyinen.haeY(), loppu);
            if (hyppypiste[0] != -1 && hyppypiste[1] != -1) {
                // Hyppypiste loytyi
                 
                int y = hyppypiste[0];
                int x = hyppypiste[1];
                double etaisyys = Laskin.euklidinenEtaisyys(x, y, nykyinen.haeX(), nykyinen.haeY()) + nykyinen.haeEtaisyysAlkuun();
                if (this.etaisyydet[y][x] > etaisyys) {
                    this.etaisyydet[y][x] = etaisyys;
                    Solmu hyppySolmu = new Solmu(x, y, etaisyys, nykyinen);
                    if (manhattan) {
                        hyppySolmu.asetaEtaisyysMaaliin(Laskin.euklidinenEtaisyys(x, y, loppu.haeX(), loppu.haeY()));
                    } else {
                        hyppySolmu.asetaEtaisyysMaaliin(Laskin.manhattanEtaisyys(x, y, loppu.haeX(), loppu.haeY()));
                    }
                    this.reitti[y][x] = 3;
                    
                    this.keko.lisaa(hyppySolmu);
                    if (reittiLoytyi) {
                        break;
                    }
                }
            }
        }
    }

    public Lista karsiNaapurit(Solmu nykyinen) {
        Solmu vanhempi = nykyinen.haeVanhempi();
        if (vanhempi == null) {
            return kaikkiNaapurit(nykyinen);
        }
        int x = nykyinen.haeX();
        int y = nykyinen.haeY();
        int vx = vanhempi.haeX();
        int vy = vanhempi.haeY();
        int dx = (x - vx) / Laskin.maksimi(Laskin.itseisarvo(x - vx), 1);
        int dy = (y - vy) / Laskin.maksimi(Laskin.itseisarvo(y - vy), 1);
        
        Lista karsitutNaapurit = new Lista();
        double etaisyys = 0;
        //Kulmikkain
        if (dx != 0 && dy != 0) {
            etaisyys = Laskin.SQRT2;
            boolean loytyi = false;
            if (ruutuKelpaa(x, y + dy)) {
                karsitutNaapurit.lisaa(new Solmu(x, y + dy, etaisyys, nykyinen));
                loytyi = true;
            }
            if (ruutuKelpaa(x + dx, y)) {
                karsitutNaapurit.lisaa(new Solmu(x + dx, y, etaisyys, nykyinen));
                loytyi = true;
            }
            if (loytyi && ruutuKelpaa(x + dx, y + dy)) {
                karsitutNaapurit.lisaa(new Solmu(x + dx, y + dy, etaisyys, nykyinen));
            }
            // Pakotetut naapurit
            if (!ruutuKelpaa(x - dx, y) && ruutuKelpaa(x, y + dy) && ruutuKelpaa(x - dx, y + dy)) {
                karsitutNaapurit.lisaa(new Solmu(x - dx, y + dy, etaisyys, nykyinen));
            }
            if (!ruutuKelpaa(x, y - dy) && ruutuKelpaa(x + dx, y) && ruutuKelpaa(x + dx, y - dy)) {
                karsitutNaapurit.lisaa(new Solmu(x + dx, y - dy, etaisyys, nykyinen));
            }   
        // Sivusuunnat
        } else {
            etaisyys = 1;
            // Pystysuunta
            if (dx == 0) {
                if (ruutuKelpaa(x, y + dy)) {
                    karsitutNaapurit.lisaa(new Solmu(x, y + dy, etaisyys, nykyinen));
                    if (!ruutuKelpaa(x + 1, y) && ruutuKelpaa(x + 1, y + dy)) {
                        karsitutNaapurit.lisaa(new Solmu(x + 1, y + dy, etaisyys, nykyinen));
                    }
                    if (!ruutuKelpaa(x - 1, y) && ruutuKelpaa(x - 1, y + dy)) {
                        karsitutNaapurit.lisaa(new Solmu(x - 1, y + dy, etaisyys, nykyinen));
                    }
                }
            // Vaakasuunta
            } else {
                if (ruutuKelpaa(x + dx, y)) {
                    karsitutNaapurit.lisaa(new Solmu(x + dx, y, etaisyys, nykyinen));
                    if (!ruutuKelpaa(x, y + 1) && ruutuKelpaa(x + dx, y + 1)) {
                        karsitutNaapurit.lisaa(new Solmu(x + dx, y + 1, etaisyys, nykyinen));
                    }
                    if (!ruutuKelpaa(x, y - 1) && ruutuKelpaa(x + dx, y - 1)) {
                        karsitutNaapurit.lisaa(new Solmu(x + dx, y - 1, etaisyys, nykyinen));
                    }
                }
            }
        }
        return karsitutNaapurit;
    }
    
    public Lista kaikkiNaapurit(Solmu nykyinen) {
        int x = nykyinen.haeX();
        int y = nykyinen.haeY();
        Lista kaikkiNaapurit = new Lista();
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i != x || j != y) {
                    double etaisyys = 0;
                    if (i != x && j != y) {
                        etaisyys = nykyinen.haeEtaisyysAlkuun() + Laskin.SQRT2;
                    } else {
                        etaisyys = nykyinen.haeEtaisyysAlkuun() + 1;
                    }
                    kaikkiNaapurit.lisaa(new Solmu(i, j, etaisyys, nykyinen));
                }
            }
        }
        return kaikkiNaapurit;
    }
    
    public int[] hyppaa(int x, int y, int vx, int vy, Solmu loppu) {
        int dx = (x - vx) / Laskin.maksimi(Laskin.itseisarvo(x - vx), 1);
        int dy = (y - vy) / Laskin.maksimi(Laskin.itseisarvo(y - vy), 1);

        int[] piste = {-1, -1};
        
        if (!ruutuKelpaa(x, y)) {
            return piste;
        }
        
        if (this.reitti[y][x] == 0) {
            this.reitti[y][x] = 1;
        }
        
        if (x == loppu.haeX() && y == loppu.haeY()) {
            this.reittiLoytyi = true;
            piste[0] = y;
            piste[1] = x;
            return piste;
        }
        
        // Kulmikkain
        if (dx != 0 && dy != 0) {
            if ((ruutuKelpaa(x - dx, y + dy) && !ruutuKelpaa(x - dx, y)) ||
                    (ruutuKelpaa(x + dx, y - dy) && !ruutuKelpaa(x, y - dy))) {
                piste[0] = y;
                piste[1] = x;
                return piste;
            }
        // Sivuttain
        } else {
            // Pystysuunta
            if (dy != 0) {
                if ((ruutuKelpaa(x + 1, y + dy) && !ruutuKelpaa(x + 1, y)) ||
                        (ruutuKelpaa(x - 1, y + dy) && !ruutuKelpaa(x - 1, y))) {
                    piste[0] = y;
                    piste[1] = x;
                    return piste;
                }
            // Vaakasuunta
            } else {
                if ((ruutuKelpaa(x + dx, y + 1) && !ruutuKelpaa(x, y + 1)) ||
                        (ruutuKelpaa(x + dx, y  - 1) && !ruutuKelpaa(x, y - 1))) {
                    piste[0] = y;
                    piste[1] = x;
                    return piste;
                }
            }
        }
        
        if (dx != 0 && dy != 0) {
            int[] hyppyX = hyppaa(x + dx, y, x, y, loppu);
            int[] hyppyY = hyppaa(x, y + dy, x, y, loppu);
            if (hyppyX[1] != -1 || hyppyY[1] != -1) {
                piste[0] = y;
                piste[1] = x;
                return piste;
            }
        }
        
        // Ei hyppypistetta, jatketaan rekursiivisesti
        if (ruutuKelpaa(x + dx, y) || ruutuKelpaa(x, y + dy)) {
            return hyppaa(x + dx, y + dy, x, y, loppu);
        }
        
        // Tama tie on tukossa
        return piste;
        
    }
    
    public boolean ruutuKelpaa(int x, int y) {
        // Rajat
        if (x < 0 || x >= this.taulukko[0].length || y < 0 || y >= this.taulukko.length) {
            return false;
        }
        // Seinat
        if (this.taulukko[y][x] != '.') {
            return false;
        }
        return true;
    }
}
