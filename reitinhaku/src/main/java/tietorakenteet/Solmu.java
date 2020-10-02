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
    private double etäisyysAlkuun;
    private double etäisyysMaaliin;
    Solmu vanhempi;
    
    public Solmu(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Solmu(int x, int y, double etäisyys, Solmu vanhempi) {
        this.x = x;
        this.y = y;
        this.etäisyysAlkuun = etäisyys;
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
    
    public double haeEtäisyysAlkuun() {
        return etäisyysAlkuun;
    }
    
    public double haeEtäisyysMaaliin() {
        return etäisyysMaaliin;
    }
    
    public void asetaEtäisyysAlkuun(double etäisyys) {
        this.etäisyysAlkuun = etäisyys;
    }
    
    public void asetaEtäisyysMaaliin(double etäisyys) {
        this.etäisyysMaaliin = etäisyys;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Solmu) {
            Solmu toinen = (Solmu)o;
            return (toinen.haeX() == x && toinen.haeY() == y);
        } return false;
    }

    @Override
    public int compareTo(Solmu solmu) {
        if(solmu == null) {
            return 0;
        }
        double toinen = (solmu.haeEtäisyysAlkuun() + solmu.haeEtäisyysMaaliin());
        double tämä = this.etäisyysAlkuun + this.etäisyysMaaliin;
        
        if(tämä == toinen) return 0;
        else return (tämä < toinen) ? -1 : 1;
    }
    
    
}
