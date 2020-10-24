/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietorakenteet;

/**
 *
 * @author eemes
 */
public class Solmu implements Comparable<Solmu> {
    
    private int x;
    private int y;
    private double etaisyysAlkuun;
    private double etaisyysMaaliin;
    Solmu vanhempi;
    
    public Solmu(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Solmu(int x, int y, double etaisyys, Solmu vanhempi) {
        this.x = x;
        this.y = y;
        this.etaisyysAlkuun = etaisyys;
        this.vanhempi = vanhempi;
    }
       
    public int haeX() {
        return x;
    }
    
    public int haeY() {
        return y;
    }
    
    public void asetaX(int x) {
        this.x = x;
    }
    
    public void asetaY(int y) {
        this.y = y;
    }
    
    public Solmu haeVanhempi() {
        return this.vanhempi;
    }
    
    public double haeEtaisyysAlkuun() {
        return etaisyysAlkuun;
    }
    
    public double haeEtaisyysMaaliin() {
        return etaisyysMaaliin;
    }
    
    public void asetaEtaisyysAlkuun(double etaisyys) {
        this.etaisyysAlkuun = etaisyys;
    }
    
    public void asetaEtaisyysMaaliin(double etaisyys) {
        this.etaisyysMaaliin = etaisyys;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Solmu) {
            Solmu toinen = (Solmu) o;
            return (toinen.haeX() == x && toinen.haeY() == y);
        } 
        return false;
    }

    /**
     * Vertaa tata solmua parametrina annettuun solmuun.
     * Mikali annetun solmun etaisyys on suurempi, palauttaa 1.
     * Mikali etaisyydet ovat samat, palauttaa 0.
     * Muuten palauttaa -1.
     * @param solmu verrattava solmu.
     * @return 1, 0 tai -1 solmun etaisyyksien perusteella.
     */
    @Override
    public int compareTo(Solmu solmu) {
        if (solmu == null) {
            return 0;
        }
        double toinen = (solmu.haeEtaisyysAlkuun() + solmu.haeEtaisyysMaaliin());
        double tama = this.etaisyysAlkuun + this.etaisyysMaaliin;
        
        if (tama == toinen) {
            return 0;
        } else {
            return (tama < toinen) ? -1 : 1;
        }
    }
    
    
}
