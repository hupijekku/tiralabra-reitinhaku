/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietorakenteet;

/**
 * Dynaaminen lista-rakenne
 * @author eemes
 */
public class Lista {
    
    private Solmu[] lista;
    private int lukumäärä;
    
    public Lista() {
        this.lista = new Solmu[50];
        this.lukumäärä = 0;
    }
    
    /**
     * Lisää solmun listaan ja kasvattaa listan kokoa tarvittaessa.
     * @param solmu 
     */
    public void lisää(Solmu solmu) {
        if (this.lukumäärä == this.lista.length) {
            Solmu[] uusi = new Solmu[this.lista.length * 2];
            for (int i = 0; i < this.lista.length; i++) {
                uusi[i] = this.lista[i];
            }
            this.lista = uusi;
        }
        this.lista[this.lukumäärä] = solmu;
        this.lukumäärä++;
    }
    
    /**
     * Palauttaa parametrina annetussa indeksissä sijaitsevan Solmu-olion
     * @param i indeksi
     * @return indeksissä sijaitseva Solmu
     */
    public Solmu haeIndeksi(int i) {
        if (i >= 0 && i < this.lukumäärä) {
            return this.lista[i];
        }
        return null;
    }
    
    /**
     * Poistaa annetussa indeksissä sijaitsevan Solmu-olion
     * @param i indeksi
     */
    public void poistaIndeksi(int i) {
        if (haeIndeksi(i) == null) {
            return;
        }
        for(int j = i; j < this.lukumäärä - 1; j++) {
            this.lista[j] = this.lista[j + 1];
        }
        this.lukumäärä--;
    }
    
    /**
     * Palauttaa listassa tällä hetkellä olevien Solmujen määrän
     * @return 
     */
    public int haeMäärä() {
        return this.lukumäärä;
    }
}
