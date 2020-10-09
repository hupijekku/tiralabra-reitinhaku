/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietorakenteet;

/**
 * Minimikeko toteutettuna binääripuuna.
 * Säilöö Solmu-olioita. Pienimmän etäisyyden solmu sijaitsee indeksissä 0.
 * @author eemes
 */
public class Binäärikeko {
    
    private Solmu[] keko;
    private int lukumäärä;
    
    public Binäärikeko() {
        this.lukumäärä = 0;
        this.keko = new Solmu[50];
    }
    
    /**
     * Lisää parametrina annetun ruudun kekoon.
     * @param solmu 
     */
    public void lisää(Solmu solmu) {
        // Jos keko on täynnä, laajennetaan sitä.
        if (this.lukumäärä == this.keko.length) {
            Solmu[] uusi = new Solmu[this.keko.length * 2];
            for (int i = 0; i < this.keko.length; i++) {
                uusi[i] = this.keko[i];
            }
            this.keko = uusi;
        }
        
        this.keko[this.lukumäärä] = solmu;
        int indeksi = this.lukumäärä++;
        
        // Sijoitetaan solmu ja vaihdetaan solmujen paikkoja kunnes solmu on oikealla paikalla.
        while (indeksi > 0 && this.keko[indeksi].compareTo(this.keko[vanhempi(indeksi)]) == -1) {
            vaihda(indeksi, vanhempi(indeksi));
            indeksi = vanhempi(indeksi);
        }
    }
    
    private int vanhempi(int indeksi) {
        return (indeksi - 1) / 2;
    }
    
    private int vasenLapsi(int indeksi) {
        return indeksi * 2 + 1;
    }
    
    private int oikeaLapsi(int indeksi) {
        return indeksi * 2 + 2;
    }
    
    // Vaihtaa kahden solmun paikkaa puussa
    private void vaihda(int indeksiYksi, int indeksiKaksi) {
        Solmu vaihto = this.keko[indeksiYksi];
        this.keko[indeksiYksi] = this.keko[indeksiKaksi];
        this.keko[indeksiKaksi] = vaihto;
    }
    
    // Korjaa rekursiivisesti puun rakenteen niin, että jokainen solmu on pienempi kuin sen lapset.
    private void korjaaRakenne(int indeksi) {
        int vasen = vasenLapsi(indeksi);
        int oikea = oikeaLapsi(indeksi);
        
        int pienin = indeksi;
        
        if (vasen < this.lukumäärä && this.keko[vasen].compareTo(this.keko[pienin]) == -1) {
            pienin = vasen;
        }
        if (oikea < this.lukumäärä && this.keko[oikea].compareTo(this.keko[pienin]) == -1) {
            pienin = oikea;
        }
        if (pienin != indeksi) {
            vaihda(indeksi, pienin);
            korjaaRakenne(pienin);
        }
    }
    
    /**
     * Palauttaa keon päällimmäisen, eli pienimmän etäisyyden Solmu-olion
     * poistamatta sitä kuitenkaan keosta.
     * @return 
     */
    public Solmu otaPäällimmäinen() {
        return this.keko[0];
    }
    /**
     * Palauttaa keon päällimmäisen, eli pienimmän etäisyyden Solmu-olion
     * ja poistaa sen keosta.
     * @return 
     */
    public Solmu poistaPäällimmäinen() {
        Solmu juuri = this.keko[0];
        this.keko[0] = this.keko[--this.lukumäärä];
        korjaaRakenne(0);
        return juuri;
    }
    
    /**
     * Palauttaa true, jos keko on tyhjä, muulloin false.
     * @return 
     */
    public boolean onTyhjä() {
        return this.lukumäärä <= 0;
    }
}
