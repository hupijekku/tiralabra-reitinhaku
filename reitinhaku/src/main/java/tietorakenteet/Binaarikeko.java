/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietorakenteet;

/**
 * Minimikeko toteutettuna binaaripuuna.
 * Sailöö Solmu-olioita. Pienimman etaisyyden solmu sijaitsee indeksissa 0.
 * @author eemes
 */
public class Binaarikeko {
    
    private Solmu[] keko;
    private int lukumaara;
    
    public Binaarikeko() {
        this.lukumaara = 0;
        this.keko = new Solmu[50];
    }
    
    /**
     * Lisaa parametrina annetun ruudun kekoon.
     * @param solmu 
     */
    public void lisaa(Solmu solmu) {
        // Jos keko on taynna, laajennetaan sita.
        if (this.lukumaara == this.keko.length) {
            Solmu[] uusi = new Solmu[this.keko.length * 2];
            for (int i = 0; i < this.keko.length; i++) {
                uusi[i] = this.keko[i];
            }
            this.keko = uusi;
        }
        
        this.keko[this.lukumaara] = solmu;
        int indeksi = this.lukumaara++;
        
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
    
    // Korjaa rekursiivisesti puun rakenteen niin, etta jokainen solmu on pienempi kuin sen lapset.
    private void korjaaRakenne(int indeksi) {
        int vasen = vasenLapsi(indeksi);
        int oikea = oikeaLapsi(indeksi);
        
        int pienin = indeksi;
        
        if (vasen < this.lukumaara && this.keko[vasen].compareTo(this.keko[pienin]) == -1) {
            pienin = vasen;
        }
        if (oikea < this.lukumaara && this.keko[oikea].compareTo(this.keko[pienin]) == -1) {
            pienin = oikea;
        }
        if (pienin != indeksi) {
            vaihda(indeksi, pienin);
            korjaaRakenne(pienin);
        }
    }
    
    /**
     * Palauttaa keon paallimmaisen, eli pienimman etaisyyden Solmu-olion
     * poistamatta sita kuitenkaan keosta.
     * @return 
     */
    public Solmu otaPaallimmainen() {
        return this.keko[0];
    }
    /**
     * Palauttaa keon paallimmaisen, eli pienimman etaisyyden Solmu-olion
     * ja poistaa sen keosta.
     * @return 
     */
    public Solmu poistaPaallimmainen() {
        Solmu juuri = this.keko[0];
        this.keko[0] = this.keko[--this.lukumaara];
        korjaaRakenne(0);
        return juuri;
    }
    
    /**
     * Palauttaa true, jos keko on tyhja, muulloin false.
     * @return 
     */
    public boolean onTyhja() {
        return this.lukumaara <= 0;
    }
}
