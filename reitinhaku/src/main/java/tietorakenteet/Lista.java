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
    private int lukumaara;
    
    public Lista() {
        this.lista = new Solmu[50];
        this.lukumaara = 0;
    }
    
    /**
     * Lisaa solmun listaan ja kasvattaa listan kokoa tarvittaessa.
     * @param solmu 
     */
    public void lisaa(Solmu solmu) {
        if (this.lukumaara == this.lista.length) {
            Solmu[] uusi = new Solmu[this.lista.length * 2];
            for (int i = 0; i < this.lista.length; i++) {
                uusi[i] = this.lista[i];
            }
            this.lista = uusi;
        }
        this.lista[this.lukumaara] = solmu;
        this.lukumaara++;
    }
    
    /**
     * Palauttaa parametrina annetussa indeksissa sijaitsevan Solmu-olion
     * @param i indeksi
     * @return indeksissa sijaitseva Solmu
     */
    public Solmu haeIndeksi(int i) {
        if (i >= 0 && i < this.lukumaara) {
            return this.lista[i];
        }
        return null;
    }
    
    /**
     * Poistaa annetussa indeksissa sijaitsevan Solmu-olion
     * @param i indeksi
     */
    public void poistaIndeksi(int i) {
        if (haeIndeksi(i) == null) {
            return;
        }
        for (int j = i; j < this.lukumaara - 1; j++) {
            this.lista[j] = this.lista[j + 1];
        }
        this.lukumaara--;
    }
    
    /**
     * Palauttaa listassa talla hetkella olevien Solmujen maaran
     * @return 
     */
    public int haeMaara() {
        return this.lukumaara;
    }
}
