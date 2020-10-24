/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietorakenteet;

/**
 * Jonorakenne kayttaen hyvaksi Listaa. 
 * Tarjoaa metodin ensimmaisen jasenen toistuvalle hakemiselle.
 * @author eemes
 */
public class Jono {
    
    private Lista lista;
    
    public Jono() {
        this.lista = new Lista();
    }
    
    public Solmu otaEnsimmainen() {
        Solmu solmu = this.lista.haeIndeksi(0);
        this.lista.poistaIndeksi(0);
        return solmu;
    }
    
    public void lisaa(Solmu solmu) {
        this.lista.lisaa(solmu);
    }
    
    public boolean onTyhja() {
        return this.lista.haeMaara() == 0;
    }
}
