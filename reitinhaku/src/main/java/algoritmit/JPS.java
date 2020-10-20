/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmit;

import tietorakenteet.Binäärikeko;
import tietorakenteet.Lista;
import tietorakenteet.Solmu;

/**
 *
 * @author eemes
 */
public class JPS {
    
    private char[][] taulukko;
    private int[][] reitti;
    private boolean[][] tilat;
    private double[][] etäisyydet;
    private int monessakoKäyty;
    public Binäärikeko keko;
    private Lista naapurit;
    boolean reittiLöytyi = false;
    
    public JPS(char[][] taulukko) {
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
    
    public double etsiReitti(Solmu alku, Solmu loppu) {
        this.keko.lisää(alku);
        this.etäisyydet[alku.haeY()][alku.haeX()] = 0;
        while(!this.keko.onTyhjä()) {
            Solmu nykyinen = this.keko.poistaPäällimmäinen();
            this.reitti[nykyinen.haeY()][nykyinen.haeX()] = 2;
            this.tilat[nykyinen.haeY()][nykyinen.haeX()] = true;
            this.monessakoKäyty++;
            if (nykyinen.equals(loppu)) {
                // Reitti löytyi
                System.out.println("Reitti!");
                merkkaaReitti(nykyinen);
                return this.etäisyydet[nykyinen.haeY()][nykyinen.haeX()];
            }
            haeSeuraavat(nykyinen, loppu);

        }
        
        return -1;
    }
    
    private double laskeEtäisyys(int x, int y, int vx, int vy) {
        return Math.sqrt(Math.pow(vx - x, 2) + Math.pow(vy - y, 2));
    }
    
    private void merkkaaReitti(Solmu solmu) {
        Solmu vanhempi = solmu.haeVanhempi();
        if(vanhempi == null) {
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
         for (int i = 0; i < this.naapurit.haeMäärä(); i++) {
             Solmu naapuri = this.naapurit.haeIndeksi(i);
             this.reitti[naapuri.haeY()][naapuri.haeX()] = 1;
             int[] hyppypiste = hyppää(naapuri.haeX(), naapuri.haeY(), nykyinen.haeX(), nykyinen.haeY(), loppu);
             if (hyppypiste[0] != -1 && hyppypiste[1] != -1) {
                 // Hyppypiste löytyi
                 
                 int y = hyppypiste[0];
                 int x = hyppypiste[1];
                 double etäisyys = laskeEtäisyys(x, y, nykyinen.haeX(), nykyinen.haeY()) + nykyinen.haeEtäisyysAlkuun();
                 if (this.etäisyydet[y][x] > etäisyys) {
                     this.etäisyydet[y][x] = etäisyys;
                     Solmu hyppySolmu = new Solmu(x, y, etäisyys, nykyinen);
                     hyppySolmu.asetaEtäisyysMaaliin(laskeEtäisyys(x, y, loppu.haeX(), loppu.haeY()));
                     this.reitti[y][x] = 1;
                     
                     this.keko.lisää(hyppySolmu);
                     if(reittiLöytyi) break;
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
        int dx = (x-vx)/Math.max(Math.abs(x-vx), 1);
        int dy = (y-vy)/Math.max(Math.abs(y-vy), 1);
        
        Lista karsitutNaapurit = new Lista();
        double etäisyys = 0;
        //Kulmikkain
        if (dx != 0 && dy != 0) {
            etäisyys = Math.sqrt(2);
            boolean löytyi = false;
            if (ruutuKelpaa(x, y+dy)) {
                karsitutNaapurit.lisää(new Solmu(x, y+dy, etäisyys, nykyinen));
                löytyi = true;
            }
            if (ruutuKelpaa(x+dx, y)) {
                karsitutNaapurit.lisää(new Solmu(x+dx, y, etäisyys, nykyinen));
                löytyi = true;
            }
            if (löytyi) {
                karsitutNaapurit.lisää(new Solmu(x+dx, y+dy, etäisyys, nykyinen));
            }
            // Pakotetut naapurit
            if (!ruutuKelpaa(x-dx, y) && ruutuKelpaa(x, y+dy)) {
                karsitutNaapurit.lisää(new Solmu(x-dx, y+dy, etäisyys, nykyinen));
            }
            if(!ruutuKelpaa(x, y-dy) && ruutuKelpaa(x+dx, y)) {
                karsitutNaapurit.lisää(new Solmu(x+dx, y-dy, etäisyys, nykyinen));
            }   
        }
        // Sivusuunnat
        else {
            etäisyys = 1;
            // Pystysuunta
            if (dx == 0) {
                if (ruutuKelpaa(x, y+dy)) {
                    karsitutNaapurit.lisää(new Solmu(x, y+dy, etäisyys, nykyinen));
                    if (!ruutuKelpaa(x+1, y)) {
                        karsitutNaapurit.lisää(new Solmu(x+1, y+dy, etäisyys, nykyinen));
                    }
                    if (!ruutuKelpaa(x-1, y)) {
                        karsitutNaapurit.lisää(new Solmu(x-1, y+dy, etäisyys, nykyinen));
                    }
                }
            }
            // Vaakasuunta
            else {
                if (ruutuKelpaa(x+dx, y)) {
                    karsitutNaapurit.lisää(new Solmu(x+dx, y, etäisyys, nykyinen));
                    if (!ruutuKelpaa(x, y+1)) {
                        karsitutNaapurit.lisää(new Solmu(x+dx, y+1, etäisyys, nykyinen));
                    }
                    if (!ruutuKelpaa(x, y-1)) {
                        karsitutNaapurit.lisää(new Solmu(x+dx, y-1, etäisyys, nykyinen));
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
                if(i != x || j != y) {
                    double etäisyys = 0;
                    if (i != x && j != y) {
                        etäisyys = nykyinen.haeEtäisyysAlkuun() + Math.sqrt(2);
                    } else {
                        etäisyys = nykyinen.haeEtäisyysAlkuun() + 1;
                    }
                    kaikkiNaapurit.lisää(new Solmu(i, j, etäisyys, nykyinen));
                }
            }
        }
        return kaikkiNaapurit;
    }
    
    public int[] hyppää(int x, int y, int vx, int vy, Solmu loppu) {
        int dx = (x-vx)/Math.max(Math.abs(x-vx),1);
        int dy = (y-vy)/Math.max(Math.abs(y-vy),1);

        int[] piste = {-1, -1};
        
        if (!ruutuKelpaa(x, y)) {
            return piste;
        }
        
        if (this.reitti[y][x] == 0) {
            this.reitti[y][x] = 3;
        }
        
        if(x == loppu.haeX() && y == loppu.haeY()) {
            this.reittiLöytyi = true;
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
        }
        // Sivuttain
        else {
            // Pystysuunta
            if (dy != 0) {
                if ((ruutuKelpaa(x + 1, y + dy) && !ruutuKelpaa(x + 1, y)) ||
                        (ruutuKelpaa(x - 1, y + dy) && !ruutuKelpaa(x - 1, y))) {
                    piste[0] = y;
                    piste[1] = x;
                    return piste;
                }
            }
            // Vaakasuunta
            else {
                if ((ruutuKelpaa(x + dx, y + 1) && !ruutuKelpaa(x, y + 1)) ||
                        (ruutuKelpaa(x + dx, y  - 1) && !ruutuKelpaa(x, y - 1))) {
                    piste[0] = y;
                    piste[1] = x;
                    return piste;
                }
            }
        }
        
        if (dx != 0 && dy != 0) {
            int[] hyppyX = hyppää(x + dx, y, x, y, loppu);
            int[] hyppyY = hyppää(x, y + dy, x, y, loppu);
            if (hyppyX[1] != -1 || hyppyY[1] != -1) {
                piste[0] = y;
                piste[1] = x;
                return piste;
            }
        }
        
        // Ei hyppypistettä, jatketaan rekursiivisesti
        if (ruutuKelpaa(x + dx, y) || ruutuKelpaa(x, y + dy)) {
            return hyppää(x + dx, y + dy, x, y, loppu);
        }
        
        // Tämä tie on tukossa
        return piste;
        
    }
    
    public boolean ruutuKelpaa(int x, int y) {
        // Rajat
        if (x < 0 || x >= this.taulukko[0].length || y < 0 || y >= this.taulukko.length) return false;
        // Seinät
        if (this.taulukko[y][x] != '.') return false;
        return true;
    }
}
